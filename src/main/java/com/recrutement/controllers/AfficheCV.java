package com.recrutement.controllers;

import com.recrutement.models.cv;
import com.recrutement.models.offreemploi;
import com.recrutement.services.CvServices;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AfficheCV {

    public Button btnRechercher;
    public TextField txtRecherche;
    public Button btnTrier;
    @FXML
    private ListView<cv> listViewCVs; // Liste des CVs

    @FXML
    public Button btnModifier;

    @FXML
    public Button btnSupprimer;
    @FXML
    public Button btnRetour;
    private CvServices cvService = new CvServices();

    // Méthode pour définir la liste des CVs
    public void setCVs(List<cv> cvs) {
        listViewCVs.getItems().clear(); // Vider la ListView avant d'ajouter de nouveaux éléments
        listViewCVs.getItems().addAll(cvs); // Ajouter tous les CVs à la ListView
    }
    public void setcv(List<cv> cvs) {
        listViewCVs.getItems().clear(); // Vider la ListView avant d'ajouter de nouveaux éléments
        listViewCVs.getItems().addAll(cvs); // Ajouter toutes les offres à la ListView
    }
    // Action pour le bouton retour
    @FXML
    void handleRetour(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCV.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Action pour le bouton modifier
    @FXML
    void handleModifier(ActionEvent event) throws IOException {
        cv selectedCV = listViewCVs.getSelectionModel().getSelectedItem();
        if (selectedCV == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un CV à modifier.");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifiercv.fxml"));
        Parent root = loader.load();

        ModifierCVController controller = loader.getController();
        controller.setCV(selectedCV);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Modifier CV");
        stage.show();
    }
    @FXML
    void handleTrier(ActionEvent event) {
        // Récupérer la liste des CVs dans la ListView
        List<cv> cvList = listViewCVs.getItems();

        // Trier les CVs par nom (en supposant que la méthode getNom() existe)
        cvList.sort((cv1, cv2) -> cv1.getNom().compareTo(cv2.getNom()));

        // Mettre à jour la ListView avec les CVs triés
        listViewCVs.setItems(FXCollections.observableArrayList(cvList));
    }
    @FXML
    void handleRechercher(ActionEvent event) {
        String searchTerm = txtRecherche.getText().toLowerCase();  // Récupérer le texte du champ de recherche
        List<cv> filteredCVs = new ArrayList<>();

        if (!searchTerm.isEmpty()) {
            // Si le champ n'est pas vide, filtrer les CVs par nom
            for (cv cv : listViewCVs.getItems()) {
                if (cv.getNom().toLowerCase().contains(searchTerm)) {
                    filteredCVs.add(cv);
                }
            }
        } else {
            // Si le champ est vide, réinitialiser la liste avec tous les CVs
            filteredCVs = cvService.getAllCVs(); // ou liste complète des CVs que vous avez
        }

        // Mettre à jour la ListView avec les CVs filtrés ou tous les CVs
        listViewCVs.setItems(FXCollections.observableArrayList(filteredCVs));
    }


    // Action pour le bouton supprimer
    @FXML
    void handleSupprimer(ActionEvent event) {
        cv selectedCV = listViewCVs.getSelectionModel().getSelectedItem();
        if (selectedCV == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un CV à supprimer.");
            alert.show();
            return;
        }

        // Demander confirmation avant suppression
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setContentText("Voulez-vous vraiment supprimer ce CV ?");

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                cvService.supprimer(selectedCV);
                listViewCVs.getItems().remove(selectedCV); // Retirer le CV de la ListView
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setTitle("Succès");
                success.setContentText("CV supprimé avec succès !");
                success.show();
            }
        });
    }
}
