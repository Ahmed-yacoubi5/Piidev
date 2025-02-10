package com.recrutement.models;

import java.util.Date;

public class cv {

    private int id_cv;
    private String nom;
    private String prenom;
    private Date date_de_naissance;
    private String adresse;
    private String email;
    private int telephone;

    public cv(int id_cv, String nom, String prenom, Date date_de_naissance, String adresse,String email,int telephone) {
        this.id_cv = id_cv;
        this.nom = nom;
        this.prenom = prenom;
        this.date_de_naissance = date_de_naissance;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
    }

    public cv(String nom, String prenom, Date date_de_naissance, String adresse,String email,int telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.date_de_naissance = date_de_naissance;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
    }

    public int getId_cv() {
        return id_cv;
    }

    public void setId_cv(int id_cv) {
        this.id_cv = id_cv;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String titre) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public Date getDate_de_naissance() {
        return date_de_naissance;
    }

    public void setDate_de_naissance(Date date_de_naissance) {
        this.date_de_naissance = date_de_naissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public int getTelephone() {
        return telephone;
    }
    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "cv{" +
                "id_cv=" + id_cv +
                ", nom='" + nom + '\'' +
                ", description='" + prenom + '\'' +
                ", date_publication='" + date_de_naissance + '\'' +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                "telephone=" + telephone +
                '}';
    }
}

