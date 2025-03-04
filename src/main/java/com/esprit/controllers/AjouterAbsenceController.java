package com.esprit.controllers;

import com.esprit.models.absence;
import com.esprit.services.ServiceAbsence;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AjouterAbsenceController {

    @FXML
    private TextField TextFieldType;

    @FXML
    private TextField TextFieldDateDebut;

    @FXML
    private TextField TextFieldDateFin;

    @FXML
    private TextField TextFieldEmployeeId;

    @FXML
    private TextField TextFieldStatut;

    @FXML
    void ButtonActionAjouter(ActionEvent event) {
        String type = TextFieldType.getText().trim();
        String dateDebut = TextFieldDateDebut.getText().trim();
        String dateFin = TextFieldDateFin.getText().trim();
        String statut = TextFieldStatut.getText().trim();

        if (type.isEmpty() || dateDebut.isEmpty() || dateFin.isEmpty() || statut.isEmpty()) {
            afficherAlerte("⚠️ Champs vides", "Tous les champs doivent être remplis.");
            return;
        }





        try {
            LocalDate debut = LocalDate.parse(dateDebut, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate fin = LocalDate.parse(dateFin, DateTimeFormatter.ISO_LOCAL_DATE);

            if (fin.isBefore(debut)) {
                afficherAlerte("⚠️ Erreur", "La date de fin doit être postérieure à la date de début.");
                return;
            }

            int employeeId = Integer.parseInt(TextFieldEmployeeId.getText().trim());

            absence nouvelleAbsence = new absence(type, dateDebut, dateFin, employeeId, statut);
            ServiceAbsence service = new ServiceAbsence();
            service.ajouter(nouvelleAbsence);
            afficherAlerte("✅ Succès", "Absence ajoutée avec succès !");
            redirigerVersListeAbsences();

        } catch (NumberFormatException e) {
            afficherAlerte("🚫 Erreur", "L'ID de l'employé doit être un nombre valide.");
        } catch (DateTimeParseException e) {
            afficherAlerte("🚫 Erreur", "Les dates doivent être au format 'yyyy-MM-dd'.");
        }
    }

    private void redirigerVersListeAbsences() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListAbsence.fxml"));
            TextFieldType.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("❌ Erreur", "Impossible d'ouvrir la liste des absences.");
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            TextFieldType.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("❌ Erreur", "Impossible de retourner au menu principal.");
        }
    }
}
