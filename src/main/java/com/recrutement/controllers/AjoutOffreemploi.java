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
    public void initialize() {
        // Populate ComboBox with ENUM values for Statut
        cmbStatut.setItems(FXCollections.observableArrayList(statut.values()));
        datePicker.setValue(LocalDate.now()); // Set default date to today
    }

    @FXML
    void AddOffreemploi(ActionEvent event) throws IOException {
        String titre = txtTitre.getText();
        String description = txtDescription.getText();
        LocalDate selectedDate = datePicker.getValue();

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

        // Charger la nouvelle scène correctement
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur de la nouvelle scène
        AfficheOffreemploi ap = loader.getController();
        ap.setTxtTitre(titre);
        ap.setTxtDescription(description);
        ap.setCmbStatut(Statut);
        ap.setDatePublication(selectedDate);  // Ajoute la date

        // Changer la scène proprement
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void PasserModifierOffreemploi(ActionEvent event) throws IOException {
        // Charger le fichier FXML de la scène de modification
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierOffreemploi.fxml"));
        Parent root = loader.load();

        // Récupérer le contrôleur de la scène de modification
        ModifierOffreemploi controller = loader.getController();

        // Créer une nouvelle instance d'offreemploi avec les données actuelles
        offreemploi offre = new offreemploi(
                txtTitre.getText(),
                txtDescription.getText(),
                Date.valueOf(datePicker.getValue()), // Conversion de LocalDate à java.sql.Date
                cmbStatut.getValue()
        );

        // Passer l'objet offre au contrôleur de la scène de modification
        controller.setOffre(offre);

        // Récupérer la scène actuelle
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        // Créer une nouvelle scène avec le contenu chargé
        Scene scene = new Scene(root);

        // Définir la nouvelle scène sur le stage
        stage.setScene(scene);

        // Afficher la nouvelle scène
        stage.show();
    }



    @FXML
    void AnnulerAjout(ActionEvent event) {
        txtTitre.clear();
        txtDescription.clear();
        cmbStatut.getSelectionModel().clearSelection();
    }
}
