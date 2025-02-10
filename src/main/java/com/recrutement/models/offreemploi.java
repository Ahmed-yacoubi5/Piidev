package com.recrutement.models;

import java.util.Date;


public class offreemploi {

    private int id;
    private String titre;
    private String description;
    private Date date_publication;
    private String statut;

    public offreemploi(int id, String titre, String description, Date date_publication, String statut) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.date_publication = date_publication;
        this.statut = statut;
    }

    public offreemploi(String titre, String description, Date date_publication, String statut) {
        this.titre = titre;
        this.description = description;
        this.date_publication = date_publication;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDate_publication() {
        return date_publication;
    }

    public void setDate_publication(Date date_publication) {
        this.date_publication = date_publication;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", date_publication='" + date_publication + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}

