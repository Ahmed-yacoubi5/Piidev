package com.esprit.controllers;

import com.esprit.models.conges;
import com.esprit.services.ServiceConges;
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

    // Bouton pour ajouter un nouveau congé
    @FXML
    void ButtonActionAjouterConges(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterConges.fxml"));
            Parent root = loader.load();
            TableViewListConges.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture de l'interface d'ajout : " + e.getMessage());
        }
    }

    // Afficher la liste des congés
    @FXML
    void afficherConges() {
        ServiceConges serviceConges = new ServiceConges();
        List<conges> congesList = serviceConges.afficher();  // Assurez-vous que la méthode afficher() existe

        ObservableList<conges> observableCongesList = FXCollections.observableArrayList(congesList);

        // Liaison des colonnes avec les attributs de l'objet conges
        ColumnType.setCellValueFactory(new PropertyValueFactory<>("type"));
        ColumnDateDebut.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
        ColumnDateFin.setCellValueFactory(new PropertyValueFactory<>("datefin"));
        ColumnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        ColumnStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        TableViewListConges.setItems(observableCongesList);
    }

    @FXML
    public void initialize() {
        afficherConges();

        // Double-clic sur une ligne pour afficher les détails
        TableViewListConges.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                conges selectedConges = TableViewListConges.getSelectionModel().getSelectedItem();
                if (selectedConges != null) {
                    ouvrirDetailsConges(selectedConges);
                }
            }
        });
    }

    // Ouvrir les détails d'un congé
    private void ouvrirDetailsConges(conges conges) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsConges.fxml"));
            Parent root = loader.load();

            DetailsCongesController dcc = loader.getController();
            dcc.setResultatType(conges.getType());
            dcc.setResultatDateDebut(conges.getDatedebut());
            dcc.setResultatDateFin(conges.getDatefin());
            dcc.setResultatEmployeeID(conges.getEmployee_id());
            dcc.setResultatStatut(conges.getStatut());

            TableViewListConges.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture des détails : " + e.getMessage());
        }
    }
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            TableViewListConges.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du retour au menu : " + e.getMessage());
        }
    }

}
