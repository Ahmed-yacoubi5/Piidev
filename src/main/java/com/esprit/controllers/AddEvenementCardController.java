package com.esprit.controllers;

import com.esprit.models.Evenement;
import com.esprit.services.IService;
import com.esprit.services.ServiceEvenement;
import com.esprit.utils.TrayNotificationAlert;
import com.esprit.utils.TrayNotificationAlert.AnimationType;
import com.esprit.utils.TrayNotificationAlert.NotificationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddEvenementCardController implements Initializable {

    @FXML
    public TextField fxNom;
    @FXML
    private TextField fxDesc;
    @FXML
    private DatePicker fxdateDebut;
    @FXML
    private DatePicker fxdateFin;
    @FXML
    private TextField fxLieu;
    @FXML
    private TextField nameInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private TextField prixInput;
    @FXML
    private Button add_new_EvenementBtn;
    @FXML
    private Button update_EvenementBtn;
    @FXML
    private ImageView imageInput;
    @FXML
    private HBox choose_photoBtn;
    @FXML
    private Text nameInputError;
    @FXML
    private Text descriptionInputError;
    @FXML
    private HBox photoInputErrorHbox;
    @FXML
    private ComboBox<String> fxProjectName;
    @FXML
    private HBox nameInputErrorHbox;
    @FXML
    private HBox fxdateDebutErrorHbox;
    @FXML
    private HBox fxdateFinErrorHbox;
    @FXML
    private HBox fxLieuErrorHbox;
    @FXML
    private HBox descriptionInputErrorHbox;
    @FXML
    private HBox prixInputErrorHbox;

    private File selectedImageFile;
    private String imageName = null;
    private Evenement evenementToUpdate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize UI based on action (add or update)
        if (Evenement.actionTest == 0) {
            update_EvenementBtn.setVisible(false);
        } else {
            add_new_EvenementBtn.setVisible(false);
            loadEvenementData();
        }
    }

    private void loadEvenementData() {
        IService evenementService = new ServiceEvenement();
        try {
            evenementToUpdate = evenementService.getOneEvenement(Evenement.getIdEvenement());
            setEvenementFields(evenementToUpdate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setEvenementFields(Evenement e) {
        nameInput.setText(e.getNom());
        fxLieu.setText(e.getLieu());
        descriptionInput.setText(e.getDescription());
        prixInput.setText(String.valueOf(e.getPrix()));
        fxdateDebut.setValue(e.getDateDebut() != null ? e.getDateDebut().toLocalDate() : null);
        fxdateFin.setValue(e.getDateFin() != null ? e.getDateFin().toLocalDate() : null);
        if (e.getImg() != null) {
            Image image = new Image(getClass().getResource("/assets/EventUploads/" + e.getImg()).toExternalForm());
            imageInput.setImage(image);
        } else {
            Image image = new Image(getClass().getResource("/assets/EventUploads/pngwing1.com.png").toExternalForm());
            imageInput.setImage(image);
        }
        imageName = e.getImg();
    }

    @FXML
    void nameTypedInput(KeyEvent event) {
        if (nameInput.getText().isEmpty()) {
            nameInputErrorHbox.setVisible(true);
        } else {
            nameInputErrorHbox.setVisible(false);
        }
    }

    @FXML
    void descriptionTypedInput(KeyEvent event) {
        if (descriptionInput.getText().isEmpty()) {
            descriptionInputErrorHbox.setVisible(true);
        } else {
            descriptionInputErrorHbox.setVisible(false);
        }
    }

    @FXML
    void dateDebutChanged(ActionEvent event) {
        if (fxdateDebut.getValue() == null) {
            fxdateDebutErrorHbox.setVisible(true);
        } else {
            fxdateDebutErrorHbox.setVisible(false);
        }
    }

    @FXML
    void dateFinChanged(ActionEvent event) {
        if (fxdateFin.getValue() == null) {
            fxdateFinErrorHbox.setVisible(true);
        } else {
            fxdateFinErrorHbox.setVisible(false);
        }
    }

    @FXML
    void lieuTypedInput(KeyEvent event) {
        if (fxLieu.getText().isEmpty()) {
            fxLieuErrorHbox.setVisible(true);
        } else {
            fxLieuErrorHbox.setVisible(false);
        }
    }

    @FXML
    void prixTypedInput(KeyEvent event) {
        if (prixInput.getText().isEmpty() || !prixInput.getText().matches("\\d+(\\.\\d+)?")) {
            prixInputErrorHbox.setVisible(true);
        } else {
            prixInputErrorHbox.setVisible(false);
        }
    }

    @FXML
    void addNewEvenement(MouseEvent event) throws SQLException, IOException {
        if (validateInputs()) {
            Evenement evenement = new Evenement();
            evenement.setNom(nameInput.getText());
            evenement.setDescription(descriptionInput.getText());
            evenement.setDateDebut(fxdateDebut.getValue() != null ? fxdateDebut.getValue().atStartOfDay() : null);
            evenement.setDateFin(fxdateFin.getValue() != null ? fxdateFin.getValue().atStartOfDay() : null);
            evenement.setLieu(fxLieu.getText());
            evenement.setPrix(Double.parseDouble(prixInput.getText()));
            evenement.setImg(imageName);

            IService evenementService = new ServiceEvenement();
            evenementService.ajouter(evenement);
            showNotification("Événement", "Événement ajouté avec succès.", NotificationType.SUCCESS);
            switchToEvenementsList(event);
        } else {
            showNotification("Erreur", "Veuillez remplir tous les champs obligatoires.", NotificationType.ERROR);
        }
    }

    @FXML
    void updateEvenement(MouseEvent event) throws IOException, SQLException {
        if (evenementToUpdate == null) {
            showNotification("Erreur", "Aucun événement à mettre à jour.", NotificationType.ERROR);
            return;
        }

        if (validateInputs()) {
            evenementToUpdate.setNom(nameInput.getText());
            evenementToUpdate.setDescription(descriptionInput.getText());
            evenementToUpdate.setDateDebut(fxdateDebut.getValue() != null ? fxdateDebut.getValue().atStartOfDay() : null);
            evenementToUpdate.setDateFin(fxdateFin.getValue() != null ? fxdateFin.getValue().atStartOfDay() : null);
            evenementToUpdate.setLieu(fxLieu.getText());
            evenementToUpdate.setPrix(Double.parseDouble(prixInput.getText()));
            evenementToUpdate.setImg(imageName);

            ServiceEvenement serviceEvenement = new ServiceEvenement();
            serviceEvenement.modifier(evenementToUpdate);
            showNotification("Événement", "Événement mis à jour avec succès.", NotificationType.SUCCESS);
            switchToEvenementsList(event);
        } else {
            showNotification("Erreur", "Veuillez remplir tous les champs obligatoires.", NotificationType.ERROR);
        }
    }
    private boolean validateInputs() {
        boolean isValid = true;

         if (nameInput.getText().isEmpty()) {
            nameInputErrorHbox.setVisible(true);
            isValid = false;
        } else {
            nameInputErrorHbox.setVisible(false);
        }

        // Validate Description
        if (descriptionInput.getText().isEmpty()) {
            descriptionInputErrorHbox.setVisible(true);
            isValid = false;
        } else {
            descriptionInputErrorHbox.setVisible(false);
        }


        if (fxdateDebut.getValue() == null) {
            fxdateDebutErrorHbox.setVisible(true);
            isValid = false;
        } else {
            fxdateDebutErrorHbox.setVisible(false);
        }


        if (fxdateFin.getValue() == null) {
            fxdateFinErrorHbox.setVisible(true);
            isValid = false;
        } else {
            fxdateFinErrorHbox.setVisible(false);
        }


        if (fxLieu.getText().isEmpty()) {
            fxLieuErrorHbox.setVisible(true);
            isValid = false;
        } else {
            fxLieuErrorHbox.setVisible(false);
        }

         if (prixInput.getText().isEmpty() || !prixInput.getText().matches("\\d+(\\.\\d+)?")) {
            prixInputErrorHbox.setVisible(true);
            isValid = false;
        } else {
            prixInputErrorHbox.setVisible(false);
        }

        return isValid;
    }
    @FXML
    void ajouter_image(MouseEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedImageFile = fileChooser.showOpenDialog(imageInput.getScene().getWindow());
        if (selectedImageFile != null) {

            Path destinationDir = Paths.get(System.getProperty("user.dir"), "src", "assets", "EventUploads");
            if (!Files.exists(destinationDir)) {
                Files.createDirectories(destinationDir);
            }


            imageName = selectedImageFile.getName();
            Path destination = destinationDir.resolve(imageName);
            Files.copy(selectedImageFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);


            Image image = new Image(selectedImageFile.toURI().toString());
            imageInput.setImage(image);


            photoInputErrorHbox.setVisible(false);
        }
    }

    private void switchToEvenementsList(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EvenementsList.fxml"));
        Parent root = loader.load();
        Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");
        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);
    }

    private void showNotification(String title, String message, NotificationType type) {
        TrayNotificationAlert.notif(title, message, type, AnimationType.FADE, Duration.millis(2500));
    }
}