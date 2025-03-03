package com.esprit.models;
import java.time.LocalDateTime;

public class RetourEvenement {
        private int id;
        private int evenementId;
        private int utilisateurId;
        private String commentaire;
        private int note;
        private String date;
        public RetourEvenement() {}

        public RetourEvenement(int evenementId, int utilisateurId,String date, String commentaire, int note) {
            this.evenementId = evenementId;
            this.utilisateurId = utilisateurId;
            this.commentaire = commentaire;
            this.note = note;
            this.date = date;
        }

        public RetourEvenement(int id, int evenementId, int utilisateurId, String commentaire, int note, String date) {
            this.id = id;
            this.evenementId = evenementId;
            this.utilisateurId = utilisateurId;
            this.commentaire = commentaire;
            this.note = note;
            this.date = date;
        }
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "RetourEvenement{" +
                "id=" + id +
                ", evenementId=" + evenementId +
                ", utilisateurId=" + utilisateurId +
                ", commentaire='" + commentaire + '\'' +
                ", note=" + note +
                ", date='" + date + '\'' +
                '}';
    }
}


