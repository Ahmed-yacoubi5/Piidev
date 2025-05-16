package com.esprit.controllers;

import com.esprit.models.Commentaire;
import com.esprit.services.IService;
import com.esprit.services.ServiceCommentaire;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

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
    int like;
    int dislike;
    private static int projetIdToUpdate = 0;
    private static int projetIdToShow = 0;
    private static int updateProjectModelShow = 0;

    @FXML
    private HBox priceHbox;
    @FXML
    private HBox ItemShowBtn;
    
    public void setCommentData(Commentaire comment) {
        try {
            // Set comment text
            if (Contenu != null) {
                Contenu.setText(comment.getContenu());
            }
            
            // Use a default image from resources
            try {
                String imagePath = "/images/add.png"; // Default image that we know exists
                Image image = new Image(getClass().getResource(imagePath).toExternalForm());
                if (img != null) {
                    img.setImage(image);
                }
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
            
            // Set username
            if (Nomuser != null) {
                Nomuser.setText(comment.getNomuser());
            }
            
            // Set date if available
            if (Date != null && comment.getDate() != null) {
                Date.setText(comment.getDate());
            }
        } catch (Exception e) {
            System.out.println("Error setting comment data: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
