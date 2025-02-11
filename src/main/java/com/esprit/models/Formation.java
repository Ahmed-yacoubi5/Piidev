package com.esprit.models;

public class Formation {
    private String Diplome;
    private String institution;
    private int anneeObtention;



    
    public Formation(String diplome, String institution, int anneeObtention) {
        Diplome = diplome;
        this.institution = institution;
        this.anneeObtention = anneeObtention;
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
}
