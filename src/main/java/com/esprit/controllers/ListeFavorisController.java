package com.esprit.controllers;

import com.esprit.models.offreemploi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class ListeFavorisController {
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

    private ObservableList<offreemploi> favoris;

    @FXML
    public void initialize() {
        // Initialiser les colonnes
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDatePublication.setCellValueFactory(new PropertyValueFactory<>("date_publication"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // Charger les favoris
        favoris = FXCollections.observableArrayList();
        tableViewFavoris.setItems(favoris);
    }

    public void setFavoris(List<offreemploi> favoris) {
        this.favoris.setAll(favoris);
    }

    @FXML
    void handleRetour() {
        Stage stage = (Stage) tableViewFavoris.getScene().getWindow();
        stage.close();
    }
}