package com.esprit.models;

import java.util.Date;

public class Admin extends User {
    public Admin(int id, String email, String password, String nom, String prenom, Date dateDeNaissance, String genre, String roles, String adresse, String numDeTelephone, String status) {
        super(id, email, password, nom, prenom, dateDeNaissance, genre, roles, adresse, numDeTelephone, status);
    }
}
