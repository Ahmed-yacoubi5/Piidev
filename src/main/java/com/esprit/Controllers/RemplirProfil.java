package com.esprit.Controllers;

import com.esprit.models.Profil;
import com.esprit.services.ProfilService;
import javafx.fxml.FXML;

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

    void ajouterProfil() throws IOException{

        int id = Integer.parseInt(tfid.getText());
        ProfilService ps = new ProfilService();
        ps.ajouter(new Profil(id,niveauFormation.getText(),Competence.getText(),experience.getText(),certification.getText()));

    }
    }

