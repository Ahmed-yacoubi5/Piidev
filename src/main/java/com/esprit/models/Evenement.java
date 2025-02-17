package com.esprit.models;

import java.util.Date;

public class Evenement {
    private int id;
    private String nom;
    private String type;
    private String titre;
    private Date DateDebut;
    private Date DateFin;

    // No-argument constructor
    public Evenement() {
    }

    public Evenement(int id, String nom, String type, String titre, Date DateDebut, Date DateFin) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.titre = titre;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
    }

    public Evenement(String nom, String type, String titre, Date DateDebut, Date DateFin) {
        this.nom = nom;
        this.type = type;
        this.titre = titre;
        this.DateDebut = DateDebut;
        this.DateFin = DateFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Date getDateDebut() {
        return DateDebut;
    }

    public void setDateDebut(Date DateDebut) {
        this.DateDebut = DateDebut;
    }

    public Date getDateFin() {
        return DateFin;
    }

    public void setDateFin(Date DateFin) {
        this.DateFin = DateFin;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", titre='" + titre + '\'' +
                ", DateDebut=" + DateDebut +
                ", DateFin=" + DateFin +
                '}';
    }
}
