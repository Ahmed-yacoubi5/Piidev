package com.esprit.models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Employe extends Profil {

    private String nom;
    private String prenom;
    private String email;
    private String poste;
    private LocalDate dateEmbauche;
    private ArrayList<Formation> formations = new ArrayList<>();

public Employe(int id ,String niveauFormation , String competence, String certification , String experience ,String n , String p , String email , String poste , LocalDate de){
    super(id , niveauFormation , competence , certification , experience);
    this.nom=n;
    this.prenom=p;
    this.email=email;
    this.poste=poste;
    this.dateEmbauche=de;
}
public Employe(int id , String nom , String prenom){
    super(id,niveauFormation,competence,experience,certification);
    this.nom=nom;
    this.prenom=prenom;

}
    public Employe(String nom , String prenom , String email , String poste , LocalDate de) {
        super(id,niveauFormation,competence,experience,certification);
        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.poste=poste;
        this.dateEmbauche=de;
    }
 public String getNom(){
    return this.nom;
 }
 public String getPrenom(){
    return this.prenom;
 }
 public String getEmail(){
    return this.email;
 }
 public String getPoste(){
    return this.poste;
 }
 public LocalDate GetDateEmbauche(){
    return this.dateEmbauche;
 }

public void setNom(String n){
    this.nom=n;
}
public void setPrenom(String p){
    this.prenom=p;
}
public void setEmail(String e){
    this.email=e;
}
public void setPoste(String p){
    this.poste=p;
}
public void setDateEmbauche(LocalDate de){
    this.dateEmbauche=de;
}
public void addFormation(Formation f){
    this.formations.add(f);
}
public void supprimerFormation(int n){
    this.formations.remove(n);
}
}