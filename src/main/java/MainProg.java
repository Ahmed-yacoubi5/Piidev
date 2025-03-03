import com.esprit.services.ServiceEvenement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainProg {
        public static void main(String[] args) {
                ServiceEvenement ev = new ServiceEvenement();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {
                        Date dateDebut = dateFormat.parse("2003-12-09");
                        Date dateFin = dateFormat.parse("2004-12-09");

                        // Convertir java.util.Date en java.sql.Date
                        java.sql.Date sqlDateDebut = new java.sql.Date(dateDebut.getTime());
                        java.sql.Date sqlDateFin = new java.sql.Date(dateFin.getTime());

                  /*      ev.ajouter(new Evenement("OMAIMA", "a", "aa", sqlDateDebut, sqlDateFin));
                        ev.modifier(new Evenement("kk", "a", "aa", sqlDateDebut, sqlDateFin));
                        ev.supprimer(new Evenement("abc", "", "", sqlDateDebut, sqlDateFin));
*/
                        System.out.println(ev.rechercher());
                } catch (ParseException e) {
                        e.printStackTrace();
                }
        }
}


