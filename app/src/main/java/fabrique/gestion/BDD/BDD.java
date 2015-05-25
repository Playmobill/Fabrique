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
                                                "avecBrassin INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";


    private static String createurTableEtatCuve = "CREATE TABLE IF NOT EXISTS EtatCuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "historique TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "avecBrassin INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableEtatFut = "CREATE TABLE IF NOT EXISTS EtatFut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "texte TEXT, " +
                                                "historique TEXT, " +
                                                "couleurTexte INTEGER NOT NULL," +
                                                "couleurFond INTEGER NOT NULL," +
                                                "avecBrassin INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableBrassinPere = "CREATE TABLE IF NOT EXISTS BrassinPere (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER NOT NULL," +
                                                "commentaire TEXT," +
                                                "dateCreation INTEGER NOT NULL," +
                                                "quantite INTEGER NOT NULL," +
                                                "id_recette INTEGER NOT NULL," +
                                                "densiteOriginale REAL," +
                                                "densiteFinale REAL)";

    private static String createurTableBrassin = "CREATE TABLE IF NOT EXISTS Brassin (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "id_brassinPere INTEGER NOT NULL," +
                                                "quantite INTEGER NOT NULL)";

    private static String createurTableFermenteur = "CREATE TABLE IF NOT EXISTS Fermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                                                "numero INTEGER NOT NULL," +
                                                "capacite INTEGER NOT NULL," +
                                                "id_emplacement INTEGER NOT NULL," +
                                                "dateLavageAcide INTEGER NOT NULL," +
                                                "id_noeudFermenteur INTEGER NOT NULL," +
                                                "dateEtat INTEGER NOT NULL," +
                                                "id_brassin INTEGER," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableCuve = "CREATE TABLE IF NOT EXISTS Cuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "numero INTEGER NOT NULL, " +
                                                "capacite INTEGER NOT NULL, " +
                                                "id_emplacement INTEGER NOT NULL, " +
                                                "dateLavageAcide INTEGER NOT NULL, " +
                                                "id_noeudCuve INTEGER NOT NULL, " +
                                                "dateEtat INTEGER NOT NULL, " +
                                                "commentaireEtat TEXT, " +
                                                "id_brassin INTEGER," +
                                                "actif INTEGER NOT NULL)";

    private static String createurTableFut = "CREATE TABLE IF NOT EXISTS Fut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "numero INTEGER NOT NULL, " +
                                                "capacite INTEGER NOT NULL, " +
                                                "id_noeudFut INTEGER NOT NULL, " +
                                                "dateEtat INTEGER NOT NULL, " +
                                                "id_brassin INTEGER, " +
                                                "dateInspection INTEGER NOT NULL," +
                                                "actif INTEGER NOT NULL)";

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
                                                "texte TEXT," +
                                                "supprimable INTEGER NOT NULL)";

    private static String createurTableCalendrier = "CREATE TABLE IF NOT EXISTS Calendrier (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "dateEvenement INTEGER NOT NULL, " +
                                                "nomEvenement TEXT NOT NULL, " +
                                                "typeObjet INTEGER NOT NULL, " +
                                                "idObjet INTEGER NOT NULL)";

    private static String createurCheminBrassinFermenteur = "CREATE TABLE IF NOT EXISTS CheminBrassinFermenteur (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "id_noeudPrecedent INTEGER NOT NULL, " +
                                                "id_etat INTEGER NOT NULL, " +
                                                "id_noeudAvecBrassin INTEGER, " +
                                                "id_noeudSansBrassin INTEGER)";

    private static String createurCheminBrassinCuve = "CREATE TABLE IF NOT EXISTS CheminBrassinCuve (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "id_noeudPrecedent INTEGER NOT NULL, " +
                                                "id_etat INTEGER NOT NULL, " +
                                                "id_noeudAvecBrassin INTEGER, " +
                                                "id_noeudSansBrassin INTEGER)";

    private static String createurCheminBrassinFut = "CREATE TABLE IF NOT EXISTS CheminBrassinFut (" +
                                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                                "id_noeudPrecedent INTEGER NOT NULL, " +
                                                "id_etat INTEGER NOT NULL, " +
                                                "id_noeudAvecBrassin INTEGER, " +
                                                "id_noeudSansBrassin INTEGER)";

    public BDD(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createurTableTypeBiere);
        db.execSQL(createurTableRecette);
        db.execSQL(createurTableBrassinPere);
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
        db.execSQL(createurCheminBrassinFermenteur);
        db.execSQL(createurCheminBrassinCuve);
        db.execSQL(createurCheminBrassinFut);

        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('RdC', 1)");
        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('Sous-sol', 1)");
        db.execSQL("INSERT INTO Emplacement (texte, actif) VALUES ('Ch. Froide', 1)");

        db.execSQL("INSERT INTO EtatFermenteur (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Vide', '', "+Color.BLACK+", "+Color.WHITE+", 0, 1)");
        db.execSQL("INSERT INTO EtatFermenteur (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Fermentation', '', "+Color.BLACK+", "+Color.WHITE+", 1, 1)");
        db.execSQL("INSERT INTO EtatFermenteur (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Lavé', '', "+Color.BLACK+", "+Color.WHITE+", 0, 1)");

        db.execSQL("INSERT INTO EtatCuve (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Vide', '', "+Color.BLACK+", "+Color.WHITE+", 0, 1)");
        db.execSQL("INSERT INTO EtatCuve (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Service', '', "+Color.BLACK+", "+Color.WHITE+", 1, 1)");
        db.execSQL("INSERT INTO EtatCuve (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Gazéification', '', "+Color.BLACK+", "+Color.WHITE+", 1, 1)");
        db.execSQL("INSERT INTO EtatCuve (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Lavé', '', "+Color.BLACK+", "+Color.WHITE+", 0, 1)");

        db.execSQL("INSERT INTO EtatFut (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Vide', '', "+Color.BLACK+", "+Color.WHITE+", 0, 1)");
        db.execSQL("INSERT INTO EtatFut (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Plein', '', "+Color.BLACK+", "+Color.WHITE+", 1, 1)");
        db.execSQL("INSERT INTO EtatFut (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Lavé', '', "+Color.BLACK+", "+Color.WHITE+", 0, 1)");
        db.execSQL("INSERT INTO EtatFut (texte, historique, couleurTexte, couleurFond, avecBrassin, actif) VALUES ('Défectueux', '', "+Color.BLACK+", "+Color.WHITE+", 0, 1)");

        db.execSQL("INSERT INTO Gestion (delaiLavageAcide, avertissementLavageAcide, delaiInspectionBaril, avertissementInspectionBaril) VALUES(1209600000, 604800000, 1209600000, 604800000)");

        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Ale Blonde', 'Blonde', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Blanche Belge', 'Blanche', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Mild', 'Rousse', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Stout à l avoine', 'Noire', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Pale Ale Américaine', 'Blonde', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Ale Ambrée Américaine', 'Rousse', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Stout Américaine', 'Noire', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Bière de Noël', 'Rousse', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Pilsener Bohémienne', 'Blonde', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Indian Pale Ale', 'Rousse', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Porter', 'Noire', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Stout Impérial', 'Noire', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Double IPA', 'Rousse', 1)");
        db.execSQL("INSERT INTO TypeBiere (nom, couleur, actif) VALUES ('Foreign Extra Stout', 'Noire', 1)");

        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('République', 'Rep', 1, "+Color.argb(255,255,255,255)+", "+Color.argb(255,130,120,80)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Rivière Blanche', 'Riv', 2, "+Color.argb(255,0,0,0)+", "+Color.argb(255,215,210,190)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Goupil', 'Gou', 3, "+Color.argb(255,255,255,255)+", "+Color.argb(255,115,65,45)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Caltor', 'Cal', 4, "+Color.argb(255,255,255,255)+", "+Color.argb(255,30,30,30)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Franquelin', 'Fra', 5, "+Color.argb(255,255,255,255)+", "+Color.argb(255,130,120,80)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Salamagone', 'Sal', 6, "+Color.argb(255,255,255,255)+", "+Color.argb(255,115,65,45)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Métisse', 'Met', 7, "+Color.argb(255,255,255,255)+", "+Color.argb(255,30,30,30)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Pain d épices', 'Pai', 8, "+Color.argb(255,255,255,255)+", "+Color.argb(255,115,65,45)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Matane Pilsen', 'Rep', 9, "+Color.argb(255,255,255,255)+", "+Color.argb(255,130,120,80)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Gros Chars', 'Gro', 10, "+Color.argb(255,255,255,255)+", "+Color.argb(255,115,65,45)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Misère noire', 'Mis', 11, "+Color.argb(255,255,255,255)+", "+Color.argb(255,30,30,30)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Kaliningrad', 'Kal', 12, "+Color.argb(255,255,255,255)+", "+Color.argb(255,30,30,30)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Dame de Pique', 'Dam', 13, "+Color.argb(255,255,255,255)+", "+Color.argb(255,115,65,45)+", 1)");
        db.execSQL("INSERT INTO Recette (nom, acronyme, id_typeBiere, couleurTexte, couleurFond, actif) VALUES ('Duplessis', 'Dup', 14, "+Color.argb(255,255,255,255)+", "+Color.argb(255,30,30,30)+", 1)");

        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Relevé de densité', 0)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Transfert', 0)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Gazéification', 0)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Enfûtage', 0)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (2 , 'Inspection de Baril', 0)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (0 , 'Lavage acide', 0)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (1 , 'Lavage acide', 0)");
        //<- auto ; -> manuel
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Relevé de température', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Ajustement contrôleur', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Crash', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Drainage de levure', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Récupération de levure', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Filtration', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Ajustement Gazéification', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (3 , 'Mesure CO2 dissout', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (0 , 'Rinçage', 1)");
        db.execSQL("INSERT INTO ListeHistorique (elementConcerne, texte, supprimable) VALUES (1 , 'Rinçage', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}