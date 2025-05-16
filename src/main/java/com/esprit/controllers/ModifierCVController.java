package com.esprit.controllers;

import com.esprit.models.cv;
import com.esprit.services.CvServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;

public class ModifierCVController {

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtPrenom;

    @FXML
    private DatePicker datePickerNaissance;

    @FXML
    private TextField txtAdresse;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelephone;

    @FXML
    public Button btnModifier;

    @FXML
    public Button btnAnnuler;

    private TableView<cv> tableViewCVs; // Changer ListView en TableView

    private cv cvToModify;
    private CvServices cvService = new CvServices();

    public void setTableViewCV(TableView<cv> tableViewCVs) {
        this.tableViewCVs = tableViewCVs;
    }

    // Méthode pour initialiser le CV à modifier
    public void setCV(cv cv) {
        this.cvToModify = cv;
        txtNom.setText(cv.getNom());
        txtPrenom.setText(cv.getPrenom());
        txtAdresse.setText(cv.getAdresse());
        txtEmail.setText(cv.getEmail());
        txtTelephone.setText(String.valueOf(cv.getTelephone()));
        if (cv.getDateDeNaissance() != null) {
            // Vérifie si c'est bien une instance de java.sql.Date avant de convertir
            if (cv.getDateDeNaissance() instanceof Date) {
                LocalDate localDate = ((Date) cv.getDateDeNaissance()).toLocalDate();
                datePickerNaissance.setValue(localDate);
            } else {
                System.err.println("❌ Erreur : date_de_naissance n'est pas une instance de java.sql.Date !");
            }
        }
    }

    @FXML
    void handleModifier(ActionEvent event) {
        // Récupérer les valeurs des champs
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        LocalDate dateNaissance = datePickerNaissance.getValue();
        String adresse = txtAdresse.getText();
        String email = txtEmail.getText();
        int telephone;

        try {
            telephone = Integer.parseInt(txtTelephone.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Le téléphone doit être un nombre valide.");
            alert.show();
            return;
        }

        // Vérifier que tous les champs sont remplis
        if (nom.isEmpty() || prenom.isEmpty() || dateNaissance == null || adresse.isEmpty() || email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.show();
            return;
        }

        // Mettre à jour les valeurs du CV
        cvToModify.setNom(nom);
        cvToModify.setPrenom(prenom);
        cvToModify.setDateDeNaissance(Date.valueOf(dateNaissance)); // Conversion de LocalDate en java.sql.Date
        cvToModify.setAdresse(adresse);
        cvToModify.setEmail(email);
        cvToModify.setTelephone(telephone);

        try {
            // Appel du service pour modifier le CV
            cvService.modifier(cvToModify);

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Succès");
            success.setContentText("CV modifié avec succès !");
            success.show();

            // Mise à jour de la TableView
            if (tableViewCVs != null) {
                // Remplacer l'élément modifié dans la TableView avec la version mise à jour
                tableViewCVs.getItems().set(tableViewCVs.getItems().indexOf(cvToModify), cvToModify);
            }

            // Fermer la fenêtre après modification
            Stage stage = (Stage) btnModifier.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Erreur");
            error.setContentText("Une erreur s'est produite lors de la modification du CV.");
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
}
