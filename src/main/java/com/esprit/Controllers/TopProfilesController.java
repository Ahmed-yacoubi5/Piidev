package com.esprit.controllers;

import com.esprit.models.Profil;
import com.esprit.services.ProfilService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class TopProfilesController implements Initializable {

    @FXML
    private Slider niveauFormationSlider;

    @FXML
    private ListView<String> profilesListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ProfilService profilService = new ProfilService();
        List<Profil> topProfiles = profilService.rechercherTop5();

        // Debug: Check the initial list of top profiles
        System.out.println("Initial top profiles: " + topProfiles);

        // Convert list to set to remove duplicates
        Set<Profil> uniqueTopProfiles = new HashSet<>(topProfiles);

        // Debug: Check the unique profiles after conversion
        System.out.println("Unique top profiles: " + uniqueTopProfiles);

        configureSlider(uniqueTopProfiles);
        updateProfilesListView(uniqueTopProfiles);
    }

    private void configureSlider(Set<Profil> topProfiles) {
        if (!topProfiles.isEmpty()) {
            double min = topProfiles.stream().mapToDouble(Profil::getNiveauFormation).min().orElse(0);
            double max = topProfiles.stream().mapToDouble(Profil::getNiveauFormation).max().orElse(100);
            niveauFormationSlider.setMin(min);
            niveauFormationSlider.setMax(max);
            niveauFormationSlider.setValue(max);
            niveauFormationSlider.setBlockIncrement(1);
        }
    }

    private void updateProfilesListView(Set<Profil> topProfiles) {
        profilesListView.getItems().clear();
        for (Profil profil : topProfiles) {
            String profileText = "Nom: " + profil.getCompetence() +
                    ", Prenom: " + profil.getExperince() +
                    ", Niveau Formation: " + profil.getNiveauFormation();
            System.out.println("Displaying profile: " + profileText);
            profilesListView.getItems().add(profileText);
        }
    }
}
