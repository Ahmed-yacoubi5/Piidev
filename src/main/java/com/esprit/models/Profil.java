package com.esprit.models;

public class Profil {

    protected static int id;
    protected static String niveauFormation;
    protected static String competence;
    protected static String experience;
    protected static String certification;

    @SuppressWarnings("static-access")
    public Profil(int id, String niveauFormation , String exp, String comp , String cer) {
        this.id = id;
        this.niveauFormation = niveauFormation;
        this.experience = exp;
        this.competence = comp;
        this.certification = cer ;
    }

    @SuppressWarnings("static-access")
    public Profil(String exp, String niveauFormation , String comp , String cer) {
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
    public String getNiveauFormation(){
        return this.niveauFormation;
    }

    @SuppressWarnings("static-access")
    public void setNiveauFormation(String nf){
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
