package com.esprit.Controllers;

import com.esprit.models.Employe;
import com.esprit.models.Candidat;
import com.esprit.models.Formation;
import com.esprit.services.CandidatService;
import com.esprit.services.EmployeService;
import com.esprit.services.FormationService;
import com.esprit.utils.AppData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Summary {

    @FXML
    private TableView<Employe> employeTableView;
    @FXML
    private TableView<Candidat> candidatTableView;
    @FXML
    private TableColumn<Candidat, Integer> cli;
    @FXML
    private TableColumn<Candidat, String> cln;
    @FXML
    private TableColumn<Candidat, String> clp;
    @FXML
    private TableColumn<Candidat, String> cle;
    @FXML
    private TableColumn<Employe, Integer> empId;
    @FXML
    private TableColumn<Employe, String> empNom;
    @FXML
    private TableColumn<Employe, String> empPrenom;
    @FXML
    private TableColumn<Employe, String> empEmail;
    @FXML
    private Button addButtonEmp;
    @FXML
    private Button addButtonCand;
    @FXML
    private ListView<Date> dateEmbauche;
    @FXML
    private Button modifyFormationEmploye;
    @FXML
    private ListView<Formation> employeFormationView;
    @FXML
    private Button deleteEmploye;

    private CandidatService candidatService;
    private EmployeService employeService;
    private FormationService formationService;

    public void initialize() {
        // Initialize services
        candidatService = new CandidatService();
        employeService = new EmployeService();
        formationService = new FormationService();

        // Set up the columns for the TableViews
        cli.setCellValueFactory(new PropertyValueFactory<>("id"));
        cln.setCellValueFactory(new PropertyValueFactory<>("nom"));
        clp.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        cle.setCellValueFactory(new PropertyValueFactory<>("email"));
        empId.setCellValueFactory(new PropertyValueFactory<>("id"));
        empNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        empPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        empEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Call methods to populate the TableViews
        fillCandidatListView();
        fillFormationListView();

    }

    public void fillCandidatListView() {
        List<Candidat> candidats = candidatService.rechercher();
        candidatTableView.setItems(FXCollections.observableList(candidats));
    }

    public void fillFormationListView() {
        List<Employe> employes = employeService.rechercher();
        employeTableView.setItems(FXCollections.observableList(employes));
        employeTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    handleDateEmbauche(newValue.getId());
                   AppData.getInstance().setCurrentSelectedId(newValue.getId());
                    fillEmployeFormation();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    // Handle the button click to switch to the new scene (Add employee form)
    @FXML
    public void handleAddButtonEmp() throws IOException {
        // Load the new FXML (EmployeCreationForm.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EmployeCreationForm.fxml"));
        AnchorPane newPane = loader.load();

        // Get the current stage and switch the scene
        Stage currentStage = (Stage) addButtonEmp.getScene().getWindow();
        currentStage.setScene(new Scene(newPane));
        currentStage.show();
    }

    public void handleAddButtonCand() throws IOException {
        // Load the new FXML (CandidatCreationForm.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CandidatCreationForm.fxml"));
        AnchorPane newPane = loader.load();

        // Get the current stage and switch the scene
        Stage currentStage = (Stage) addButtonCand.getScene().getWindow();
        currentStage.setScene(new Scene(newPane));
        currentStage.show();
    }

    // This method is responsible for fetching and displaying the Date of Hiring (dateEmbauche)
    public void handleDateEmbauche(int employeId) throws IOException {
        // Fetch the date of hire based on the employee's ID
        Date dateEmbaucheValue = employeService.dateEmbauche(employeId);

        // Create a list containing just the fetched date (assuming it's a single date)
        List<Date> dates = new ArrayList<>();
        dates.add(dateEmbaucheValue); // Add the date to the list

        // Update the ListView with the date(s)
        dateEmbauche.setItems(FXCollections.observableList(dates));
    }

    public void empFormationRedirect() throws IOException {

        if(AppData.getInstance().getCurrentSelectedId()!=0){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddFormation.fxml"));
        AnchorPane newPane = loader.load();


        // Get the current stage and switch the scene
        Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
        currentStage.setScene(new Scene(newPane));
        currentStage.show();
    }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a user first");
            alert.show();
        }

        }
public void fillEmployeFormation() {
    List <Formation> formationList = formationService.rechercher();
    employeFormationView.setItems(FXCollections.observableList(formationList));
   System.out.println(formationList);
    }

    public void empDeleteButton() throws IOException {

        // Create the confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.equals(null);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to proceed?");
        alert.setContentText("Click Yes to confirm, No to cancel.");

        // Show the alert and capture the result of the user's action
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Proceed with the delete action if 'Yes' is clicked
                employeService.supprimer(employeTableView.getSelectionModel().getSelectedItem());
                employeTableView.getItems().remove(employeTableView.getSelectionModel().getSelectedItem());
                employeTableView.refresh();

                System.out.println("Action confirmed!");
            } else {
                // Cancel the action if 'No' is clicked
                System.out.println("Action canceled.");
            }

        });
    }
    public void candDeleteButton() throws IOException {

        // Create the confirmation alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.equals(null);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to proceed?");
        alert.setContentText("Click Yes to confirm, No to cancel.");

        // Show the alert and capture the result of the user's action
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Proceed with the delete action if 'Yes' is clicked
                candidatService.supprimer(candidatTableView.getSelectionModel().getSelectedItem());
                candidatTableView.getItems().remove(candidatTableView.getSelectionModel().getSelectedItem());
                candidatTableView.refresh();

                System.out.println("Action confirmed!");
            } else {
                // Cancel the action if 'No' is clicked
                System.out.println("Action canceled.");
            }

        });
    }


}




