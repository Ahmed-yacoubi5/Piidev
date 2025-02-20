package com.esprit.Controllers;

import com.esprit.models.Candidat;
import com.esprit.services.CandidatService;
import com.esprit.utils.AppData;
import com.esprit.utils.IdUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.esprit.utils.IdUtil.getExistingIdsFromDatabase;
import static com.esprit.utils.IdUtil.generateUniqueRandomId;
import static java.lang.Integer.parseInt;

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
                        int enteredId = parseInt(newValue);
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

    public void ajouterCandidat(javafx.event.ActionEvent actionEvent) throws SQLException, IOException {
        CandidatService cs = new CandidatService();
        if (!IdUtil.getExistingIdsFromDatabase().contains(parseInt(this.id.getText()))) {
            cs.ajouter(new Candidat(nom.getText(), prenom.getText(), email.getText(), cv.getText(), Integer.valueOf(id.getText())));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Candidat ajoutée avec success");
            alert.showAndWait();
            System.out.println(IdUtil.getExistingIdsFromDatabase());
            AppData.getInstance().setPendingId(Integer.valueOf(id.getText()));

            Alert proceedAlert = new Alert(Alert.AlertType.CONFIRMATION);
            proceedAlert.setTitle("Proceed to profile setup ?");
            proceedAlert.setHeaderText(null);
            proceedAlert.setContentText("Do you want to finish setting up a profile for this user ?");
            proceedAlert.showAndWait();
            if (proceedAlert.getResult() == ButtonType.OK) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileSetup.fxml"));
                AnchorPane newPane = loader.load();

                Stage currentStage = (Stage) email.getScene().getWindow();
                currentStage.setScene(new Scene(newPane));
                currentStage.show();
            }
            else{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SummaryView.fxml"));
                AnchorPane newPane = loader.load();

                Stage currentStage = (Stage) email.getScene().getWindow();
                currentStage.setScene(new Scene(newPane));
                currentStage.show();
            }

        } else {
            Alert idAlert = new Alert(Alert.AlertType.ERROR);
            idAlert.setTitle("Erreur");
            idAlert.setHeaderText(null);
            idAlert.setContentText("Id already exists.");
            idAlert.showAndWait();
        }

    }

    public void autoId() throws SQLException {
        List<Integer> usedId = getExistingIdsFromDatabase();
        String autogen = String.valueOf(generateUniqueRandomId(usedId));
        this.id.setText(autogen);
    }
}
