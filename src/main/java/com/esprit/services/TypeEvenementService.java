package com.esprit.services;


import com.esprit.models.TypeEvenement;
import com.esprit.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TypeEvenementService {
    private Connection con;

    public TypeEvenementService() {
        con = DataSource.getInstance().getConnection();
    }

    public void ajouter(TypeEvenement te) {
        String req = "INSERT INTO typeevenement (nom, description) VALUES (?, ?)";
        try {
            PreparedStatement pst = con.prepareStatement(req);
            pst.setString(1, te.getNom());
            pst.setString(2, te.getDescription());
            pst.executeUpdate();
            System.out.println("TypeEvenement ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    public void modifier(TypeEvenement te) {  // Vérifie bien que c'est TypeEvenement et pas Evenement
        String req = "UPDATE typeevenement SET nom=?, description=? WHERE id=?";
        try {
            PreparedStatement pst = con.prepareStatement(req);
            pst.setString(1, te.getNom());
            pst.setString(2, te.getDescription());
            pst.setInt(3, te.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Mise à jour réussie !");
            } else {
                System.out.println("Aucune modification effectuée.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    public void supprimer(int id) {  // Vérifie bien que la méthode prend un ID et non un objet TypeEvenement
        String req = "DELETE FROM typeevenement WHERE id=?";
        try {
            PreparedStatement pst = con.prepareStatement(req);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Suppression réussie !");
            } else {
                System.out.println("Aucun élément trouvé à supprimer.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    public List<TypeEvenement> rechercher() {
        List<TypeEvenement> list = new ArrayList<>();
        String req = "SELECT * FROM type_evenement";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                list.add(new TypeEvenement(rs.getInt("id"), rs.getString("nom"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return list;
    }
}

