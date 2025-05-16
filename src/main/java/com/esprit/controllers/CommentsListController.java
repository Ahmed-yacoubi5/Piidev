package com.esprit.controllers;

import com.esprit.models.Commentaire;
import com.esprit.services.IService;
import com.esprit.services.ServiceCommentaire;
import com.esprit.utils.TrayNotificationAlert;
import com.esprit.utils.TrayNotificationAlert.AnimationType;
import com.esprit.utils.TrayNotificationAlert.NotificationType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class CommentsListController implements Initializable {

    @FXML
    private GridPane CommentsListContainer;

    @FXML
    private Pane content_area;

    @FXML
    private HBox categoriesModel;

    @FXML
    private GridPane categoriesListContainer;

    @FXML
    private TextField evenementsearchInput;

    @FXML
    private Button stockBtn;

    @FXML
    private ComboBox<String> categoryInput;

    @FXML
    private ImageView qrCodeImg;

    @FXML
    private HBox qrCodeImgModel;

    @FXML
    private HBox offreModel;

    @FXML
    private TextField reductionInput;

    @FXML
    private TextField couponInput;

    @FXML
    private Text reductionInputError;

    @FXML
    private Text couponInputError;

    @FXML
    private Text backToReductionBtn;

    @FXML
    private Text addNewCouponBtn;

    @FXML
    private HBox reductionInputErrorHbox;

    @FXML
    private HBox couponInputErrorHbox;

    @FXML
    private VBox couponForm;

    @FXML
    private VBox reductionForm;

    @FXML
    private HBox submitCouponBtn;


    @FXML
    private ComboBox<String> couponCombobox;

    private int categId = -1;

    private int sortValue = -1;

    private int submitCouponTest = 0;

    private static int categoryModelShow = 0;
    private String selectedOption = null;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField ContenuInput;
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        try {
            this.setCommentGridPaneList();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private void setCommentGridPaneList() throws SQLException {
        IService<Commentaire> commentaireService = new ServiceCommentaire();
        try {
            List<Commentaire> commentaires = commentaireService.afficher();
            
            if (commentaires.isEmpty()) {
                System.out.println("No comments found to display");
                return;
            }
            
            int column = 0;
            int row = 1;
            
            for (Commentaire commentaire : commentaires) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/OneCommentListCard.fxml"));
                    HBox oneCommentCard = fxmlLoader.load();
                    OneCommentListCardController commentCardController = fxmlLoader.getController();
                    commentCardController.setCommentData(commentaire);
                    if (column == 1) {
                        column = 0;
                        ++row;
                    }
                    CommentsListContainer.add(oneCommentCard, column++, row);
                    GridPane.setMargin(oneCommentCard, new Insets(0, 10, 25, 10));
                    oneCommentCard.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.09), 25, 0.1, 0, 0);");
                } catch (IOException e) {
                    System.out.println("Error loading comment card: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving comments: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    private void open_addComment(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/AddComment.fxml"));
        content_area.getChildren().removeAll();
        content_area.getChildren().setAll(fxml);

    }
    private void showNotification(String title, String message, NotificationType type) {
        TrayNotificationAlert.notif(title, message, type, AnimationType.FADE, Duration.millis(2500));
    }
    private void switchToEvenementsList(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommentsList.fxml"));
        Parent root = loader.load();
        Pane contentArea = (Pane) ((Node) event.getSource()).getScene().lookup("#content_area");
        contentArea.getChildren().clear();
        contentArea.getChildren().add(root);
    }


}
