package com.esprit.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import com.esprit.models.User;
import com.esprit.services.ServiceUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import javafx.scene.control.Label;
public class RegistreUserController implements Initializable {

    @FXML
    private TextField txtnom1;
    
    @FXML
    private TextField txtnom2;
    
    @FXML
    private TextField txtemail;
    
    @FXML
    private TextField txtnumT;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField passwordField2;
    
    @FXML
    private ImageView imageview;
    
    @FXML
    private TextField urlTF;
    
    @FXML
    private Button btnChoisir;
    
    @FXML
    private Button btnAjouter;
    
    private File selectedFile;
    private Stage stage;
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    
    private boolean isValidEmail(String email) {
        if (email == null) return false;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    private boolean isValidPhoneNumber(String phone) {
        // Vérifie que le numéro contient exactement 8 chiffres
        if (phone == null || phone.length() != 8) {
            return false;
        }
        try {
            Long.parseLong(phone);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private boolean isValidPassword(String password) {
        // Au moins 8 caractères, une majuscule, une minuscule et un chiffre
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password != null && password.matches(passwordPattern);
    }

    @FXML
    void importer(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image de profil");
        
        // Définir les filtres d'extension
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
            "Images", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        
        // Afficher la boîte de dialogue et obtenir le fichier sélectionné
        selectedFile = fileChooser.showOpenDialog(stage);
        
        if (selectedFile != null) {
            // Mettre à jour le champ de texte avec le chemin du fichier
            urlTF.setText(selectedFile.getAbsolutePath());
            
            // Afficher un aperçu de l'image
            Image image = new Image(selectedFile.toURI().toString());
            imageview.setImage(image);
        }
    }
    
    @FXML
    void retourLogin(ActionEvent event) {
        try {
            // Fermer la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
            
            // Charger la vue de connexion
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            
            // Afficher la nouvelle fenêtre
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.setTitle("Connexion");
            loginStage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir la page de connexion.", AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    @FXML
    void ajouter(ActionEvent event) {
        // Validation des champs obligatoires
        if (txtnom1.getText().isEmpty() || txtnom2.getText().isEmpty() || 
            txtemail.getText().isEmpty() || txtnumT.getText().isEmpty() ||
            passwordField.getText().isEmpty() || passwordField2.getText().isEmpty()) {
            
            showAlert("Champs manquants", "Veuillez remplir tous les champs obligatoires.", AlertType.WARNING);
            return;
        }
        
        // Validation de l'email
        if (!isValidEmail(txtemail.getText())) {
            showAlert("Email invalide", "Veuillez entrer une adresse email valide.", AlertType.ERROR);
            txtemail.requestFocus();
            return;
        }
        
        // Validation du numéro de téléphone
        if (!isValidPhoneNumber(txtnumT.getText())) {
            showAlert("Numéro invalide", "Le numéro de téléphone doit contenir exactement 8 chiffres.", AlertType.ERROR);
            txtnumT.requestFocus();
            return;
        }
        
        // Validation du mot de passe
        if (!isValidPassword(passwordField.getText())) {
            showAlert("Mot de passe faible", "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre.", AlertType.ERROR);
            passwordField.requestFocus();
            return;
        }
        
        // Vérification de la correspondance des mots de passe
        if (!passwordField.getText().equals(passwordField2.getText())) {
            showAlert("Mots de passe différents", "Les mots de passe ne correspondent pas.", AlertType.ERROR);
            passwordField2.requestFocus();
            return;
        }
        
        try {
            // Création d'un nouvel utilisateur
            User newUser = new User();
            newUser.setFirstName(txtnom1.getText().trim());
            newUser.setLastName(txtnom2.getText().trim());
            newUser.setEmail(txtemail.getText().trim());
            newUser.setPhoneNumber(txtnumT.getText().trim());
            
            // Hachage du mot de passe
            String hashedPassword = BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt());
            newUser.setPassword(hashedPassword);
            
            // Gestion de l'image de profil
            if (selectedFile != null) {
                newUser.setImage(selectedFile.getAbsolutePath());
            } else if (!urlTF.getText().isEmpty()) {
                newUser.setImage(urlTF.getText().trim());
            } else {
                newUser.setImage(""); // Ou une image par défaut
            }
            
            // Ajout de l'utilisateur via le service
            ServiceUser userService = new ServiceUser();
            userService.ajouter(newUser);
            
            // Afficher un message de succès
            showAlert("Inscription réussie", "Votre compte a été créé avec succès !", AlertType.INFORMATION);
            
            // Rediriger vers la page de connexion
            retourLogin(event);
            
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'inscription : " + e.getMessage(), AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialisation du stage
        stage = new Stage();
        
        // Configuration de l'image par défaut
        try {
            Image defaultImage = new Image(getClass().getResourceAsStream("/images/user-avatar.png"));
            imageview.setImage(defaultImage);
        } catch (Exception e) {
            System.err.println("Image par défaut non trouvée");
        }
    }
}
