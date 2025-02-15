package com.esprit.controllers;

import com.esprit.models.conges;

import com.esprit.services.ServiceConges;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterCongesController {

    @FXML
    private TextField TextFieldDateDebut;

    @FXML
    private TextField TextFieldDateFin;

    @FXML
    private TextField TextFieldEmployeeId;

    @FXML
    private TextField TextFieldStatut;

    @FXML
    private TextField TextFieldType;

    @FXML
    void ButtonActionAjouter(ActionEvent event) {
        // Récupération et validation des données
        String type = TextFieldType.getText().trim();
        String datedebut = TextFieldDateDebut.getText().trim();
        String datefin = TextFieldDateFin.getText().trim();
        String statut = TextFieldStatut.getText().trim();

        // Validation et conversion de l'ID de l'employé
        int employeeId;
        try {
            employeeId = Integer.parseInt(TextFieldEmployeeId.getText().trim());
        } catch (NumberFormatException e) {
            System.out.println("❌ Erreur : L'ID de l'employé doit être un nombre valide.");
            return;
        }

        // Vérification du format des dates
        if (!datedebut.matches("\\d{4}-\\d{2}-\\d{2}") || !datefin.matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("❌ Erreur : Les dates doivent être au format 'yyyy-MM-dd'.");
            return;
        }

        // Création d'un objet  (congé)
        conges newConges = new conges(type, datedebut, datefin, employeeId, statut);

        // Ajout du congé via le service
        ServiceConges serviceConges = new ServiceConges();
        serviceConges.ajouter(newConges);

        // Confirmation et redirection
        System.out.println("✅ Congé ajouté avec succès !");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListConges.fxml"));
            Parent root = loader.load();
            TextFieldType.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }
}
