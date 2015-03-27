package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by thibaut on 26/03/15.
 */
public class TableTypeBiere extends Ctrl{

    public ArrayList<String[]> result;

    private static TableTypeBiere instance;

    public TableTypeBiere instance(Context ctxt){
        if(instance == null){
            instance = new TableTypeBiere(ctxt);
        }
        return instance;
    }

    private TableTypeBiere(Context ctxt){
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
