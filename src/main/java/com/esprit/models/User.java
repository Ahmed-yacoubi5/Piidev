package com.esprit.models;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private Date dateDeNaissance;
    private String roles;
    private String genre;
    private String adresse;
    private String numDeTelephone;
    private String status;

    public User(int id, String email, String password, String nom, String prenom, Date dateDeNaissance,
                String roles, String genre, String adresse, String numDeTelephone, String status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.roles = roles;
        this.genre = genre;
        this.adresse = adresse;
        this.numDeTelephone = numDeTelephone;
        this.status = status;
    }

    public User(String email, String password, String nom, String prenom, Date dateDeNaissance,
                String roles, String genre, String adresse, String numDeTelephone, String status) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateDeNaissance;
        this.roles = roles;
        this.genre = genre;
        this.adresse = adresse;
        this.numDeTelephone = numDeTelephone;
        this.status = status;
    }

    // Getters and Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public Date getDateDeNaissance() { return dateDeNaissance; }
    public void setDateDeNaissance(Date dateDeNaissance) { this.dateDeNaissance = dateDeNaissance; }

    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getNumDeTelephone() { return numDeTelephone; }
    public void setNumDeTelephone(String numDeTelephone) { this.numDeTelephone = numDeTelephone; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateDeNaissance=" + dateDeNaissance +
                ", roles='" + roles + '\'' +
                ", genre='" + genre + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numDeTelephone='" + numDeTelephone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
