package com.esprit.models;

import java.util.Objects;

public class Profil {

    private int id;
    private double niveauFormation;
    private String competence;
    private String experience;
    private String certification;

    public Profil() {
    }

    public Profil(int id, double niveauFormation, String exp, String comp, String cer) {
        this.id = id;
        this.niveauFormation = niveauFormation;
        this.experience = exp;
        this.competence = comp;
        this.certification = cer;
    }

    public Profil(double niveauFormation, String exp, String comp, String cer) {
        this.niveauFormation = niveauFormation;
        this.experience = exp;
        this.competence = comp;
        this.certification = cer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNiveauFormation() {
        return niveauFormation;
    }

    public void setNiveauFormation(double niveauFormation) {
        this.niveauFormation = niveauFormation;
    }

    public String getExperince() {
        return experience;
    }

    public void setExperince(String experience) {
        this.experience = experience;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profil profil = (Profil) o;
        return id == profil.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Profil{" +
                "id=" + id +
                ", niveauFormation=" + niveauFormation +
                ", experience='" + experience + '\'' +
                ", competence='" + competence + '\'' +
                ", certification='" + certification + '\'' +
                '}';
    }

    public void setExperience(String experience) {
        this.experience=experience;
    }
}
