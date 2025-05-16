package com.esprit.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class offreemploi {
    private SimpleIntegerProperty id;
    private SimpleStringProperty titre;
    private SimpleStringProperty description;
    private SimpleStringProperty adresse;
    private Date date_publication;
    private statut statut;

    // Constructeurs
    public offreemploi(int id, String titre, String description, String adresse, Date date_publication, statut statut) {
        this.id = new SimpleIntegerProperty(id);
        this.titre = new SimpleStringProperty(titre);
        this.description = new SimpleStringProperty(description);
        this.adresse = new SimpleStringProperty(adresse);
        this.date_publication = date_publication;
        this.statut = statut;
    }

    public offreemploi(String titre, String description, String adresse, Date date_publication, statut statut) {
        this.titre = new SimpleStringProperty(titre);
        this.description = new SimpleStringProperty(description);
        this.adresse = new SimpleStringProperty(adresse);
        this.date_publication = date_publication;
        this.statut = statut;
    }

    // Getter and Setter for ID (as Property)
    public SimpleIntegerProperty idProperty() {
        if (id == null) {
            id = new SimpleIntegerProperty();
        }
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    // Getter and Setter for Titre
    public SimpleStringProperty titreProperty() {
        if (titre == null) {
            titre = new SimpleStringProperty();
        }
        return titre;
    }

    public String getTitre() {
        return titre.get();
    }

    public void setTitre(String titre) {
        this.titre.set(titre);
    }

    // Getter and Setter for Description
    public SimpleStringProperty descriptionProperty() {
        if (description == null) {
            description = new SimpleStringProperty();
        }
        return description;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    // Getter and Setter for Date of Publication
    public Date getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(Date date_publication) {
        this.date_publication = date_publication;
    }

    // Getter and Setter for Statut
    public statut getStatut() {
        return statut;
    }

    public void setStatut(statut statut) {
        this.statut = statut;
    }
    public SimpleStringProperty adresseProperty() {
        if (adresse == null) {
            adresse = new SimpleStringProperty();
        }
        return adresse;
    }

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    // Enhanced toString method
    @Override
    public String toString() {
        return "OffreEmploi{" +
                "id=" + id.get() +
                ", titre='" + titre.get() + '\'' +
                ", description='" + description.get() + '\'' +
                ", adresse='" + adresse.get() + '\'' +
                ", date_publication=" + date_publication +
                ", statut=" + statut +
                '}';
    }
}
