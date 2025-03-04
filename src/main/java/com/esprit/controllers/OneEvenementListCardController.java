package com.esprit.controllers;

import com.esprit.models.Evenement;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import com.esprit.services.IService;
import com.esprit.services.ServiceEvenement;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class OneEvenementListCardController {

    @FXML
    private ImageView img;

    @FXML
    private HBox deleteEvenement;

    @FXML
    private HBox editEvenement;

    @FXML
    private Text productName;


    @FXML
    private Text descfx;

    @FXML
    private Text pricefx;

    @FXML
    private Text lieufx;


    @FXML
    private HBox qrCodeEvenement;

    @FXML
    private HBox offerEvent;



    public void setEvenementData(Evenement evenement) {

        IService EvenementService = new ServiceEvenement();

        Image image = new Image(
                getClass().getResource("/assets/EventUploads/" + evenement.getImg()).toExternalForm());
        img.setImage(image);
        productName.setText(evenement.getNom());
        descfx.setText(evenement.getDescription());
        lieufx.setText(evenement.getLieu());

            pricefx.setText("" + evenement.getPrix());

       // pricefx.setText(String.valueOf(evenement.getPrix()));

        deleteEvenement.setId(String.valueOf(evenement.getId()));

        deleteEvenement.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Supprimer l'événement");
            alert.setContentText("Êtes-vous sûr de vouloir supprimer cet événement?");


            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("/assets/style/alerts.css").toExternalForm());
            dialogPane.getStyleClass().add("my-dialog");

            ButtonType ouiButton = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType nonButton = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(ouiButton, nonButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ouiButton) {
                System.out.println("ID du Evenement à supprimer : " + evenement.getId());
                try {
                    EvenementService.supprimer(evenement.getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/EvenementsList.fxml"));
                try {
                    Parent root = loader.load();

                    Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");

                    contentArea.getChildren().clear();
                    contentArea.getChildren().add(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        editEvenement.setId(String.valueOf(evenement.getId()));
        editEvenement.setOnMouseClicked(event -> {
            System.out.println("ID du Evenement à modifier : " + evenement.getId());
            Evenement.setIdEvenement(evenement.getId());
            Evenement.actionTest = 1;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/AddEvenement.fxml"));
            try {
                Parent root = loader.load();
                Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");
                contentArea.getChildren().clear();
                contentArea.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        editEvenement.setId(String.valueOf(evenement.getId()));
        qrCodeEvenement.setOnMouseClicked(event -> {
            System.out.println("ID du Evenement à générer qr Code : " + evenement.getId());
            Evenement.setIdEvenement(evenement.getId());
            StringBuilder builder = new StringBuilder();
            builder.append(" ID de l'événement: ").append(evenement.getId()).append("\n");
            builder.append("Nom de l'événement: ").append(evenement.getNom()).append("\n");
            builder.append("Description de l'événement: ").append(evenement.getDescription()).append("\n");
            builder.append("Prix de l'événement: ").append(evenement.getPrix()).append("\n");
            builder.append("Lieu de l'événement: ").append(evenement.getLieu()).append("\n");
            String text = builder.toString();
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix;
            try {
                bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
                 BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
                ImageView qrCodeImg = (ImageView) ((Node) event.getSource()).getScene().lookup("#qrCodeImg");
                qrCodeImg.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
                HBox qrCodeImgModel = (HBox) ((Node) event.getSource()).getScene().lookup("#qrCodeImgModel");
                qrCodeImgModel.setVisible(true);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
    }
}
