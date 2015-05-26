package fabrique.gestion.Objets;

/**
 * Created by thibaut on 01/05/15.
 */
public class Calendrier extends Objet implements Comparable<Calendrier>{

    private long dateEvenement;
    private String nomEvenement;
    private int typeObjet;
    private long idObjet;

    public Calendrier(long id, long dateEvent, String nomEvent, int typeObjet, long idObjet) {
        super(id);
        this.dateEvenement = dateEvent;
        this.nomEvenement = nomEvent;
        this.typeObjet = typeObjet;
        this.idObjet = idObjet;
    }

    @Override
    public String sauvegarde() {
        return ("<O:Calendrier>" +
                    "<E:dateEvenement>" + dateEvenement + "</E:dateEvenement>" +
                    "<E:nomEvenement>" + nomEvenement + "</E:nomEvenement>" +
                    "<E:typeObjet>" + typeObjet + "</E:typeObjet>" +
                    "<E:idObjet>" + idObjet + "</E:idObjet>" +
                "</O:Calendrier>");
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
