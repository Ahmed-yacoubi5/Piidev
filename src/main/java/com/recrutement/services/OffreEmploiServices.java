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
            pst.setDate(3, new java.sql.Date(offreemploi.getDate_publication().getTime())); // ✅ Index corrigé
            pst.setString(4, offreemploi.getStatut().toString()); //
            // ✅ Index corrigé
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
            pst.setString(1,offreemploi.getTitre());
            pst.setString(2,offreemploi.getDescription());
            pst.setDate(3,new java.sql.Date(offreemploi.getDate_publication().getTime())); // ✅ Correction
            pst.setString(4, offreemploi.getStatut().toString()); //
            // ✅ Correction
            pst.setInt(5, offreemploi.getId()); // ✅ Correction
            pst.executeUpdate();
            System.out.println("✅ Offre d'emploi modifiée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification : " + e.getMessage());
        }
    }


    @Override
    public void supprimer(offreemploi offreemploi) {
        String req = "DELETE from offreemploi WHERE id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, offreemploi.getId());
            pst.executeUpdate();
            System.out.println("offreemploi supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<offreemploi> rechercher() {
        List<offreemploi> offreemplois = new ArrayList<>();

        String req = "SELECT * FROM offreemploi";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery(req);
            while (rs.next()) {
                offreemplois.add(new offreemploi(rs.getInt("id"), rs.getString("titre"), rs.getString("description"),rs.getDate("date_publication"), statut.valueOf(rs.getString("statut"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return offreemplois;
    }
}
