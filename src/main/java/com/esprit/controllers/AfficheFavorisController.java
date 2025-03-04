package com.recrutement.controllers;

import com.recrutement.models.offreemploi;
import com.recrutement.services.OffreEmploiServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AfficheFavorisController {

    @FXML
    private TableView<offreemploi> tableViewFavoris;
    @FXML
    private TableColumn<offreemploi, String> colTitre;
    @FXML
    private TableColumn<offreemploi, String> colDescription;
    @FXML
    private TableColumn<offreemploi, String> colDatePublication;
    @FXML
    private TableColumn<offreemploi, String> colStatut;

    private OffreEmploiServices offreService = new OffreEmploiServices();

    @FXML
    public void initialize() {
        // Initialiser les colonnes du TableView
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDatePublication.setCellValueFactory(new PropertyValueFactory<>("date_publication"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Charger les données depuis le service
        List<offreemploi> favoris = offreService.getFavoris();
        tableViewFavoris.setItems(FXCollections.observableArrayList(favoris));
    }
    public void setOffreService(OffreEmploiServices offreService) {
        this.offreService = offreService;
        // Recharger les données après avoir défini le service
        List<offreemploi> favoris = offreService.getFavoris();
        tableViewFavoris.setItems(FXCollections.observableArrayList(favoris));
    }
    public void refreshFavoris() {
        List<offreemploi> favoris = offreService.getFavoris();
        tableViewFavoris.setItems(FXCollections.observableArrayList(favoris));
    }

    @FXML
    void handleRetour(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficheOffreemploi.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}