package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Controle {

    public final static int version = 1;
    protected final static String nomBDD = "Brasserie";

    protected SQLiteDatabase bdd;
    protected DBHandler dbHandler;

    public Controle(Context contexte){
        dbHandler = new DBHandler(contexte, nomBDD, null, version);
        ouvrir();
    }

    public SQLiteDatabase ouvrir() {
        bdd = dbHandler.getWritableDatabase();
        return bdd;
    }

    public void fermer() {
        bdd.close();
    }

    public SQLiteDatabase bdd(){
        return bdd;
    }

    public Cursor select(String table){
        return bdd.query(table, null, null, null, null, null, null);
    }
}
