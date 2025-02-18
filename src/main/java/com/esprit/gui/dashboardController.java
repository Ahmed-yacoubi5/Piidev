package com.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.List;
import java.util.stream.Collectors;

import com.esprit.models.Admin;
import com.esprit.services.UserService;
import com.esprit.utils.DataSource;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class dashboardController implements Initializable {

    private Connection connection = DataSource.getInstance().getConnection();

    @FXML
    private Button btn1;

    @FXML
    private TableColumn<Admin, Integer> id;


    @FXML
    private TableView<Admin> adminTableView;

    @FXML
    private TableView<Admin> tableView;

    @FXML
    private TableColumn<Admin, String> nom;

    @FXML
    private TableColumn<Admin, String> prenom;

    @FXML
    private TableColumn<Admin, String> email;

    @FXML
    private TableColumn<Admin, String> statut;
    @FXML
    private Button checkDataButton;
    @FXML
    private TableColumn<Admin, String> phone;

    @FXML
    private TableColumn<Admin, String> privileges;

    @FXML
    private TableColumn<Admin, Integer> fidelityPoints;

    @FXML
    private TableColumn<Admin, String> departement;

    @FXML
    private TextField filtre;

    @FXML
    private Button btn11;

    @FXML
    private TableColumn<Admin, Integer> Id;

    @FXML
    private Button addUserButton;

    @FXML
    private Button updateUserButton;

    ObservableList<Admin> listeB = FXCollections.observableArrayList();


    @FXML
    public void show() {
        System.out.println("✅ show() method called!");

        AdminService adminService = new AdminService();
        List<Admin> adminList = adminService.afficherTous();  // Fetch the updated list from the database

        if (adminList == null || adminList.isEmpty()) {
            System.err.println("⚠ No admins found in the database!");
            showAlert("No data available", "No Admins were found in the database.");
            return;
        }

        listeB = FXCollections.observableArrayList(adminList);  // Create a new ObservableList with the latest data
        System.out.println("✅ ObservableList created with size: " + listeB.size());

        // Setting up table columns with appropriate data
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        statut.setCellValueFactory(new PropertyValueFactory<>("status"));
        phone.setCellValueFactory(new PropertyValueFactory<>("numDeTelephone"));
        privileges.setCellValueFactory(new PropertyValueFactory<>("privileges"));
        fidelityPoints.setCellValueFactory(new PropertyValueFactory<>("fidelityPoints"));
        departement.setCellValueFactory(new PropertyValueFactory<>("departement"));
        TableColumn<Admin, String> password = new TableColumn<>("Password");
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        TableColumn<Admin, String> genre = new TableColumn<>("Genre");
        genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        TableColumn<Admin, String> roles = new TableColumn<>("Roles");
        roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
        TableColumn<Admin, String> adresse = new TableColumn<>("Adresse");
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        // Adding new columns to the TableView if not already present
        if (!tableView.getColumns().contains(password)) tableView.getColumns().add(password);
        if (!tableView.getColumns().contains(genre)) tableView.getColumns().add(genre);
        if (!tableView.getColumns().contains(roles)) tableView.getColumns().add(roles);
        if (!tableView.getColumns().contains(adresse)) tableView.getColumns().add(adresse);

        if (tableView == null) {
            System.err.println("❌ TableView is null!");
            return;
        } else {
            System.out.println("✅ TableView is not null.");
        }

        // Refreshing the table by setting the items to the updated ObservableList
        tableView.setItems(listeB);
        System.out.println("✅ Data successfully set in TableView!");
    }



    @FXML
    void supp(ActionEvent event) {
        // Get the selected admin item from the table view
        Admin selectedAdmin = tableView.getSelectionModel().getSelectedItem();

        if (selectedAdmin == null) {
            // Display error alert if no admin is selected
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de supprimer l'utilisateur");
            alert.setContentText("Veuillez sélectionner un utilisateur à supprimer !");
            alert.showAndWait();
        } else {
            // Show confirmation alert before deletion
            Alert confirmation = new Alert(AlertType.CONFIRMATION, "Êtes-vous sûr de vouloir supprimer cet utilisateur ?", ButtonType.YES, ButtonType.NO);
            confirmation.setHeaderText(null);

            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    // If the user confirms, proceed with deletion
                    AdminService adminService = new AdminService();
                    adminService.supprimer(selectedAdmin);  // Pass the entire selectedAdmin object

                    // Show success alert after deletion
                    Alert success = new Alert(AlertType.INFORMATION, "L'utilisateur a été supprimé avec succès !");
                    success.setHeaderText(null);
                    success.showAndWait();

                    // Refresh the TableView to reflect changes
                    show();  // This will reload the data and refresh the TableView
                }
            });
        }
    }





    // Method for modifying an Admin (currently not implemented)
    @FXML
    void Modifier(ActionEvent event) {
        // Implement the modify functionality
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        show();
    }
    // Helper method to show alert boxes
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void checkData(ActionEvent event) {
        System.out.println("✅ Check Data button clicked!");

        AdminService adminService = new AdminService();
        List<Admin> adminList = adminService.afficherTous();

        if (adminList == null || adminList.isEmpty()) {
            System.err.println("⚠ No admins found in the database!");
            showAlert("No data available", "No Admins were found in the database.");
            return;
        }

        // Filter the admins with missing or incorrect data
        List<Admin> incorrectDataAdmins = adminList.stream()
                .filter(admin -> admin.getNom() == null || admin.getNom().isEmpty() ||
                        admin.getPrenom() == null || admin.getPrenom().isEmpty() ||
                        admin.getEmail() == null || admin.getEmail().isEmpty() ||
                        admin.getStatus() == null || admin.getStatus().isEmpty() ||
                        admin.getNumDeTelephone() == null || admin.getNumDeTelephone().isEmpty())
                .collect(Collectors.toList());

        if (incorrectDataAdmins.isEmpty()) {
            showAlert("All data is correct", "All users have valid data.");
            return;
        }

        // If there are users with incorrect data, display them
        listeB = FXCollections.observableArrayList(incorrectDataAdmins);
        tableView.setItems(listeB);
        System.out.println("✅ Displaying users with missing or incorrect data.");

        // Set up table columns (optional: could also re-use the `show()` method if needed)
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        statut.setCellValueFactory(new PropertyValueFactory<>("status"));
        phone.setCellValueFactory(new PropertyValueFactory<>("numDeTelephone"));
        privileges.setCellValueFactory(new PropertyValueFactory<>("privileges"));
        fidelityPoints.setCellValueFactory(new PropertyValueFactory<>("fidelityPoints"));
        departement.setCellValueFactory(new PropertyValueFactory<>("departement"));

        System.out.println("✅ Data successfully set in TableView!");
    }

    @FXML
    public void onAddUserClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/adduser.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add User");
            stage.show();

            // When the add user window is closed, refresh the table in the dashboard
            stage.setOnHiding(e -> show()); // Refresh TableView after closing the add window

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Add User screen.");
        }
    }

    @FXML
    void onUpdateUserClick(ActionEvent event) {
        // Get selected admin from TableView
        Admin selectedAdmin = tableView.getSelectionModel().getSelectedItem();

        if (selectedAdmin == null) {
            showAlert("Selection Error", "Please select an admin to update.");
            return;
        }

        try {
            // Load the update user view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/updateuser.fxml"));
            Parent root = loader.load();

            // Get the controller of the update user view
            UpdateUser updateController = loader.getController();
            updateController.setAdmin(selectedAdmin);

            // Open the update user window
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Update User");
            stage.show();

            // When the update window is closed, refresh the table in the dashboard
            stage.setOnHiding(e -> show()); // Refresh TableView after closing the update window

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load Update User screen.");
        }
    }


}
