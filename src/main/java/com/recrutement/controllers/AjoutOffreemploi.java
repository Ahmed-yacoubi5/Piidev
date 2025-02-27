package com.recrutement.controllers;

import com.recrutement.models.statut;
import com.recrutement.models.offreemploi;
import com.recrutement.services.OffreEmploiServices;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AjoutOffreemploi {

    @FXML
    private TextField txtTitre;

    @FXML
    private TextField txtDescription;

    @FXML
    private ComboBox<statut> cmbStatut;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnAjouter, btnAnnuler;
    @FXML
    private Button btnSupprimer;

    @FXML
    public void initialize() {
        // Remplir la ComboBox avec les valeurs de l'énumération Statut
        cmbStatut.setItems(FXCollections.observableArrayList(statut.values()));
        datePicker.setValue(LocalDate.now()); // Définir la date par défaut à aujourd'hui
    }

    @FXML
    void AddOffreemploi(ActionEvent event) throws IOException {
        String titre = txtTitre.getText();
        String description = txtDescription.getText();
        LocalDate selectedDate = datePicker.getValue();

        // Vérifier si tous les champs sont remplis
        if (titre.isEmpty() || description.isEmpty() || selectedDate == null || cmbStatut.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return;
        }

        statut Statut = cmbStatut.getValue();
        OffreEmploiServices ms = new OffreEmploiServices();
        offreemploi newOffre = new offreemploi(titre, description, Date.valueOf(selectedDate), Statut);

        // Ajouter l'offre à la base de données
        ms.ajouter(newOffre);

        // Charger la nouvelle scène
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();

        // Passer la liste des offres au contrôleur de la nouvelle scène
        AfficheOffreemploi controller = loader.getController();
        controller.setOffre(ms.getAllOffres()); // Passer la liste des offres

        // Changer de scène
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void afficherOffres(ActionEvent event) throws IOException {
        // Récupérer toutes les offres d'emploi depuis la base de données
        OffreEmploiServices service = new OffreEmploiServices();
        List<offreemploi> offres = service.getAllOffres(); // Récupérer la liste des offres

        // Charger le fichier FXML de la scène qui affiche les résultats
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();

        // Passer les données (les offres d'emploi) au contrôleur de la nouvelle scène
        AfficheOffreemploi controller = loader.getController();
        controller.setOffre(offres); // Passer la liste des offres au contrôleur

        // Créer une nouvelle scène et l'afficher
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void AnnulerAjout(ActionEvent event) {
        txtTitre.clear();
        txtDescription.clear();
        cmbStatut.getSelectionModel().clearSelection();
    }
    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
        Parent root = loader.load();

        // Récupérer la fenêtre actuelle et modifier la scène
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    void handleSupprimer(ActionEvent event) {
        // Vérifier si le champ titre est vide
        String titre = txtTitre.getText();

        if (titre.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez entrer le titre de l'offre à supprimer.");
            alert.show();
            return;
        }

        // Demander confirmation avant suppression
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setContentText("Voulez-vous vraiment supprimer cette offre ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                OffreEmploiServices ms = new OffreEmploiServices();
                boolean deleted = ms.supprimerParTitre(titre);  // Assurez-vous que cette méthode existe

                if (deleted) {
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setTitle("Succès");
                    success.setContentText("Offre supprimée avec succès !");
                    success.show();

                    // Nettoyer les champs après suppression
                    txtTitre.clear();
                    txtDescription.clear();
                    cmbStatut.getSelectionModel().clearSelection();
                } else {
                    Alert failure = new Alert(Alert.AlertType.ERROR);
                    failure.setTitle("Échec");
                    failure.setContentText("Échec de la suppression. Vérifiez si l'offre existe.");
                    failure.show();
                }
            }
        });
    }
}