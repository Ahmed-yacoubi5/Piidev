package com.recrutement.services;

import com.recrutement.models.cv;
import com.recrutement.utils.DataSources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CvServices implements iservices<cv> {

    private Connection connection = DataSources.getInstance().getConnection();

    @Override
    public void ajouter(cv cv) {
        String req = "INSERT INTO cv (nom, prenom,date_de_naissance,adresse,email,telephone) VALUES (?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, cv.getNom());
            pst.setString(2, cv.getPrenom());
            pst.setDate(2, new java.sql.Date(cv.getDate_de_naissance().getTime()));
            pst.setString(2, cv.getAdresse());
            pst.setString(2, cv.getEmail());
            pst.setInt(2, cv.getTelephone());
            pst.executeUpdate();
            System.out.println("cv ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(cv cv) {
        String req = "UPDATE cv SET nom=? ,prenom=?,date_de_naissance=?,adresse=?,email=?,telephone=? WHERE id_cv=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, cv.getNom());
            pst.setString(2, cv.getPrenom());
            pst.setDate(2, new java.sql.Date(cv.getDate_de_naissance().getTime()));
            pst.setString(2, cv.getAdresse());
            pst.setString(2, cv.getEmail());
            pst.setInt(2, cv.getTelephone());
            pst.setInt(3, cv.getId_cv());
            pst.executeUpdate();
            System.out.println("cv modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(cv cv) {
        String req = "DELETE from cv WHERE id_cv=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, cv.getId_cv());
            pst.executeUpdate();
            System.out.println("cv supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<cv> rechercher() {
        List<cv> cvs = new ArrayList<>();

        String req = "SELECT * FROM cv";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next()) {
                cvs.add(new cv(rs.getInt("id_cv"), rs.getString("nom"), rs.getString("prenom"),rs.getDate("date_de_naissance"),rs.getString("adresse"),rs.getString("email"),rs.getInt("telephone")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cvs;
    }
}
