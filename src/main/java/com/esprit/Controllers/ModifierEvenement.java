package com.esprit.Controllers;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.sql.Date;

public class ModifierEvenement {
    private EvenementService evenementService = new EvenementService();

    @FXML
    private TextField tf_id;
    @FXML
    private TextField tf_nom;
    @FXML
    private TextField tf_type;
    @FXML
    private TextField tf_titre;
    @FXML
    private DatePicker dp_datedebut;
    @FXML
    private DatePicker dp_datefin;
    @FXML
    private TableView<Evenement> affichertable;

    @FXML
    public void initialize() {
        affichertable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFields(newSelection);
            }
        });
    }

    private void populateFields(Evenement selectedEvent) {
        tf_id.setText(String.valueOf(selectedEvent.getId()));
        tf_nom.setText(selectedEvent.getNom());
        tf_type.setText(selectedEvent.getType());
        tf_titre.setText(selectedEvent.getTitre());

        // Correction des conversions de dates en évitant LocalDate
        if (selectedEvent.getDateDebut() != null) {
            dp_datedebut.setValue(selectedEvent.getDateDebut().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        }
        if (selectedEvent.getDateFin() != null) {
            dp_datefin.setValue(selectedEvent.getDateFin().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        }
    }

    @FXML
    public void modifierEvenement() {
        Evenement evenement = new Evenement();
        evenement.setId(Integer.parseInt(tf_id.getText()));
        evenement.setNom(tf_nom.getText());
        evenement.setType(tf_type.getText());
        evenement.setTitre(tf_titre.getText());
        evenement.setDateDebut(Date.valueOf(dp_datedebut.getValue()));
        evenement.setDateFin(Date.valueOf(dp_datefin.getValue()));
        evenementService.modifier(evenement);
        System.out.println("Événement modifié");

        AfficherEvenement afficherEvenement = new AfficherEvenement();
        afficherEvenement.refreshTable();
    }

    public void setAfficherEvenementController(AfficherEvenement afficherEvenement) {
    }
}
