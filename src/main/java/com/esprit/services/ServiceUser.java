package com.esprit.services;

import com.esprit.models.User ;
import com.esprit.utils.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Properties;
import java.util.ArrayList;

public class ServiceUser {

    private Connection connection;
    
    public ServiceUser() {
        this.connection = DataBaseConnection.getInstance().getConnection();
    }
    
    /**
     * Get all users from the database
     * @return ObservableList of User objects
     */
    public static ObservableList<User> getAll() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM user";
        
        try (Connection conn = DataBaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setImage(rs.getString("image"));
                user.setRole(rs.getString("role"));
                user.setIsActive(rs.getBoolean("is_active"));
                user.setLastLogin(rs.getTimestamp("last_login") != null ? 
                                 rs.getTimestamp("last_login").toLocalDateTime() : null);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    /**
     * Get count of active users (logged in within the last 30 days)
     * @return number of active users
     */
    public static int getActiveUsersCount() {
        String query = "SELECT COUNT(*) as active_count FROM user " +
                     "WHERE last_login >= ?";
        int count = 0;
        
        try (Connection conn = DataBaseConnection.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            // Consider users active if they've logged in within the last 30 days
            LocalDateTime thirtyDaysAgo = LocalDateTime.now().minus(30, ChronoUnit.DAYS);
            pstmt.setTimestamp(1, Timestamp.valueOf(thirtyDaysAgo));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("active_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


    public String getUserImage(int userId) {
        String query = "SELECT image FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("image"); // Retourne le chemin de l'image
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'image : " + e.getMessage());
        }
        return null; // Retourne null si aucune image trouvée
    }

    public String getUserStatus(String username) {
        String query = "SELECT status FROM user WHERE first_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username); // Utiliser le nom d'utilisateur au lieu de l'ID
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("status");
            }
        } catch (SQLException e) {
            System.err.println("Erreur d'exécution de la requête SQL : " + e.getMessage());
        }
        return null; // Retourner null si aucun résultat trouvé
    }

    public void ajouter(User user) {
        String query = "INSERT INTO user (email, roles, password, is_verified, first_name, last_name, phone_number, image, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, "user");
            statement.setString(3, user.getPassword());
            statement.setInt(4, 0);
            statement.setString(5, user.getFirstName());
            statement.setString(6, user.getLastName());
            statement.setString(7, user.getPhoneNumber());
            statement.setString(8, user.getImage());
            statement.setString(9, "enabled");

            statement.executeUpdate();
            System.out.println("Utilisateur ajouté avec succès.");

            // Envoi d'email
            String subject = "Nouvelle compte";
            String body = "Bonjour,\n\nUne nouvelle compte a été créée pour votre email.\n\nCordialement,\nL'équipe de support";
            sendEmail(user.getEmail(), subject, body);

        } catch (SQLException ex) {
            System.out.println("Erreur SQL : " + ex.getMessage());
        }
    }

    private void sendEmail(String to, String subject, String body) {
        final String username = "ahmedchihi00@gmail.com";
        final String password = "jvtb zhll kdlk gyvn";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        ;


    }
 
     
	 
	 
	
    public void supprimer(int id) {
        String query = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("ok");
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
    
    public void modifier(User user, int id) {
        String query = "UPDATE user SET status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
       
            statement.setString(1, "desable");
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
    public void Update(User user, int id) {
        String query = "UPDATE user SET first_name = ? ,last_name=?,phone_number=? WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
       
            statement.setString(1,user.getFirstName());
            statement.setString(2,user.getLastName());
            statement.setString(3,user.getPhoneNumber());
            statement.setInt(4, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
    }
    
    public ObservableList<User> afficherTous() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT * FROM user";
        
        try (Connection conn = DataBaseConnection.getInstance().getConnection();
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String role = resultSet.getString("roles");
                String password = resultSet.getString("password");
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                String phonenumber = resultSet.getString("phone_number");
                String image = resultSet.getString("image");
                String status = resultSet.getString("status");
                User user = new User(id, email, role, password, firstname, lastname, phonenumber, image, status);
                users.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
        }
        return users;
    }
    public boolean login(String username, String password) {
         
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isAuthenticated = false;

        try {
            // Etape 1 : se connecter � la base de donn�es
 
            // Etape 2 : Pr�parer la requ�te SQL
            String sql = "SELECT * FROM user WHERE first_name = ? AND password = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Etape 3 : Ex�cuter la requ�te SQL
            rs = stmt.executeQuery();

            // Etape 4 : V�rifier si l'utilisateur existe et que le mot de passe correspond
            if (rs.next()) {
                isAuthenticated = true;
            }
        } catch (SQLException e) {
            // G�rer les erreurs de connexion � la base de donn�es
            e.printStackTrace();
        }   
      

        return isAuthenticated;
    }
    public User rechercherUserParId(int id) {
        try {
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("roles"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setPhoneNumber(rs.getString("phone_number"));
                user.setImage(rs.getString("image"));
                user.setStatus(rs.getString("status"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String getHashedPasswordForUser(String username) {
        String hashedPassword = null;
        try {
             PreparedStatement ps = connection.prepareStatement("SELECT password FROM user WHERE first_name = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                hashedPassword = rs.getString("password");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }
    public void bloquer(int userId) {
        try {
            String query = "UPDATE user SET bloque = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 1);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
            System.out.println("bloquerr");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

	public String getRole(String username) {
		// TODO Auto-generated method stub
		String query="SELECT roles FROM user WHERE first_name = ?";
	 try {
	      PreparedStatement ps = connection.prepareStatement(query);
           
          ps.setString(1, username);
          ResultSet rs = ps.executeQuery();
          if (rs.next()) {
        	  return rs.getString("roles");
          }          
          System.out.println("bloquerr");
      } catch (SQLException ex) {
          System.err.println(ex.getMessage());
      }
	return null;
	   
		 
	}


    
}
