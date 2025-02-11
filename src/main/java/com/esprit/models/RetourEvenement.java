package com.esprit.models;
import java.time.LocalDateTime;

public class RetourEvenement {



        private int id;
        private int evenementId;
        private int utilisateurId;
        private String commentaire;
        private int note;
        private LocalDateTime dateRetour;

        public RetourEvenement() {}

        public RetourEvenement(int evenementId, int utilisateurId, String commentaire, int note) {
            this.evenementId = evenementId;
            this.utilisateurId = utilisateurId;
            this.commentaire = commentaire;
            this.note = note;
            this.dateRetour = LocalDateTime.now();
        }

        public RetourEvenement(int id, int evenementId, int utilisateurId, String commentaire, int note, LocalDateTime dateRetour) {
            this.id = id;
            this.evenementId = evenementId;
            this.utilisateurId = utilisateurId;
            this.commentaire = commentaire;
            this.note = note;
            this.dateRetour = dateRetour;
        }

        // Getters et Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getEvenementId() {
            return evenementId;
        }

        public void setEvenementId(int evenementId) {
            this.evenementId = evenementId;
        }

        public int getUtilisateurId() {
            return utilisateurId;
        }

        public void setUtilisateurId(int utilisateurId) {
            this.utilisateurId = utilisateurId;
        }

        public String getCommentaire() {
            return commentaire;
        }

        public void setCommentaire(String commentaire) {
            this.commentaire = commentaire;
        }

        public int getNote() {
            return note;
        }

        public void setNote(int note) {
            if (note >= 1 && note <= 5) {
                this.note = note;
            } else {
                throw new IllegalArgumentException("La note doit être entre 1 et 5");
            }
        }

        public LocalDateTime getDateRetour() {
            return dateRetour;
        }

        public void setDateRetour(LocalDateTime dateRetour) {
            this.dateRetour = dateRetour;
        }

        @Override
        public String toString() {
            return "RetourEvenement{" +
                    "id=" + id +
                    ", evenementId=" + evenementId +
                    ", utilisateurId=" + utilisateurId +
                    ", commentaire='" + commentaire + '\'' +
                    ", note=" + note +
                    ", dateRetour=" + dateRetour +
                    '}';
        }
    }


