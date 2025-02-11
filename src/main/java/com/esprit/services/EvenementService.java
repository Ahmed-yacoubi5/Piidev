package com.esprit.services;

import com.esprit.models.Evenement;
import com.esprit.utils.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EvenementService implements Iservice<Evenement> {
    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Evenement evenement) {
        String req = "UPDATE evenement SET nom='"+evenement.getNom()+"' ,type='"+evenement.getType()+"',titre='"+evenement.getTitle()+"',DateDebut='"+evenement.getDateDebut()+"',DateFin='"+evenement.getDateFin()+"' WHERE id="+evenement.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Evenement modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Evenement evenement) {
        String req = "UPDATE evenement SET nom='"+evenement.getNom()+"' ,type='"+evenement.getType()+"',titre='"+evenement.getTitle()+"',DateDebut='"+evenement.getDateDebut()+"',DateFin='"+evenement.getDateFin()+"' WHERE id="+evenement.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Evenement modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Evenement evenement) {
        String req = "DELETE from evenement WHERE id="+evenement.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Evenement supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Evenement> rechercher() {

        List<Evenement> evenement = new ArrayList<>();

        String req = "SELECT * FROM evenement";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next()) {
                evenement.add(new Evenement(rs.getInt("id"), rs.getString("nom"), rs.getString("type"),rs.getString("titre"),rs.getDate("DateDebut"),rs.getDate("DateFin")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evenement;
    }

}
