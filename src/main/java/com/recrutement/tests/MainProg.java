package com.recrutement.tests;

import com.recrutement.models.offreemploi;
import com.recrutement.services.OffreEmploiServices;


public class MainProg {

    public static void main(String[] args) {
            OffreEmploiServices ps = new OffreEmploiServices();
            ps.ajouter(new offreemploi("aaa", "bbb", 10/02/2025, "accepter"));
            ps.modifier(new offreemploi(2, "rrr", "ccc", 10/02/2025, "refuser"));
            ps.supprimer(new offreemploi(2, "", "", 10/02/2025, ""));
            System.out.println(ps.rechercher());

    }
}

