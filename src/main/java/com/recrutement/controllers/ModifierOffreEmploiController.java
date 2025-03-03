package com.recrutement.controllers;

import com.recrutement.models.offreemploi;
import com.recrutement.models.statut;
import com.recrutement.services.OffreEmploiServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent; // Correction de l'importation

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ModifierOffreEmploiController implements Initializable {

    @FXML
    private DatePicker datePickerPublication; // Assurez-vous que vous utilisez cette variable pour le DatePicker

    @FXML
    private TextField txtTitre;

    @FXML
    private TextField txtDescription;

    @FXML
    private ComboBox<statut> cmbStatut;

    @FXML
    private DatePicker datePicker; // Si vous en avez besoin, vous pouvez l'utiliser aussi

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnAnnuler;

    private offreemploi offreToModify;
    private OffreEmploiServices offreEmploiServices = new OffreEmploiServices(); // Injection du service

    public void setOffreEmploiServices(OffreEmploiServices offreEmploiServices) {
        this.offreEmploiServices = offreEmploiServices;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Remplir la ComboBox avec les valeurs de l'énumération Statut
        cmbStatut.setItems(javafx.collections.FXCollections.observableArrayList(statut.values()));

        if (datePickerPublication != null) {
            // Par exemple, vous pouvez définir une date par défaut si nécessaire
            datePickerPublication.setValue(LocalDate.now());
        }
    }

    public void setOffreToModify(offreemploi offre) {
        this.offreToModify = offre;
        txtTitre.setText(offre.getTitre());
        txtDescription.setText(offre.getDescription());
        cmbStatut.setValue(offre.getStatut());

        if (offre.getDate_publication() != null) {
            // Convertir la date uniquement si nécessaire (vérifiez si c'est déjà un LocalDate)
            LocalDate localDate = offre.getDate_publication().toLocalDate();
            datePickerPublication.setValue(localDate);
        }
    }

    @FXML
    void handleModifier(ActionEvent event) {
        String titre = txtTitre.getText();
        String description = txtDescription.getText();
        LocalDate selectedDate = datePickerPublication.getValue();
        statut Statut = cmbStatut.getValue();

        if (titre.isEmpty() || description.isEmpty() || selectedDate == null || Statut == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return;
        }

        offreToModify.setTitre(titre);
        offreToModify.setDescription(description);
        offreToModify.setDate_publication(java.sql.Date.valueOf(selectedDate));
        offreToModify.setStatut(Statut);

        try {
            offreEmploiServices.modifier(offreToModify);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succès");
            success.setContentText("Offre modifiée avec succès !");
            success.show();

            // Fermer la fenêtre de modification
            Stage stage = (Stage) btnModifier.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur");
            error.setContentText("Une erreur s'est produite lors de la modification de l'offre.");
            error.show();
            e.printStackTrace();
        }
    }

    @FXML
    void handleAnnuler(ActionEvent event) {
        // Fermer la fenêtre de modification sans sauvegarder
        Stage stage = (Stage) btnAnnuler.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheCV.fxml"));
        Parent root = loader.load();

        // Récupérer la fenêtre actuelle et modifier la scène
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
