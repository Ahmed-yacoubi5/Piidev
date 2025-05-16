package com.esprit.controllers;

import com.esprit.services.ServiceUser;
import com.esprit.models.User;
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
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    private ServiceUser serviceUser;

    @FXML
    private TextField login;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button log;

    @FXML
    private Button regs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        serviceUser = new ServiceUser();

    }

    @FXML
    private void login(ActionEvent event) {
        // Vérifier si les champs sont vides
        if (login.getText().isEmpty() || passwordField.getText().isEmpty()) {
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setTitle("Oops");
            alert1.setHeaderText(null);
            alert1.setContentText("Veuillez remplir tous les champs.");
            alert1.showAndWait();
            return;
        }

        String username = login.getText();
        String password = passwordField.getText();

        // Vérifier si l'utilisateur est désactivé avant de continuer
        if (isUserDisabled(username)) {
            Alert disabledAlert = new Alert(AlertType.WARNING);
            disabledAlert.setTitle("Compte Désactivé");
            disabledAlert.setHeaderText(null);
            disabledAlert.setContentText("Vous êtes désactivé par l'administrateur. Veuillez vérifier avec l'admin.");
            disabledAlert.showAndWait();
            return; // Si l'utilisateur est désactivé, on arrête ici
        }

        // Récupérer le mot de passe hashé depuis la base de données
        String hashedPasswordFromDB = serviceUser.getHashedPasswordForUser(username);
        if (hashedPasswordFromDB == null) {
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setTitle("Utilisateur non trouvé");
            alert1.setHeaderText(null);
            alert1.setContentText("Nom d'utilisateur incorrect.");
            alert1.showAndWait();
            return;
        }

        // Vérifier le mot de passe
        if (BCrypt.checkpw(password, hashedPasswordFromDB)) {
            // Authentification réussie
            String role = serviceUser.getRole(username);
            Alert successAlert = new Alert(AlertType.INFORMATION);
            successAlert.setTitle("Confirmation");
            successAlert.setHeaderText(null);
            successAlert.setContentText("Login réussi.");
            successAlert.showAndWait();

            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                // Load Home.fxml after successful login
                String fxmlFile = "/Home.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                if (loader.getLocation() == null) {
                    throw new IOException("Cannot load Home.fxml: " + fxmlFile);
                }
                Parent root = loader.load();

                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setScene(scene);
                newStage.setTitle("Dashboard - " + role.toUpperCase());
                newStage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to load the application");
                alert.setContentText("Could not load the dashboard. Please try again or contact support.\n" +
                    "Error: " + ex.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setTitle("Oops");
            alert1.setHeaderText(null);
            alert1.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
            alert1.showAndWait();
        }
    }

    // Fonction séparée pour vérifier le statut de l'utilisateur
    private boolean isUserDisabled(String username) {
        String status = serviceUser.getUserStatus(username);
        System.out.println("Statut de l'utilisateur : " + status);  // Déboguer le statut récupéré
        return "disable".equalsIgnoreCase(status);  // Renvoie true si l'utilisateur est désactivé
    }

    @FXML
    void regsitre(ActionEvent event) {
        try {
            // Fermer la fenêtre actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Charger le fichier FXML pour la page registreUser
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/registreUser.fxml"));
            
            if (loader.getLocation() == null) {
                throw new IOException("Cannot load registreUser.fxml");
            }

            // Charger la scène et l'afficher
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setScene(scene);
            stage2.setTitle("Création de compte");
            stage2.show();

        } catch (IOException ex) {
            ex.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Impossible de charger la page de création de compte. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
}
