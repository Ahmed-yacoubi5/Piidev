package com.esprit.tests;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainProg {
    public static void main(String[] args) {
        EvenementService ev = new EvenementService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dateDebut = dateFormat.parse("2003-12-09");
            Date dateFin = dateFormat.parse("2004-12-09");

            ev.ajouter(new Evenement("OMAIMA", "a", "aa", dateDebut, dateFin));

            ev.modifier(new Evenement("kk", "a", "aa", dateDebut, dateFin));
            ev.supprimer(new Evenement("abc", "", "", dateDebut, dateFin));

            System.out.println(ev.rechercher());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
