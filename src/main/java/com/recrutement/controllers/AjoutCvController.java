package com.recrutement.controllers;

import com.recrutement.models.cv;
import com.recrutement.services.CvServices;
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

public class AjoutCvController {

    public Button btnRetour;
    @FXML
    private TextField txtNom, txtPrenom, txtAdresse, txtEmail, txtTelephone;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnAjouter, btnAnnuler;

    private CvServices cvServices = new CvServices();

    @FXML
    void ajouterCv() {
        String nom = txtNom.getText();
        String prenom = txtPrenom.getText();
        LocalDate dateDeNaissance = datePicker.getValue();
        String adresse = txtAdresse.getText();
        String email = txtEmail.getText();
        String telephoneStr = txtTelephone.getText();

        // Validation des champs
        if (nom.isEmpty() || prenom.isEmpty() || dateDeNaissance == null || adresse.isEmpty() || email.isEmpty() || telephoneStr.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        int telephone;
        try {
            telephone = Integer.parseInt(telephoneStr); // Corrected to parse properly as an integer
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le téléphone doit être un nombre valide.");
            return;
        }
        if (!telephoneStr.matches("\\d{8}")) {
            showAlert("Erreur", "Le téléphone doit être un nombre valide avec exactement 8 chiffres.");
            return;
        }
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Erreur", "L'email n'est pas valide.");
            return;
        }

        // Création du CV
        cv newCv = new cv(nom, prenom, Date.valueOf(dateDeNaissance), adresse, email, telephone);
        cvServices.ajouter(newCv);

        showAlert("Succès", "CV ajouté avec succès !");
        annuler();
    }

    @FXML
    void afficherCVs(ActionEvent event) throws IOException {
        // Récupérer toutes les offres d'emploi depuis la base de données
        CvServices service = new CvServices();
        List<cv> cvs = service.getAllCVs(); // Récupérer la liste des offres

        // Charger le fichier FXML de la scène qui affiche les résultats
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheCV.fxml"));
        Parent root = loader.load();

        // Passer les données (les offres d'emploi) au contrôleur de la nouvelle scène
        AfficheCV controller = loader.getController();
        controller.setcv(cvs); // Passer la liste des offres au contrôleur

        // Créer une nouvelle scène et l'afficher
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void annuler() {
        txtNom.clear();
        txtPrenom.clear();
        txtAdresse.clear();
        txtEmail.clear();
        txtTelephone.clear();
        datePicker.setValue(null);
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
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}