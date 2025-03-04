package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private VBox pnItems;
    @FXML
    private Button btnOverview;
    @FXML
    private Pane content_area;
    @FXML
    private Button btnOrders;
    @FXML
    private Button btnCustomers;
    @FXML
    private Button btnMenus;
    @FXML
    private Button btnPackages;
    @FXML
    private Button btnSettings;
    @FXML
    private Button btnSignout;
    @FXML
    private Pane pnlCustomer;
    @FXML
    private Pane pnlOrders;
    @FXML
    private Pane pnlOverview;
    @FXML
    private Pane pnlMenus;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnOrders) {
            pnlOrders.setStyle("-fx-background-color : #02030A");
            pnlOrders.toFront();
        } else if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color : #02030A");
            pnlCustomer.toFront();
        } else if (actionEvent.getSource() == btnMenus) {
            pnlMenus.setStyle("-fx-background-color : #02030A");
            pnlMenus.toFront();
        } else if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color : #02030A");
            pnlOverview.toFront();
        }
    }

    @FXML
    private void open_evenementsList(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/FXML/EvenementsList.fxml"));
        content_area.getChildren().clear();
        content_area.getChildren().add(fxml);
    }

    @FXML
    private void open_commentaireList(ActionEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/FXML/CommentsList.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);
    }
}