package com.recrutement.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button btnOffreemploi;

    @FXML
    private Button btnCV;


    @FXML
    private Button btnQuitter;

    @FXML
    public void initialize() {
        // Ajouter des tooltips
        btnOffreemploi.setTooltip(new Tooltip("Accédez à la gestion des offres emplois"));
        btnCV.setTooltip(new Tooltip("Accédez à la gestion des CV"));
        btnQuitter.setTooltip(new Tooltip("Quittez l'application"));

        // Gestion des actions
        btnOffreemploi.setOnAction(event -> ouvrirInterface("/AfficheOffreemploi.fxml"));
        btnCV.setOnAction(event -> ouvrirInterface("/AfficheCV.fxml"));
        btnQuitter.setOnAction(event -> fermerApplication());
    }

    // Méthode pour ouvrir une nouvelle interface
    private void ouvrirInterface(String fxmlPath) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (Stage) btnOffreemploi.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (java.io.IOException e) {
            System.out.println("❌ Erreur lors de l'ouverture de l'interface : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour fermer l'application
    private void fermerApplication() {
        Stage stage = (Stage) btnQuitter.getScene().getWindow();
        stage.close();
    }
}
