package com.esprit.tests;

import com.esprit.models.Candidat;
import com.esprit.models.Employe;
import com.esprit.models.Profil;
import com.esprit.services.CandidatService;
import com.esprit.services.EmployeService;
import com.esprit.services.ProfilService;
import com.esprit.utils.DataSource;

@SuppressWarnings("unused")
public class MainProg {
    public static void main(String[] args) {
        EmployeService es = new EmployeService();
        ProfilService ps = new ProfilService();

        CandidatService cs = new CandidatService();


      //  cs.ajouter(new Candidat("3","aad",3));
        cs.supprimer(new Candidat("4","aad",3));


        // es.ajouter(new Employe(2,"ca","cc"));
      //  es.modifier(new Employe(1,"abc","aaa"));
       // es.supprimer(new Employe(1,"",""));




        //ps.ajouter(new Profil(1,"aabc","abce","SSS","ffrzr"));
        //ps.modifier(new Profil(1,"aaa","aaaaaaaa","aaaaaa","aaa"));
    // es.ajouter(new Employe("Fedi", "Salah","@da","11",""));

//        ps.modifier(new Personne(2, "Salah", "Samir"));
//        ps.supprimer(new Personne(2, "", ""));
        System.out.println(es.rechercher());
    }
}
