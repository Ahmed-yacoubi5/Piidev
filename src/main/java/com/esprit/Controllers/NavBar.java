package com.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavBar {

    @FXML
    private Button backButton;
    @FXML
    private Button homeButton;
    @FXML
    private Button profileButton;

    // Back button functionality
    @FXML
    public void goBack() throws IOException {
        // Logic for going back to the previous page
        System.out.println("Back button clicked");
    }

    // Home button functionality (Placeholder for now)
    @FXML
    public void goHome() {
        System.out.println("Home button clicked (Functionality not yet implemented)");
    }

    // Profile button functionality (Placeholder for now)
    @FXML
    public void handleProfile() {
        System.out.println("Profile Handling button clicked (Functionality not yet implemented)");
    }
}
