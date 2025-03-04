package com.esprit.Controllers;

import com.esprit.models.Candidat;
import com.esprit.models.Employe;
import com.esprit.models.Formation;
import com.esprit.models.Profil;
import com.esprit.services.CandidatService;
import com.esprit.services.EmployeService;
import com.esprit.services.FormationService;
import com.esprit.services.ProfilService;
import com.esprit.utils.AppData;
import com.esprit.utils.AutoMail;
import com.esprit.utils.ImageSrc;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;


public class ProfileController {
    @FXML
    private ImageView profileImageView;
    @FXML
    private TextArea competence;
    @FXML
    private TextArea experience;
    @FXML
    private TextArea certification;
    @FXML
    private Slider niveauFormation;
    @FXML
    private Label nomLabel,prenomLabel,emailLabel;
    @FXML
    private ListView<Formation> formationListView ;
    @FXML
    private Button emailButton;

    public void initialize() {
        niveauFormation.setMin(0);
        niveauFormation.setMax(100);
        loadProfileImage();
        afficheProfile();
        fillFormationListView();
        niveauFormation.opacityProperty().setValue(1);
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
        public void afficheProfile() {
            Profil profil = new Profil();
            ProfilService profilService = new ProfilService();
            if (AppData.getInstance().getRedirectionFrom() == "employe") {
                profil = profilService.getProfile(AppData.getInstance().getCurrentSelectedId());
                EmployeService employeService = new EmployeService();
                Employe employe = new Employe();
                employe.setId(AppData.getInstance().getCurrentSelectedId());
                employeService.empAff(employe);
                competence.setText(profil.getCompetence());
                experience.setText(profil.getExperince());
                certification.setText(profil.getCertification());
                niveauFormation.setValue(profil.getNiveauFormation());
                nomLabel.setText(employe.getNom());
                prenomLabel.setText(employe.getPrenom());
                emailLabel.setText(employe.getEmail());
                competence.setEditable(false);
                experience.setEditable(false);
                certification.setEditable(false);
                niveauFormation.setDisable(true);


            } else {
                profil = profilService.getProfile(AppData.getInstance().getSelectedCandidatId());
                CandidatService candidatService = new CandidatService();
                Candidat candidat = new Candidat();
                candidat.setId(AppData.getInstance().getSelectedCandidatId());
                candidatService.candidatAff(candidat);

                competence.setText(profil.getCompetence());
                competence.setEditable(false);
                experience.setText(profil.getExperince());
                experience.setEditable(false);
                certification.setText(profil.getCertification());
                certification.setEditable(false);
                niveauFormation.setValue(profil.getNiveauFormation());
                niveauFormation.setDisable(true);
                nomLabel.setText(candidat.getNom());
                prenomLabel.setText(candidat.getPrenom());
                emailLabel.setText(candidat.getEmail());
            }
        }
    public void fillFormationListView() {
        ObservableList<Formation> fl = FXCollections.observableArrayList();
        if (AppData.getInstance().getRedirectionFrom().equals("employe")) {
            Formation formation = new Formation();
            FormationService formationService = new FormationService();
            formation.setId(AppData.getInstance().getCurrentSelectedId());

            // Assuming rechercher returns a List
            List<Formation> list = formationService.rechercher(formation.getId());

            // Convert the list to an ObservableList
            fl = FXCollections.observableArrayList(list);

            // Debugging: print the number of items and the items themselves
            System.out.println("Number of items in the list: " + fl.size());
            for (Formation f : fl) {
                System.out.println(f);
            }

            formationListView.setItems(fl);
            formationListView.refresh(); // Refresh the ListView
        }
    }
    public void sendVerificationEmail() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to Send a verification email?");
        alert.setContentText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {


            String destination = emailLabel.getText();
            String fullName = nomLabel.getText() + " " + prenomLabel.getText();
            AutoMail.sendAutomatedEmail(destination, fullName);
        }
    }

}



