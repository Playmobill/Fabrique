package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Controle {

    public final static int version = 1;
    protected final static String nomBDD = "Brasserie";

    protected SQLiteDatabase BDD;
    protected DBHandler dbHandler;

    public Controle(Context contexte){
        dbHandler = new DBHandler(contexte, nomBDD, null);
        BDD = dbHandler.getWritableDatabase();
    }

    public void fermer() {
        BDD.close();
    }

    public Cursor select(String table){
        return BDD.query(table, null, null, null, null, null, null);
    }
}
