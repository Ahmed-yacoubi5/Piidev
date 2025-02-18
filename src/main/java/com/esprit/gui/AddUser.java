package com.esprit.gui;

import com.esprit.services.UserService;
import com.esprit.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Date;

public class AddUser {

    @FXML
    private TextField adresse_input;

    @FXML
    private DatePicker birthday_input;

    @FXML
    private TextField email_input;

    @FXML
    private Text error;

    @FXML
    private ComboBox<String> role_combobox;

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
    private TextField privilegesField;

    @FXML
    private TextField fidelityPointsField;

    @FXML
    private TextField departementField;

    @FXML
    private ComboBox<String> departement_combobox; // New - Department Selection

    @FXML
    void back_to_list(ActionEvent event) throws IOException {
        // Load the dashboard FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/dashboard.fxml"));
        Parent root = loader.load();

        // Get the current scene and set the root to the dashboard view
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
        departement_combobox.setValue(null); // Reset department
    }

    @FXML
    void submit_admin(ActionEvent event) throws IOException, SQLException {
        if (validateForm()) {
            // Correct handling of LocalDate
            LocalDate selectedDate = birthday_input.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedBirthday = selectedDate.format(formatter);

            String selectedRole = role_combobox.getValue();
            if (selectedRole == null || selectedRole.isEmpty()) {
                error.setText("Role must be selected");
                error.setVisible(true);
                return;
            }

            String selectedGender = gender_combobox.getValue();
            String selectedDepartment = departement_combobox.getValue(); // Get department value

            // Convert LocalDate to java.sql.Date for database compatibility
            Date dateDeNaissance = java.sql.Date.valueOf(selectedDate);

            // Fidelity points - convert string input to int
            int fidelityPoints = 0;
            if (!fidelityPointsField.getText().isEmpty()) {
                try {
                    fidelityPoints = Integer.parseInt(fidelityPointsField.getText());
                } catch (NumberFormatException e) {
                    error.setText("Fidelity points must be a number");
                    error.setVisible(true);
                    return;
                }
            }

            // Get privileges and ensure it's passed as a String
            String privileges = privilegesField.getText();

            // Now create the Admin object
            Admin admin = new Admin(
                    email_input.getText(),
                    mdp_input.getText(),
                    nom_input.getText(),
                    prenom_input.getText(),
                    dateDeNaissance,
                    role_combobox.getValue(),
                    selectedGender,
                    adresse_input.getText(),
                    numtel_input.getText(),
                    "Active",
                    "default.png",
                    privileges,
                    fidelityPoints,
                    selectedDepartment
            );


            // Add the admin using the AdminService
            AdminService adminService = new AdminService();
            adminService.ajouter(admin);
        }
    }

    private boolean validateForm() {
        if (nom_input.getText().isEmpty() || email_input.getText().isEmpty() || mdp_input.getText().isEmpty() ||
                prenom_input.getText().isEmpty() || adresse_input.getText().isEmpty() ||
                birthday_input.getValue() == null || role_combobox.getValue() == null || gender_combobox.getValue() == null ||
                departement_combobox.getValue() == null || fidelityPointsField.getText().isEmpty() || privilegesField.getText().isEmpty()) { // Validate all fields including new ones
            error.setText("All fields must be filled");
            error.setVisible(true);
            return false;
        }

        String email = email_input.getText();
        if (!isEmailValid(email)) {
            error.setText("Invalid email address");
            error.setVisible(true);
            return false;
        }

        if (!isValidPassword(mdp_input.getText())) {
            error.setText("Password must contain at least one uppercase letter, one number, and one special character");
            error.setVisible(true);
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!/])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @FXML
    void initialize() {
        ObservableList<String> genderOptions = FXCollections.observableArrayList("Female", "Male");
        gender_combobox.setItems(genderOptions);

        ObservableList<String> roleOptions = FXCollections.observableArrayList("ADMIN", "CLIENT", "EMPLOYE");
        role_combobox.setItems(roleOptions);

        // Populate department combo box
        ObservableList<String> departmentOptions = FXCollections.observableArrayList("IT", "HR", "Finance", "Marketing");
        departement_combobox.setItems(departmentOptions);
    }
}