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
    private Button btnCV;
    
    @FXML
    private Button btnOffres;
    
    @FXML
    private Button btnEvenements;
    
    @FXML
    private Button btnCommentaires;

    @FXML
    private Button btnQuitter;
    
    @FXML
    private Button btnProfile;
    
    @FXML
    private Button btnEmployees;

    @FXML
    public void initialize() {
        // Ajouter des tooltips
        btnAbsences.setTooltip(new Tooltip("Accédez à la gestion des absences"));
        btnConges.setTooltip(new Tooltip("Accédez à la gestion des congés"));
        btnBienEtre.setTooltip(new Tooltip("Accédez à la gestion du bien-être"));
        btnCV.setTooltip(new Tooltip("Accédez à la gestion des CV"));
        btnOffres.setTooltip(new Tooltip("Accédez à la gestion des offres d'emploi"));
        btnEvenements.setTooltip(new Tooltip("Accédez à la gestion des événements"));
        btnCommentaires.setTooltip(new Tooltip("Accédez à la gestion des commentaires"));
        btnProfile.setTooltip(new Tooltip("Accédez à la gestion des profils"));
        btnEmployees.setTooltip(new Tooltip("Accédez à la gestion des employés et candidats"));
        btnQuitter.setTooltip(new Tooltip("Quittez l'application"));

        // Gestion des actions
        btnAbsences.setOnAction(event -> ouvrirInterface("/ListAbsence.fxml"));
        btnConges.setOnAction(event -> ouvrirInterface("/ListConges.fxml"));
        btnBienEtre.setOnAction(event -> ouvrirInterface("/ListBienetre.fxml"));
        btnCV.setOnAction(event -> ouvrirInterface("/AfficheCV.fxml"));
        btnOffres.setOnAction(event -> ouvrirInterface("/AfficheOffreemploi.fxml"));
        btnEvenements.setOnAction(event -> ouvrirInterface("/EvenementsList.fxml"));
        btnCommentaires.setOnAction(event -> ouvrirInterface("/CommentsList.fxml"));
        btnProfile.setOnAction(event -> ouvrirInterface("/ProfileSetup.fxml"));
        btnEmployees.setOnAction(event -> ouvrirInterface("/SummaryView.fxml"));
        btnQuitter.setOnAction(event -> fermerApplication());
    }

    // Méthode pour ouvrir une nouvelle interface
    private void ouvrirInterface(String fxmlPath) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource(fxmlPath));
            javafx.scene.Parent root = loader.load();
            Stage stage = (Stage) btnAbsences.getScene().getWindow();
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
