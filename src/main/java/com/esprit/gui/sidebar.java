package com.esprit.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class sidebar {

    @FXML
    private void handleLogoutClick(Event event) {
        try {
            // Load the login.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/login.fxml"));
            AnchorPane loginRoot = loader.load();

            // Create a new scene with the login layout
            Scene scene = new Scene(loginRoot);

            // Get the current stage and set the scene to the login scene
            Stage stage = (Stage) ((Label) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
