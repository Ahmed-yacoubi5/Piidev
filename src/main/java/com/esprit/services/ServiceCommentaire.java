package com.esprit.services;

import com.esprit.models.Commentaire;
import com.esprit.models.Evenement;
import com.esprit.models.Candidat;
import com.esprit.models.Employe;
import com.esprit.models.Profil;
import com.esprit.utils.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaire implements IService<Commentaire> {

    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        // Since there's no specific commentaire table, we'll use history table as a substitute
        String query = "INSERT INTO history (action, performed_at, entity_type, entity_id, details, performed_by) VALUES (?, NOW(), ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, "COMMENT");
            preparedStatement.setString(2, "Event");
            preparedStatement.setInt(3, commentaire.getEvenement_id());
            preparedStatement.setString(4, commentaire.getContenu());
            preparedStatement.setString(5, commentaire.getNomuser());
            preparedStatement.executeUpdate();
            System.out.println("Commentaire ajouté");
        }
    }

    @Override
    public List<Evenement> sortEvent(int value) {
        return null;
    }

    @Override
    public void modifier(Commentaire commentaire) throws SQLException {
        String query = "UPDATE commentaire SET contenu=?, date_creation=?, nomuser=?, img=?, likes=?, dislikes=?  WHERE id=?";
        try (PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, commentaire.getContenu());
            preparedStatement.setString(2, commentaire.getDate());
            preparedStatement.setString(3, commentaire.getNomuser());
            preparedStatement.setString(4, commentaire.getImg());
            preparedStatement.setInt(5, commentaire.getLikes()+1);
            preparedStatement.setInt(6, commentaire.getDislikes());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(Evenement evenement) throws SQLException {
        // Not applicable for Commentaire
        System.out.println("Operation not supported for Commentaire");
    }

    @Override
    public void supprimer(Candidat candidat) throws SQLException {
        // Not applicable for Commentaire
        System.out.println("Operation not supported for Commentaire");
    }

    @Override
    public void supprimer(Employe employe) throws SQLException {
        // Not applicable for Commentaire
        System.out.println("Operation not supported for Commentaire");
    }

    @Override
    public void supprimer(Profil profil) throws SQLException {
        // Not applicable for Commentaire
        System.out.println("Operation not supported for Commentaire");
    }

    @Override
    public void supprimer(Commentaire commentaire) throws SQLException {
        String query = "DELETE FROM history WHERE id=? AND action='COMMENT'";
        try (PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, commentaire.getId());
            preparedStatement.executeUpdate();
            System.out.println("Commentaire supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Commentaire> rechercher() {
        return null;
    }

    @Override
    public Evenement getOneEvenement(int idEvenement) throws SQLException {
        return null;
    }

    @Override
    public void AddEvenenemtOffer(Evenement evenement) {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM commentaire WHERE id=?";
        try (PreparedStatement preparedStatement = (PreparedStatement) database.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("commentaire supprimée avec succès");
        }
    }

    @Override
    public List<Commentaire> afficher() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String query = "SELECT * FROM history WHERE action = 'COMMENT' AND entity_type = 'Event' ORDER BY performed_at DESC";
        try (PreparedStatement preparedStatement = (PreparedStatement) database.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(resultSet.getInt("id"));
                commentaire.setContenu(resultSet.getString("details"));
                commentaire.setDate(resultSet.getString("performed_at"));
                commentaire.setNomuser(resultSet.getString("performed_by"));
                commentaire.setEvenement_id(resultSet.getInt("entity_id")); 
                // Set default values for non-existing fields
                commentaire.setImg(null);
                commentaire.setLikes(0);
                commentaire.setDislikes(0);
                commentaires.add(commentaire);
            }
        }
        return commentaires;
    }

    @Override
    public List<Commentaire> show(int evenement_id) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String query = "SELECT * FROM history WHERE action = 'COMMENT' AND entity_type = 'Event' AND entity_id = ? ORDER BY performed_at DESC";
        try (PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, evenement_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Commentaire commentaire = new Commentaire();
                    commentaire.setId(resultSet.getInt("id"));
                    commentaire.setContenu(resultSet.getString("details"));
                    commentaire.setDate(resultSet.getString("performed_at"));
                    commentaire.setNomuser(resultSet.getString("performed_by"));
                    commentaire.setEvenement_id(resultSet.getInt("entity_id"));
                    // Set default values for non-existing fields
                    commentaire.setImg(null);
                    commentaire.setLikes(0);
                    commentaire.setDislikes(0);
                    commentaires.add(commentaire);
                }
            }
        }
        return commentaires;
    }

    @Override
    public Commentaire afficher1(int id) throws SQLException {
        return null;
    }

    @Override
    public void supprimer(Object t) throws SQLException {
        if (t instanceof Commentaire) {
            Commentaire commentaire = (Commentaire) t;
            String query = "DELETE FROM history WHERE id=? AND action='COMMENT'";
            try (PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query)) {
                preparedStatement.setInt(1, commentaire.getId());
                preparedStatement.executeUpdate();
                System.out.println("Commentaire supprimé");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Object to delete must be of type Commentaire");
        }
    }
}
