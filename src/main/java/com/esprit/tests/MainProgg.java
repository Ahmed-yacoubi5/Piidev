package com.esprit.tests;



import com.esprit.models.TypeEvenement;
import com.esprit.services.TypeEvenementService;

public class MainProgg {
    public static void main(String[] args) {
        TypeEvenementService tes = new TypeEvenementService();

        // Ajouter un type d'événement
        tes.ajouter(new TypeEvenement("Conférence", "Un événement où des experts discutent d'un sujet"));

        // Modifier un type d'événement (exemple pour l'ID 1)
        tes.modifier(new TypeEvenement(1, "Séminaire", "Une session éducative plus interactive"));

        // Supprimer un type d'événement (exemple pour l'ID 1)
        tes.supprimer(2);

        // Afficher tous les types d'événements
        System.out.println("Liste des types d'événements : " + tes.rechercher());
    }
}

