package com.esprit.gui;

import com.esprit.models.Admin;
import com.esprit.services.AdminService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

public class UpdateUser {

    @FXML
    private TextField adresse_input;

    @FXML
    private DatePicker birthday_input;

    @FXML
    private TextField email_input;

    @FXML
    private Text error;

    @FXML
    private ComboBox<String> gender_combobox;

    @FXML
    private TextField mdp_input;

    @FXML
    private TextField nom_input;

    @FXML
    private TextField numtel_input;

    @FXML
    private TextField prenom_input;

    @FXML
    private ComboBox<String> role_combobox;

    @FXML
    private ComboBox<String> status_combobox;

    @FXML
    private ComboBox<String> privileges_combobox;

    @FXML
    private ComboBox<String> departement_combobox;
    @FXML
    private TextField fidelityPoints_input;

    private Admin admin;
    private AdminService adminService = new AdminService();

    @FXML
    public void initialize() {
        // Populate role choices
        role_combobox.getItems().addAll("ADMIN", "EMPLOYE", "CLIENT");
        role_combobox.setValue("ADMIN");

        // Populate status choices
        status_combobox.getItems().addAll("Active", "Inactive");
        status_combobox.setValue("Active");

        // Populate privileges choices
        privileges_combobox.getItems().addAll("Standard", "Moderator", "Super Admin");
        privileges_combobox.setValue("Standard");

        // Populate department choices
        departement_combobox.getItems().addAll("IT", "HR", "Finance", "Marketing");
        departement_combobox.setValue("IT");

    }

    @FXML
    public void setAdmin(Admin admin) {
        this.admin = admin;

        adresse_input.setText(admin.getAdresse());
        email_input.setText(admin.getEmail());
        numtel_input.setText(admin.getNumDeTelephone());
        prenom_input.setText(admin.getPrenom());
        nom_input.setText(admin.getNom());
        role_combobox.setValue(admin.getRoles());
        mdp_input.setText(admin.getPassword());
        gender_combobox.setValue(admin.getGenre());
        status_combobox.setValue(admin.getStatus());
        privileges_combobox.setValue(admin.getPrivileges());
        departement_combobox.setValue(admin.getDepartement());

        if (admin.getDateDeNaissance() != null) {
            birthday_input.setValue(LocalDate.parse(admin.getDateDeNaissance().toString()));
        }
        fidelityPoints_input.setText(String.valueOf(admin.getFidelityPoints()));
    }
    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setRoot(root);
    }

    @FXML
    void reset_input(ActionEvent event) {
        nom_input.clear();
        birthday_input.setValue(null);
        email_input.clear();
        prenom_input.clear();
        role_combobox.setValue(null);
        gender_combobox.setValue(null);
        mdp_input.clear();
        adresse_input.clear();
        numtel_input.clear();
        status_combobox.setValue(null);
        privileges_combobox.setValue(null);
        departement_combobox.setValue(null);
        fidelityPoints_input.clear();
    }

    @FXML
    void modify_admin(ActionEvent event) {
        if (admin == null) {
            showErrorAlert("Admin not set", "Admin object is null.");
            return;
        }

        // Retrieve values from input fields
        String adresse = adresse_input.getText();
        LocalDate birthday = birthday_input.getValue();
        String email = email_input.getText();
        String genre = gender_combobox.getValue();
        String password = mdp_input.getText();
        String nom = nom_input.getText();
        String prenom = prenom_input.getText();
        String numtel = numtel_input.getText();
        String role = role_combobox.getValue();
        String status = status_combobox.getValue();
        String privileges = privileges_combobox.getValue();
        String departement = departement_combobox.getValue();
        String fidelityPointsText = fidelityPoints_input.getText();
        int fidelityPoints = 0;

        try {
            fidelityPoints = Integer.parseInt(fidelityPointsText);
        } catch (NumberFormatException e) {
            showErrorAlert("Invalid Fidelity Points", "Please enter a valid number for Fidelity Points.");
            return;
        }
        if (role == null || role.isEmpty()) {
            showErrorAlert("Role not selected", "Please select a role.");
            return;
        }


        admin.setAdresse(adresse);
        if (birthday != null) {
            admin.setDateDeNaissance(java.sql.Date.valueOf(birthday));
        }

        admin.setEmail(email);
        admin.setGenre(genre);
        admin.setPassword(password);
        admin.setNom(nom);
        admin.setPrenom(prenom);
        admin.setNumDeTelephone(numtel);
        admin.setRoles(role);
        admin.setStatus(status);
        admin.setPrivileges(privileges);
        admin.setDepartement(departement);
        admin.setFidelityPoints(fidelityPoints);


        adminService.modifier(admin);


        showConfirmationAlert("User Updated", "Admin details have been updated successfully.");


        refreshTableView();
    }

    private void refreshTableView() {
        // This method should call the refresh function in your main view controller
        // Example: DashboardController.refreshAdminTable();
    }

    private void showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String title, String content) {
        showAlert(Alert.AlertType.ERROR, title, null, content);
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
