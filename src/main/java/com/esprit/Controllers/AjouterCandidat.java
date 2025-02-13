package com.esprit.Controllers;
import com.esprit.models.Candidat;
import com.esprit.services.CandidatService;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
public class AjouterCandidat {
    @FXML
    private TextField id;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField cv;

    void ajouterCandidat(ActionEvent event) throws IOException {
        CandidatService cs = new CandidatService();
        cs.ajouter(new Candidat( nom.getText(), prenom.getText(), email.getText(), cv.getText(),Integer.valueOf(id.getText())));


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Personne ajoutée");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CandidatCreationForm.fxml"));
        Parent root = (Parent) loader.load();
        AjouterCandidat controller = loader.getController();

    }
}
