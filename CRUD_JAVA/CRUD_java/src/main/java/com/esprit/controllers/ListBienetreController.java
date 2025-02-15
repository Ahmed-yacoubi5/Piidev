package com.esprit.controllers;

import com.esprit.models.bienetre;
import com.esprit.services.ServiceBienetre;
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

public class ListBienetreController {

    @FXML
    private TableColumn<bienetre, String> ColumnNom;

    @FXML
    private TableColumn<bienetre, Integer> ColumnRate;

    @FXML
    private TableColumn<bienetre, String> ColumnReview;

    @FXML
    private TableView<bienetre> TableViewListBienetre;

    // Bouton pour ajouter un nouveau bien-être
    @FXML
    void ButtonActionAjouterBienetre(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterBienetre.fxml"));
            Parent root = loader.load();
            TableViewListBienetre.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture de l'interface d'ajout : " + e.getMessage());
        }
    }

    // Afficher la liste des bien-êtres
    @FXML
    void afficherBienetres() {
        ServiceBienetre serviceBienetre = new ServiceBienetre();
        List<bienetre> bienetreList = serviceBienetre.afficher();

        ObservableList<bienetre> observableBienetreList = FXCollections.observableArrayList(bienetreList);

        // Liaison des colonnes avec les attributs de l'objet Bienetre
        ColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ColumnRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        ColumnReview.setCellValueFactory(new PropertyValueFactory<>("review"));

        TableViewListBienetre.setItems(observableBienetreList);
    }

    @FXML
    public void initialize() {
        afficherBienetres();

        // Double-clic sur une ligne pour afficher les détails
        TableViewListBienetre.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                bienetre selectedBienetre = TableViewListBienetre.getSelectionModel().getSelectedItem();
                if (selectedBienetre != null) {
                    ouvrirDetailsBienetre(selectedBienetre);
                }
            }
        });
    }

    // Ouvrir les détails d'un bien-être
    private void ouvrirDetailsBienetre(bienetre bienetre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/DetailsBienetre.fxml"));
            Parent root = loader.load();

            DetailsBienetreController dbc = loader.getController();
            dbc.setResultatNom(bienetre.getNom());
            dbc.setResultatRate(bienetre.getRate());
            dbc.setResultatReview(bienetre.getReview());

            TableViewListBienetre.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture des détails : " + e.getMessage());
        }
    }
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Home.fxml"));
            TableViewListBienetre.getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println("❌ Erreur lors du retour au menu : " + e.getMessage());
        }
    }

}
