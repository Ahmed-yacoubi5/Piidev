package com.esprit.controllers;

import com.esprit.models.bienetre;
import com.esprit.services.ServiceBienetre;
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

public class ListBienetreController {

    @FXML
    private TableColumn<bienetre, String> ColumnNom;

    @FXML
    private TableColumn<bienetre, Integer> ColumnRate;

    @FXML
    private TableColumn<bienetre, String> ColumnReview;

    @FXML
    private TableView<bienetre> TableViewListBienetre;

    /**
     * 🆕 Afficher la liste des bien-êtres.
     */
    @FXML
    void afficherBienetres() {
        try {
            ServiceBienetre serviceBienetre = new ServiceBienetre();
            List<bienetre> bienetreList = serviceBienetre.afficher();
            ObservableList<bienetre> observableBienetreList = FXCollections.observableArrayList(bienetreList);

            // ⚙️ Liaison des colonnes
            ColumnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            ColumnRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
            ColumnReview.setCellValueFactory(new PropertyValueFactory<>("review"));

            TableViewListBienetre.setItems(observableBienetreList);
        } catch (Exception e) {
            afficherAlerte("❌ Erreur", "Impossible d'afficher les bien-êtres", e.getMessage());
        }
    }

    /**
     * 🔄 Initialisation automatique.
     */
    @FXML
    public void initialize() {
        afficherBienetres();

        // 🖱️ Double-clic pour afficher les détails
        TableViewListBienetre.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                bienetre selectedBienetre = TableViewListBienetre.getSelectionModel().getSelectedItem();
                if (selectedBienetre != null) {
                    ouvrirDetailsBienetre(selectedBienetre);
                }
            }
        });
    }

    /**
     * ➕ Ouvrir l'interface d'ajout.
     */
    @FXML
    void ButtonActionAjouterBienetre(ActionEvent event) {
        changerScene("/AjouterBienetre.fxml", "➕ Ajouter un Bien-être");
    }

    /**
     * 🔍 Ouvrir les détails d'un bien-être.
     */
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
            afficherAlerte("⚠️ Erreur d'ouverture", "Impossible d'ouvrir les détails", e.getMessage());
        }
    }

    /**
     * 🔙 Retour au menu principal.
     */
    @FXML
    void ButtonActionRetourMenu(ActionEvent event) {
        changerScene("/Home.fxml", "🏡 Menu Principal");
    }

    /**
     * 🌐 Méthode générique pour changer de scène.
     */
    private void changerScene(String cheminFXML, String titre) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(cheminFXML));
            TableViewListBienetre.getScene().setRoot(root);
            System.out.println("✅ Changement vers : " + titre);
        } catch (IOException e) {
            afficherAlerte("⚠️ Erreur de navigation", "Impossible de charger l'interface", e.getMessage());
        }
    }

    /**
     * 🚨 Affiche une alerte informative.
     */
    private void afficherAlerte(String titre, String enTete, String contenu) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(enTete);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
