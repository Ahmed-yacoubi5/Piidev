package com.esprit.services;

import com.esprit.models.User;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private final Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(User user) {
        String req = "INSERT INTO user (email, password, nom, prenom, date_de_naissance, roles, genre, adresse, num_de_telephone, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNom());
            ps.setString(4, user.getPrenom());
            ps.setDate(5, new java.sql.Date(user.getDateDeNaissance().getTime()));
            ps.setString(6, user.getRoles());
            ps.setString(7, user.getGenre());
            ps.setString(8, user.getAdresse());
            ps.setString(9, user.getNumDeTelephone());
            ps.setString(10, user.getStatus());

            ps.executeUpdate();
            System.out.println("User ajouté avec succée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(User user) {
        String req = "UPDATE user SET email=?, password=?, nom=?, prenom=?, date_de_naissance=?, roles=?, genre=?, adresse=?, num_de_telephone=?, status=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNom());
            ps.setString(4, user.getPrenom());
            ps.setDate(5, new java.sql.Date(user.getDateDeNaissance().getTime()));
            ps.setString(6, user.getRoles());
            ps.setString(7, user.getGenre());
            ps.setString(8, user.getAdresse());
            ps.setString(9, user.getNumDeTelephone());
            ps.setString(10, user.getStatus());
            ps.setInt(11, user.getId());

            ps.executeUpdate();
            System.out.println("User modifié avec Succés");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(User user) {
        String req = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setInt(1, user.getId());

            ps.executeUpdate();
            System.out.println("User supprimé avec succée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> rechercher() {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getDate("date_de_naissance"),
                        rs.getString("roles"),
                        rs.getString("genre"),
                        rs.getString("adresse"),
                        rs.getString("num_de_telephone"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
