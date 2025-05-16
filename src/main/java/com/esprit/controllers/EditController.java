package com.esprit.controllers;

import com.esprit.models.Candidat;
import com.esprit.models.Employe;
import com.esprit.services.CandidatService;
import com.esprit.services.EmployeService;
import com.esprit.services.IService;
import com.esprit.utils.AppData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;


public class EditController {
    @FXML
    private TextField id, nom, prenom, email, poste;
    @FXML
    private Pane candPane,empPane;
    @FXML
    private DatePicker dateEmbauche;
    @FXML
    private Button submitButton;
    private boolean modified = false;
    private boolean isemp=false;
    private String cv;
    public void initialize() {
        id.setVisible(false);
        submitButton.setDisable(true);
        fillFields();
        addChangeListeners();
    }
    public void fillFields() {
        if (AppData.getInstance().getSelectedCandidatId() == 0) {
            candPane.setVisible(false);
            isemp=true;
            Employe employe = new Employe();
            employe.setId(AppData.getInstance().getCurrentSelectedId());
            EmployeService employeService = new EmployeService();
            employeService.empAff(employe);
            id.setText(String.valueOf(employe.getId()));
            nom.setText(employe.getNom());
            prenom.setText(employe.getPrenom());
            email.setText(employe.getEmail());
            poste.setText(employe.getPoste());
            dateEmbauche.setValue(employe.GetDateEmbauche().toLocalDate());
        } else if (AppData.getInstance().getCurrentSelectedId() == 0) {
            empPane.setVisible(false);
            Candidat candidat = new Candidat();
            candidat.setId(AppData.getInstance().getSelectedCandidatId());
            IService<Candidat> candidatService = new CandidatService();
            ((CandidatService) candidatService).candidatAff(candidat);
            id.setText(String.valueOf(candidat.getId()));
            nom.setText(candidat.getNom());
            prenom.setText(candidat.getPrenom());
            email.setText(candidat.getEmail());
            dateEmbauche.setDisable(true);
            cv=candidat.getCv();
        }
    }

    private void addChangeListeners() {
        ChangeListener<String> listener = new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (oldValue != newValue) {
                    modified = true;
                    System.out.println("Text field content changed: " + newValue);
                    submitButton.setDisable(false);
                }
                else {
                    submitButton.setDisable(true);
                }
            }
        };
        id.textProperty().addListener(listener);
        nom.textProperty().addListener(listener);
        prenom.textProperty().addListener(listener);
        email.textProperty().addListener(listener);
        poste.textProperty().addListener(listener);
    }
    public void submit(){
        if (modified) {
            if(!isemp) {
                Candidat candidat = new Candidat(nom.getText(),prenom.getText(),email.getText(),cv,parseInt(id.getText()));
                IService<Candidat> candidatService = new CandidatService();
                try {
                    candidatService.modifier(candidat);
                } catch (SQLException e) {
                    System.out.println("Error modifying candidat: " + e.getMessage());
                }
            }
            else{
                Employe employe = new Employe(parseInt(id.getText()),nom.getText(),prenom.getText(),email.getText(), poste.getText(), Date.valueOf(dateEmbauche.getValue()));
                EmployeService employeService = new EmployeService();
                employeService.empAff(employe);
            }
        }
    }
    public void newCv() {
        // Create a file chooser to select a file
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

                // Update the cv field with the file path
                String filePath = destinationFile.getAbsolutePath();
                cv = filePath;
                modified=true;
                System.out.println("CV uploaded successfully. File path: " + cv);

            } catch (IOException e) {
                System.out.println("Error copying the CV: " + e.getMessage());
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
