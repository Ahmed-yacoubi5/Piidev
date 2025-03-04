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
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;

public class ServiceBienetre {
    private Connection connection;

    public ServiceBienetre() {
        connection = database.getInstance().getConnection();
    }

    /**
     * Méthode pour classifier un avis via Flask
     * @param review - Texte de l'avis à analyser
     * @return "Positive" ou "Negative"
     */
    public String analyserSentiment(String review) {
        if (review == null || review.trim().isEmpty()) {
            return "Indéterminé";
        }

        try {
            URL url = new URL("http://localhost:5000/predict");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Créer un JSON avec l'avis
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("review", review);

            // Envoyer la requête
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Lire la réponse
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            // Extraire la classification du JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse.optString("prediction", "Indéterminé");

        } catch (Exception e) {
            System.err.println("Erreur lors de l'analyse du sentiment : " + e.getMessage());
            e.printStackTrace();
            return "Indéterminé";
        }
    }

    /**
     * Ajouter un bien-être avec classification de l'avis
     */
    public void ajouter(bienetre bienetre) {
        String sentiment = analyserSentiment(bienetre.getReview());
        String req = "INSERT INTO bienetre (nom, review, rate, sentiment) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, bienetre.getNom());
            ps.setString(2, bienetre.getReview());
            ps.setInt(3, bienetre.getRate());
            ps.setString(4, sentiment);
            ps.executeUpdate();
            System.out.println("✅ Ajout réussi : " + bienetre.getNom() + " (Sentiment : " + sentiment + ")");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Modifier un bien-être existant
     */
    public void modifier(bienetre bienetre) {
        String sentiment = analyserSentiment(bienetre.getReview());
        String req = "UPDATE bienetre SET review = ?, rate = ?, sentiment = ? WHERE nom = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, bienetre.getReview());
            ps.setInt(2, bienetre.getRate());
            ps.setString(3, sentiment);
            ps.setString(4, bienetre.getNom());
            ps.executeUpdate();
            System.out.println("✅ Modification réussie : " + bienetre.getNom() + " (Nouveau Sentiment : " + sentiment + ")");
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la modification : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Supprimer un bien-être par son nom
     */
    public void supprimer(String nom) {
        String req = "DELETE FROM bienetre WHERE nom = ?";

        try (PreparedStatement ps = connection.prepareStatement(req)) {
            ps.setString(1, nom);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Suppression réussie : " + nom);
            } else {
                System.out.println("⚠️ Aucun bien-être trouvé avec ce nom : " + nom);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Récupérer tous les bien-êtres enregistrés
     */
    public List<bienetre> afficher() {
        List<bienetre> bienetreList = new ArrayList<>();
        String req = "SELECT * FROM bienetre";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(req)) {
            while (rs.next()) {
                bienetre b = new bienetre(
                        rs.getString("nom"),
                        rs.getString("review"),
                        rs.getInt("rate"),
                        rs.getString("sentiment")
                );
                bienetreList.add(b);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'affichage : " + e.getMessage());
            e.printStackTrace();
        }
        return bienetreList;
    }
}
