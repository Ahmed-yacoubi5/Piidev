


package com.esprit.controllers;


import com.esprit.models.Admin;
import com.esprit.utils.DataSource;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class sidebar {

    @FXML
    private Button btnServices;

    @FXML
    private Button btnUtilisateurs;

    @FXML
    private Button btnEvenements;

    @FXML
    private Button btnBlogs;

    @FXML
    private Button btnTransport;

    @FXML
    private Button btnDechets;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnHelp;
    private DataSource SharedDataModel;

    @FXML
    private void handleLogoutClick(Event event) {
        switchScene(event, "/com/esprit/view/login.fxml");
    }

    @FXML
    private void handleServicesClick(Event event) {
        switchScene(event, "/com/esprit/view/listproduct.fxml");
    }

    @FXML
    private void handleUtilisateursClick(Event event) {
        switchScene(event, "/com/esprit/view/dashboard.fxml");
    }

    @FXML
    private void handleEvenementsClick(Event event) {
        switchScene(event, "/com/esprit/view/listevent.fxml");
    }

    @FXML
    private void handleBlogsClick(Event event) {
        switchScene(event, "/com/esprit/view/listblog.fxml");
    }

    @FXML
    private void handleTransportClick(Event event) {
        switchScene(event, "/com/esprit/view/listvehicule.fxml");
    }

    @FXML
    private void handleRecyclingCentersClick(Event event) {
        switchScene(event, "/com/esprit/view/listrecyclingcenter.fxml");
    }

    @FXML
    private void handfrontClick(Event event) {

        switchScene(event, "/com/esprit/view/front.fxml");
    }
    private void switchScene(Event event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Retrieve the logged-in admin from SharedDataModel
            Admin loggedInAdmin = SharedDataModel.getInstance().getLoggedInAdmin();

            // Pass the logged-in admin to the new controller (if it's a NavbarController)
            Object controller = loader.getController();
            if (controller instanceof NavbarController) {
                ((NavbarController) controller).setLoggedInAdmin(loggedInAdmin);
            }

            // Get current stage and switch scene
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}