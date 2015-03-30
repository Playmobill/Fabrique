package fabrique.gestion.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thibaut on 26/03/15.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static String createTTypeBiere = "CREATE TABLE IF NOT EXISTS TypeBiere(id INTEGER PRIMARY KEY AUTOINCREMENT,texte TEXT)";
    private static String createTBrassin = "CREATE TABLE IF NOT EXISTS Brassin (id INTEGER PRIMARY KEY AUTOINCREMENT,numero INTEGER,commentaire TEXT,acronyme TEXT,dateCreation INTEGER,quantite INTEGER,id_typeBiere INTEGER NOT NULL,couleur TEXT,densiteOriginale REAL,densiteFinale REAL,pourcentageAlcool REAL)";
    private static String createTEmplacement = "CREATE TABLE IF NOT EXISTS Emplacement (id INTEGER PRIMARY KEY AUTOINCREMENT,texte TEXT)";
    private static String createTEtatFermenteur = "CREATE TABLE IF NOT EXISTS EtatFermenteur (id INTEGER PRIMARY KEY AUTOINCREMENT,texte TEXT)";
    private static String createTFermenteur = "CREATE TABLE IF NOT EXISTS Fermenteur (id INTEGER PRIMARY KEY AUTOINCREMENT,numero INTEGER,capacite INTEGER,dateLavageAcide INTEGER,id_etatFermenteur INTEGER NOT NULL,dateEtat INTEGER,id_brassin INTEGER,id_emplacement INTEGER NOT NULL)";
    private static String createTEtatCuve = "CREATE TABLE EtatCuve(id INTEGER PRIMARY KEY AUTOINCREMENT,texte TEXT)";
    private static String createTCuve = "CREATE TABLE Cuve (id INTEGER PRIMARY KEY AUTOINCREMENT, numero INTEGER, capacite INTEGER, dateLavageAcide INTEGER, id_emplacement INTEGER NOT NULL, id_etatCuve INTEGER NOT NULL, dateEtat INTEGER, commentaireEtat TEXT, id_brassin INTEGER)";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTTypeBiere);
        db.execSQL(createTBrassin);
        db.execSQL(createTEmplacement);
        db.execSQL(createTEtatFermenteur);
        db.execSQL(createTFermenteur);
        db.execSQL(createTEtatCuve);
        db.execSQL(createTCuve);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
