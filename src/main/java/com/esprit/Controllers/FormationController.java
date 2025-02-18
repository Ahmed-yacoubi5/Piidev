package com.esprit.Controllers;

import com.esprit.models.Candidat;
import com.esprit.models.Formation;
import com.esprit.services.CandidatService;
import com.esprit.services.FormationService;
import com.esprit.utils.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class FormationController {


    @FXML
    private TextField diplome;
    @FXML
    private TextField institution;
    @FXML
    private TextField anne;

    public void ajouterFormation(int id) {
        FormationService fs = new FormationService();
        fs.ajouter(new Formation(id, diplome.getText(), institution.getText(), Integer.valueOf(anne.getText())));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Formation ajoutée");
        alert.show();

    }
    public void addButton(ActionEvent actionEvent) throws IOException {
        ajouterFormation(AppData.getInstance().getCurrentSelectedId());

    }


}
