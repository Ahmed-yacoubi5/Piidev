package com.esprit.controllers;

import com.esprit.services.ServiceConges;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.chart.PieChart;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.Map;

public class StatistiquesCongesController {

    @FXML
    private PieChart pieChartConges; // Diagramme circulaire

    @FXML
    private Button btnRetour; // Bouton retour

    /**
     * Initialisation de la page : Chargement des statistiques.
     */
    @FXML
    public void initialize() {
        chargerStatistiques();
    }

    /**
     * Charge et affiche les statistiques des congés sous forme de PieChart.
     */
    private void chargerStatistiques() {
        try {
            ServiceConges serviceConges = new ServiceConges();
            Map<String, Integer> congesParStatut = serviceConges.getCongesParStatut();

            // Créer une liste d'éléments pour le PieChart
            ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

            for (Map.Entry<String, Integer> entry : congesParStatut.entrySet()) {
                pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }

            // Ajouter les données au PieChart
            pieChartConges.setData(pieChartData);
            pieChartConges.setTitle("Répartition des Congés par Statut");

        } catch (Exception e) {
            afficherAlerte("⚠️ Erreur", "Impossible de charger les statistiques.");
            e.printStackTrace(); // Debug en console
        }
    }

    /**
     * Bouton retour vers la liste des congés.
     */
    @FXML
    void ButtonActionRetour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ListConges.fxml"));
            btnRetour.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur", "Impossible de retourner à la liste des congés.");
        }
    }

    /**
     * Affiche une alerte avec un message d'erreur.
     */
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
