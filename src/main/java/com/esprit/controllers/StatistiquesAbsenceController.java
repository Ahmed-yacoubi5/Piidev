package com.esprit.controllers;

import com.esprit.services.ServiceAbsence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import java.io.IOException;
import java.util.Map;

public class StatistiquesAbsenceController {

    @FXML
    private PieChart pieChart;

    /**
     * Initialise les statistiques lors du chargement.
     */
    @FXML
    public void initialize() {
        afficherStatistiques();
    }

    /**
     * Récupère les statistiques des absences et les affiche sous forme de graphique.
     */
    private void afficherStatistiques() {
        ServiceAbsence serviceAbsence = new ServiceAbsence();
        Map<String, Integer> stats = serviceAbsence.getStatistiquesAbsences();

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            data.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChart.setData(data);
    }

    /**
     * Retourner à la liste des absences.
     */
    @FXML
    void ButtonActionRetour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListAbsence.fxml"));
            pieChart.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur", "Impossible de retourner à la liste des absences.");
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
