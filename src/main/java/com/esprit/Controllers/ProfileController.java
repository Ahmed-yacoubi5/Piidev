package com.esprit.Controllers;

import com.esprit.utils.AppData;
import com.esprit.utils.ImageSrc;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ProfileController {
    @FXML
    private ImageView profileImageView;

    public void initialize() {
        loadProfileImage();
    }

    public void loadProfileImage() {
        String imagePath =ImageSrc.getSrc(AppData.getInstance().getCurrentSelectedId());

        if (imagePath != null) {
            Image image = new Image(imagePath);
            profileImageView.setImage(image);
            System.out.println(imagePath);

                } else {
                    System.out.println("Profile image not found at path: " + imagePath);
                }
            }
        }



