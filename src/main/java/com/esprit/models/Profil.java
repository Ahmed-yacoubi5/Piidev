package com.esprit.models;

public class Profil {

    protected static int id;
    protected static double niveauFormation;
    protected static String competence;
    protected static String experience;
    protected static String certification;

    @SuppressWarnings("static-access")
    public Profil() {

    }
    public Profil(int id, double niveauFormation , String exp, String comp , String cer) {
        this.id = id;
        this.niveauFormation = niveauFormation;
        this.experience = exp;
        this.competence = comp;
        this.certification = cer ;
    }

    @SuppressWarnings("static-access")
    public Profil(String exp, double niveauFormation , String comp , String cer) {
        this.niveauFormation = niveauFormation ;
        this.experience = exp;
        this.competence = comp;
        this.certification = cer;
    }

    public int getId() {
        return id;
    }

    @SuppressWarnings("static-access")
    public void setId(int id) {
        this.id = id;
    }
    
    @SuppressWarnings("static-access")
    public double getNiveauFormation(){
        return this.niveauFormation;
    }

    @SuppressWarnings("static-access")
    public void setNiveauFormation(double nf){
        this.niveauFormation=nf;
    }
    public String getExperince() {
        return experience;
    }

    @SuppressWarnings("static-access")
    public void setNom(String exp) {
        this.experience = exp;
    }

    public String getCompetence() {
        return competence;
    }

    @SuppressWarnings("static-access")
    public void setCompetence(String comp) {
        this.competence = comp;
    }

    @SuppressWarnings("static-access")
    public String getCertification(){
        return this.certification;
    }
    @SuppressWarnings("static-access")
    public void setCertification(String cer){
        this.certification = cer ;
    }

    public void setExperience(String experience) {
        Profil.experience = experience;
    }

    @Override
    public String toString() {
        return "Profil{" +
                "id=" + id +
                ", Niveau Formation='" + niveauFormation + '\'' +
                ", Experience='" + experience + '\'' +
                ", Competence='" + competence + '\'' +
                ", Certification='" + certification + '\'' +
                
                '}';
    }
}
