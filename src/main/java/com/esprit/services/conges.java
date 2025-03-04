package com.esprit.models;

public class conges {
    private String type;
    private String datedebut;
    private String datefin;
    private int employee_id;
    private String statut;

    public conges(String type, String datedebut, String datefin, int employee_id, String statut) {
        this.type = type;
        this.datedebut = datedebut;
        this.datefin = datefin;
        this.employee_id = employee_id;
        this.statut = statut;
    }

    public String getType() { return type; }
    public String getDatedebut() { return datedebut; }
    public String getDatefin() { return datefin; }
    public int getEmployee_id() { return employee_id; }
    public String getStatut() { return statut; }

    public void setType(String type) {
        this.type = type;
    }

    public void setDatedebut(String datedebut) {
        this.datedebut = datedebut;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "conges{" +
                "type='" + type + '\'' +
                ", datedebut='" + datedebut + '\'' +
                ", datefin='" + datefin + '\'' +
                ", employee_id=" + employee_id +
                ", statut='" + statut + '\'' +
                '}';
    }


}
