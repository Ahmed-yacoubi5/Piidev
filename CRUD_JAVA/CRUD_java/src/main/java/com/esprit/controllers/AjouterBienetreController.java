package com.esprit.controllers;

import com.esprit.models.bienetre;
import com.esprit.services.ServiceBienetre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterBienetreController {

    @FXML
    private TextField TextFieldNom;

    @FXML
    private TextField TextFieldReview;

    @FXML
    private TextField TextFieldRate;

    /**
     * 🆕 Action : Ajouter un nouvel enregistrement de bien-être
     */
    @FXML
    void ButtonActionAjouter(ActionEvent event) {
        // 🔍 Récupération et validation des champs
        String nom = TextFieldNom.getText();
        String review = TextFieldReview.getText();

        if (nom.isEmpty() || review.isEmpty() || TextFieldRate.getText().isEmpty()) {
            afficherAlerte("⚠️ Champs vides", "Veuillez remplir tous les champs.");
            return;
        }

        // 🎯 Validation du taux (rate)
        int rate;
        try {
            rate = Integer.parseInt(TextFieldRate.getText());
            if (rate < 1 || rate > 5) {
                afficherAlerte("🚫 Erreur de saisie", "Le taux doit être compris entre 1 et 5.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("🚫 Erreur de saisie", "Le taux doit être un nombre entier.");
            return;
        }

        // 📋 Création de l'objet Bienetre
        bienetre nouveauBienetre = new bienetre(nom, review, rate);

        // ⚙️ Ajout via le service
        ServiceBienetre serviceBienetre = new ServiceBienetre();
        serviceBienetre.ajouter(nouveauBienetre);
        afficherAlerte("✅ Succès", "Bien-être ajouté avec succès !");

        // 🔄 Redirection vers la liste des bien-être
        redirigerVersListeBienetre();
    }

    /**
     * 🚀 Rediriger vers la liste des enregistrements de bien-être
     */
    private void redirigerVersListeBienetre() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListBienetre.fxml"));
            TextFieldNom.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("❌ Erreur", "Impossible de charger la liste des bien-être.");
        }
    }

    /**
     * 🔙 Retour au menu principal
     */
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            TextFieldNom.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("❌ Erreur", "Impossible de retourner au menu principal.");
        }
    }

    /**
     * 🛑 Afficher une alerte d'information
     */
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
