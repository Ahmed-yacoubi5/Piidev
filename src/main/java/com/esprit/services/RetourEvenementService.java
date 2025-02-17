package com.esprit.services;

import com.esprit.models.RetourEvenement;
import com.esprit.utils.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class RetourEvenementService {
        private Connection con;

        public RetourEvenementService() {
            con = DataSource.getInstance().getConnection();
        }

        // Ajouter un retour d'événement
        public void ajouter(RetourEvenement re) {
            String req = "INSERT INTO retourevenement (evenement_id, utilisateur_id, commentaire, note, date_retour) VALUES (?, ?, ?, ?, ?)";
            try {
                PreparedStatement pst = con.prepareStatement(req);
                pst.setInt(1, re.getEvenementId());
                pst.setInt(2, re.getUtilisateurId());
                pst.setString(3, re.getCommentaire());
                pst.setInt(4, re.getNote());
                pst.setTimestamp(5, Timestamp.valueOf(re.getDateRetour()));
                pst.executeUpdate();
                System.out.println("Retour ajouté avec succès !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du retour : " + e.getMessage());
            }
        }

        // Modifier un retour d'événement
        public void modifier(RetourEvenement re) {
            String req = "UPDATE retourevenement SET commentaire=?, note=?, date_retour=? WHERE id=?";
            try {
                PreparedStatement pst = con.prepareStatement(req);
                pst.setString(1, re.getCommentaire());
                pst.setInt(2, re.getNote());
                pst.setTimestamp(3, Timestamp.valueOf(re.getDateRetour()));
                pst.setInt(4, re.getId());
                int rowsUpdated = pst.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Mise à jour du retour réussie !");
                } else {
                    System.out.println("Aucune modification effectuée.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la modification du retour : " + e.getMessage());
            }
        }

        // Supprimer un retour d'événement
        public void supprimer(int id) {
            String req = "DELETE FROM retourevenement WHERE id=?";
            try {
                PreparedStatement pst = con.prepareStatement(req);
                pst.setInt(1, id);
                int rowsDeleted = pst.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Retour supprimé avec succès !");
                } else {
                    System.out.println("Aucun retour trouvé à supprimer.");
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la suppression du retour : " + e.getMessage());
            }
        }

        // Rechercher tous les retours d'événements
        public List<RetourEvenement> rechercher() {
            List<RetourEvenement> list = new ArrayList<>();
            String req = "SELECT * FROM retour_evenement";
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(req);
                while (rs.next()) {
                    list.add(new RetourEvenement(
                            rs.getInt("id"),
                            rs.getInt("evenement_id"),
                            rs.getInt("utilisateur_id"),
                            rs.getString("commentaire"),
                            rs.getInt("note"),
                            rs.getTimestamp("date_retour").toLocalDateTime()
                    ));
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de la récupération des retours : " + e.getMessage());
            }
            return list;
        }
    }


