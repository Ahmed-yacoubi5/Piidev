package com.esprit.controllers;

import com.esprit.models.absence;
import com.esprit.services.ServiceAbsence;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
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

    @FXML
    private TextField searchField;  // ✅ Ajout du champ de recherche

    private ObservableList<absence> observableAbsenceList;

    /**
     * Afficher la liste des absences.
     */
    @FXML
    void afficherAbsences() {
        try {
            ServiceAbsence serviceAbsence = new ServiceAbsence();
            List<absence> absenceList = serviceAbsence.afficher();
            observableAbsenceList = FXCollections.observableArrayList(absenceList);

            // Lier les colonnes avec les propriétés de l'objet
            ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ColumnDateDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
            ColumnDateFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            ColumnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
            ColumnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

            // Ajout de la recherche dynamique
            rechercherAbsence();

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
     * Filtre les absences en fonction du texte saisi dans le champ de recherche.
     */
    private void rechercherAbsence() {
        FilteredList<absence> filteredList = new FilteredList<>(observableAbsenceList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(absence -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                return absence.getType().toLowerCase().contains(lowerCaseFilter)
                        || absence.getDatedebut().toLowerCase().contains(lowerCaseFilter)
                        || absence.getDatefin().toLowerCase().contains(lowerCaseFilter)
                        || absence.getStatut().toLowerCase().contains(lowerCaseFilter)
                        || String.valueOf(absence.getEmployee_id()).contains(lowerCaseFilter);
            });
        });

        SortedList<absence> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(TableViewListAbsence.comparatorProperty());
        TableViewListAbsence.setItems(sortedList);
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(enTete);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    void ButtonActionStatistiques(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/StatistiquesAbsence.fxml"));
            TableViewListAbsence.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur ", "Impossible de charger les statistques", e.getMessage());
        }
    }
    @FXML
    void ButtonActionTrierCroissant(ActionEvent event) {
        TableViewListAbsence.getSortOrder().clear(); // Réinitialiser le tri
        ColumnDateDebut.setComparator((date1, date2) -> {
            LocalDate d1 = LocalDate.parse(date1);
            LocalDate d2 = LocalDate.parse(date2);
            return d1.compareTo(d2);  // 🔹 Ordre croissant
        });
        TableViewListAbsence.getSortOrder().add(ColumnDateDebut);
    }

    @FXML
    void ButtonActionTrierDecroissant(ActionEvent event) {
        TableViewListAbsence.getSortOrder().clear(); // Réinitialiser le tri
        ColumnDateDebut.setComparator((date1, date2) -> {
            LocalDate d1 = LocalDate.parse(date1);
            LocalDate d2 = LocalDate.parse(date2);
            return d2.compareTo(d1);  // 🔹 Ordre décroissant
        });
        TableViewListAbsence.getSortOrder().add(ColumnDateDebut);
    }

}

