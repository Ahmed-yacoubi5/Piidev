package com.esprit.services;

import com.esprit.models.Candidat;
import com.esprit.models.Employe;
import com.esprit.utils.DataSource;

import java.sql.Date;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeService implements IService<Employe> {

    private final Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Employe employe) {
        String req = "INSERT INTO employe (id,nom, prenom, email, poste, dateEmbauche) VALUES ('"+employe.getId()+ "','"+employe.getNom()+"','"+employe.getPrenom()+"','"+employe.getEmail()+"','"+employe.getPoste()+"','"+ employe.GetDateEmbauche()+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("employe ajoutée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Employe employe) {
        String req = "UPDATE employe SET id='"+employe.getId()+"',nom='"+employe.getNom()+"',prenom='"+employe.getPrenom()+"', email='"+employe.getEmail()+"',poste='"+employe.getPoste()+"',dateEmbauche='"+employe.GetDateEmbauche()+"' WHERE id='"+employe.getId()+"'";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("employe modifiée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Employe employe) {
        String req = "DELETE from employe WHERE id="+employe.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("employe supprimée");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Employe> rechercher() {
        List<Employe> employe = new ArrayList<>();

        String req = "SELECT * FROM employe";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                employe.add(new Employe(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),rs.getString("email"),rs.getString("poste"),rs.getDate("dateEmbauche")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return employe;
    }
    public Date dateEmbauche(int id) {
        Date dateEmbauche = null;
       String req = "SELECT dateEmbauche FROM employe WHERE id = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {
                dateEmbauche = rs.getDate("dateEmbauche");
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
                dateEmbauche = rs.getDate("dateEmbauche");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        employe.setId(id);
        employe.setPrenom(prenom);
        employe.setEmail(email);
        employe.setNom(nom);
        employe.setPoste("poste");
        employe.setDateEmbauche(dateEmbauche);

    }
}
