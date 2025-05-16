package com.esprit.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImageSrc {
    private static Connection connection = database.getInstance().getConnection();

    public static String getSrc(int id) {
        String src = null;
        String query = "SELECT src FROM imageprofil WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);  // Use parameterized query to prevent SQL injection
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                src = resultSet.getString("src");
            }

            // Ensure src is not null before returning the file path
            if (src != null) {
                return "file:/C:/Users/poste/Documents/Profil/" + src;
            } else {
                // Handle case when src is null, maybe return a default image or throw an exception
                return "file:/C:/Users/poste/Documents/Profil/images/Default.jpg";  // or any fallback image
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error while fetching image src.", e);
        }
    }
}