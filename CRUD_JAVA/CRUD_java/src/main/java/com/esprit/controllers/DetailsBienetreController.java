package com.esprit.controllers;

import com.esprit.models.bienetre;
import com.esprit.services.ServiceBienetre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    // Méthodes pour définir les champs de détails
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

    @FXML
    void ButtonActionModifier(ActionEvent event) {
        // Récupération des données modifiées
        String nom = ResultatNom.getText();
        int rate;
        try {
            rate = (int) Double.parseDouble(ResultatRate.getText());
        } catch (NumberFormatException e) {
            System.out.println("Le taux doit être un nombre entier valide.");
            return;
        }
        String review = ResultatReview.getText();

        // Création et modification du bien-être
        bienetre updatedBienetre = new bienetre(nom, review, rate);
        ServiceBienetre serviceBienetre = new ServiceBienetre();
        serviceBienetre.modifier(updatedBienetre);

        // Redirection vers la liste des bien-êtres
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListBienetre.fxml"));
            ResultatNom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    @FXML
    void ButtonActionSupprimer(ActionEvent event) {
        // Suppression du bien-être
        ServiceBienetre serviceBienetre = new ServiceBienetre();
        serviceBienetre.supprimer(nom);

        // Redirection vers la liste des bien-êtres après suppression
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListBienetre.fxml"));
            ResultatNom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors de la redirection après suppression : " + e.getMessage());
        }
    }
}
