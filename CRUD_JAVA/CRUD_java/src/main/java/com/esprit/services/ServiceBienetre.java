package com.esprit.services;

import com.esprit.models.bienetre;
import com.esprit.utils.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ServiceBienetre {
    private Connection connection;

    public ServiceBienetre() {
        connection = database.getInstance().getConnection();
    }

    // Ajouter un bien-être
    public void ajouter(bienetre bienetre) {
        String req = "INSERT INTO bienetre (nom, review, rate) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, bienetre.getNom());
            ps.setString(2, bienetre.getReview());
            ps.setInt(3, bienetre.getRate());

            ps.executeUpdate();
            System.out.println("Ajout du bien-être avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du bien-être : " + e.getMessage());
        }
    }

    // Modifier un bien-être
    public void modifier(bienetre bienetre) {
        String req = "UPDATE bienetre SET nom = ?, review = ?, rate = ? WHERE nom = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, bienetre.getNom());
            ps.setString(2, bienetre.getReview());
            ps.setInt(3, bienetre.getRate());
            ps.setString(4, bienetre.getNom()); // On met à jour par le nom

            ps.executeUpdate();
            System.out.println("Bien-être modifié avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    // Supprimer un bien-être
    public void supprimer(String nom) {
        String req = "DELETE FROM bienetre WHERE nom = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, nom);

            ps.executeUpdate();
            System.out.println("Bien-être supprimé avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // Afficher tous les bien-êtres
    public List<bienetre> afficher() {
        List<bienetre> bienetreList = new ArrayList<>();
        String req = "SELECT * FROM bienetre";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                bienetre b = new bienetre(
                        rs.getString("nom"),
                        rs.getString("review"),
                        rs.getInt("rate")
                );
                bienetreList.add(b);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage des bien-êtres : " + e.getMessage());
        }
        return bienetreList;
    }
}
