package com.esprit.services;

import com.esprit.models.*;
import com.esprit.utils.database;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeService implements IService<Employe> {

    private final Connection connection = database.getInstance().getConnection();

    @Override
    public void ajouter(Employe employe) throws SQLException, IOException {
        String req = "INSERT INTO employe (id,nom, prenom, email, poste, date_embauche) VALUES ('"+employe.getId()+ "','"+employe.getNom()+"','"+employe.getPrenom()+"','"+employe.getEmail()+"','"+employe.getPoste()+"','"+ employe.GetDateEmbauche()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("employe ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void modifier(Employe employe) throws SQLException {
        String req = "UPDATE employe SET id='"+employe.getId()+"',nom='"+employe.getNom()+"',prenom='"+employe.getPrenom()+"', email='"+employe.getEmail()+"',poste='"+employe.getPoste()+"',date_embauche='"+employe.GetDateEmbauche()+"' WHERE id='"+employe.getId()+"'";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("employe modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void supprimer(Evenement evenement) throws SQLException {
        // Not applicable for Employe
        System.out.println("Operation not supported for Employe");
    }

    @Override
    public void supprimer(Employe employe) throws SQLException {
        String req = "DELETE from employe WHERE id="+employe.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("employe supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE from employe WHERE id=" + id;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("employe supprimée avec ID: " + id);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void supprimer(Object t) throws SQLException {
        if (t instanceof Employe) {
            Employe employe = (Employe) t;
            String req = "DELETE from employe WHERE id="+employe.getId();
            try {
                Statement st = connection.createStatement();
                st.executeUpdate(req);
                System.out.println("employe supprimée");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Object to delete must be of type Employe");
        }
    }

    @Override
    public void supprimer(Candidat candidat) throws SQLException {

    }

    @Override
    public List<Employe> rechercher() {
        List<Employe> employe = new ArrayList<>();

        String req = "SELECT * FROM employe";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                employe.add(new Employe(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("email"),rs.getString("poste"),rs.getDate("date_embauche")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employe;
    }

    @Override
    public List<Employe> afficher() throws SQLException {
        List<Employe> employes = new ArrayList<>();
        String req = "SELECT * FROM employe";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                employes.add(new Employe(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("email"),rs.getString("poste"),rs.getDate("date_embauche")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return employes;
    }

    @Override
    public Employe afficher1(int id) throws SQLException {
        Employe employe = null;
        String req = "SELECT * FROM employe WHERE id = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                employe = new Employe(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    rs.getString("poste"),
                    rs.getDate("date_embauche")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return employe;
    }

    @Override
    public List<Evenement> sortEvent(int value) {
        // Not applicable for Employe
        return null;
    }

    @Override
    public Evenement getOneEvenement(int idEvenement) throws SQLException {
        // Not applicable for Employe
        return null;
    }

    @Override
    public void AddEvenenemtOffer(Evenement evenement) {
        // Not applicable for Employe
    }

    @Override
    public List<Commentaire> show(int evenement_id) throws SQLException {
        // Not applicable for Employe
        return null;
    }

    public Date dateEmbauche(int id) {
        Date dateEmbauche = null;
       String req = "SELECT date_embauche FROM employe WHERE id = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                dateEmbauche = rs.getDate("date_embauche");
            }

            } catch(SQLException e){
                throw new RuntimeException(e);
            }

        return dateEmbauche;
    }
    
    public void empAff(Employe employe) {
        int id = 0;
        String nom = "";
        String prenom = "";
        String poste = "";
        String email = "";
        Date dateEmbauche = null;

        String req = "SELECT * FROM employe where id = " + employe.getId();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                id = rs.getInt("id");
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                poste = rs.getString("poste");
                email = rs.getString("email");
                dateEmbauche = rs.getDate("date_embauche");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        employe.setId(id);
        employe.setPrenom(prenom);
        employe.setEmail(email);
        employe.setNom(nom);
        employe.setPoste(poste);
        employe.setDateEmbauche(dateEmbauche);
    }

    @Override
    public void supprimer(Profil profil) throws SQLException {
        // Not applicable for Employe
        System.out.println("Operation not supported for Employe");
    }
    
    @Override
    public void supprimer(Commentaire commentaire) throws SQLException {
        // Not applicable for Employe
        System.out.println("Operation not supported for Employe");
    }
}
