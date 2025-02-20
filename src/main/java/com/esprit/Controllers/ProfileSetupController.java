package com.esprit.Controllers;

import com.esprit.models.Profil;
import com.esprit.services.ProfilService;
import com.esprit.utils.AppData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class ProfileSetupController {
    @FXML
    private javafx.scene.control.Button setup;
    @FXML
    private Slider slider;
    @FXML
    private TextArea competence;  // Correctly defined as TextArea (JavaFX)
    @FXML
    private TextArea experience;
    @FXML
    private TextArea certification;
    @FXML
    private Button chooseImageButton;

    private String newImageUrl;

    private Profil profile;
    private String fn;
public void initialize() {
    chooseImageButton.setOnAction(event -> handleChooseImage());

}
public void setupProfile() throws IOException {
    profile = new Profil(AppData.getInstance().getPendingId(), slider.getValue(), competence.getText(), experience.getText(), certification.getText());
    ProfilService ps = new ProfilService();
    ps.ajouter(profile);
    ps.handleImageProfile(AppData.getInstance().getPendingId(),getNewImageUrl() );

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText("Information");
    alert.setContentText("Profile is Setup");
    alert.showAndWait();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/SummaryView.fxml"));
    AnchorPane newPane = loader.load();

    Stage currentStage = (Stage) setup.getScene().getWindow();
    currentStage.setScene(new Scene(newPane));
    currentStage.show();
}
    public void handleChooseImage() {
        // Create a file chooser to select an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));

        // Open the file chooser and wait for the user to select a file
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Generate a new unique filename for the server (e.g., UUID)
                String fileName = String.valueOf(AppData.getInstance().getPendingId())+"_"+AppData.getCurrentDateTime() + getFileExtension(selectedFile);

                // Define the destination path on the server or desired directory
                File destinationFile = new File("images/" + fileName);
                fn="images/" + fileName;
                // Copy the selected file to the server directory
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update the URL variable with the new file's path
                newImageUrl = destinationFile.getAbsolutePath(); // You can store the relative path if needed

                // Load the image into the ImageView
                Image image = new Image(destinationFile.toURI().toString());

                System.out.println("Image successfully uploaded. New image URL: " + newImageUrl);

            } catch (IOException e) {
                System.out.println("Error copying the image: " + e.getMessage());
            }

            }
        else {
            fn = "/images/Default.jpg";
        }
    }
    private String getFileExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        return (index > 0) ? fileName.substring(index) : "";
    }

    // Getter for the new image URL (if needed for other operations)
    public String getNewImageUrl() {
        return fn;
    }

}
