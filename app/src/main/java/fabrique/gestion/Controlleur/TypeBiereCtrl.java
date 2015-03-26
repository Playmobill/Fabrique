package fabrique.gestion.Controlleur;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by thibaut on 26/03/15.
 */
public class TypeBiereCtrl extends Ctrl{

    public ArrayList<String[]> result;

    public TypeBiereCtrl(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("TypeBiere", new String[] {"*"});
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            result.add(new String[] {tmp.getString(0), tmp.getString(1)});
        }
    }

    public void ajout(String texte){
        result.add(new String[] {Integer.toString(result.size()), texte});
        BDD.execSQL("INSERT INTO TypeBiere (texte) VALUES ('"+texte+"')");
    }
}
