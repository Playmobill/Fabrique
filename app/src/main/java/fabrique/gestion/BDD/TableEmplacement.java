package fabrique.gestion.BDD;

import java.util.ArrayList;

public class TableEmplacement {

    private ArrayList<String> emplacements = new ArrayList<String>();

    private static TableEmplacement instance;

    private TableEmplacement() {
        emplacements.add("RC");
        emplacements.add("SS");
        emplacements.add("Ch.Froide");
    }

    public static TableEmplacement instance() {
        if (instance == null) {
            instance = new TableEmplacement();
        }
        return instance;
    }

    public void ajouter(String emplacement) {
        emplacements.add(emplacement);
    }

    public String emplacement(int index ) {
        return emplacements.get(index);
    }

    public ArrayList<String> emplacements() {
        return emplacements;
    }

}
