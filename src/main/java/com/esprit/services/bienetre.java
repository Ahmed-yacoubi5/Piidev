package com.esprit.models;

public class bienetre {
    private String nom;
    private String review;
    private int rate;
    private String sentiment; // Nouvelle variable pour stocker le sentiment

    // Constructor avec sentiment
    public bienetre(String nom, String review, int rate, String sentiment) {
        this.nom = nom;
        this.review = review;
        this.rate = rate;
        this.sentiment = sentiment;
    }

    // Constructeur sans sentiment (utile pour l'ajout avant classification)
    public bienetre(String nom, String review, int rate) {
        this.nom = nom;
        this.review = review;
        this.rate = rate;
        this.sentiment = "Indéterminé"; // Valeur par défaut avant analyse
    }

    // Getter methods
    public String getNom() { return nom; }
    public String getReview() { return review; }
    public int getRate() { return rate; }
    public String getSentiment() { return sentiment; } // Getter pour le sentiment

    // Setter methods
    public void setNom(String nom) { this.nom = nom; }
    public void setReview(String review) { this.review = review; }
    public void setRate(int rate) { this.rate = rate; }
    public void setSentiment(String sentiment) { this.sentiment = sentiment; } // Setter pour le sentiment

    @Override
    public String toString() {
        return "bienetre{" +
                "nom='" + nom + '\'' +
                ", review='" + review + '\'' +
                ", rate=" + rate +
                ", sentiment='" + sentiment + '\'' + // Ajout du sentiment dans le toString
                '}';
    }
}
