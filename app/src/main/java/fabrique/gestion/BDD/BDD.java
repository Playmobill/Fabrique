package fabrique.gestion.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

public class BDD extends SQLiteOpenHelper {

    private static String createurTableRecette = "CREATE TABLE Recette(" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "nom TEXT," +
                                                "couleur TEXT," +
                                                "acronyme TEXT);";

    private static String createurTableEmplacement = "CREATE TABLE IF NOT EXISTS Emplacement (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEtatFermenteur = "CREATE TABLE IF NOT EXISTS EtatFermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";


    private static String createurTableEtatCuve = "CREATE TABLE IF NOT EXISTS EtatCuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEtatFut = "CREATE TABLE IF NOT EXISTS EtatFut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableBrassin = "CREATE TABLE IF NOT EXISTS Brassin (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER," +
                                                "commentaire TEXT," +
                                                "dateCreation INTEGER NOT NULL," +
                                                "quantite INTEGER," +
                                                "id_recette INTEGER NOT NULL," +
                                                "densiteOriginale REAL," +
                                                "densiteFinale REAL," +
                                                "pourcentageAlcool REAL)";

    private static String createurTableFermenteur = "CREATE TABLE IF NOT EXISTS Fermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER NOT NULL," +
                                                "capacite INTEGER NOT NULL," +
                                                "id_emplacement INTEGER NOT NULL," +
                                                "dateLavageAcide INTEGER NOT NULL," +
                                                "id_etatFermenteur INTEGER NOT NULL," +
                                                "dateEtat INTEGER NOT NULL," +
                                                "id_brassin INTEGER)";

    private static String createurTableCuve = "CREATE TABLE IF NOT EXISTS Cuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER NOT NULL," +
                                                "capacite INTEGER NOT NULL," +
                                                "id_emplacement INTEGER NOT NULL," +
                                                "dateLavageAcide INTEGER NOT NULL," +
                                                "id_etatCuve INTEGER NOT NULL," +
                                                "dateEtat INTEGER NOT NULL," +
                                                "commentaireEtat TEXT NOT NULL," +
                                                "id_brassin INTEGER)";

    private static String gestion = "CREATE TABLE IF NOT EXISTS Gestion (" +
                                                "delaiLavageAcide INTEGER DEFAULT 604800000," +
                                                "delaiInspectionBaril INTEGER DEFAULT 604800000)";

    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createurTableRecette);
        db.execSQL(createurTableBrassin);
        db.execSQL(createurTableEmplacement);
        db.execSQL(createurTableEtatFermenteur);
        db.execSQL(createurTableFermenteur);
        db.execSQL(createurTableEtatCuve);
        db.execSQL(createurTableEtatFut);
        db.execSQL(createurTableCuve);
        db.execSQL(gestion);

        db.execSQL("INSERT INTO EtatFermenteur (texte, couleurTexte, couleurFond, actif) VALUES ('Vide', " + Color.BLACK + ", " + Color.WHITE +", 1)");
        db.execSQL("INSERT INTO EtatCuve (texte, couleurTexte, couleurFond, actif) VALUES ('Vide', " + Color.BLACK + ", " + Color.WHITE +", 1)");
        db.execSQL("INSERT INTO EtatFut (texte, couleurTexte, couleurFond, actif) VALUES ('Vide', " + Color.BLACK + ", " + Color.WHITE +", 1)");

        db.execSQL("INSERT INTO Recette (nom, couleur, acronyme) VALUES ('Riv. Blanche', 'Blanche', 'Rvb')");
        db.execSQL("INSERT INTO Recette (nom, couleur, acronyme) VALUES ('République', 'Blonde', 'Rpb')");
        db.execSQL("INSERT INTO Recette (nom, couleur, acronyme) VALUES ('Goupil', 'Rousse', 'Gpl')");

        db.execSQL("INSERT INTO Gestion (delaiLavageAcide, delaiInspectionBaril) VALUES(604800000, 604800000)");

        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('SS', 1)");
        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('Ch.Froide', 1)");
        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('RC', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
