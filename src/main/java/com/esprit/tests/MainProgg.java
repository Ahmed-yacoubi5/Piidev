package com.esprit.tests;



import com.esprit.models.TypeEvenement;
import com.esprit.services.TypeEvenementService;

public class MainProgg {
    public static void main(String[] args) {
        TypeEvenementService tes = new TypeEvenementService();


        tes.ajouter(new TypeEvenement("Conférence", "Un événement où des experts discutent d'un sujet"));

        tes.modifier(new TypeEvenement(1, "Séminaire", "Une session éducative plus interactive"));

        tes.supprimer(2);


        System.out.println("Liste des types d'événements : " + tes.rechercher());
    }
}

