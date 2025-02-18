package com.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainProgGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Try loading the FXML file with the correct path
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/login.fxml"));

            // If null, it might indicate path issue, check in the console output
            if (loader.getLocation() == null) {
                System.out.println("Failed to locate FXML file.");
            }

            AnchorPane root = loader.load();

            // Set up the scene and the stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login Screen");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}