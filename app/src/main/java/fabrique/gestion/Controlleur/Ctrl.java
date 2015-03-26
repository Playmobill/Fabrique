package fabrique.gestion.Controlleur;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by thibaut on 26/03/15.
 */
public abstract class Ctrl {

    public final static int version = 1;
    protected final static String nomDB = "Brasserie";

    protected SQLiteDatabase BDD;
    protected DBHandler dbHandler;

    public Ctrl(Context ctxt){
        dbHandler = new DBHandler(ctxt, nomDB, null, version);
    }

    public SQLiteDatabase open() {
        BDD = dbHandler.getWritableDatabase();
        return BDD;
    }

    public void close() {
        BDD.close();
    }

    public SQLiteDatabase getDb(){
        return BDD;
    }

    public Cursor select(String table, String[] columns){
        return BDD.query(table, columns, null, null, null, null, null);
    }
}
