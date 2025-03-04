package com.esprit.models;

import java.sql.Date;
import java.time.LocalDateTime;

public class Evenement {
    private int id;
    private String nom;
    private int remise;
    private double prix;
    private String img;
    private String description;

    private String lieu;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

    private static String searchValue;

    public static int actionTest = 0;

    public static int getIdEvenement() {
        return idEvenement;
    }

    public static void setIdEvenement(int idEvenement) {
        Evenement.idEvenement = idEvenement;
    }

    private static int idEvenement;
    public Evenement(int id, String nom, String img, String description,  String lieu, LocalDateTime dateDebut, LocalDateTime dateFin) {
        this.id = id;
        this.nom = nom;
        this.img = img;
        this.description = description;
        this.lieu = lieu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }


    public Evenement() {
    }




    // Getters et setters
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

    public int getRemise() {
        return remise;
    }

    public void setRemise(int remise) {
        this.remise = remise;
    }

    public double getPrix() {
        return prix;
    }
    public static String getSearchValue() {
        return searchValue;
    }
    public static void setSearchValue(String searchValue) {
        Evenement.searchValue = searchValue;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    public static int getActionTest() {
        return actionTest;
    }

    public static void setActionTest(int actionTest) {
        Evenement.actionTest = actionTest;
    }


}
