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
        // Requête pour insérer un nouvel événement
        String req = "INSERT INTO evenement (nom, type, titre, DateDebut, DateFin) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, evenement.getNom());
            pst.setString(2, evenement.getType());
            pst.setString(3, evenement.getTitre());
            pst.setDate(4, new java.sql.Date(evenement.getDateDebut().getTime()));
            pst.setDate(5, new java.sql.Date(evenement.getDateFin().getTime()));
            pst.executeUpdate();
            System.out.println("Événement ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Evenement evenement) {
        // Requête pour mettre à jour un événement existant
        String req = "UPDATE evenement SET nom=?, type=?, titre=?, DateDebut=?, DateFin=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, evenement.getNom());
            pst.setString(2, evenement.getType());
            pst.setString(3, evenement.getTitre());
            pst.setDate(4, new java.sql.Date(evenement.getDateDebut().getTime()));
            pst.setDate(5, new java.sql.Date(evenement.getDateFin().getTime()));
            pst.setInt(6, evenement.getId());
            pst.executeUpdate();
            System.out.println("Événement modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Evenement evenement) {
        // Requête pour supprimer un événement existant
        String req = "DELETE FROM evenement WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, evenement.getId());
            pst.executeUpdate();
            System.out.println("Événement supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Evenement> rechercher() {
        List<Evenement> evenementList = new ArrayList<>();
        String req = "SELECT * FROM evenement";
        try (PreparedStatement pst = connection.prepareStatement(req);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                evenementList.add(new Evenement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getString("titre"),
                        rs.getDate("DateDebut"),
                        rs.getDate("DateFin")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return evenementList;
    }

    public Evenement getAllEvenements() {
        return null;
    }
}
