package com.esprit.services;

import com.esprit.models.postulation;
import com.esprit.utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PostulationServices implements iservices<postulation> {

    private Connection connection = database.getInstance().getConnection();

    @Override
    public void ajouter(postulation postulation) {
        String req = "INSERT INTO postulation (date_postulation) VALUES (?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(2, new Date(postulation.getDate_postulation().getTime()));
            pst.executeUpdate();
            System.out.println("postulation ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(postulation postulation) {
        String req = "UPDATE postulation SET date_postulation=? WHERE id_postulation=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setDate(2, new Date(postulation.getDate_postulation().getTime()));
            pst.setInt(2, postulation.getId_postulation());
            pst.executeUpdate();
            System.out.println("postulation modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(postulation postulation) {
        String req = "DELETE from postulation WHERE id_postulation=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, postulation.getId_postulation());
            pst.executeUpdate();
            System.out.println("postulation supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<postulation> rechercher() {
        List<postulation> postulations = new ArrayList<>();

        String req = "SELECT * FROM postulation";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next()) {
                postulations.add(new postulation(rs.getInt("id_postulation"),rs.getDate("date_postulation")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return postulations;
    }
}
