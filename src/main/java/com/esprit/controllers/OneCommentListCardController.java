package com.esprit.controllers;
import com.esprit.models.Commentaire;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import com.esprit.services.IService;
import com.esprit.services.ServiceCommentaire;

public class OneCommentListCardController {
    @FXML
    private ImageView img;
    @FXML
    private Text Contenu;

    @FXML
    private HBox addlike;
    @FXML
    private HBox adddislike;
    @FXML
    private Text likef;

    @FXML
    private Text dislikef;
    @FXML
    private Text Date;
    @FXML
    private Text Nomuser;
    @FXML
    private Text Img;
    int like ;
    int dislike;
    private static int projetIdToUpdate = 0;
    private static int projetIdToShow = 0;
    private static int updateProjectModelShow = 0;

    @FXML
    private HBox priceHbox;
    @FXML
    private HBox ItemShowBtn;
    public void setCommentData(Commentaire comment) {
        IService commentService = new ServiceCommentaire();
        Contenu.setText(comment.getContenu());
        Image image = new Image(
                getClass().getResource("/assets/EventUploads/" + comment.getImg()).toExternalForm());
        img.setImage(image);
        Nomuser.setText(String.valueOf(comment.getNomuser()));


    }


}
