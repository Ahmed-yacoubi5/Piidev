package com.recrutement.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    void goToOffreEmploi(ActionEvent event) throws IOException {
        // Charger le fichier FXML pour l'interface OffreEmploi
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutOffreemploi.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène et l'afficher
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void goToAjoutCV(ActionEvent event) throws IOException {
        // Charger le fichier FXML pour l'interface AjoutCV
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCV.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène et l'afficher
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}