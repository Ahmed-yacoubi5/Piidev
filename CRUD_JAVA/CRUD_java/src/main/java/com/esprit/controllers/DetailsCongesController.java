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
        // Récupération et validation des données modifiées
        String type = ResultatType.getText().trim();
        String dateDebut = ResultatDateDebut.getText().trim();
        String dateFin = ResultatDateFin.getText().trim();
        String statut = ResultatStatut.getText().trim();

        // Validation et conversion de l'ID de l'employé
        int employeeId;
        try {
            employeeId = Integer.parseInt(ResultatEmployeeID.getText().trim());
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "L'ID de l'employé doit être un nombre valide.");
            return;
        }

        // Vérification du format des dates
        if (!dateDebut.matches("\\d{4}-\\d{2}-\\d{2}") || !dateFin.matches("\\d{4}-\\d{2}-\\d{2}")) {
            afficherAlerte("Erreur", "Les dates doivent être au format 'yyyy-MM-dd'.");
            return;
        }

        // Création et modification du congé
        conges updatedConges = new conges(type, dateDebut, dateFin, employeeId, statut);
        ServiceConges serviceConges = new ServiceConges();
        serviceConges.modifier(updatedConges);

        // Confirmation et redirection
        afficherAlerte("Succès", "Le congé a été modifié avec succès !");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListConges.fxml"));
            ResultatType.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    @FXML
    void ButtonActionSupprimer(ActionEvent event) {
        // Confirmation de suppression
        ServiceConges serviceConges = new ServiceConges();
        serviceConges.supprimer(employeeId);

        // Confirmation et redirection
        afficherAlerte("Succès", "Le congé a été supprimé avec succès !");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListConges.fxml"));
            ResultatType.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors de la redirection après suppression : " + e.getMessage());
        }
    }

    // Méthode utilitaire pour afficher des alertes
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
