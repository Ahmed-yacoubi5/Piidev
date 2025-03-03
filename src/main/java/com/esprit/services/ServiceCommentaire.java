package com.esprit.services;

import com.esprit.models.Commentaire;
import com.esprit.models.Evenement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import com.esprit.utils.DataSource;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommentaire implements IService<Commentaire> {

    @Override
    public void ajouter(Commentaire commentaire) throws SQLException {
        String query = "INSERT INTO commentaire (contenu, date_creation, nomuser, img, evenement_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, commentaire.getContenu());
            preparedStatement.setString(2, commentaire.getDate());
            preparedStatement.setString(3, commentaire.getNomuser());
            preparedStatement.setString(4, "avatar2.png");
            preparedStatement.setInt(5, 20);
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
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query)) {
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
    public void supprimer(Evenement evenement) {

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
        try (PreparedStatement preparedStatement = (PreparedStatement) DataSource.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("commentaire supprimée avec succès");
        }
    }

    @Override
    public List<Commentaire> afficher() throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String query = "SELECT * FROM commentaire";
        try (PreparedStatement preparedStatement = (PreparedStatement) DataSource.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Commentaire commentaire = new Commentaire();
                commentaire.setId(resultSet.getInt("id"));
                commentaire.setContenu(resultSet.getString("contenu"));
                commentaire.setDate(resultSet.getString("date_creation"));
                commentaire.setNomuser(resultSet.getString("nomuser"));
                commentaire.setImg(resultSet.getString("img"));
                commentaire.setEvenement_id(resultSet.getInt("evenement_id")); 
                commentaires.add(commentaire);
            }
        }
        return commentaires;
    }


    @Override
    public List<Commentaire> show(int evenement_id) throws SQLException {
        List<Commentaire> commentaires = new ArrayList<>();
        String query = "SELECT * FROM commentaire WHERE evenement_id = ?";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, evenement_id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Commentaire commentaire = new Commentaire();
                    commentaire.setId(resultSet.getInt("id"));
                    commentaire.setContenu(resultSet.getString("contenu"));
                    commentaire.setDate(resultSet.getString("date_creation"));
                    commentaire.setNomuser(resultSet.getString("nomuser"));
                    commentaire.setImg(resultSet.getString("img"));
                    commentaire.setEvenement_id(resultSet.getInt("evenement_id"));
                    commentaire.setDislikes(resultSet.getInt("dislikes"));
                    commentaire.setLikes(resultSet.getInt("likes"));
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
}
