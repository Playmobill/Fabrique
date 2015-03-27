package fabrique.gestion.BDD;

import java.util.ArrayList;

public class TableEtatCuve {

    private static TableEtatCuve instance;

    private ArrayList<String> etats;

    private TableEtatCuve() {
        etats = new ArrayList<>();
        etats.add("Vide");
        etats.add("Gazéification");
        etats.add("En service");
        etats.add("Lavé");
    }

    public static TableEtatCuve instance() {
        if (instance == null) {
            instance = new TableEtatCuve();
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
