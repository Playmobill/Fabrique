package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import fabrique.gestion.Objets.EtatFermenteur;

public class TableEtatFermenteur extends Controle {

    private ArrayList<EtatFermenteur> etats;

    private static TableEtatFermenteur INSTANCE;

    public static TableEtatFermenteur instance(Context ctxt){
        if(INSTANCE == null){
            INSTANCE = new TableEtatFermenteur(ctxt);
        }
        return INSTANCE;
    }

    private TableEtatFermenteur(Context contexte){
        super(contexte, "EtatFermenteur");

        etats = new ArrayList<>();
        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            etats.add(new EtatFermenteur(tmp.getInt(0), tmp.getString(1)));
            Log.i("TableEtatFermenteur", "BDD " + tmp.getString(1));
        }

        ajouter("Vide");
        ajouter("Fermentation");
        ajouter("Lavé");
    }

    public void ajouter(String texte){
        accesBDD.execSQL("INSERT INTO EtatFermenteur (texte) VALUES ('"+texte+"')");
        etats.add(new EtatFermenteur(etats.size(), texte));
    }

    public EtatFermenteur recuperer(int index){
        return etats.get(index);
    }

    public void supprimer(int index){
        etats.remove(index);
    }

    public int tailleListe() {
        return etats.size();
    }

    public String etat(int index){
        return etats.get(index).getTexte();
    }

    public String[] etats () {
        String[] etats2 = new String[etats.size()];
        for (int i=0; i<etats.size(); i++) {
            Log.i("TableEtatFermenteur", "Tableau " + etat(i));
            etats2[i] = etat(i);
        }
        return etats2;
    }
}
