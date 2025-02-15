package com.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
        // Gestion du bouton Absences
        btnAbsences.setOnAction(event -> ouvrirInterface("/ListAbsence.fxml", "Gestion des Absences"));

        // Gestion du bouton Congés
        btnConges.setOnAction(event -> ouvrirInterface("/ListConges.fxml", "Gestion des Congés"));

        // Gestion du bouton Bien-être
        btnBienEtre.setOnAction(event -> ouvrirInterface("/ListBienEtre.fxml", "Gestion du Bien-être"));

        // Gestion du bouton Quitter
        btnQuitter.setOnAction(event -> fermerApplication());
    }

    // Méthode pour charger et afficher une interface
    private void ouvrirInterface(String fxmlPath, String titre) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) btnAbsences.getScene().getWindow();
            stage.setTitle(titre);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
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
