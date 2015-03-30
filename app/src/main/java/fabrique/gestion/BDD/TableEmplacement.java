package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

import fabrique.gestion.Objets.Emplacement;

public class TableEmplacement extends Controle {

    private ArrayList<Emplacement> result;
    private static TableEmplacement instance;


    public static TableEmplacement instance(Context ctxt){
        if(instance == null){
            instance = new TableEmplacement(ctxt);
        }
        return instance;
    }

    private TableEmplacement(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("Emplacement");
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            result.add(new Emplacement(tmp.getInt(0), tmp.getString(1)));
        }
    }

    public void ajout(String texte){
        result.add(new Emplacement(result.size(), texte));
        BDD.execSQL("INSERT INTO Emplacement (texte) VALUES ('"+texte+"')");
    }

    public Emplacement recuperer(int index){
        return result.get(index);
    }

    public void modifier(int index, String texte){
        result.get(index).setTexte(texte);
    }

    public void supprimer(int index){
        result.remove(index);
    }

    public String emplacement(int numero){
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getId() == numero){
                return result.get(i).getTexte();
            }
        }
        return null;
    }

    public String[] emplacements () {
        String[] etats = new String[result.size()];
        for (int i=0; i<result.size(); i++) {
            etats[i] = emplacement(i);
        }
        return etats;
    }
}
