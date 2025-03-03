package com.recrutement.services;

import com.recrutement.models.cv;
import com.recrutement.utils.DataSources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CvServices {

    private final Connection connection = DataSources.getInstance().getConnection();

    // Ajouter un CV
    public void ajouter(cv cv) {
        String req = "INSERT INTO cv (nom, prenom, dateDeNaissance, adresse, email, telephone, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, cv.getNom());
            pst.setString(2, cv.getPrenom());
            pst.setDate(3, new java.sql.Date(cv.getDateDeNaissance().getTime()));
            pst.setString(4, cv.getAdresse());
            pst.setString(5, cv.getEmail());
            pst.setInt(6, cv.getTelephone());
            pst.setString(7, cv.getImage());  // Stocker le chemin de l'image dans la base de données
            pst.executeUpdate();
            System.out.println("✅ CV ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    // Modifier un CV
    public void modifier(cv cv) {
        String req = "UPDATE cv SET nom=?, prenom=?, dateDeNaissance=?, adresse=?, email=?, telephone=? WHERE idCv=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setString(1, cv.getNom());
            pst.setString(2, cv.getPrenom());
            pst.setDate(3, new java.sql.Date(cv.getDateDeNaissance().getTime()));
            pst.setString(4, cv.getAdresse());
            pst.setString(5, cv.getEmail());
            pst.setInt(6, cv.getTelephone());
            pst.setInt(7, cv.getIdCv());
            pst.executeUpdate();
            System.out.println("✅ CV modifié avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification : " + e.getMessage());
        }
    }

    // Supprimer un CV
    public void supprimer(cv cv) {
        String req = "DELETE FROM cv WHERE idCv=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, cv.getIdCv());
            pst.executeUpdate();
            System.out.println("✅ CV supprimé avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public List<cv> getAllCVs() {
        List<cv> cvs = new ArrayList<>();
        String req = "SELECT * FROM cv";
        try (PreparedStatement pst = connection.prepareStatement(req);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                String image = rs.getString("image");  // Récupérer le chemin de l'image

                cvs.add(new cv(
                        rs.getInt("idCv"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("dateDeNaissance"),
                        rs.getString("adresse"),
                        rs.getString("email"),
                        rs.getInt("telephone"),
                        image  // Passer le chemin de l'image
                ));
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des CVs : " + e.getMessage());
        }
        return cvs;
    }
    // Récupérer tous les CVs



}