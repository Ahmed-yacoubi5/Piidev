package com.recrutement.controllers;

import com.recrutement.models.statut;
import com.recrutement.models.offreemploi;
import com.recrutement.services.OffreEmploiServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ModifierOffreemploi {

    @FXML
    private TextField txtTitre;

    @FXML
    private TextField txtDescription;

    @FXML
    private ComboBox<statut> cmbStatut;

    @FXML
    private DatePicker datePicker;

    private offreemploi offre; // Stocker l'offre à modifier

    // Méthode pour définir l'offre à modifier
    public void setOffre(offreemploi offre) {
        this.offre = offre;
        txtTitre.setText(offre.getTitre());
        txtDescription.setText(offre.getDescription());

        // Conversion de java.sql.Date en LocalDate
        Instant instant = offre.getDate_publication().toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate localDate = zdt.toLocalDate();
        datePicker.setValue(localDate);

        cmbStatut.setValue(offre.getStatut());
    }

    @FXML
    void ModifierOffreemploi(ActionEvent event) throws IOException {
        String titre = txtTitre.getText();
        String description = txtDescription.getText();
        LocalDate selectedDate = datePicker.getValue();
        statut Statut = cmbStatut.getValue();

        // Validation des champs
        if (titre.isEmpty() || description.isEmpty() || selectedDate == null || Statut == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return;
        }

        // Mettre à jour l'objet
        offre.setTitre(titre);
        offre.setDescription(description);
        offre.setDate_publication(Date.valueOf(selectedDate));
        offre.setStatut(Statut);

        // Mettre à jour la base de données
        OffreEmploiServices ms = new OffreEmploiServices();
        ms.modifier(offre);

        // Afficher la confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Offre modifiée !");
        alert.show();

        // Passer à l'affichage des offres
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();
        AfficheOffreemploi ap = loader.getController();
        ap.setTxtTitre(titre);
        ap.setTxtDescription(description);
        ap.setCmbStatut(Statut);
        ap.setDatePublication(selectedDate);

        // Change the scene to AfficheOffreemploi
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void RetourAjout(ActionEvent event) throws IOException {
        // Naviguer vers la scène d'ajout d'offre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutOffreemploi.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
