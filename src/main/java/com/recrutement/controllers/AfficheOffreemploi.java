package com.recrutement.controllers;

import com.recrutement.models.statut;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AfficheOffreemploi {

    @FXML
    private Label txtTitre;

    @FXML
    private Label txtDescription;

    @FXML
    private Label cmbStatut;

    @FXML
    private Label txtDatePublication; // Ajout du label pour la date

    public void setTxtTitre(String titre) {
        this.txtTitre.setText(titre);
    }

    public void setTxtDescription(String description) {
        this.txtDescription.setText(description);
    }

    public void setCmbStatut(statut statut) {
        if (statut != null) {
            this.cmbStatut.setText(statut.name());
        } else {
            this.cmbStatut.setText("N/A"); // Pour éviter une valeur null affichée
        }
    }

    public void setDatePublication(LocalDate date) {
        if (date != null) {
            this.txtDatePublication.setText(date.toString()); // Convertit LocalDate en texte
        } else {
            this.txtDatePublication.setText("Non spécifiée"); // Valeur par défaut si null
        }
    }

    @FXML
    void retourAccueil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutOffreemploi.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtTitre.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur en cas de problème
        }
    }
}
