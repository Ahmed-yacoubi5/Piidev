package com.esprit.controllers;

import com.esprit.models.absence;
import com.esprit.services.ServiceAbsence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.List;

public class ListAbsenceController {

    @FXML
    private TableColumn<absence, Integer> ColumnEmployeeId;

    @FXML
    private TableColumn<absence, String> ColumnDateDebut;

    @FXML
    private TableColumn<absence, String> ColumnDateFin;

    @FXML
    private TableColumn<absence, String> ColumnStatut;

    @FXML
    private TableColumn<absence, String> ColumnType;

    @FXML
    private TableView<absence> TableViewListAbsence;

    /**
     * Afficher la liste des absences.
     */
    @FXML
    void afficherAbsences() {
        try {
            ServiceAbsence serviceAbsence = new ServiceAbsence();
            List<absence> absenceList = serviceAbsence.afficher();
            ObservableList<absence> observableAbsenceList = FXCollections.observableArrayList(absenceList);

            // Lier les colonnes avec les propriétés de l'objet
            ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ColumnDateDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
            ColumnDateFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            ColumnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
            ColumnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

            TableViewListAbsence.setItems(observableAbsenceList);
        } catch (Exception e) {
            afficherAlerte("❌ Erreur", "Impossible d'afficher les absences", e.getMessage());
        }
    }

    /**
     * Initialisation automatique au chargement de la vue.
     */
    @FXML
    public void initialize() {
        afficherAbsences();
        TableViewListAbsence.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                absence selectedAbsence = TableViewListAbsence.getSelectionModel().getSelectedItem();
                if (selectedAbsence != null) {
                    ouvrirDetailsAbsence(selectedAbsence);
                }
            }
        });
    }

    /**
     * Ouvre l'interface d'ajout d'absence.
     */
    @FXML
    void ButtonActionAjouterAbsence(ActionEvent event) {
        changerScene("/AjouterAbsence.fxml", "➕ Ajouter une Absence");
    }

    /**
     * Ouvre les détails d'une absence sélectionnée.
     */
    private void ouvrirDetailsAbsence(absence absence) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsAbsence.fxml"));
            Parent root = loader.load();

            DetailsAbsenceController dac = loader.getController();
            dac.setResultatType(absence.getType());
            dac.setResultatDateDebut(absence.getDatedebut());
            dac.setResultatDateFin(absence.getDatefin());
            dac.setResultatEmployeeID(absence.getEmployee_id());
            dac.setResultatStatut(absence.getStatut());

            TableViewListAbsence.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur d'ouverture", "Impossible d'ouvrir les détails", e.getMessage());
        }
    }

    /**
     * Retour au menu principal.
     */
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        changerScene("/Home.fxml", "🏡 Menu Principal");
    }

    /**
     * Méthode utilitaire pour changer de scène.
     */
    private void changerScene(String fxmlPath, String titre) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            TableViewListAbsence.getScene().setRoot(root);
            System.out.println("✅ Changement vers : " + titre);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur de navigation", "Impossible de charger l'interface", e.getMessage());
        }
    }

    /**
     * Affiche une alerte avec le message d'erreur.
     */
    private void afficherAlerte(String titre, String enTete, String contenu) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(enTete);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
