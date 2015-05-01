package fabrique.gestion.Objets;

import java.util.Comparator;

/**
 * Created by thibaut on 01/05/15.
 */
public class Calendrier extends Objet implements Comparable<Calendrier>{

    private long dateEvenement;
    private String nomEvenement;
    private long typeObjet;
    private int idObjet;

    public Calendrier(long id, long dateEvent, String nomEvent, long typeObjet, int idObjet) {
        super(id);
        this.dateEvenement = dateEvent;
        this.nomEvenement = nomEvent;
        this.typeObjet = typeObjet;
        this.idObjet = idObjet;
    }

    @Override
    public String sauvegarde() {
        return null;
    }

    @Override
    public int compareTo(Calendrier another) {
        return 0;
    }

    public long getDateEvenement() {
        return dateEvenement;
    }

    public String getNomEvenement() {
        return nomEvenement;
    }
}
