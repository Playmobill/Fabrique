package fabrique.gestion.BDD;

import java.util.ArrayList;

public class TableEtatFermenteur {

    private static TableEtatFermenteur instance;

    private ArrayList<String> etats;

    private TableEtatFermenteur() {
        etats.add("Vide");
        etats.add("Fermentation");
        etats.add("Lavé");
    }

    public static TableEtatFermenteur instance() {
        if (instance == null) {
            instance = new TableEtatFermenteur();
        }
        return instance;
    }

    public void ajouter(String etat) {
        etats.add(etat);
    }

    public String etat(int index) {
        return etats.get(index);
    }

    public ArrayList<String> etats() {
        return etats;
    }

}
