package fabrique.gestion.Objets;

public class EtatFermenteur {

    public static final String[] etats = new String[] {"Vide", "Fermentation", "Lavé"};

    public static String etat(int index) {
        return(etats[index]);
    }

    public static String[] etats() {
        return etats;
    }
}