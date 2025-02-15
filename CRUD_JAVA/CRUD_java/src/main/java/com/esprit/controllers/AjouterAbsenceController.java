package com.esprit.controllers;

import com.esprit.models.absence;
import com.esprit.services.ServiceAbsence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjouterAbsenceController {

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
        // Récupération des données saisies par l'utilisateur
        String type = TextFieldType.getText();
        String datedebut = TextFieldDateDebut.getText();
        String datefin = TextFieldDateFin.getText();
        String statut = TextFieldStatut.getText();

        // Validation et conversion de l'ID de l'employé
        int employeeId;
        try {
            employeeId = Integer.parseInt(TextFieldEmployeeId.getText());
        } catch (NumberFormatException e) {
            System.out.println("L'ID de l'employé doit être un nombre valide.");
            return;
        }

        // Création d'un objet absence
        absence newAbsence = new absence(type, datedebut, datefin, employeeId, statut);

        // Ajout de l'absence via le service
        ServiceAbsence serviceAbsence = new ServiceAbsence();
        serviceAbsence.ajouter(newAbsence);

        // Redirection vers l'interface ListAbsence après l'ajout
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListAbsence.fxml"));
            Parent root = loader.load();
            TextFieldType.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }
}
