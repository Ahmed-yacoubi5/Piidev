package com.esprit.controllers;

import com.esprit.services.ServiceUser;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AcceuilAdminController implements Initializable {

    @FXML private VBox mainContainer;
    @FXML private ImageView adminIcon;
    @FXML private Text welcomeTitle;
    @FXML private Label userCountLabel;
    @FXML private Label activityCountLabel;
    @FXML private Button manageUsersBtn;
    @FXML private Button logoutBtn;
    @FXML private Label lastLoginLabel;

    private final ServiceUser userService = new ServiceUser();
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // First load statistics to ensure labels are populated
        loadStatistics();
        
        // Update last login time
        updateLastLoginTime();
        
        // Setup button actions
        setupButtonActions();
        
        // Finally add animations
        setupAnimations();
    }

    private void setupAnimations() {
        // Only add hover effect if the button exists
        if (manageUsersBtn != null) {
            // Add hover effect to the manage users button
            addButtonHoverEffect(manageUsersBtn);
        }
    }

    private void loadStatistics() {
        try {
            int totalUsers = ServiceUser.getAll().size();
            int activeUsers = ServiceUser.getActiveUsersCount(); // You'll need to implement this method

            userCountLabel.setText(String.format("%,d", totalUsers));
            activityCountLabel.setText(String.format("%,d", activeUsers));

        } catch (Exception e) {
            e.printStackTrace();
            // Handle error
        }
    }

    private void updateLastLoginTime() {
        String currentTime = LocalDateTime.now().format(TIME_FORMAT);
        lastLoginLabel.setText("Dernière connexion: aujourd'hui à " + currentTime);
    }

    private void setupButtonActions() {
        // Manage Users button
        manageUsersBtn.setOnAction(event -> openUsersManagement());

        // Logout button
        logoutBtn.setOnAction(event -> logout());
    }

    @FXML
    private void openUsersManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo4/ListeUsers.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) manageUsersBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle error
        }
    }

    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo4/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Handle error
        }
    }

    private void addButtonHoverEffect(Button button) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(100), button);
        scaleUp.setToX(1.05);
        scaleUp.setToY(1.05);

        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(100), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);

        button.setOnMouseEntered(e -> {
            scaleUp.playFromStart();
            button.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0.5, 0, 3);");
        });

        button.setOnMouseExited(e -> {
            scaleDown.playFromStart();
            button.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0.5, 0, 3);");
        });
    }
}