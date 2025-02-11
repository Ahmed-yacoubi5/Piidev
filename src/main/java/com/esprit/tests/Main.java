package com.esprit.tests;


import com.esprit.models.RetourEvenement;
import com.esprit.services.RetourEvenementService;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        RetourEvenementService res = new RetourEvenementService();

        // Ajouter un retour d'événement
        RetourEvenement retour1 = new RetourEvenement(1, 2, "Super événement, bien organisé !", 5);
        res.ajouter(retour1);

        // Modifier un retour (exemple pour l'ID 1)
        RetourEvenement retourModifie = new RetourEvenement(1, 1, 2, "Événement intéressant mais trop long", 4, LocalDateTime.now());
        res.modifier(retourModifie);

        // Supprimer un retour (exemple pour l'ID 1)
        res.supprimer(1);

        // Afficher tous les retours
        System.out.println("Liste des retours : " + res.rechercher());
    }
}



