package com.esprit.tests;

import com.esprit.models.Employe;
import com.esprit.services.EmployeService;
import com.esprit.utils.DataSource;

@SuppressWarnings("unused")
public class MainProg {
    public static void main(String[] args) {
        EmployeService es = new EmployeService();
     es.ajouter(new Employe("Fedi", "Salah",'@da','11','19/1/2022'));
//        ps.modifier(new Personne(2, "Salah", "Samir"));
//        ps.supprimer(new Personne(2, "", ""));
        System.out.println(es.rechercher());
    }
}
