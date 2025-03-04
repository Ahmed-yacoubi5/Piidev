package com.esprit.services;

import com.esprit.models.conges;
import com.esprit.utils.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ServiceConges {
    private final Connection connection;

    public ServiceConges() {
        connection = database.getInstance().getConnection();
    }

    // Méthode pour ajouter un congé
    public void ajouter(conges conges) {
        String req = "INSERT INTO conges (type, datedebut, datefin, employee_id, statut) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, conges.getType());

            // Utiliser java.sql.Date.valueOf pour éviter les erreurs de format
            ps.setDate(2, java.sql.Date.valueOf(conges.getDatedebut()));
            ps.setDate(3, java.sql.Date.valueOf(conges.getDatefin()));

            ps.setInt(4, conges.getEmployee_id());
            ps.setString(5, conges.getStatut());

            ps.executeUpdate();
            System.out.println("✅ Congé ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'ajout du congé : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Format de date incorrect. Utilisez le format yyyy-MM-dd.");
        }
    }

    // Méthode pour modifier un congé
    public void modifier(conges conges) {
        String req = "UPDATE conges SET type = ?, datedebut = ?, datefin = ?, employee_id = ?, statut = ? WHERE employee_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, conges.getType());

            // Utiliser java.sql.Date.valueOf pour éviter les erreurs de format
            ps.setDate(2, java.sql.Date.valueOf(conges.getDatedebut()));
            ps.setDate(3, java.sql.Date.valueOf(conges.getDatefin()));

            ps.setInt(4, conges.getEmployee_id());
            ps.setString(5, conges.getStatut());
            ps.setInt(6, conges.getEmployee_id());

            ps.executeUpdate();
            System.out.println("✅ Congé modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la modification : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Format de date incorrect. Utilisez le format yyyy-MM-dd.");
        }
    }

    // Méthode pour supprimer un congé
    public void supprimer(int id) {
        String req = "DELETE FROM conges WHERE employee_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Congé supprimé avec succès !");
            } else {
                System.out.println("⚠️ Aucun congé trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // Méthode pour afficher tous les congés
    public List<conges> afficher() {
        List<conges> congesList = new ArrayList<>();
        String req = "SELECT * FROM conges";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                conges c = new conges(
                        rs.getString("type"),
                        rs.getString("datedebut"),
                        rs.getString("datefin"),
                        rs.getInt("employee_id"),
                        rs.getString("statut")
                );
                congesList.add(c);
            }
            System.out.println("✅ Affichage des congés terminé !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur lors de l'affichage des congés : " + e.getMessage());
        }

        return congesList;
    }
    public int getTotalConges() {
        int total = 0;
        String query = "SELECT COUNT(*) FROM conges";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur SQL : " + e.getMessage());
        }
        return total;
    }

    /**
     * Retourne le nombre de congés par type.
     */
    public Map<String, Integer> getCongesParType() {
        Map<String, Integer> stats = new HashMap<>();
        String query = "SELECT type, COUNT(*) FROM conges GROUP BY type";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stats.put(rs.getString("type"), rs.getInt(2));
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur SQL : " + e.getMessage());
        }
        return stats;
    }

    /**
     * Retourne le nombre de congés par statut.
     */
    public Map<String, Integer> getCongesParStatut() {
        Map<String, Integer> stats = new HashMap<>();
        String query = "SELECT statut, COUNT(*) FROM conges GROUP BY statut";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                stats.put(rs.getString("statut"), rs.getInt(2));
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur SQL : " + e.getMessage());
        }
        return stats;
    }
}
