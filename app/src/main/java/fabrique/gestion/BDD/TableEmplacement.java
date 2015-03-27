package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;

import fabrique.gestion.Objets.Emplacement;

public class TableEmplacement extends Ctrl{

    public ArrayList<Emplacement> result;
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
