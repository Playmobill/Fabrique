package fabrique.gestion.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

public class BDD extends SQLiteOpenHelper {

    private static String createurTableRecette = "CREATE TABLE Recette(" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "nom TEXT NOT NULL," +
                                                "couleur TEXT NOT NULL," +
                                                "acronyme TEXT NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEmplacement = "CREATE TABLE IF NOT EXISTS Emplacement (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEtatFermenteur = "CREATE TABLE IF NOT EXISTS EtatFermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT NOT NULL, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";


    private static String createurTableEtatCuve = "CREATE TABLE IF NOT EXISTS EtatCuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT NOT NULL, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEtatFut = "CREATE TABLE IF NOT EXISTS EtatFut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT NOT NULL, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableBrassin = "CREATE TABLE IF NOT EXISTS Brassin (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER NOT NULL," +
                                                "commentaire TEXT NOT NULL," +
                                                "dateCreation INTEGER NOT NULL," +
                                                "quantite INTEGER NOT NULL," +
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
                                                "commentaireEtat TEXT," +
                                                "id_brassin INTEGER)";

    private static String createurTableFut = "CREATE TABLE IF NOT EXISTS Fut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER NOT NULL," +
                                                "capacite INTEGER NOT NULL," +
                                                "id_etatFut INTEGER NOT NULL," +
                                                "dateEtat INTEGER NOT NULL," +
                                                "id_brassin INTEGER," +
                                                "dateInspection INTEGER NOT NULL)";

    private static String createurTableGestion = "CREATE TABLE IF NOT EXISTS Gestion (" +
                                                "delaiLavageAcide INTEGER DEFAULT 604800000," +
                                                "delaiInspectionBaril INTEGER DEFAULT 604800000)";

    private static String createurTableHistorique = "CREATE TABLE IF NOT EXISTS Historique (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT NOT NULL," +
                                                "date INTEGER NOT NULL," +
                                                "id_fermenteur INTEGER NOT NULL," +
                                                "id_cuve INTEGER NOT NULL," +
                                                "id_fut INTEGER NOT NULL," +
                                                "id_brassin INTEGER NOT NULL)";

    private static String createurTableListeHistorique = "CREATE TABLE IF NOT EXISTS ListeHistorique (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "elementConcerne INTEGER NOT NULL," +
                                                "texte TEXT NOT NULL)";


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
        db.execSQL(createurTableFut);
        db.execSQL(createurTableGestion);
        db.execSQL(createurTableHistorique);
        db.execSQL(createurTableListeHistorique);

        db.execSQL("INSERT INTO EtatFermenteur (texte, couleurTexte, couleurFond, actif) VALUES ('Vide', " + Color.BLACK + ", " + Color.WHITE +", 1)");
        db.execSQL("INSERT INTO EtatCuve (texte, couleurTexte, couleurFond, actif) VALUES ('Vide', " + Color.BLACK + ", " + Color.WHITE +", 1)");
        db.execSQL("INSERT INTO EtatFut (texte, couleurTexte, couleurFond, actif) VALUES ('Vide', " + Color.BLACK + ", " + Color.WHITE +", 1)");

        db.execSQL("INSERT INTO Recette (nom, couleur, acronyme, actif) VALUES ('Riv. Blanche', 'Blanche', 'Rvb', 1)");
        db.execSQL("INSERT INTO Recette (nom, couleur, acronyme, actif) VALUES ('République', 'Blonde', 'Rpb', 1)");
        db.execSQL("INSERT INTO Recette (nom, couleur, acronyme, actif) VALUES ('Goupil', 'Rousse', 'Gpl', 1)");

        db.execSQL("INSERT INTO Gestion (delaiLavageAcide, delaiInspectionBaril) VALUES(604800000, 604800000)");

        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('SS', 1)");
        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('Ch.Froide', 1)");
        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('RC', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}