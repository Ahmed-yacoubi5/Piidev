package com.esprit.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class IdUtil {

    // Private constructor to prevent instantiation
    private IdUtil() {
        throw new AssertionError("Cannot instantiate this class");
    }

    // Static method to retrieve a list of existing IDs from the 'candidat' table in the database
    public static List<Integer> getExistingIdsFromDatabase() throws SQLException {
        List<Integer> existingIds = new ArrayList<>();
        String query = "SELECT id FROM candidat";// Assuming you're querying the candidat table
        String query2 = "SELECT id FROM employe";

        // Database connection details
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
             Statement stmt = conn.createStatement();
             Statement stmt2 = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             ResultSet rs2 = stmt2.executeQuery(query2))
        {

            while (rs.next()) {
                existingIds.add(rs.getInt("id"));
            }
            while (rs2.next()) {
                existingIds.add(rs2.getInt("id"));
            }
        }

        return existingIds;
    }

    // Static method to retrieve a list of existing IDs from the 'employe' table in the database

    // Static method to generate a unique random ID not in the existing IDs list (for candidat)
    public static int generateUniqueRandomId(List<Integer> existingIds) {
        Random random = new Random();
        int randomId;

        // Keep generating random numbers until one is not in the existing list
        do {
            randomId = random.nextInt(9999) + 1; // Generates a number between 1 and 999
        } while (existingIds.contains(randomId)); // Ensure the random number is not in the list

        return randomId;
    }

    // Static method to generate a unique random ID for employees that isn't already used
    public static int empGenerateUniqueRandomId(List<Integer> existingEmpIds) {
        Random random = new Random();
        int randomEmpId;

        // Keep generating random numbers until one is not in the existing employee list
        do {
            randomEmpId = random.nextInt(9999) + 1; // Generates a number between 1 and 999
        } while (existingEmpIds.contains(randomEmpId)); // Ensure the random number is not in the list

        return randomEmpId;
    }
}
