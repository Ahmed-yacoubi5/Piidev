package com.esprit.Controllers;

import com.esprit.utils.AppData;
import com.esprit.utils.IdUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NavBar {


    // Back button functionality
    @FXML
    private Button summaryButton;
    public void redirectToSummary() throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Action");
        alert.setHeaderText("Confirm Action");
        alert.setContentText("Are you sure you want to go to Summary?");
        alert.showAndWait();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SummaryView.fxml"));
        AnchorPane newPane = loader.load();

        Stage currentStage = (Stage) summaryButton.getScene().getWindow();
        currentStage.setScene(new Scene(newPane));
        currentStage.show();
        AppData.getInstance().setCurrentSelectedId(0);
        AppData.getInstance().setSelectedCandidatId(0);

    }

    // Home button functionality (Placeholder for now)
    @FXML
    public void goHome() {
        System.out.println("Home button clicked (Functionality not yet implemented)");
    }

    // Profile button functionality (Placeholder for now)
    @FXML
    public void handleProfile() {
        System.out.println("Profile Handling button clicked (Functionality not yet implemented)");
    }
}
