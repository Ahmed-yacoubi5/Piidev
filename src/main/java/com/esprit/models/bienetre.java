package com.esprit.models;

public class bienetre {
    private String nom;
    private String review;
    private int rate;

    // Constructor for bienetre
    public bienetre(String nom, String review, int rate) {
        this.nom = nom;
        this.review = review;
        this.rate = rate;
    }

    // Getter methods
    public String getNom() { return nom; }
    public String getReview() { return review; }
    public int getRate() { return rate; }

    // Setter methods
    public void setNom(String nom) { this.nom = nom; }
    public void setReview(String review) { this.review = review; }
    public void setRate(int rate) { this.rate = rate; }

    @Override
    public String toString() {
        return "bienetre{" +
                "nom='" + nom + '\'' +
                ", review='" + review + '\'' +
                ", rate=" + rate +
                '}';
    }
}
