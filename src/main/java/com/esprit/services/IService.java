package com.esprit.services;

import com.esprit.models.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    void supprimer(Evenement evenement) throws SQLException;
    void supprimer(Candidat candidat) throws SQLException;
    void supprimer(Employe employe) throws SQLException;
    void supprimer(Profil profil) throws SQLException;
    void supprimer(Commentaire commentaire) throws SQLException;
    void supprimer(Object t) throws SQLException;

    List<T> rechercher();

    void ajouter(T t) throws SQLException, IOException;
    public List<Evenement> sortEvent(int value);

    void modifier (T t) throws SQLException;
    public Evenement getOneEvenement(int idEvenement) throws SQLException;
    public void AddEvenenemtOffer(Evenement evenement);

    public void supprimer(int id) throws SQLException;
    public List<T> afficher() throws SQLException;

    List<Commentaire> show(int evenement_id) throws SQLException;

    public T afficher1(int id) throws SQLException;

}
