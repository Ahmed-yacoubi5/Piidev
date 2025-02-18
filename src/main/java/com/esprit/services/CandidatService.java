package com.esprit.services;

import com.esprit.models.Candidat;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidatService implements IService<Candidat> {

    private static Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Candidat candidat) {
        String req = "INSERT INTO candidat (nom, prenom,email,cv,id) VALUES ('"+candidat.getNom()+"','"+candidat.getPrenom()+"','"+candidat.getEmail()+"','"+candidat.getCv()+"','"+candidat.getId()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("candidat ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Candidat candidat) {
        String req = "UPDATE candidat SET nom='"+candidat.getNom()+"' ,prenom='"+candidat.getPrenom()+"',email='"+candidat.getEmail()+"',cv='"+candidat.getCv()+ "' WHERE id="+candidat.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("candidat modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Candidat candidat) {
        String req = "DELETE from candidat WHERE id="+candidat.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("candidat supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

     public List<Candidat> rechercher() {
        List<Candidat> candidat = new ArrayList<>();

        String req = "SELECT * FROM candidat";
       try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                candidat.add(new Candidat(rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("cv"),rs.getInt("id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return candidat;
    }
    public void candidatAff(Candidat candidat) {
         int id;
         String nom;
         String prenom;
         String cv;
         String email;

        String req = "SELECT * FROM candidat where id = " + candidat.getId();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                id = rs.getInt("id");
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                cv = rs.getString("cv");
                email = rs.getString("email");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}