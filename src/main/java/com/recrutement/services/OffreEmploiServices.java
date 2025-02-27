package com.recrutement.services;

import com.recrutement.models.statut;
import com.recrutement.models.offreemploi;
import com.recrutement.utils.DataSources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreEmploiServices implements iservices<offreemploi> {

    private final Connection connection = DataSources.getInstance().getConnection();

    @Override
    public void ajouter(offreemploi offreemploi) {
        String req = "INSERT INTO offreemploi (titre, description, date_publication, statut) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, offreemploi.getTitre());
            pst.setString(2, offreemploi.getDescription());
            pst.setDate(3, new java.sql.Date(offreemploi.getDate_publication().getTime()));
            pst.setString(4, offreemploi.getStatut().toString());
            pst.executeUpdate();
            System.out.println("✅ Offre d'emploi ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    @Override
    public void modifier(offreemploi offreemploi) {
        String req = "UPDATE offreemploi SET titre=?, description=?, date_publication=?, statut=? WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, offreemploi.getTitre());
            pst.setString(2, offreemploi.getDescription());
            pst.setDate(3, new java.sql.Date(offreemploi.getDate_publication().getTime()));
            pst.setString(4, offreemploi.getStatut().toString());
            pst.setInt(5, offreemploi.getId());
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
                        rs.getDate("date_publication"),
                        statut.valueOf(rs.getString("statut"))
                ));
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la récupération des offres : " + e.getMessage());
        }
        return offreemplois;
    }
    public boolean supprimerParTitre(String titre) {
        String query = "DELETE FROM offreemploi WHERE titre = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, titre);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression par titre : " + e.getMessage());
            return false;
        }
    }
    }



