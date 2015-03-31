package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.Emplacement;

public class TableEmplacement extends Controle {

    private ArrayList<Emplacement> emplacements;

    private static TableEmplacement INSTANCE;

    public static TableEmplacement instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableEmplacement(contexte);
        }
        return INSTANCE;
    }

    private TableEmplacement(Context contexte){
        super(contexte, "Emplacement");

        emplacements = new ArrayList<>();
        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            emplacements.add(new Emplacement(tmp.getInt(0), tmp.getString(1)));
        }

        ajouter("SS");
        ajouter("RC");
        ajouter("ChFroide");
    }

    public void ajouter(String texte){
        emplacements.add(new Emplacement(emplacements.size(), texte));
        accesBDD.execSQL("INSERT INTO Emplacement (texte) VALUES ('"+texte+"')");
    }

    public Emplacement recuperer(int index){
        return emplacements.get(index);
    }

    public void modifier(int index, String texte){
        emplacements.get(index).setTexte(texte);
    }

    public void supprimer(int index){
        emplacements.remove(index);
    }

    public int tailleListe() {
        return emplacements.size();
    }

    public String emplacement(int index){
        return emplacements.get(index).getTexte();
    }

    public String[] emplacements () {
        String[] etats = new String[emplacements.size()];
        for (int i=0; i<emplacements.size(); i++) {
            etats[i] = emplacement(i);
        }
        return etats;
    }
}
