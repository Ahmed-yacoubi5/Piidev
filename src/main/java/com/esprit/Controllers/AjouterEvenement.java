package com.esprit.Controllers;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AjouterEvenement {
    private EvenementService evenementService = new EvenementService();

    @FXML
    private TextField tfNom;
    @FXML
    private TextField tfType;
    @FXML
    private TextField tfTitre;
    @FXML
    private DatePicker dpDateDebut;
    @FXML
    private DatePicker dpDateFin;

    @FXML
    public void ajouterEvenement() {
        Evenement evenement = new Evenement();
        evenement.setNom(tfNom.getText());
        evenement.setType(tfType.getText());
        evenement.setTitre(tfTitre.getText());
        evenement.setDateDebut(java.sql.Date.valueOf(dpDateDebut.getValue()));
        evenement.setDateFin(java.sql.Date.valueOf(dpDateFin.getValue()));
        evenementService.ajouter(evenement);
        System.out.println("Événement ajouté");
    }
}
