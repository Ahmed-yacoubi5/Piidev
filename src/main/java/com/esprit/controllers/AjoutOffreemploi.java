// AjoutOffreemploi.java
package com.esprit.controllers;

import com.esprit.models.offreemploi;
import com.esprit.models.statut;
import com.esprit.services.OffreEmploiServices;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AjoutOffreemploi {

    @FXML private TextField txtTitre;
    @FXML private TextField txtDescription;
    @FXML private TextField txtAdresse;
    @FXML private ComboBox<statut> cmbStatut;
    @FXML private DatePicker datePicker;


    @FXML
    public void initialize() {
        cmbStatut.setItems(FXCollections.observableArrayList(statut.values()));
        datePicker.setValue(LocalDate.now());
    }



    @FXML
    void AddOffreemploi(ActionEvent event) throws IOException {
        String titre = txtTitre.getText();
        String description = txtDescription.getText();
        String adresse = txtAdresse.getText();
        LocalDate selectedDate = datePicker.getValue();
        statut Statut = cmbStatut.getValue();
        if (titre.isEmpty() || description.isEmpty() || adresse.isEmpty() ||
                selectedDate == null || cmbStatut.getValue() == null ) {
            showAlert("Erreur", "Veuillez remplir tous les champs ");
            return;
        }
        OffreEmploiServices ms = new OffreEmploiServices();
        offreemploi newOffre = new offreemploi(
                titre,
                description,
                adresse,
                Date.valueOf(selectedDate),
                Statut
        );

        ms.ajouter(newOffre);
        showSuccessNotification("Offre ajoutée", "L'offre a été ajoutée avec succès");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void showSuccessNotification(String title, String message) {
        GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");
        Notifications.create()
                .title(title)
                .text(message)
                .graphic(fontAwesome.create(FontAwesome.Glyph.CHECK_CIRCLE))
                .showInformation();
    }

    @FXML
    void AnnulerAjout(ActionEvent event) {
        txtTitre.clear();
        txtDescription.clear();
        cmbStatut.getSelectionModel().clearSelection();
    }

    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}