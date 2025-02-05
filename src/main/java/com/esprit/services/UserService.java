package com.esprit.services;

import com.esprit.models.User;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(User user) {
        String req = "INSERT INTO user (email, password, nom, prenom, date_de_naissance, roles, genre, adresse, num_de_telephone, status) " +
                "VALUES ('" + user.getEmail() + "', '" + user.getPassword() + "', '" + user.getNom() + "', '" +
                user.getPrenom() + "', '" + new java.sql.Date(user.getDateDeNaissance().getTime()) + "', '" + user.getRoles() +
                "', '" + user.getGenre() + "', '" + user.getAdresse() + "', '" + user.getNumDeTelephone() + "', '" + user.getStatus() + "')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("User ajouté");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(User user) {
        String req = "UPDATE user SET email='" + user.getEmail() + "', password='" + user.getPassword() + "', nom='" + user.getNom() +
                "', prenom='" + user.getPrenom() + "', date_de_naissance='" + new java.sql.Date(user.getDateDeNaissance().getTime()) +
                "', roles='" + user.getRoles() + "', genre='" + user.getGenre() + "', adresse='" + user.getAdresse() +
                "', num_de_telephone='" + user.getNumDeTelephone() + "', status='" + user.getStatus() + "' WHERE id=" + user.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("User modifié");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(User user) {
        String req = "DELETE FROM user WHERE id=" + user.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("User supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> rechercher() {
        List<User> users = new ArrayList<>();
        String req = "SELECT * FROM user";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
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
                        rs.getString("Status")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }
}
