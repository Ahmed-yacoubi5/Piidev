package com.esprit.gui;

import com.esprit.services.UserService;

import com.esprit.models.Admin;
import com.esprit.models.Employé;
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


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField Username;

    @FXML
    private PasswordField Password;

    @FXML
    private Button log;

    @FXML
    private Button regs;

    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize the services
        UserService userService = new UserService();
    }

    @FXML
    void login(ActionEvent event) {
        // Check if fields are empty
        if (Username.getText().isEmpty() || Password.getText().isEmpty()) {
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setTitle("Oops");
            alert1.setHeaderText(null);
            alert1.setContentText("Veuillez remplir tous les champs.");
            alert1.showAndWait();
            return;
        }

        String username = Username.getText();
        String password = Password.getText();

        // Try to find the user in Admin, Client, or Employe
        Admin admin = adminService.getAdminByUsername(username);
        Client client = clientService.getClientByUsername(username);
        Employé employe = employeService.getEmployeByUsername(username);

        // Check if the user exists
        if (admin != null) {
            authenticateUser(admin.getPassword(), password, "admin", event);
        } else if (client != null) {
            authenticateUser(client.getPassword(), password, "client", event);
        } else if (employe != null) {
            authenticateUser(employe.getPassword(), password, "employe", event);
        } else {
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setTitle("Utilisateur non trouvé");
            alert1.setHeaderText(null);
            alert1.setContentText("Nom d'utilisateur incorrect.");
            alert1.showAndWait();
        }
    }

    private void authenticateUser(String storedPassword, String enteredPassword, String role, ActionEvent event) {
        // Direct comparison of entered and stored password (without BCrypt)
        if (storedPassword.equals(enteredPassword)) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Login réussi.");
            alert.showAndWait();

            try {
                // Close the current stage
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();

                // Load the appropriate dashboard based on role
                String fxmlFile = getFxmlFileBasedOnRole(role);
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage2 = new Stage();
                stage2.setScene(scene);
                stage2.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setTitle("Oops");
            alert1.setHeaderText(null);
            alert1.setContentText("Nom d'utilisateur ou mot de passe incorrect.");
            alert1.showAndWait();
        }
    }

    private String getFxmlFileBasedOnRole(String role) {
        // Make sure the paths are correct for your project structure
        switch (role.toLowerCase()) {
            case "admin":
                return "/com/esprit/view/dashboard.fxml";
            case "client":
                return "/com/esprit/view/front.fxml";
            case "employe":
                return "/com/esprit/view/dashboard.fxml";
            default:
                return "/com/esprit/view/dashboard.fxml";  // Default FXML if role is unknown
        }
    }

    @FXML
    void regsitre(ActionEvent event) {
        try {
            // Close the current window
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            // Load the registration page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/registreUser.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void registre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/registreuser.fxml"));
            Parent root = loader.load();

            // Get the current stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);

            // Set the new scene
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}