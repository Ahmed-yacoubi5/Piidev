// OffreEmploiServices.java
package com.esprit.services;

import com.esprit.models.offreemploi;
import com.esprit.models.statut;
import com.esprit.utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreEmploiServices implements iservices<offreemploi> {

    private final Connection connection = database.getInstance().getConnection();

    @Override
    public void ajouter(offreemploi offreemploi) {
        String req = "INSERT INTO offreemploi (titre, description, adresse, date_publication, statut) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, offreemploi.getTitre());
            pst.setString(2, offreemploi.getDescription());
            pst.setString(3, offreemploi.getAdresse());
            pst.setDate(4, new Date(offreemploi.getDate_publication().getTime()));
            pst.setString(5, offreemploi.getStatut().toString());
            pst.executeUpdate();
            System.out.println("✅ Offre d'emploi ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void modifier(offreemploi offreemploi) {
        String req = "UPDATE offreemploi SET titre=?, description=?, adresse=?, date_publication=?, statut=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, offreemploi.getTitre());
            pst.setString(2, offreemploi.getDescription());
            pst.setString(3, offreemploi.getAdresse());
            pst.setDate(4, new Date(offreemploi.getDate_publication().getTime()));
            pst.setString(5, offreemploi.getStatut().toString());
            pst.setInt(6, offreemploi.getId());
            pst.executeUpdate();
            System.out.println("✅ Offre d'emploi modifiée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification : " + e.getMessage());
        }
    }

    @Override
    public void supprimer(offreemploi offreemploi) {
        String req = "DELETE FROM offreemploi WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, offreemploi.getId());
            pst.executeUpdate();
            System.out.println("✅ Offre d'emploi supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression : " + e.getMessage());
        }
    }

    @Override
    public List<offreemploi> rechercher() {
        return getAllOffres();
    }

    private List<offreemploi> favoris = new ArrayList<>();

    public void ajouterFavoris(offreemploi offre) {
        if (!favoris.contains(offre)) {
            favoris.add(offre);
        }
    }

    public List<offreemploi> getFavoris() {
        return favoris;
    }

    public List<offreemploi> getAllOffres() {
        List<offreemploi> offreemplois = new ArrayList<>();
        String req = "SELECT * FROM offreemploi";
        try (PreparedStatement pst = connection.prepareStatement(req);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                offreemplois.add(new offreemploi(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("adresse"),
                        rs.getDate("date_publication"),
                        statut.valueOf(rs.getString("statut"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des offres : " + e.getMessage());
        }
        return offreemplois;
    }
}