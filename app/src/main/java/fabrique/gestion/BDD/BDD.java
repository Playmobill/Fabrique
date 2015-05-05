package fabrique.gestion.BDD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

public class BDD extends SQLiteOpenHelper {

    private static String createurTableTypeBiere = "CREATE TABLE TypeBiere (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "nom TEXT," +
                                                "couleur TEXT," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableRecette = "CREATE TABLE Recette(" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "nom TEXT," +
                                                "acronyme TEXT," +
                                                "id_typeBiere INTEGER NOT NULL," +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEmplacement = "CREATE TABLE IF NOT EXISTS Emplacement (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEtatFermenteur = "CREATE TABLE IF NOT EXISTS EtatFermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "historique TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";


    private static String createurTableEtatCuve = "CREATE TABLE IF NOT EXISTS EtatCuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "historique TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEtatFut = "CREATE TABLE IF NOT EXISTS EtatFut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "historique TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableBrassin = "CREATE TABLE IF NOT EXISTS Brassin (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER NOT NULL," +
                                                "commentaire TEXT," +
                                                "dateCreation INTEGER NOT NULL," +
                                                "quantite INTEGER NOT NULL," +
                                                "id_recette INTEGER NOT NULL," +
                                                "densiteOriginale REAL," +
                                                "densiteFinale REAL)";

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
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "numero INTEGER NOT NULL, " +
                                                "capacite INTEGER NOT NULL, " +
                                                "id_emplacement INTEGER NOT NULL, " +
                                                "dateLavageAcide INTEGER NOT NULL, " +
                                                "id_etatCuve INTEGER NOT NULL, " +
                                                "dateEtat INTEGER NOT NULL, " +
                                                "commentaireEtat TEXT, " +
                                                "id_brassin INTEGER)";

    private static String createurTableFut = "CREATE TABLE IF NOT EXISTS Fut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "numero INTEGER NOT NULL, " +
                                                "capacite INTEGER NOT NULL, " +
                                                "id_etatFut INTEGER NOT NULL, " +
                                                "dateEtat INTEGER NOT NULL, " +
                                                "id_brassin INTEGER, " +
                                                "dateInspection INTEGER NOT NULL)";

    private static String createurTableGestion = "CREATE TABLE IF NOT EXISTS Gestion (" +
                                                "delaiLavageAcide INTEGER DEFAULT 1209600000, " +
                                                "avertissementLavageAcide INTEGER DEFAULT 604800000, " +
                                                "delaiInspectionBaril INTEGER DEFAULT 1209600000, " +
                                                "avertissementInspectionBaril INTEGER DEFAULT 604800000)";

    private static String createurTableHistorique = "CREATE TABLE IF NOT EXISTS Historique (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "texte TEXT, " +
                                                "date INTEGER NOT NULL, " +
                                                "id_fermenteur INTEGER NOT NULL, " +
                                                "id_cuve INTEGER NOT NULL, " +
                                                "id_fut INTEGER NOT NULL, " +
                                                "id_brassin INTEGER NOT NULL)";

    private static String createurTableListeHistorique = "CREATE TABLE IF NOT EXISTS ListeHistorique (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "elementConcerne INTEGER NOT NULL, " +
                                                "texte TEXT)";

    private static String createurTableCalendrier = "CREATE TABLE IF NOT EXISTS Calendrier(" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "dateEvenement INTEGER NOT NULL, " +
                                                "nomEvenement TEXT NOT NULL, " +
                                                "typeObjet INTEGER NOT NULL, " +
                                                "idObjet INTEGER NOT NULL)";


    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createurTableTypeBiere);
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
        db.execSQL(createurTableCalendrier);

        db.execSQL("INSERT INTO EtatFermenteur (texte, historique, couleurTexte, couleurFond, actif) VALUES ('Vide', '', "+Color.BLACK+", "+Color.WHITE+", 1)");
        db.execSQL("INSERT INTO EtatCuve (texte, historique, couleurTexte, couleurFond, actif) VALUES ('Vide', '', "+Color.BLACK+", "+Color.WHITE+", 1)");
        db.execSQL("INSERT INTO EtatFut (texte, historique, couleurTexte, couleurFond, actif) VALUES ('Vide', '', "+Color.BLACK+", "+Color.WHITE+", 1)");

        db.execSQL("INSERT INTO Gestion (delaiLavageAcide, avertissementLavageAcide, delaiInspectionBaril, avertissementInspectionBaril) VALUES(1209600000, 604800000, 1209600000, 604800000)");

        db.execSQL("INSERT INTO Calendrier (dateEvenement, nomEvenement, typeObjet, idObjet) VALUES (1430438400, 'Test Calendrier #1', 1, 1)");
        db.execSQL("INSERT INTO Calendrier (dateEvenement, nomEvenement, typeObjet, idObjet) VALUES (1432512000, 'Test Calendrier #2', 1, 1)");
        db.execSQL("INSERT INTO Calendrier (dateEvenement, nomEvenement, typeObjet, idObjet) VALUES (1431216000, 'Test Calendrier #3', 1, 1)");
        db.execSQL("INSERT INTO Calendrier (dateEvenement, nomEvenement, typeObjet, idObjet) VALUES (1431216000, 'Test Calendrier #3bis', 1, 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}