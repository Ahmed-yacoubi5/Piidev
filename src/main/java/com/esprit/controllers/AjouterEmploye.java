package com.esprit.controllers;

import com.esprit.models.Employe;
import com.esprit.services.EmployeService;
import com.esprit.utils.AppData;
import com.esprit.utils.IdUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static com.esprit.utils.IdUtil.*;
import static java.lang.Integer.parseInt;

public class AjouterEmploye implements Initializable {
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
    @FXML
    private Label errorLabel;
    @FXML
    private Button addButton;
    private Parent root;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            autoId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        id.setVisible(false);
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
                            addButton.setDisable(true);
                        } else {
                            // Reset the text field style if the ID doesn't exist
                            id.setStyle("-fx-border-color: green;");
                            errorLabel.setText("ID Available");
                            addButton.setDisable(false);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    errorLabel.setText("Please enter a valid ID");
                    addButton.setDisable(true);
                }
            }
        });
    }

    public void ajouterEmploye(javafx.event.ActionEvent actionEvent) throws IOException, SQLException {
        LocalDate ld = dateEmbauche.getValue();
        Date sqldate = Date.valueOf(ld);
        EmployeService es = new EmployeService();
        if (!IdUtil.getExistingIdsFromDatabase().contains(parseInt(this.id.getText()))) {
            es.ajouter(new Employe(Integer.valueOf(this.id.getText()), nom.getText(), prenom.getText(), email.getText(), poste.getText(), sqldate));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Employee added");
            alert.show();
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
        }
        else {
            Alert idAlert = new Alert(Alert.AlertType.ERROR);
            idAlert.setTitle("Erreur");
            idAlert.setHeaderText(null);
            idAlert.setContentText("Id already exists.");
            idAlert.showAndWait();
        }

    }
    public void autoId() throws SQLException {
        List<Integer> usedId = getExistingIdsFromDatabase();
        String autogen = String.valueOf(empGenerateUniqueRandomId(usedId));
        this.id.setText(autogen);
    }
}
