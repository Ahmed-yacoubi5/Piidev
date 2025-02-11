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

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Candidat candidat) {
        String req = "INSERT INTO candidat (nom, prenom,email,cv) VALUES ('"+candidat.getNom()+"','"+candidat.getPrenom()+"','"+candidat.getEmail()+"','"+candidat.getCv()+"')";
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

    @Override
    public List<Candidat> rechercher() {
        List<Candidat> candidat = new ArrayList<>();

        String req = "SELECT * FROM candidat";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                candidat.add(new Candidat(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return candidat;
    }
}