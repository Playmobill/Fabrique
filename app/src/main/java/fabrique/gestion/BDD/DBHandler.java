package fabrique.gestion.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thibaut on 26/03/15.
 */
public class DBHandler extends SQLiteOpenHelper{

    private static String createTTypeBiere = "CREATE TABLE TypeBiere(id INTEGER AUTOINCREMENT PRIMARY KEY NOT NULL,texte TEXT)";
    private static String createTBrassin = "CREATE TABLE Brassin (id INTEGER AUTOINCREMENT PRIMARY KEY,numero INTEGER,commentaire TEXT,acronyme TEXT,dateCreation INTEGER,quantite INTEGER,id_typeBiere INTEGER NOT NULL,couleur TEXT,densiteOriginale REAL,densiteFinale REAL,pourcentageAlcool REAL)";
    private static String createTEmplacement = "CREATE TABLE Emplacement (id INTEGER AUTOINCREMENT PRIMARY KEY,texte TEXT)";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTTypeBiere);
        db.execSQL(createTBrassin);
        db.execSQL(createTEmplacement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
