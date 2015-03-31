package fabrique.gestion.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDD extends SQLiteOpenHelper {

    private static String createurTableTypeBiere = "CREATE TABLE IF NOT EXISTS TypeBiere (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT)";

    private static String createurTableEmplacement = "CREATE TABLE IF NOT EXISTS Emplacement (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT)";

    private static String createurTableEtatFermenteur = "CREATE TABLE IF NOT EXISTS EtatFermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT)";

    private static String createurTableEtatCuve = "CREATE TABLE IF NOT EXISTS EtatCuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT)";

    private static String createurTableBrassin = "CREATE TABLE IF NOT EXISTS Brassin (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER," +
                                                "commentaire TEXT," +
                                                "acronyme TEXT," +
                                                "dateCreation INTEGER," +
                                                "quantite INTEGER," +
                                                "id_typeBiere INTEGER NOT NULL," +
                                                "couleur TEXT," +
                                                "densiteOriginale REAL," +
                                                "densiteFinale REAL," +
                                                "pourcentageAlcool REAL)";

    private static String createurTableFermenteur = "CREATE TABLE IF NOT EXISTS Fermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER," +
                                                "capacite INTEGER," +
                                                "id_emplacement INTEGER NOT NULL," +
                                                "dateLavageAcide INTEGER," +
                                                "id_etatFermenteur INTEGER NOT NULL," +
                                                "dateEtat INTEGER," +
                                                "id_brassin INTEGER)";

    private static String createurTableCuve = "CREATE TABLE Cuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER," +
                                                "capacite INTEGER," +
                                                "id_emplacement INTEGER NOT NULL," +
                                                "dateLavageAcide INTEGER," +
                                                "id_etatCuve INTEGER NOT NULL," +
                                                "dateEtat INTEGER," +
                                                "commentaireEtat TEXT," +
                                                "id_brassin INTEGER)";

    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createurTableTypeBiere);
        db.execSQL(createurTableBrassin);
        db.execSQL(createurTableEmplacement);
        db.execSQL(createurTableEtatFermenteur);
        db.execSQL(createurTableFermenteur);
        db.execSQL(createurTableEtatCuve);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
