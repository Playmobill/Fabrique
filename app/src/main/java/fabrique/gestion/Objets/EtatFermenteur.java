package fabrique.gestion.Objets;

public class EtatFermenteur {

    public static final String[] etat = new String[] {"Vide", "Fermentation", "Lavé"};

    public static String etat(int index) {
        return(etat[index]);
    }
}