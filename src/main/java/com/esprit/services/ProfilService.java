package com.esprit.services;

import com.esprit.models.Profil;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfilService implements IService<Profil> {

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Profil profil) {
        String req = "INSERT INTO personne (id,niveauFormation, competence, experience, certification) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, profil.getId());
            pst.setString(2, profil.getNiveauFormation());
            pst.setString(3, profil.getCompetence());
            pst.setString(4, profil.getExperince());
            pst.setString(5, profil.getCertification());
            pst.executeUpdate();

            System.out.println("Personne ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifier(Profil profil) {
        String req = "UPDATE personne SET niveauFormation=? ,competence=? , experience=?, certification=? WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1, profil.getNiveauFormation());
            pst.setString(2, profil.getCompetence());
            pst.setInt(5, profil.getId());
            pst.setString(3, profil.getExperince());
            pst.setString(4, profil.getCertification());
            pst.executeUpdate();
            System.out.println("Personne modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Profil profil) {
        String req = "DELETE from personne WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, profil.getId());
            pst.executeUpdate();
            System.out.println("Personne supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

  @Override
    public List<Profil> rechercher() {

        List<Profil> profil = new ArrayList<>();

      /**  String req = "SELECT * FROM personne";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next()) {
                personnes.add(new Profil(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

       **/ return profil;
  }
}
