package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button btnAbsences;

    @FXML
    private Button btnConges;

    @FXML
    private Button btnBienEtre;

    @FXML
    private Button btnQuitter;

    @FXML
    public void initialize() {
        // Ajouter des tooltips
        btnAbsences.setTooltip(new Tooltip("Accédez à la gestion des absences"));
        btnConges.setTooltip(new Tooltip("Accédez à la gestion des congés"));
        btnBienEtre.setTooltip(new Tooltip("Accédez à la gestion du bien-être"));
        btnQuitter.setTooltip(new Tooltip("Quittez l'application"));

        // Gestion des actions
        btnAbsences.setOnAction(event -> ouvrirInterface("/ListAbsence.fxml"));
        btnConges.setOnAction(event -> ouvrirInterface("/ListConges.fxml"));
        btnBienEtre.setOnAction(event -> ouvrirInterface("/ListBienEtre.fxml"));
        btnQuitter.setOnAction(event -> fermerApplication());
    }

    // Méthode pour ouvrir une nouvelle interface
    private void ouvrirInterface(String fxmlPath) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent root = loader.load();
            javafx.stage.Stage stage = (Stage) btnAbsences.getScene().getWindow();
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
