package com.esprit.controllers;

import com.esprit.models.conges;
import com.esprit.services.ServiceConges;
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
import java.util.List;

public class ListCongesController {

    @FXML
    private TableColumn<conges, Integer> ColumnEmployeeId;

    @FXML
    private TableColumn<conges, String> ColumnDateDebut;

    @FXML
    private TableColumn<conges, String> ColumnDateFin;

    @FXML
    private TableColumn<conges, String> ColumnStatut;

    @FXML
    private TableColumn<conges, String> ColumnType;

    @FXML
    private TableView<conges> TableViewListConges;

    @FXML
    private TextField searchField; // ✅ Ajout du champ de recherche

    private ObservableList<conges> observableCongesList;

    /**
     * Affichage de la liste des congés et activation du double-clic.
     */
    @FXML
    public void initialize() {
        afficherConges();
        configurerRecherche();

        // 📌 Ouvrir l'interface des détails en cliquant sur une ligne
        TableViewListConges.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // ✅ Double-clic pour ouvrir les détails
                conges selectedConges = TableViewListConges.getSelectionModel().getSelectedItem();
                if (selectedConges != null) {
                    ouvrirDetailsConges(selectedConges);
                }
            }
        });
    }

    /**
     * Charge et affiche les congés.
     */
    void afficherConges() {
        try {
            ServiceConges serviceConges = new ServiceConges();
            List<conges> congesList = serviceConges.afficher();
            observableCongesList = FXCollections.observableArrayList(congesList);

            // Lier les colonnes avec les propriétés
            ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ColumnDateDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
            ColumnDateFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            ColumnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
            ColumnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

            TableViewListConges.setItems(observableCongesList);
        } catch (Exception e) {
            afficherAlerte("❌ Erreur", "Impossible d'afficher les congés.");
        }
    }

    /**
     * Configuration de la recherche dynamique.
     */
    private void configurerRecherche() {
        FilteredList<conges> filteredList = new FilteredList<>(observableCongesList, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(conges -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return (conges.getType() != null && conges.getType().toLowerCase().contains(lowerCaseFilter)) ||
                        (conges.getDatedebut() != null && conges.getDatedebut().toLowerCase().contains(lowerCaseFilter)) ||
                        (conges.getDatefin() != null && conges.getDatefin().toLowerCase().contains(lowerCaseFilter)) ||
                        (conges.getStatut() != null && conges.getStatut().toLowerCase().contains(lowerCaseFilter)) ||
                        String.valueOf(conges.getEmployee_id()).contains(lowerCaseFilter);
            });
        });

        SortedList<conges> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(TableViewListConges.comparatorProperty());
        TableViewListConges.setItems(sortedList);
    }

    /**
     * Ouvre les détails d'un congé sélectionné.
     */
    private void ouvrirDetailsConges(conges conge) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsConges.fxml"));
            Parent root = loader.load();

            DetailsCongesController dcc = loader.getController();
            dcc.setResultatType(conge.getType());
            dcc.setResultatDateDebut(conge.getDatedebut());
            dcc.setResultatDateFin(conge.getDatefin());
            dcc.setResultatEmployeeID(conge.getEmployee_id());
            dcc.setResultatStatut(conge.getStatut());

            // 📌 Affichage de la nouvelle interface
            TableViewListConges.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur", "Impossible d'ouvrir les détails.");
        }
    }

    /**
     * Ouvre l'interface d'ajout de congés.
     */
    @FXML
    void ButtonActionAjouterConges(ActionEvent event) {
        changerScene("/AjouterConges.fxml", "➕ Ajouter un Congé");
    }

    /**
     * Retour au menu principal.
     */
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        changerScene("/Home.fxml", "🏡 Menu Principal");
    }

    /**
     * Change la scène.
     */
    private void changerScene(String fxmlPath, String titre) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            TableViewListConges.getScene().setRoot(root);
            System.out.println("✅ Changement vers : " + titre);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur", "Impossible de charger l'interface.");
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

    /**
     * Trie les congés par date de début en ordre croissant.
     */
    @FXML
    void ButtonActionTrierCroissant(ActionEvent event) {
        observableCongesList.sort((c1, c2) -> c1.getDatedebut().compareTo(c2.getDatedebut()));
    }

    /**
     * Trie les congés par date de début en ordre décroissant.
     */
    @FXML
    void ButtonActionTrierDecroissant(ActionEvent event) {
        observableCongesList.sort((c1, c2) -> c2.getDatedebut().compareTo(c1.getDatedebut()));
    }

    /**
     * Affiche la page des statistiques des congés.
     */
    @FXML
    void ButtonActionStatistiques(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StatistiquesConges.fxml"));
            Parent root = loader.load();
            TableViewListConges.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console pour déboguer
            afficherAlerte("⚠️ Erreur", "Impossible de charger les statistiques : " + e.getMessage());
        }
    }


}
