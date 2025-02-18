package com.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import com.esprit.models.Client;
import com.esprit.services.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class RegistreUserController implements Initializable {


    @FXML
    private Button btnAjouter;
    @FXML
    private TextField urlTF;
    @FXML
    private TextField txtimage;
    @FXML
    private TextField txtemail;
    @FXML
    private TextField txtnom1;
    @FXML
    private TextField txtnom2;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private TextField txtnumT;
    @FXML
    private TextField genre;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private TextField txtAddress;

    @FXML
    private Stage stage;


    private boolean isValid(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isvalid(String num) {
        if (num.length() != 8) {
            return false;
        }
        for (int i = 0; i < num.length(); i++) {
            if (!Character.isDigit(num.charAt(i))) {
                return false;
            }
        }
        return !"12345678".equals(num);
    }

    @FXML
    void ajouter(ActionEvent event) {
        String nom = txtnom1.getText();
        String nom2 = txtnom2.getText();
        String email = txtemail.getText();
        String num_t = txtnumT.getText();
        String pass = passwordField.getText();
        String pass2 = passwordField2.getText();
        Date birthDate = java.sql.Date.valueOf(birthDatePicker.getValue());
        String gender = genre.getText();
        String address = txtAddress.getText();

        if (nom.isEmpty() || email.isEmpty() || nom2.isEmpty() || num_t.isEmpty() ||
                urlTF.getText().isEmpty() || pass.isEmpty() || pass2.isEmpty() || birthDate == null || address.isEmpty()) {
            Alert alert1 = new Alert(AlertType.WARNING);
            alert1.setTitle("Oops");
            alert1.setHeaderText(null);
            alert1.setContentText("Remplir tous les champs");
            alert1.showAndWait();
            return;
        }

        if (!isValid(email)) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Email");
            alert.setHeaderText(null);
            alert.setContentText("Entrer une adresse email valide");
            alert.show();
            txtemail.requestFocus();
            txtemail.selectAll();
            return;
        }
        if (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female")) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Gender");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez entrer 'Male' ou 'Female' pour le genre.");
            alert.show();
            genre.requestFocus();
            return;
        }
        if (!isvalid(num_t)) {
            Alert alert1 = new Alert(AlertType.ERROR);
            alert1.setTitle("Invalid Numero");
            alert1.setHeaderText(null);
            alert1.setContentText("Entrer un numéro valide");
            alert1.show();
            txtnumT.requestFocus();
            txtnumT.selectAll();
            return;
        }

        if (!pass.equals(pass2)) {
            JOptionPane.showMessageDialog(null, "Le deuxième mot de passe ne correspond pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client nouvelleUser = new Client(
                0,
                email,
                pass,
                nom,
                nom2,
                birthDate,
                gender,
                address,
                num_t,
                "Active",
                "DefaultImageString",
                0
        );

        ClientService bs = new ClientService();
        bs.ajouter(nouvelleUser);

        Alert alert2 = new Alert(AlertType.CONFIRMATION);
        alert2.setTitle("Information Dialog");
        alert2.setHeaderText(null);
        alert2.setContentText("User inséré avec succès!");
        alert2.showAndWait();

        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esprit/view/login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage2 = new Stage();
            stage2.setScene(scene);
            stage2.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        System.out.println("Initializing controller...");
        System.out.println("Genre TextField: " + genre);

        if (genre == null) {
            System.out.println("Error: genre is null! Check FXML binding.");
        }
    }

}
