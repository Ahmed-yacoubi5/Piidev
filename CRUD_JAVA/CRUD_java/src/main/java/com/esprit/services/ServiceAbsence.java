package com.esprit.services;

import com.esprit.models.absence;
import com.esprit.utils.database;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceAbsence {
    private Connection connection;

    public ServiceAbsence() {
        connection = database.getInstance().getConnection();
    }

    // Ajouter une absence et générer un PDF au lieu d'envoyer un email
    public void ajouter(absence absence) {
        String req = "INSERT INTO absence (type, datedebut, datefin, employee_id, statut) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, absence.getType());
            ps.setDate(2, java.sql.Date.valueOf(absence.getDatedebut()));
            ps.setDate(3, java.sql.Date.valueOf(absence.getDatefin()));
            ps.setInt(4, absence.getEmployee_id());
            ps.setString(5, absence.getStatut());

            ps.executeUpdate();
            System.out.println("✅ Ajout de l'absence avec succès !");

            // Générer un PDF après l'ajout
            genererPDF("Employe_" + absence.getEmployee_id(), absence);

        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout de l'absence : " + e.getMessage());
        }
    }

    // Modifier une absence
    public void modifier(absence absence) {
        String req = "UPDATE absence SET type = ?, datedebut = ?, datefin = ?, statut = ? WHERE employee_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, absence.getType());
            ps.setDate(2, java.sql.Date.valueOf(absence.getDatedebut()));
            ps.setDate(3, java.sql.Date.valueOf(absence.getDatefin()));
            ps.setString(4, absence.getStatut());
            ps.setInt(5, absence.getEmployee_id());

            ps.executeUpdate();
            System.out.println("✅ Absence modifiée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification : " + e.getMessage());
        }
    }

    // Supprimer une absence
    public void supprimer(int id) {
        String req = "DELETE FROM absence WHERE employee_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("✅ Absence supprimée avec succès !");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression : " + e.getMessage());
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
                        rs.getInt("employee_id"),
                        rs.getString("statut")
                );
                absenceList.add(a);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'affichage des absences : " + e.getMessage());
        }
        return absenceList;
    }

    public Map<String, Integer> getStatistiquesAbsences() {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT statut, COUNT(*) AS count FROM absence GROUP BY statut";

        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                stats.put(rs.getString("statut"), rs.getInt("count"));
            }
            System.out.println("📊 Statistiques chargées : " + stats);
        } catch (SQLException e) {
            System.err.println("❌ Erreur SQL : " + e.getMessage());
        }
        return stats;
    }

    // Méthode pour appeler le script Python et générer un PDF
    private void genererPDF(String nom, absence absence) {
            try {
                // 🟢 Étape 1 : Générer le QR Code
                ProcessBuilder pbQR = new ProcessBuilder("python", "generer_qr.py",
                        nom, absence.getType(), absence.getDatedebut(), absence.getDatefin());
                Process processQR = pbQR.start();

                BufferedReader readerQR = new BufferedReader(new InputStreamReader(processQR.getInputStream()));
                String lineQR;
                while ((lineQR = readerQR.readLine()) != null) {
                    System.out.println(lineQR);
                }
                processQR.waitFor();
                System.out.println("📷 QR Code généré avec succès !");

                // 🟢 Étape 2 : Générer le PDF
                ProcessBuilder pbPDF = new ProcessBuilder("python", "generer_pdf.py",
                        nom, absence.getType(), absence.getDatedebut(), absence.getDatefin());
                Process processPDF = pbPDF.start();

                BufferedReader readerPDF = new BufferedReader(new InputStreamReader(processPDF.getInputStream()));
                String linePDF;
                while ((linePDF = readerPDF.readLine()) != null) {
                    System.out.println(linePDF);
                }
                processPDF.waitFor();
                System.out.println("📄 PDF généré avec succès !");

            } catch (Exception e) {
                System.err.println("❌ Erreur lors de la génération des documents : " + e.getMessage());
            }
        }

    }
