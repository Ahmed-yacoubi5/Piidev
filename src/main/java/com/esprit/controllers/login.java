package com.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class login {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Text errorMessage;

    @FXML
    private void handleLoginAction(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (isValidLogin(username, password)) {
            errorMessage.setText("Login successful!");
            // Proceed to the next screen or logic after successful login
        } else {
            errorMessage.setText("Invalid username or password. Please try again.");
        }
    }

    private boolean isValidLogin(String username, String password) {
        // Replace this logic with actual authentication check (e.g., database or service call)
        return "user".equals(username) && "password".equals(password);
    }
}