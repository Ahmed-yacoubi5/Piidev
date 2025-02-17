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

public class DetailsBienetreController {

    @FXML
    private TextField ResultatNom;

    @FXML
    private TextField ResultatRate;

    @FXML
    private TextField ResultatReview;

    private String nom;

    // ✅ Définir les champs de détails
    public void setResultatNom(String nom) {
        this.nom = nom;
        ResultatNom.setText(nom);
    }

    public void setResultatRate(double rate) {
        ResultatRate.setText(String.valueOf(rate));
    }

    public void setResultatReview(String review) {
        ResultatReview.setText(review);
    }

    /**
     * ✏️ Modifier un enregistrement de bien-être.
     */
    @FXML
    void ButtonActionModifier(ActionEvent event) {
        String nom = ResultatNom.getText();
        String review = ResultatReview.getText();

        // 🎯 Validation du taux
        int rate;
        try {
            rate = Integer.parseInt(ResultatRate.getText());
            if (rate < 0 || rate > 5) {
                afficherAlerte("⚠️ Erreur", "Le taux doit être compris entre 0 et 5.");
                return;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("🚫 Erreur", "Le taux doit être un nombre entier valide.");
            return;
        }

        // 🔄 Modification de l'enregistrement
        bienetre updatedBienetre = new bienetre(nom, review, rate);
        ServiceBienetre serviceBienetre = new ServiceBienetre();
        serviceBienetre.modifier(updatedBienetre);
        afficherAlerte("✅ Succès", "Les détails du bien-être ont été modifiés avec succès.");

        // 🔙 Redirection vers la liste des bien-êtres
        rediriger("/ListBienetre.fxml");
    }

    /**
     * 🗑️ Supprimer un enregistrement de bien-être.
     */
    @FXML
    void ButtonActionSupprimer(ActionEvent event) {
        ServiceBienetre serviceBienetre = new ServiceBienetre();
        serviceBienetre.supprimer(nom);
        afficherAlerte("🗑️ Suppression", "Le bien-être a été supprimé avec succès.");

        // 🔙 Redirection vers la liste des bien-êtres
        rediriger("/ListBienetre.fxml");
    }

    /**
     * 🔙 Retourner au menu principal.
     */
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        rediriger("/Home.fxml");
    }

    /**
     * 🌐 Rediriger vers une autre vue.
     */
    private void rediriger(String cheminFXML) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(cheminFXML));
            ResultatNom.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("❌ Erreur", "Impossible de charger la vue : " + e.getMessage());
        }
    }

    /**
     * ⚠️ Afficher une alerte.
     */
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
