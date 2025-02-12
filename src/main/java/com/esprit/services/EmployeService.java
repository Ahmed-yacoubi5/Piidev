package com.esprit.services;

import com.esprit.models.Employe;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmployeService implements IService<Employe> {

    private Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Employe employe) {
        String req = "INSERT INTO employe (id,nom, prenom, email, poste, dateEmbauche) VALUES ('"+employe.getId()+ "''"+employe.getNom()+"','"+employe.getPrenom()+"','"+employe.getEmail()+"','"+employe.getPoste()+"','"+ employe.GetDateEmbauche()+"')";
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
        String req = "UPDATE employe SET id='"+employe.getId()+"' ,nom='"+employe.getNom()+"',prenom='"+employe.getPrenom()+"', email='"+employe.getEmail()+"',poste='"+employe.getPoste()+"',dateEmbauche='"+employe.GetDateEmbauche()+"' WHERE id="+employe.getId();
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
}
