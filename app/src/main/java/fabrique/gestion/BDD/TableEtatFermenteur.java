package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;

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
            etats.add(new EtatFermenteur(tmp.getInt(0), tmp.getString(1), tmp.getInt(2), tmp.getInt(3), tmp.getInt(4) == 1));
        }

        ajouter("Vide", Color.RED, Color.GREEN, false);
        ajouter("Fermentation", Color.WHITE, Color.BLACK, false);
        ajouter("Lavé", Color.BLACK, Color.WHITE, false);
    }

    public void ajouter(String texte, int couleurTexte, int couleurFond, boolean actif) {
        int intActif = 0;
        if (actif) {
            intActif = 1;
        }
        accesBDD.execSQL("INSERT INTO EtatFermenteur (texte, couleurTexte, couleurFond, actif) VALUES ('" + texte + "', " + couleurTexte + ", " + couleurFond +", " + intActif + ")");
        etats.add(new EtatFermenteur(etats.size(), texte, couleurTexte, couleurFond, actif));
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
            etats2[i] = etat(i);
        }
        return etats2;
    }
}
