package com.recrutement.controllers;

import com.recrutement.models.offreemploi;
import com.recrutement.services.OffreEmploiServices;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent; // Utilisation de ActionEvent pour le bouton Rechercher

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AfficheOffreemploi {

    @FXML
    public Button btnRetour;
    public Button btnTrier;
    public TextField txtRecherche;
    public Button btnRechercher;
    @FXML
    private ListView<offreemploi> listViewOffres; // Changez le type de ListView à offreemploi

    @FXML
    public Button btnModifier;

    @FXML
    public Button btnSupprimer;

    private OffreEmploiServices offreService = new OffreEmploiServices();

    // Méthode pour définir la liste des offres
    public void setOffre(List<offreemploi> offres) {
        listViewOffres.getItems().clear(); // Vider la ListView avant d'ajouter de nouveaux éléments
        listViewOffres.getItems().addAll(offres); // Ajouter toutes les offres à la ListView
    }

    // Action pour le bouton retour
    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutOffreemploi.fxml"));
        Parent root = loader.load();

        // Récupérer la fenêtre actuelle et modifier la scène
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Action pour le bouton modifier
    @FXML
    void handleModifier(ActionEvent event) throws IOException {
        offreemploi selectedOffre = listViewOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une offre à modifier.");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifieroffreemploi.fxml"));
        Parent root = loader.load();

        ModifierOffreEmploiController controller = loader.getController();
        controller.setOffreToModify(selectedOffre);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modifier Offre d'Emploi");
        stage.show();
    }

    // Action pour le bouton Trier
    @FXML
    void handleTrier(ActionEvent event) {
        ObservableList<offreemploi> offres = listViewOffres.getItems(); // Récupérer les éléments de la ListView
        if (offres != null && !offres.isEmpty()) {
            // Trier les offres par titre en utilisant un Comparator
            offres.sort((o1, o2) -> o1.getTitre().compareTo(o2.getTitre()));

            // Réafficher les offres triées dans la ListView
            listViewOffres.setItems(offres);
        }
    }

    // Action pour le bouton Rechercher
    @FXML
    void handleRechercher(ActionEvent event) {
        String searchTerm = txtRecherche.getText().toLowerCase();  // Récupérer le texte du champ de recherche
        List<offreemploi> filteredOffres = new ArrayList<>();

        if (!searchTerm.isEmpty()) {
            // Filtrer les offres si le champ n'est pas vide
            for (offreemploi offre : listViewOffres.getItems()) {
                if (offre.getTitre().toLowerCase().contains(searchTerm)) {
                    filteredOffres.add(offre);
                }
            }
        } else {
            // Si le champ est vide, réinitialiser la liste des offres
            filteredOffres = new ArrayList<>(offreService.getAllOffres()); // Réinitialisez la liste complète des offres
        }

        // Mettre à jour la ListView avec les offres filtrées ou complètes
        listViewOffres.setItems(FXCollections.observableArrayList(filteredOffres));
    }

    // Action pour le bouton supprimer
    @FXML
    void handleSupprimer(ActionEvent event) {
        offreemploi selectedOffre = listViewOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une offre à supprimer.");
            alert.show();
            return;
        }

        // Demander confirmation avant suppression
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setContentText("Voulez-vous vraiment supprimer cette offre ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                offreService.supprimer(selectedOffre);
                listViewOffres.getItems().remove(selectedOffre); // Retirer l'offre de la ListView
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setContentText("Offre supprimée avec succès !");
                success.show();
            }
        });
    }
}
