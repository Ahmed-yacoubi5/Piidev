package com.esprit.controllers;

import com.esprit.models.Candidat;
import com.esprit.services.CandidatService;
import com.esprit.services.IService;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import static com.esprit.utils.IdUtil.generateUniqueRandomId;
import static com.esprit.utils.IdUtil.getExistingIdsFromDatabase;
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
       id.setVisible(false);
       generateId.setVisible(false);
        try {
            autoId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
        IService<Candidat> cs = new CandidatService();
        if (!IdUtil.getExistingIdsFromDatabase().contains(parseInt(this.id.getText()))) {
            Candidat candidat = new Candidat(nom.getText(), prenom.getText(), email.getText(), cv.getText(), Integer.valueOf(id.getText()));
            cs.ajouter(candidat);

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
    public void uploadCv() {
        // Create a file chooser to select an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        // Open the file chooser and wait for the user to select a file
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Generate the destination filename using the current ID
                String fileName = id.getText() + getFileExtension(selectedFile);

                // Define the destination path
                File destinationFile = new File("profile/Cv/" + fileName);

                // Create the directories if they do not exist
                destinationFile.getParentFile().mkdirs();

                // Copy the selected file to the destination directory
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println(destinationFile.getAbsolutePath());

                // Update the cv field with the file path
                cv.setText("profile/cv/"+String.valueOf(id.getText()));

            } catch (IOException e) {
                System.out.println("Error copying the image: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected.");
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        return (index > 0) ? fileName.substring(index) : "";
    }
}
