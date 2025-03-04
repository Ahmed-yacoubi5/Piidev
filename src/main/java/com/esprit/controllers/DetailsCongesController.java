package com.esprit.controllers;

import com.esprit.models.conges;
import com.esprit.services.ServiceConges;
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

public class DetailsCongesController {

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

    public void setResultatDateDebut(String dateDebut) { ResultatDateDebut.setText(dateDebut); }
    public void setResultatDateFin(String dateFin) { ResultatDateFin.setText(dateFin); }
    public void setResultatEmployeeID(int employeeId) { this.employeeId = employeeId; ResultatEmployeeID.setText(String.valueOf(employeeId)); }
    public void setResultatStatut(String statut) { ResultatStatut.setText(statut); }
    public void setResultatType(String type) { ResultatType.setText(type); }

    @FXML
    void ButtonActionModifier(ActionEvent event) {
        String type = ResultatType.getText().trim();
        String dateDebut = ResultatDateDebut.getText().trim();
        String dateFin = ResultatDateFin.getText().trim();
        String statut = ResultatStatut.getText().trim();

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
            int employeeId = Integer.parseInt(ResultatEmployeeID.getText().trim());
            conges updatedConges = new conges(type, dateDebut, dateFin, employeeId, statut);
            ServiceConges service = new ServiceConges();
            service.modifier(updatedConges);
            afficherAlerte("✅ Succès", "Congé modifié avec succès !");
            redirigerVersListeConges();
        } catch (NumberFormatException e) {
            afficherAlerte("🚫 Erreur", "L'ID de l'employé doit être un nombre valide.");
        } catch (DateTimeParseException e) {
            afficherAlerte("🚫 Erreur", "Les dates doivent être au format 'yyyy-MM-dd'.");
        }
    }

    @FXML
    void ButtonActionSupprimer(ActionEvent event) {
        ServiceConges service = new ServiceConges();
        service.supprimer(employeeId);
        afficherAlerte("✅ Succès", "Congé supprimé avec succès !");
        redirigerVersListeConges();
    }

    private void redirigerVersListeConges() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListConges.fxml"));
            ResultatType.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("❌ Erreur", "Impossible de retourner à la liste des congés.");
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}