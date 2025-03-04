package com.esprit.Controllers;

import com.esprit.models.Candidat;
import com.esprit.models.Employe;
import com.esprit.services.CandidatService;
import com.esprit.services.EmployeService;
import com.esprit.utils.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class FeaturesController implements Initializable {
    @FXML
    private ChoiceBox<String> choiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceBox.setItems(FXCollections.observableArrayList("Candidat", "Employe"));
        choiceBox.setValue("Employe");
    }
    public void exportToCsv() {
        String fileName = "csv/" + choiceBox.getValue() + String.valueOf(LocalDate.now()) + ".csv";
        List<Object> items = new ArrayList<>();

        if (choiceBox.getValue().equals("Candidat")) {
            CandidatService candidatService = new CandidatService();
            items = (List<Object>) (List<?>) candidatService.rechercher();
        } else {
            EmployeService employeService = new EmployeService();
            items = (List<Object>) (List<?>) employeService.rechercher();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Object item : items) {
                if (item instanceof Employe) {
                    Employe employe = (Employe) item;
                    writer.write("NOM :");
                    writer.write(employe.getNom());
                    writer.write(", PRENOM :");
                    writer.write(employe.getPrenom());
                    writer.write(", EMAIL :");
                    writer.write(employe.getEmail());
                    writer.write(", POSTE :");
                    writer.write(employe.getPoste());
                    writer.newLine();
                } else if (item instanceof Candidat) {
                    Candidat candidat = (Candidat) item;
                    writer.write("NOM :");
                    writer.write(candidat.getNom());
                    writer.write(", PRENOM :");
                    writer.write(candidat.getPrenom());
                    writer.write(", EMAIL :");
                    writer.write(candidat.getEmail());
                    writer.newLine();
                }
            }
            System.out.println("Data exported to CSV file successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            }
        }
        public void redirectToTopEmp() throws IOException {
            AppData.getInstance().setSelectedCandidatId(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TopEmp.fxml"));
            AnchorPane newPane = loader.load();


            // Get the current stage and switch the scene
            Stage currentStage = (Stage) choiceBox.getScene().getWindow();
            currentStage.setScene(new Scene(newPane));
            currentStage.show();
        }
    }
