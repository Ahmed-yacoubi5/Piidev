


package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class sidebar {

    @FXML
    private VBox sidebarContainer;

    @FXML
    private Button homeButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button aboutButton;

    @FXML
    public void initialize() {
        // Initialize actions or event handlers for the sidebar
        homeButton.setOnAction(event -> handleMenuAction("home"));
        settingsButton.setOnAction(event -> handleMenuAction("settings"));
        aboutButton.setOnAction(event -> handleMenuAction("about"));
    }

    private void handleMenuAction(String action) {
        switch (action) {
            case "home":
                System.out.println("Home button clicked");
                break;
            case "settings":
                System.out.println("Settings button clicked");
                break;
            case "about":
                System.out.println("About button clicked");
                break;
            default:
                System.out.println("Unknown action: " + action);
        }
    }
}