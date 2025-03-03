
package com.esprit.services;

import com.esprit.models.Commentaire;
import com.esprit.models.Evenement;
import com.esprit.utils.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ServiceEvenement implements IService<Evenement> {
    private Connection connection = DataSource.getInstance().getConnection();

    public List<Evenement> sortEvent(int value) {
        System.out.println("sortEvent is working");
        List<Evenement> evenementList = new ArrayList<>();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM evenement");
            if (value == 1 ) {
                queryBuilder.append(" ORDER BY prix ASC");
            }
            String query = queryBuilder.toString();
            PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            Iterator<Evenement> iterator = new Iterator<Evenement>() {
                @Override
                public boolean hasNext() {
                    try {
                        return resultSet.next();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
                @Override
                public Evenement next() {
                    Evenement evenement = new Evenement();
                    try {
                        evenement.setId(resultSet.getInt("id"));
                        evenement.setNom(resultSet.getString("nom"));
                        evenement.setImg(resultSet.getString("img"));
                        evenement.setDescription(resultSet.getString("description"));
                        evenement.setDateDebut(resultSet.getTimestamp("date_debut").toLocalDateTime());
                        evenement.setDateFin(resultSet.getTimestamp("date_fin").toLocalDateTime());
                        evenement.setLieu(resultSet.getString("lieu"));
                         evenement.setPrix(resultSet.getDouble("prix"));
                        evenement.setRemise(resultSet.getInt("remise"));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return evenement;
                }
            };
            Stream<Evenement> stream = StreamSupport.stream(
                    Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);

            evenementList = stream.collect(Collectors.toList());
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenementList;
    }


    @Override
    public void ajouter(Evenement evenement) throws SQLException {
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement("INSERT INTO evenement (nom,img, description, date_debut, date_fin, lieu, prix ) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, evenement.getNom());
            preparedStatement.setString(2, evenement.getImg());
            preparedStatement.setString(3, evenement.getDescription());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(evenement.getDateDebut()));
            preparedStatement.setTimestamp(5, Timestamp.valueOf(evenement.getDateFin()));
            preparedStatement.setString(6, evenement.getLieu());
            preparedStatement.setDouble(7, evenement.getPrix());
            preparedStatement.executeUpdate();
            System.out.println("Evenement ajouté");
        }
    }

    @Override
    public void modifier(Evenement evenement) throws SQLException {
        String query = "UPDATE evenement SET nom=?, description=?, date_debut=?, date_fin=?, lieu=?, prix=? WHERE id=?";
        try (PreparedStatement preparedStatement = (PreparedStatement) DataSource.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, evenement.getNom());
            preparedStatement.setString(2, evenement.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(evenement.getDateDebut()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(evenement.getDateFin()));
            preparedStatement.setString(5, evenement.getLieu());
            preparedStatement.setDouble(6, evenement.getPrix());
            preparedStatement.setInt(7, evenement.getId());
            preparedStatement.executeUpdate();
        }
    }



    @Override
    public Evenement getOneEvenement(int idEvenement) throws SQLException {
        String req = "SELECT * FROM `evenement` where id = ?";
        PreparedStatement ps = DataSource.getInstance().getConnection().prepareStatement(req);
        ps.setInt(1, idEvenement);

        ResultSet resultSet = ps.executeQuery();
        Evenement evenement = new Evenement();
        while (resultSet.next()) {
            evenement.setId(resultSet.getInt("id"));
            evenement.setNom(resultSet.getString("nom"));
            evenement.setImg(resultSet.getString("img"));
            evenement.setDescription(resultSet.getString("description"));
            evenement.setDateDebut(resultSet.getTimestamp("date_debut").toLocalDateTime());
            evenement.setDateFin(resultSet.getTimestamp("date_fin").toLocalDateTime());
            evenement.setLieu(resultSet.getString("lieu"));
             evenement.setPrix(resultSet.getDouble("prix"));
        }
        ps.close();
        return evenement;
    }

    @Override
    public void AddEvenenemtOffer(Evenement evenement) {

    }

    @Override
    public void supprimer(Evenement evenement) {
         String req = "DELETE FROM evenement WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, evenement.getId());
            pst.executeUpdate();
            System.out.println("Événement supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Evenement> rechercher() {
        return null;
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM evenement WHERE id=?";
        try (PreparedStatement preparedStatement = (PreparedStatement) DataSource.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Evenement supprimée avec succès");
        }
    }

    public List<Evenement> afficher() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement";
        try (PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Evenement evenement = new Evenement();
                evenement.setId(resultSet.getInt("id"));
                evenement.setNom(resultSet.getString("nom"));
                evenement.setImg(resultSet.getString("img"));
                evenement.setDescription(resultSet.getString("description"));
                evenement.setDateDebut(resultSet.getTimestamp("date_debut").toLocalDateTime());
                evenement.setDateFin(resultSet.getTimestamp("date_fin").toLocalDateTime());
                evenement.setLieu(resultSet.getString("lieu"));
                evenement.setPrix(resultSet.getDouble("prix"));
                evenement.setRemise(resultSet.getInt("remise"));

                evenements.add(evenement);
            }
        }
        return evenements;
    }

    @Override
    public List<Commentaire> show(int evenement_id) throws SQLException {
        return null;
    }

    @Override
    public Evenement afficher1(int id) throws SQLException {
        return null;
    }

    public static List<Evenement> searchEvenement(String search) {
        List<Evenement> evenements = new ArrayList<>();
        try {
            String query = "SELECT * FROM evenement WHERE nom LIKE ? OR description LIKE ? OR prix LIKE ?";
            PreparedStatement preparedStatement = DataSource.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            Stream<Evenement> stream = Stream.generate(() -> {
                try {
                    if (resultSet.next()) {
                        Evenement evenement = new Evenement();
                        evenement.setId(resultSet.getInt("id"));
                        evenement.setNom(resultSet.getString("nom"));
                        evenement.setImg(resultSet.getString("img"));
                        evenement.setDescription(resultSet.getString("description"));
                        evenement.setDateDebut(resultSet.getTimestamp("date_debut").toLocalDateTime());
                        evenement.setDateFin(resultSet.getTimestamp("date_fin").toLocalDateTime());
                        evenement.setLieu(resultSet.getString("lieu"));
                        evenement.setPrix(resultSet.getDouble("prix"));
                        evenement.setRemise(resultSet.getInt("remise"));
                        return evenement;
                    } else {
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }).takeWhile(Objects::nonNull);
            evenements = stream.collect(Collectors.toList());
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenements;
    }
}
