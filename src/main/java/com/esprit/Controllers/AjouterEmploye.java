package com.esprit.Controllers;

import com.esprit.models.Employe;
import com.esprit.services.EmployeService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;

import java.awt.*;

public class AjouterEmploye {
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField poste;
    @FXML
    private DatePicker dateEmbauche;

    void ajouterEmploye() throws IOException {
        LocalDate ld= dateEmbauche.getValue();
        Date sqldate = Date.valueOf(ld);
        EmployeService es = new EmployeService();
        es.ajouter(new Employe(Integer.valueOf(id.getText()),nom.getText(),prenom.getText(), email.getText(), poste.getText(),sqldate));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Personne ajoutée");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeCreationForm.fxml"));
        Parent root = (Parent) loader.load();
        AjouterCandidat controller = loader.getController();

    }
}
