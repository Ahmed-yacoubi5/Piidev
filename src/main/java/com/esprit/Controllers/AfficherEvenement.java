package com.esprit.Controllers;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;

public class AfficherEvenement {
    private final EvenementService evenementService = new EvenementService();

    @FXML
    private TableView<Evenement> affichertable;
    @FXML
    private TableColumn<Evenement, String> nom;
    @FXML
    private TableColumn<Evenement, String> type;
    @FXML
    private TableColumn<Evenement, String> titre;
    @FXML
    private TableColumn<Evenement, java.sql.Date> dateDebut;
    @FXML
    private TableColumn<Evenement, java.sql.Date> dateFin;

    private final ObservableList<Evenement> evenementList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        dateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

        loadData();
    }

    private void loadData() {
        evenementList.setAll(evenementService.getAllEvenements());
        affichertable.setItems(evenementList);
    }

    public void refreshTable() {
        evenementList.setAll(evenementService.getAllEvenements());
        affichertable.refresh();
    }

    @FXML
    void ajouterEvenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ajevent.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter Événement");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTable();
        } catch (IOException e) {
            afficherAlerte("Erreur", "Impossible de charger la fenêtre d'ajout.");
            e.printStackTrace();
        }
    }

    @FXML
    void modifierEvenement(ActionEvent event) {
        Evenement selectedEvent = affichertable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/modifierevent.fxml"));
                Parent root = loader.load();

                ModifierEvenement controller = loader.getController();
                controller.setEvenement(selectedEvent); // Remplissage des champs avec l'événement sélectionné

                Stage stage = new Stage();
                stage.setTitle("Modifier Événement");
                stage.setScene(new Scene(root));
                stage.showAndWait();

                refreshTable(); // Rafraîchir la table après modification
            } catch (IOException e) {
                afficherAlerte("Erreur", "Impossible de charger la fenêtre de modification.");
                e.printStackTrace();
            }
        } else {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement à modifier.");
        }
    }

    @FXML
    void supprimer_event(ActionEvent event) {
        Evenement selectedEvent = affichertable.getSelectionModel().getSelectedItem();
        if (selectedEvent != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Suppression d'un événement");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet événement ?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                evenementService.supprimer(selectedEvent);
                afficherAlerte("Succès", "Événement supprimé avec succès !");
                refreshTable();
            }
        } else {
            afficherAlerte("Erreur", "Veuillez sélectionner un événement à supprimer.");
        }
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.show();
    }
}
