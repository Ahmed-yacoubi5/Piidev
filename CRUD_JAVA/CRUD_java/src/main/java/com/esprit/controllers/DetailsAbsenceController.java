package com.esprit.controllers;

import com.esprit.models.absence;
import com.esprit.services.ServiceAbsence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;

import java.io.IOException;

public class DetailsAbsenceController {

    @FXML
    private TextField ResultatDateDebut;

    @FXML
    private TextField ResultatDateFin;

    @FXML
    private TextField ResultatEmployeeID;

    @FXML
    private TextField ResultatStatut;

    @FXML
    private TextField ResultatType;

    private int employeeId;

    // Méthodes pour définir les champs de détails
    public void setResultatDateDebut(String dateDebut) {
        ResultatDateDebut.setText(dateDebut);
    }

    public void setResultatDateFin(String dateFin) {
        ResultatDateFin.setText(dateFin);
    }

    public void setResultatEmployeeID(int employeeId) {
        this.employeeId = employeeId;
        ResultatEmployeeID.setText(String.valueOf(employeeId));
    }

    public void setResultatStatut(String statut) {
        ResultatStatut.setText(statut);
    }

    public void setResultatType(String type) {
        ResultatType.setText(type);
    }

    @FXML
    void ButtonActionModifier(ActionEvent event) {
        // Récupération des données modifiées
        String type = ResultatType.getText();
        String dateDebut = ResultatDateDebut.getText();
        String dateFin = ResultatDateFin.getText();
        String statut = ResultatStatut.getText();

        int employeeId;
        try {
            employeeId = Integer.parseInt(ResultatEmployeeID.getText());
        } catch (NumberFormatException e) {
            System.out.println("L'ID de l'employé doit être un nombre valide.");
            return;
        }

        // Création et modification de l'absence
        absence updatedAbsence = new absence(type, dateDebut, dateFin, employeeId, statut);
        ServiceAbsence serviceAbsence = new ServiceAbsence();
        serviceAbsence.modifier(updatedAbsence);

        // Redirection vers la liste des absences
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListAbsence.fxml"));
            ResultatType.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    @FXML
    void ButtonActionSupprimer(ActionEvent event) {
        // Suppression de l'absence
        ServiceAbsence serviceAbsence = new ServiceAbsence();
        serviceAbsence.supprimer(employeeId);

        // Redirection vers la liste des absences après suppression
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListAbsence.fxml"));
            ResultatType.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors de la redirection après suppression : " + e.getMessage());
        }
    }
} 