package com.esprit.models;

public class Formation {
   private int id;
    private String Diplome;
    private String institution;
    private int anneeObtention;



    
    public Formation(int id,String diplome, String institution, int anneeObtention) {
       this.id=id;
        this.Diplome = diplome;
        this.institution = institution;
        this.anneeObtention = anneeObtention;
    }

    public Formation(String diplome) {
        this.Diplome = diplome;
    }


    public String getDiplome() {
        return Diplome;
    }
    public void setDiplome(String diplome) {
        Diplome = diplome;
    }
    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    public int getAnneeObtention() {
        return anneeObtention;
    }
    public void setAnneeObtention(int anneeObtention) {
        this.anneeObtention = anneeObtention;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return ("diplome: " + Diplome + '\n');
    }
}
