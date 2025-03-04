package com.esprit.controllers;

import com.esprit.models.Admin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class NavbarController {

    @FXML
    private Label lblUtilisateurs;

    @FXML
    private Label lblEvenements;

    @FXML
    private Label lblBlogs;

    @FXML
    private Label lblServices;

    @FXML
    private Label lblDechets;

    @FXML
    private Label lblTransport;

    @FXML
    private Label lblLogout;

    @FXML
    private Label lblHelp;

    private Admin loggedInAdmin;

    public void setLoggedInAdmin(Admin loggedInAdmin) {
        this.loggedInAdmin = loggedInAdmin;
        updateWelcomeMessage();
    }

    private void updateWelcomeMessage() {
        // If you have a welcome label, you can update it here
        // Example: lblWelcome.setText("Welcome, " + loggedInAdmin.getUsername() + "!");
    }

    @FXML
    private void handleUtilisateursClick(MouseEvent event) {
        switchScene(event, "/com/esprit/view/dashboard.fxml");
    }

    @FXML
    private void handleEvenementsClick(MouseEvent event) {
        switchScene(event, "/com/esprit/view/listevent.fxml");
    }

    @FXML
    private void handleBlogsClick(MouseEvent event) {
        switchScene(event, "/com/esprit/view/listblog.fxml");
    }

    @FXML
    private void handleServicesClick(MouseEvent event) {
        switchScene(event, "/com/esprit/view/listproduct.fxml");
    }

    @FXML
    private void handleDechetsClick(MouseEvent event) {
        switchScene(event, "/com/esprit/view/listrecyclingcenter.fxml");
    }

    @FXML
    private void handleTransportClick(MouseEvent event) {
        switchScene(event, "/com/esprit/view/listvehicule.fxml");
    }

    @FXML
    private void handleLogoutClick(MouseEvent event) {
        switchScene(event, "/com/esprit/view/login.fxml");
    }

    @FXML
    private void handleHelpClick(MouseEvent event) {
        // Logic to handle help (e.g., open a help dialog or switch to a help view)
        System.out.println("Help clicked!");
    }

    private void switchScene(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Pass the logged-in admin to the new controller (if it's a NavbarController)
            Object controller = loader.getController();
            if (controller instanceof NavbarController) {
                ((NavbarController) controller).setLoggedInAdmin(loggedInAdmin);
            }

            // Get current stage and switch scene
            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}