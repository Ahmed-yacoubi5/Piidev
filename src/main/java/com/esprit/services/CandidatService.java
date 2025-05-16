package com.esprit.services;

import com.esprit.models.*;
import com.esprit.utils.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidatService implements IService<Candidat> {

    private static Connection connection = database.getInstance().getConnection();

    @Override
    public void ajouter(Candidat candidat) throws SQLException, IOException {
        String req = "INSERT INTO candidat (nom, prenom,email,cv,id) VALUES ('"+candidat.getNom()+"','"+candidat.getPrenom()+"','"+candidat.getEmail()+"','"+candidat.getCv()+"','"+candidat.getId()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("candidat ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void modifier(Candidat candidat) throws SQLException {
        String req = "UPDATE candidat SET nom='"+candidat.getNom()+"' ,prenom='"+candidat.getPrenom()+"',email='"+candidat.getEmail()+"',cv='"+candidat.getCv()+ "' WHERE id="+candidat.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("candidat modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void supprimer(Evenement evenement) throws SQLException {
        // Not applicable for Candidat
        System.out.println("Operation not supported for Candidat");
    }

    @Override
    public void supprimer(Candidat candidat) throws SQLException {
        String req = "DELETE from candidat WHERE id=" + candidat.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("candidat supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
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

    @Override
    public List<Candidat> afficher() throws SQLException {
        List<Candidat> candidats = new ArrayList<>();
        String req = "SELECT * FROM candidat";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                candidats.add(new Candidat(rs.getString("nom"),rs.getString("prenom"),rs.getString("email"),rs.getString("cv"),rs.getInt("id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return candidats;
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE from candidat WHERE id=" + id;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("candidat supprimée avec succès");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public Candidat afficher1(int id) throws SQLException {
        Candidat candidat = null;
        String req = "SELECT * FROM candidat WHERE id = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                candidat = new Candidat(
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("cv"),
                    rs.getInt("id")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return candidat;
    }

    @Override
    public List<Evenement> sortEvent(int value) {
        // Not applicable for Candidat
        return null;
    }

    @Override
    public Evenement getOneEvenement(int idEvenement) throws SQLException {
        // Not applicable for Candidat
        return null;
    }

    @Override
    public void AddEvenenemtOffer(Evenement evenement) {
        // Not applicable for Candidat
    }

    @Override
    public void supprimer(Employe employe) throws SQLException {

    }

    @Override
    public void supprimer(Profil profil) throws SQLException {

    }

    @Override
    public List<Commentaire> show(int evenement_id) throws SQLException {
        // Not applicable for Candidat
        return null;
    }
    
    public void candidatAff(Candidat candidat) {
        int id = 0;
        String nom = "";
        String prenom = "";
        String cv = "";
        String email = "";

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
        candidat.setId(id);
        candidat.setPrenom(prenom);
        candidat.setEmail(email);
        candidat.setNom(nom);
        candidat.setCv(cv);
    }

    @Override
    public void supprimer(Object t) throws SQLException {
        if (t instanceof Candidat) {
            Candidat candidat = (Candidat) t;
            String req = "DELETE from candidat WHERE id=" + candidat.getId();
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("candidat supprimée");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Object to delete must be of type Candidat");
        }
    }

    @Override
    public void supprimer(Commentaire commentaire) throws SQLException {
        // Not applicable for Candidat
        System.out.println("Operation not supported for Candidat");
    }
}
