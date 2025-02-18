package com.esprit.Controllers;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;

public class ModifierEvenement {
    private final EvenementService evenementService = new EvenementService();
    private Evenement evenement;

    @FXML
    private TextField tf_nom, tf_type, tf_titre, tf_id;
    @FXML
    private DatePicker dp_datedebut, dp_datefin;

    // Setter pour initialiser l'événement dans l'interface
    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
        tf_id.setText(String.valueOf(evenement.getId()));
        tf_nom.setText(evenement.getNom());
        tf_type.setText(evenement.getType());
        tf_titre.setText(evenement.getTitre());

        // Vérification si les dates sont nulles avant d'assigner
        if (evenement.getDateDebut() != null) {
            dp_datedebut.setValue(evenement.getDateDebut().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        }
        if (evenement.getDateFin() != null) {
            dp_datefin.setValue(evenement.getDateFin().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
        }
    }

    // Méthode pour modifier l'événement avec les nouvelles valeurs
    @FXML
    private void modifierEvenement() {
        if (evenement != null) {
            evenement.setNom(tf_nom.getText());
            evenement.setType(tf_type.getText());
            evenement.setTitre(tf_titre.getText());
            evenement.setDateDebut(Date.valueOf(dp_datedebut.getValue()));
            evenement.setDateFin(Date.valueOf(dp_datefin.getValue()));

            evenementService.modifier(evenement);
            fermerFenetre();
        }
    }

    // Méthode pour fermer la fenêtre de modification
    private void fermerFenetre() {
        ((Stage) tf_nom.getScene().getWindow()).close();
    }
}
