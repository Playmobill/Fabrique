package fabrique.gestion.Objets;

import android.support.annotation.NonNull;

public class Historique extends Objet implements Comparable<Historique> {

    private String texte;
    private long date;
    private long id_fermenteur;
    private long id_cuve;
    private long id_fut;
    private long id_brassin;

    public Historique(long id, String texte, long date, long id_fermenteur, long id_cuve, long id_fut, long id_brassin) {
        super(id);
        this.texte = texte;
        this.date = date;
        this.id_fermenteur = id_fermenteur;
        this.id_cuve = id_cuve;
        this.id_fut = id_fut;
        this.id_brassin = id_brassin;
    }

    public String getTexte() {
        return texte;
    }
    public long getDate() {
        return date;
    }
    public String getDateToString() {
        return DateToString.dateToString(date);
    }
    public long getId_fermenteur() {
        return id_fermenteur;
    }
    public long getId_cuve() {
        return id_cuve;
    }
    public long getId_fut() {
        return id_fut;
    }
    public long getId_brassin() {
        return id_brassin;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int compareTo(@NonNull Historique historique) {
        if (date == historique.date) {
            return 0;
        } else if (date > historique.date) {
            return 1;
        }
        return -1;
    }

    public String sauvegarde() {
        return ("<Historique>" +
                    "<texte>" + texte + "</texte>" +
                    "<date>" + date + "</date>" +
                    "<id_fermenteur>" + id_fermenteur + "</id_fermenteur>" +
                    "<id_cuve>" + id_cuve + "</id_cuve>" +
                    "<id_fut>" + id_fut + "</id_fut>" +
                    "<id_brassin>" + id_brassin + "</id_brassin>" +
                "</Historique>");
    }
}
