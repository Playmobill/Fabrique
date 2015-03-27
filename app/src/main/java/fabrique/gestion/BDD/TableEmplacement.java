package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

import fabrique.gestion.Objets.Emplacement;

<<<<<<< HEAD
public class TableEmplacement extends Ctrl{
=======
    private ArrayList<String> emplacements;
>>>>>>> dacac8d9716465a206f1cd8995bf93f3ff077b94

    public ArrayList<Emplacement> result;

<<<<<<< HEAD
    private static TableEmplacement instance;
=======
    private TableEmplacement() {
        emplacements = new ArrayList<>();
        emplacements.add("RC");
        emplacements.add("SS");
        emplacements.add("Ch.Froide");
    }
>>>>>>> dacac8d9716465a206f1cd8995bf93f3ff077b94

    public static TableEmplacement instance(Context ctxt){
        if(instance == null){
            instance = new TableEmplacement(ctxt);
        }
        return instance;
    }

    private TableEmplacement(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("Emplacement", new String[] {"*"});
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            result.add(new Emplacement(tmp.getInt(0), tmp.getString(1)));
        }
    }

    public void ajout(String texte){
        result.add(new Emplacement(result.size(), texte));
        BDD.execSQL("INSERT INTO Emplacement (texte) VALUES ('"+texte+"')");
    }

}
