package com.esprit.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class cv {

    private SimpleIntegerProperty idCv;
    private SimpleStringProperty nom;
    private SimpleStringProperty prenom;
    private Date dateDeNaissance;
    private SimpleStringProperty adresse;
    private SimpleStringProperty email;
    private SimpleIntegerProperty telephone;
    private SimpleStringProperty image;

    // Constructeur
    public cv(int idCv, String nom, String prenom, Date dateDeNaissance, String adresse, String email, int telephone, String image) {
        this.idCv = new SimpleIntegerProperty(idCv);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.dateDeNaissance = dateDeNaissance;
        this.adresse = new SimpleStringProperty(adresse);
        this.email = new SimpleStringProperty(email);
        this.telephone = new SimpleIntegerProperty(telephone);
        this.image = new SimpleStringProperty(image);  // Changer pour StringProperty
    }

    // Getter and Setter for idCv (as Property)
    public SimpleIntegerProperty idCvProperty() {
        if (idCv == null) {
            idCv = new SimpleIntegerProperty();
        }
        return idCv;
    }

    public int getIdCv() {
        return idCv.get();
    }

    public void setIdCv(int idCv) {
        this.idCv.set(idCv);
    }

    // Getter and Setter for image
    public SimpleStringProperty imageProperty() {
        if (image == null) {
            image = new SimpleStringProperty();
        }
        return image;
    }

    public String getImage() {
        return image.get();
    }

    public void setImage(String image) {
        this.image.set(image);
    }
    // Getter and Setter for nom
    public SimpleStringProperty nomProperty() {
        if (nom == null) {
            nom = new SimpleStringProperty();
        }
        return nom;
    }

    public String getNom() {
        return nom.get();
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    // Getter and Setter for prenom
    public SimpleStringProperty prenomProperty() {
        if (prenom == null) {
            prenom = new SimpleStringProperty();
        }
        return prenom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    // Getter and Setter for dateDeNaissance
    public Date getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(Date dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    // Getter and Setter for adresse
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

    // Getter and Setter for email
    public SimpleStringProperty emailProperty() {
        if (email == null) {
            email = new SimpleStringProperty();
        }
        return email;
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    // Getter and Setter for telephone
    public SimpleIntegerProperty telephoneProperty() {
        if (telephone == null) {
            telephone = new SimpleIntegerProperty();
        }
        return telephone;
    }

    public int getTelephone() {
        return telephone.get();
    }

    public void setTelephone(int telephone) {
        this.telephone.set(telephone);
    }
}
