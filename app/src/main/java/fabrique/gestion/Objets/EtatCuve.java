package fabrique.gestion.Objets;

public class EtatCuve {

    public static final String[] etat = new String[] {"Vide", "Gazéification", "En service", "Lavé"};

    public static String etat(int index) {
        return(etat[index]);
    }
}