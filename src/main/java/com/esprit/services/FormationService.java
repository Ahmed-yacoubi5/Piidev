package com.esprit.services;
import com.esprit.models.Formation;
import com.esprit.utils.AppData;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class FormationService {
    private Connection connection = DataSource.getInstance().getConnection();


    public void ajouter( Formation formation) {
        String req = "INSERT INTO formation (diplome,institution,anneeobtention,id) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, formation.getDiplome());
            pst.setString(2, formation.getInstitution());
            pst.setInt(3, formation.getAnneeObtention());
            pst.setInt(4, formation.getId());
            pst.executeUpdate();
            System.out.println("Personne ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void modifier(Formation formation) {
        String req = "UPDATE personne SET diplome=? ,institution=?, anneeobtention=? WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, formation.getDiplome());
            pst.setString(2, formation.getInstitution());
            pst.setInt(3, formation.getAnneeObtention());
            pst.setInt(4, AppData.getInstance().getCurrentSelectedId());
            pst.executeUpdate();
            System.out.println("Personne modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(Formation formation) {
        String req = "DELETE from personne WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, formation.getId());
            pst.executeUpdate();
            System.out.println("Personne supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public List<Formation> rechercher() {
        List<Formation> formation = new ArrayList<>();

        String req = "SELECT diplome FROM formation where id= "+ AppData.getInstance().getCurrentSelectedId();
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next()) {
                formation.add(new Formation( rs.getString("diplome")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return formation;
    }
}
