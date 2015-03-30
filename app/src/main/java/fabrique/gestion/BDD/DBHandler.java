package fabrique.gestion.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper{

    private static String createTTypeBiere = "CREATE TABLE TypeBiere(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, texte TEXT)";
    private static String createTBrassin = "CREATE TABLE Brassin (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, numero INTEGER, commentaire TEXT, acronyme TEXT, dateCreation INTEGER, quantite INTEGER, id_typeBiere INTEGER NOT NULL, couleur TEXT, densiteOriginale REAL, densiteFinale REAL, pourcentageAlcool REAL)";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTTypeBiere);
        db.execSQL(createTBrassin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
