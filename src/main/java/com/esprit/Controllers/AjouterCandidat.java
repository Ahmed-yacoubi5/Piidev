package com.esprit.Controllers;

import com.esprit.models.Candidat;
import com.esprit.services.CandidatService;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

import static com.esprit.utils.IdUtil.getExistingIdsFromDatabase;
import static com.esprit.utils.IdUtil.generateUniqueRandomId;

public class AjouterCandidat implements Initializable {
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
    @FXML
    private Button generateId;
    @FXML
    private Label errorLabel;

    // Initialize method to add listener to the id field
    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        // Add a listener to the id TextField to check real-time changes
        id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    // Get the existing IDs from the database
                    List<Integer> existingIds = getExistingIdsFromDatabase();

                    // Check if the ID exists in the database
                    if (newValue.isEmpty()) {
                        // Clear the error if the text field is empty
                        id.setStyle("-fx-border-color: transparent;");
                        errorLabel.setText("");
                    } else {
                        int enteredId = Integer.parseInt(newValue);
                        if (existingIds.contains(enteredId)) {
                            // Set the text field to red if the ID already exists
                            id.setStyle("-fx-border-color: red;");
                            errorLabel.setText("This ID already exists.");
                        } else {
                            // Reset the text field style if the ID doesn't exist
                            id.setStyle("-fx-border-color: green;");
                            errorLabel.setText("ID Available");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    errorLabel.setText("Database error.");
                } catch (NumberFormatException e) {
                    // Handle invalid number input
                    id.setStyle("-fx-border-color: transparent;");
                    errorLabel.setText("Please enter a valid number.");
                }
            }
        });
    }

    public void ajouterCandidat(javafx.event.ActionEvent actionEvent) {
        CandidatService cs = new CandidatService();
        cs.ajouter(new Candidat(nom.getText(), prenom.getText(), email.getText(), cv.getText(), Integer.valueOf(id.getText())));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("Employe ajoutée");
        alert.show();
    }

    public void autoId() throws SQLException {
        List<Integer> usedId = getExistingIdsFromDatabase();
        String autogen = String.valueOf(generateUniqueRandomId(usedId));
        this.id.setText(autogen);
    }
}
