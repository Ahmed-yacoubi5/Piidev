package com.esprit.controllers;

import com.esprit.models.cv;
import com.esprit.services.CvServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

public class AjoutCvController {

    @FXML
    private ImageView imageView;

    @FXML
    public Button btnRetour;

    @FXML
    private TextField txtNom, txtPrenom, txtAdresse, txtEmail, txtTelephone;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button btnAjouter, btnAnnuler;

    private CvServices cvServices = new CvServices();

    @FXML
    void handleImageUpload(ActionEvent event) {
        // Ouvrir une boîte de dialogue pour sélectionner un fichier image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Afficher la fenêtre de sélection de fichier
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // Créer un objet Image en utilisant le fichier sélectionné
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image); // Afficher l'image dans l'ImageView
        }
    }

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

        // Validation du format du téléphone
        if (!telephoneStr.matches("\\d{8}")) {
            showAlert("Erreur", "Le téléphone doit être un nombre valide avec exactement 8 chiffres.");
            return;
        }

        // Validation du format de l'email
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showAlert("Erreur", "L'email n'est pas valide.");
            return;
        }

        int telephone;
        try {
            telephone = Integer.parseInt(telephoneStr);  // Utilisation correcte pour convertir le numéro en entier
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Le téléphone doit être un nombre valide.");
            return;
        }

        // Vérifier si une image a été sélectionnée
        if (this.imageView.getImage() == null) {
            showAlert("Erreur", "Veuillez sélectionner une image.");
            return;
        }

        // Récupérer le fichier image
        File file = new File(this.imageView.getImage().getUrl().replace("file:/", ""));
        String imagePath = file.getAbsolutePath();  // Enregistrer le chemin du fichier au lieu des octets de l'image

        // Création du CV avec l'image sous forme de chemin
        cv newCv = new cv(0, nom, prenom, Date.valueOf(dateDeNaissance), adresse, email, telephone, imagePath);

        // Appel à la méthode de service pour ajouter le CV
        cvServices.ajouter(newCv);

        showAlert("Succès", "CV ajouté avec succès !");
        annuler();  // Réinitialisation des champs
    }


    @FXML
    void annuler() {
        txtNom.clear();
        txtPrenom.clear();
        txtAdresse.clear();
        txtEmail.clear();
        txtTelephone.clear();
        datePicker.setValue(null);
        imageView.setImage(null);  // Réinitialisation de l'image
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}
