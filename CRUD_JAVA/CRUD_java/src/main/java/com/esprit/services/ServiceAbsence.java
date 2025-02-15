package com.esprit.services;

import com.esprit.models.absence;
import com.esprit.utils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceAbsence {
    private Connection connection;

    public ServiceAbsence() {
        connection = database.getInstance().getConnection();
    }

    // Ajouter une absence
    public void ajouter(absence absence) {
        String req = "INSERT INTO absence (type, datedebut, datefin, employe_id, statut) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, absence.getType());

            // Utiliser java.sql.Date.valueOf pour éviter les erreurs de format
            ps.setDate(2, java.sql.Date.valueOf(absence.getDatedebut()));
            ps.setDate(3, java.sql.Date.valueOf(absence.getDatefin()));

            ps.setInt(4, absence.getEmployee_id());
            ps.setString(5, absence.getStatut());

            ps.executeUpdate();
            System.out.println("Ajout de l'absence avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'absence : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Format de date incorrect. Utilisez le format yyyy-MM-dd.");
        }
    }

    // Modifier une absence
    public void modifier(absence absence) {
        String req = "UPDATE absence SET type = ?, datedebut = ?, datefin = ?, employe_id = ?, statut = ? WHERE employe_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, absence.getType());

            // Utiliser java.sql.Date.valueOf pour les dates
            ps.setDate(2, java.sql.Date.valueOf(absence.getDatedebut()));
            ps.setDate(3, java.sql.Date.valueOf(absence.getDatefin()));

            ps.setInt(4, absence.getEmployee_id());
            ps.setString(5, absence.getStatut());
            ps.setInt(6, absence.getEmployee_id());

            ps.executeUpdate();
            System.out.println("Absence modifiée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Format de date incorrect. Utilisez le format yyyy-MM-dd.");
        }
    }

    // Supprimer une absence
    public void supprimer(int id) {
        String req = "DELETE FROM absence WHERE employe_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Absence supprimée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // Afficher toutes les absences
    public List<absence> afficher() {
        List<absence> absenceList = new ArrayList<>();
        String req = "SELECT * FROM absence";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                absence a = new absence(
                        rs.getString("type"),
                        rs.getString("datedebut"),
                        rs.getString("datefin"),
                        rs.getInt("employe_id"),
                        rs.getString("statut")
                );
                absenceList.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des absences : " + e.getMessage());
        }
        return absenceList;
    }
}
