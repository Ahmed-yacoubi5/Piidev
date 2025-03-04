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

    public Button btnRetour;
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
        cmbStatut.setItems(FXCollections.observableArrayList(statut.values()));
        datePicker.setValue(LocalDate.now());
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

        ms.ajouter(newOffre);

        // Retourner à la page de liste après ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();
        AfficheOffreemploi controller = loader.getController();
        controller.setOffres(ms.getAllOffres());

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
