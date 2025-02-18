package com.esprit.Controllers;

import com.esprit.models.Profil;
import com.esprit.services.ProfilService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.IOException;

public class RemplirProfil {
    @FXML
    private TextField tfid;
    @FXML
    private TextField niveauFormation;
    @FXML
    private TextField Competence;
    @FXML
    private TextField experience;
    @FXML
    private TextField certification;

    public void ajouterProfil(javafx.event.ActionEvent actionEvent) throws IOException{

        int id = Integer.parseInt(tfid.getText());
        ProfilService ps = new ProfilService();
        ps.ajouter(new Profil(id,niveauFormation.getText(),Competence.getText(),experience.getText(),certification.getText()));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Personne ajoutée");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileForm.fxml"));
        Parent root = (Parent) loader.load();
        AjouterCandidat controller = loader.getController();


    }
    }

