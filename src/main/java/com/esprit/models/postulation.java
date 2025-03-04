package com.recrutement.models;

import java.util.Date;

public class postulation {

    private int id_postulation;

    private Date date_postulation;


    public postulation(int id_postulation,Date date_postulation) {
        this.id_postulation = id_postulation;
        this.date_postulation = date_postulation;
    }

    public postulation( Date date_postulation) {
        this.date_postulation = date_postulation;
    }

    public int getId_postulation() {return id_postulation;
    }

    public void setId_postulation(int id_postulation) {
        this.id_postulation = id_postulation;
    }
    public Date getDate_postulation() {
        return date_postulation;
    }

    public void setDate_postulation(Date date_postulation) {
        this.date_postulation = date_postulation;
    }

    @Override
    public String toString() {
        return "postulation{" +
                "id_postulation=" + id_postulation +
                ", date_postulation='" + date_postulation + '\'' +
                '}';
    }
}

