package com.esprit.controllers;

import com.esprit.models.bienetre;
import com.esprit.services.ServiceBienetre;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterBienetreController {

    @FXML
    private TextField TextFieldNom;

    @FXML
    private TextField TextFieldRate;

    @FXML
    private TextField TextFieldReview;

    @FXML
    void ButtonActionAjouter(ActionEvent event) {
        // Récupération des données saisies par l'utilisateur
        String nom = TextFieldNom.getText();
        String review = TextFieldReview.getText();

        // Validation et conversion du taux
        int rate;
        try {
            rate = Integer.parseInt(TextFieldRate.getText());
        } catch (NumberFormatException e) {
            System.out.println("Le taux doit être un nombre entier valide.");
            return;
        }

        // Création d'un objet Bienetre
        bienetre newBienetre = new bienetre(nom, review, rate);

        // Ajout du bien-être via le service
        ServiceBienetre serviceBienetre = new ServiceBienetre();
        serviceBienetre.ajouter(newBienetre);

        // Redirection vers l'interface ListBienetre après l'ajout
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListBienetre.fxml"));
            Parent root = loader.load();
            TextFieldNom.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }
}
