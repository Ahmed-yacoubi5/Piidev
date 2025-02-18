package com.recrutement.tests;

import com.recrutement.models.statut;
import com.recrutement.models.offreemploi;
import com.recrutement.services.CvServices;
import com.recrutement.services.OffreEmploiServices;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainProg {

    public static void main(String[] args) {
            OffreEmploiServices ps = new OffreEmploiServices();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date_publication = dateFormat.parse("2025-02-15");
            java.sql.Date sqlDate = new java.sql.Date(date_publication.getTime());
            ps.ajouter(new offreemploi("aaa", "bbb",sqlDate, statut.DISPONIBLE));
            ps.modifier(new offreemploi(1, "rrr", "ccc", sqlDate, statut.valueOf("INDISPONIBLE")));
            ps.supprimer(new offreemploi(1, "", "", sqlDate, statut.valueOf("INDISPONIBLE")));
            System.out.println(ps.rechercher());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}

