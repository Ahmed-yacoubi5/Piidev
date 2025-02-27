package com.recrutement.models;

import java.util.Date;

public class cv {
    private int idCv;
    private String nom;
    private String prenom;
    private Date dateDeNaissance;
    private String adresse;
    private String email;
    private int telephone;

    // Constructeurs
    public cv() {}

    public cv(int idCv, String nom, String prenom, Date dateDeNaissance, String adresse, String email, int telephone) {
        this.idCv = idCv;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
    }

    public cv(String nom, String prenom, Date dateDeNaissance, String adresse, String email, int telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
    }

    // Getters et Setters
    public int getIdCv() { return idCv; }
    public void setIdCv(int idCv) { this.idCv = idCv; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public Date getDateDeNaissance() { return dateDeNaissance; }
    public void setDateDeNaissance(Date dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getTelephone() { return telephone; }
    public void setTelephone(int telephone) { this.telephone = telephone; }

    @Override
    public String toString() {
        return "cv{" +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateDeNaissance=" + dateDeNaissance +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                ", telephone=" + telephone +
                '}';
    }
}