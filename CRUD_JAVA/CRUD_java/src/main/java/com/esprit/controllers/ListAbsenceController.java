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

    // Bouton pour ajouter une nouvelle absence
    @FXML
    void ButtonActionAjouterAbsence(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterAbsence.fxml"));
            Parent root = loader.load();
            TableViewListAbsence.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture de l'interface d'ajout : " + e.getMessage());
        }
    }

    // Afficher la liste des absences
    @FXML
    void afficherAbsences() {
        ServiceAbsence serviceAbsence = new ServiceAbsence();
        List<absence> absenceList = serviceAbsence.afficher();  // Assurez-vous que la méthode afficher() existe

        ObservableList<absence> observableAbsenceList = FXCollections.observableArrayList(absenceList);

        // Liaison des colonnes avec les attributs de l'objet absence
        ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        ColumnDateDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        ColumnDateFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        ColumnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        ColumnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        TableViewListAbsence.setItems(observableAbsenceList);
    }

    @FXML
    public void initialize() {
        afficherAbsences();

        // Double-clic sur une ligne pour afficher les détails
        TableViewListAbsence.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                absence selectedAbsence = TableViewListAbsence.getSelectionModel().getSelectedItem();
                if (selectedAbsence != null) {
                    ouvrirDetailsAbsence(selectedAbsence);
                }
            }
        });
    }

    // Ouvrir les détails d'une absence
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
            System.out.println("Erreur lors de l'ouverture des détails : " + e.getMessage());
        }
    }
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            TableViewListAbsence.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du retour au menu : " + e.getMessage());
        }
    }

}
