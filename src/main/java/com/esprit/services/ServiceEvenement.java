package com.esprit.services;

import com.esprit.models.*;
import com.esprit.utils.database;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ServiceEvenement implements IService<Evenement> {
    private Connection connection = database.getInstance().getConnection();

    public List<Evenement> sortEvent(int value) {
        System.out.println("sortEvent is working");
        List<Evenement> evenementList = new ArrayList<>();
        try {
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM event");
            if (value == 1) {
                queryBuilder.append(" ORDER BY id DESC");
            }
            String query = queryBuilder.toString();
            PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query);
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
                        evenement.setNom(resultSet.getString("title"));
                        evenement.setImg(resultSet.getString("id"));
                        evenement.setDescription(resultSet.getString("description"));
                        evenement.setDateDebut(resultSet.getTimestamp("start_date").toLocalDateTime());
                        evenement.setDateFin(resultSet.getTimestamp("end_date").toLocalDateTime());
                        evenement.setLieu(resultSet.getString("location"));
                        evenement.setPrix(0.0);
                        evenement.setRemise(0);
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
        try (PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(
                "INSERT INTO event (title, description, start_date, end_date, location, capacity, is_public) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, evenement.getNom());
            preparedStatement.setString(2, evenement.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(evenement.getDateDebut()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(evenement.getDateFin()));
            preparedStatement.setString(5, evenement.getLieu());
            preparedStatement.setInt(6, 100); // Default capacity
            preparedStatement.setBoolean(7, true); // Default is_public
            preparedStatement.executeUpdate();
            System.out.println("Evenement ajouté");
        }
    }

    @Override
    public void modifier(Evenement evenement) throws SQLException {
        String query = "UPDATE event SET title=?, description=?, start_date=?, end_date=?, location=? WHERE id=?";
        try (PreparedStatement preparedStatement = (PreparedStatement) database.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, evenement.getNom());
            preparedStatement.setString(2, evenement.getDescription());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(evenement.getDateDebut()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(evenement.getDateFin()));
            preparedStatement.setString(5, evenement.getLieu());
            preparedStatement.setInt(6, evenement.getId());
            preparedStatement.executeUpdate();
        }
    }



    @Override
    public Evenement getOneEvenement(int idEvenement) throws SQLException {
        String req = "SELECT * FROM `event` where id = ?";
        PreparedStatement ps = database.getInstance().getConnection().prepareStatement(req);
        ps.setInt(1, idEvenement);

        ResultSet resultSet = ps.executeQuery();
        Evenement evenement = new Evenement();
        while (resultSet.next()) {
            evenement.setId(resultSet.getInt("id"));
            evenement.setNom(resultSet.getString("title"));
            evenement.setImg(resultSet.getString("id"));
            evenement.setDescription(resultSet.getString("description"));
            evenement.setDateDebut(resultSet.getTimestamp("start_date").toLocalDateTime());
            evenement.setDateFin(resultSet.getTimestamp("end_date").toLocalDateTime());
            evenement.setLieu(resultSet.getString("location"));
            evenement.setPrix(0.0);
        }
        ps.close();
        return evenement;
    }

    @Override
    public void AddEvenenemtOffer(Evenement evenement) {

    }

    @Override
    public void supprimer(Evenement evenement) throws SQLException {
        String req = "DELETE FROM event WHERE id=?";
        try (PreparedStatement pst = connection.prepareStatement(req)) {
            pst.setInt(1, evenement.getId());
            pst.executeUpdate();
            System.out.println("Événement supprimé");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void supprimer(Object t) throws SQLException {
        if (t instanceof Evenement) {
            Evenement evenement = (Evenement) t;
            String req = "DELETE FROM event WHERE id=?";
            try (PreparedStatement pst = connection.prepareStatement(req)) {
                pst.setInt(1, evenement.getId());
                pst.executeUpdate();
                System.out.println("Événement supprimé");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Object to delete must be of type Evenement");
        }
    }

    @Override
    public List<Evenement> rechercher() {
        return null;
    }


    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM event WHERE id=?";
        try (PreparedStatement preparedStatement = (PreparedStatement) database.getInstance().getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Evenement supprimée avec succès");
        }
    }

    public List<Evenement> afficher() throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM event";
        try (PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Evenement evenement = new Evenement();
                evenement.setId(resultSet.getInt("id"));
                evenement.setNom(resultSet.getString("title"));
                evenement.setImg(resultSet.getString("id"));
                evenement.setDescription(resultSet.getString("description"));
                evenement.setDateDebut(resultSet.getTimestamp("start_date").toLocalDateTime());
                evenement.setDateFin(resultSet.getTimestamp("end_date").toLocalDateTime());
                evenement.setLieu(resultSet.getString("location"));
                evenement.setPrix(0.0);
                evenement.setRemise(0);

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
            String query = "SELECT * FROM event WHERE title LIKE ? OR description LIKE ?";
            PreparedStatement preparedStatement = database.getInstance().getConnection().prepareStatement(query);
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            Stream<Evenement> stream = Stream.generate(() -> {
                try {
                    if (resultSet.next()) {
                        Evenement evenement = new Evenement();
                        evenement.setId(resultSet.getInt("id"));
                        evenement.setNom(resultSet.getString("title"));
                        evenement.setImg(resultSet.getString("id"));
                        evenement.setDescription(resultSet.getString("description"));
                        evenement.setDateDebut(resultSet.getTimestamp("start_date").toLocalDateTime());
                        evenement.setDateFin(resultSet.getTimestamp("end_date").toLocalDateTime());
                        evenement.setLieu(resultSet.getString("location"));
                        evenement.setPrix(0.0);
                        evenement.setRemise(0);
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

    @Override
    public void supprimer(Candidat candidat) throws SQLException {
        // Not applicable for Evenement
        System.out.println("Operation not supported for Evenement");
    }
    
    @Override
    public void supprimer(Employe employe) throws SQLException {
        // Not applicable for Evenement
        System.out.println("Operation not supported for Evenement");
    }
    
    @Override
    public void supprimer(Profil profil) throws SQLException {
        // Not applicable for Evenement
        System.out.println("Operation not supported for Evenement");
    }
    
    @Override
    public void supprimer(Commentaire commentaire) throws SQLException {
        // Not applicable for Evenement
        System.out.println("Operation not supported for Evenement");
    }
}
