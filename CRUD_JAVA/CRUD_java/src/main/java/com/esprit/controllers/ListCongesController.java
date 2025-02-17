package com.esprit.controllers;

import com.esprit.models.conges;
import com.esprit.services.ServiceConges;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    /**
     * Affiche la liste des congés.
     */
    @FXML
    void afficherConges() {
        try {
            ServiceConges serviceConges = new ServiceConges();
            List<conges> congesList = serviceConges.afficher();
            ObservableList<conges> observableCongesList = FXCollections.observableArrayList(congesList);

            // Liaison des colonnes avec les propriétés
            ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
            ColumnDateDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
            ColumnDateFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
            ColumnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
            ColumnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

            TableViewListConges.setItems(observableCongesList);
        } catch (Exception e) {
            afficherAlerte("⚠️ Erreur", "Impossible d'afficher les congés", e.getMessage());
        }
    }

    /**
     * Initialisation automatique au chargement de la vue.
     */
    @FXML
    public void initialize() {
        afficherConges();
        TableViewListConges.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                conges selectedConges = TableViewListConges.getSelectionModel().getSelectedItem();
                if (selectedConges != null) {
                    ouvrirDetailsConges(selectedConges);
                }
            }
        });
    }

    /**
     * Ouvre l'interface d'ajout de congés.
     */
    @FXML
    void ButtonActionAjouterConges(ActionEvent event) {
        changerScene("/AjouterConges.fxml", "➕ Ajouter un Congé");
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

            TableViewListConges.getScene().setRoot(root);
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
            TableViewListConges.getScene().setRoot(root);
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
}
