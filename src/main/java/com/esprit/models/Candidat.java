package com.esprit.models;

import java.util.ArrayList;

public class Candidat {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String cv;
    private ArrayList<Formation> formations = new ArrayList<>(); 

    public Candidat(String n , String p , String e ,String cv , int id) {

        this.nom=n;
        this.prenom=p;
        this.email=e;
        this.cv=cv;
        this.id=id;
    }
    public Candidat(String nom , String prenom,int id){
       this.id=id;
        this.nom=nom;
        this.prenom=prenom;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCv() {
        return cv;
    }
    public void setCv(String cv) {
        this.cv = cv;
    }
    public void addFormation(Formation f){
        this.formations.add(f);
    }
    public void supprimerFormation(int n){
        this.formations.remove(n);
    }
    @Override
    public String toString() {
        return "Candidat [nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", cv=" + cv + "]";
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }


}