package com.esprit.controllers;

import com.esprit.models.Employe;
import com.esprit.models.Candidat;
import com.esprit.models.Formation;
import com.esprit.models.Profil;
import com.esprit.services.CandidatService;
import com.esprit.services.EmployeService;
import com.esprit.services.FormationService;
import com.esprit.services.ProfilService;
import com.esprit.utils.AppData;
import com.esprit.utils.IdUtil;
import com.esprit.utils.ImageSrc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
    @FXML
    private ListView<Formation> candidatFormationView;

    private CandidatService candidatService;
    private EmployeService employeService;
    private FormationService formationService;

    public void initialize() {
        // Initialize services
        empId.setVisible(false);
        cli.setVisible(false);
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
        List<Candidat> candidats = candidatService.rechercher();
        candidatTableView.setItems(FXCollections.observableList(candidats));
        candidatTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    AppData.getInstance().setSelectedCandidatId(newValue.getId());
                    fillCandidatFormation();
                    System.out.print("Selected Candidat with ID : " + AppData.getInstance().getSelectedCandidatId());
                } catch (Exception e) {
                    e.printStackTrace();
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

        if (AppData.getInstance().getCurrentSelectedId() != 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddFormation.fxml"));
            AnchorPane newPane = loader.load();


            // Get the current stage and switch the scene
            Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
            currentStage.setScene(new Scene(newPane));
            currentStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a user first");
            alert.show();
        }

    }

    public void canFormationRedirect() throws IOException {

        if (AppData.getInstance().getSelectedCandidatId() != 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddFormationCand.fxml"));
            AnchorPane newPane = loader.load();


            // Get the current stage and switch the scene
            Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
            currentStage.setScene(new Scene(newPane));
            currentStage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Please Select a user first");
            alert.show();
        }
    }

    public void fillEmployeFormation() {
        List<Formation> formationList = formationService.rechercher(AppData.getInstance().getCurrentSelectedId());
        employeFormationView.setItems(FXCollections.observableList(formationList));
        System.out.println(formationList);
    }

    public void fillCandidatFormation() {
        List<Formation> candidatList = formationService.rechercher(AppData.getInstance().getSelectedCandidatId());
        candidatFormationView.setItems(FXCollections.observableList(candidatList));
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
                ProfilService profilService = new ProfilService();
                // Proceed with the delete action if 'Yes' is clicked
                formationService.suppressionComplete(employeTableView.getSelectionModel().getSelectedItem().getId());
                profilService.suppressionTotale(employeTableView.getSelectionModel().getSelectedItem().getId());
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
                ProfilService profilService = new ProfilService();

                // Proceed with the delete action if 'Yes' is clicked
                candidatService.supprimer(candidatTableView.getSelectionModel().getSelectedItem());
                formationService.suppressionComplete(candidatTableView.getSelectionModel().getSelectedItem().getId());
                profilService.suppressionTotale(candidatTableView.getSelectionModel().getSelectedItem().getId());
                candidatTableView.getItems().remove(candidatTableView.getSelectionModel().getSelectedItem());
                candidatTableView.refresh();

                System.out.println("Action confirmed!");
            } else {
                // Cancel the action if 'No' is clicked
                System.out.println("Action canceled.");
            }

        });
    }

    public void deleteEmployeFormation() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to proceed?");
        alert.setContentText("Click Yes to confirm, No to cancel.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                FormationService formationService = new FormationService();
                Formation formation = new Formation(AppData.getInstance().getCurrentSelectedId(), employeFormationView.getSelectionModel().getSelectedItem().getDiplome()
                        , employeFormationView.getSelectionModel().getSelectedItem().getInstitution(), employeFormationView.getSelectionModel().getSelectedItem().getAnneeObtention());
                formationService.supprimer(formation);
                employeFormationView.getItems().remove(employeFormationView.getSelectionModel().getSelectedItem());
                System.out.println("id: " + formation.getId() + "a" + formation.getAnneeObtention());
            }
        });

    }

    public void deleteCandidatFormation() throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to proceed?");
        alert.setContentText("Click Yes to confirm, No to cancel.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                FormationService formationService = new FormationService();
                Formation formation = new Formation(AppData.getInstance().getSelectedCandidatId(), candidatFormationView.getSelectionModel().getSelectedItem().getDiplome()
                        , candidatFormationView.getSelectionModel().getSelectedItem().getInstitution(), candidatFormationView.getSelectionModel().getSelectedItem().getAnneeObtention());
                formationService.supprimer(formation);
                candidatFormationView.getItems().remove(candidatFormationView.getSelectionModel().getSelectedItem());
                System.out.println("id: " + formation.getId() + "a" + formation.getAnneeObtention());
            }
        });
    }

    public void viewEmpProfile() throws IOException {
        if (AppData.getInstance().getCurrentSelectedId() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No profile found");
            alert.setHeaderText("No Employe is selected");
            alert.setContentText("please select an employe");
            alert.showAndWait();
        } else {
            ProfilService profilService = new ProfilService();
            if (profilService.haveProfil(AppData.getInstance().getCurrentSelectedId())) {
                AppData.getInstance().setRedirectionFrom("employe");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
                AnchorPane newPane = loader.load();


                // Get the current stage and switch the scene
                Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
                currentStage.setScene(new Scene(newPane));
                currentStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("No profile found");
                alert.setHeaderText("No profile found, do you want to Set-up a profile for this Employe?");
                alert.setContentText("Click Yes to confirm, No to cancel.");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    AppData.getInstance().setPendingId(AppData.getInstance().getCurrentSelectedId());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileSetup.fxml"));
                    AnchorPane newPane = loader.load();


                    // Get the current stage and switch the scene
                    Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
                    currentStage.setScene(new Scene(newPane));
                    currentStage.show();
                }
            }
        }
    }

    public void viewCanProfile() throws IOException {
        ProfilService profilService = new ProfilService();
        if (AppData.getInstance().getSelectedCandidatId() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No profile found");
            alert.setHeaderText("No Candidat is selected");
            alert.setContentText("please select a Candidat");
            alert.showAndWait();

        } else {
            if (profilService.haveProfil(AppData.getInstance().getSelectedCandidatId())) {
                AppData.getInstance().setRedirectionFrom("candidat");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Profile.fxml"));
                AnchorPane newPane = loader.load();


                // Get the current stage and switch the scene
                Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
                currentStage.setScene(new Scene(newPane));
                currentStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("No profile found");
                alert.setHeaderText("No profile found, do you want to Set-up a profile for this Candidat?");
                alert.setContentText("Click Yes to confirm, No to cancel.");
                alert.showAndWait();
                if (alert.getResult() == ButtonType.OK) {
                    AppData.getInstance().setPendingId(AppData.getInstance().getSelectedCandidatId());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ProfileSetup.fxml"));
                    AnchorPane newPane = loader.load();


                    // Get the current stage and switch the scene
                    Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
                    currentStage.setScene(new Scene(newPane));
                    currentStage.show();
                }
            }
        }
    }

    public void editEmp() throws IOException {
        if (AppData.getInstance().getCurrentSelectedId() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No profile found");
            alert.setHeaderText("No Employe is selected");
            alert.setContentText("please select an Employe");
            alert.showAndWait();
        } else {
            AppData.getInstance().setSelectedCandidatId(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit.fxml"));
            AnchorPane newPane = loader.load();


            // Get the current stage and switch the scene
            Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
            currentStage.setScene(new Scene(newPane));
            currentStage.show();
        }
    }

    public void editCand() throws IOException {
        if (AppData.getInstance().getSelectedCandidatId() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No profile found");
            alert.setHeaderText("No Candidat is selected");
            alert.setContentText("please select a Candidat");
            alert.showAndWait();
        } else {
            AppData.getInstance().setCurrentSelectedId(0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Edit.fxml"));
            AnchorPane newPane = loader.load();


            // Get the current stage and switch the scene
            Stage currentStage = (Stage) modifyFormationEmploye.getScene().getWindow();
            currentStage.setScene(new Scene(newPane));
            currentStage.show();
        }
    }

    public void openCv() {
        Candidat candidat = new Candidat();
        candidat.setId(AppData.getInstance().getSelectedCandidatId());
        CandidatService candidatService = new CandidatService();
        candidatService.candidatAff(candidat);
        String filePath = candidat.getCv() + ".pdf";

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);
            return;
        }

        // Use Desktop class to open the file with the system's default application
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().open(file);
                System.out.println("Opening file: " + filePath);
            } catch (IOException e) {
                System.out.println("Error opening the file: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Desktop is not supported on this system.");
        }
    }

}




