package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class updateuser {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private Button updateButton;

    @FXML
    private Button cancelButton;

    @FXML
    private void initialize() {
        // Initialization if needed
    }

    @FXML
    private void handleUpdateButtonAction() {
        String username = usernameField.getText();
        String email = emailField.getText();

        // Logic to update user
        System.out.println("Updating user: " + username + " with email: " + email);
    }

    @FXML
    private void handleCancelButtonAction() {
        // Logic for canceling the update process
        System.out.println("Update canceled");
    }
}