package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

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

    public ArrayList<String> etatsActifs() {
        ArrayList<String> listeEtatActif = new ArrayList<>();
        for (int i=0; i<etats.size(); i++) {
            if (etats.get(i).getActif()) {
                listeEtatActif.add(etats.get(i).getTexte());
            }
        }
        return listeEtatActif;
    }

    public void modifier(EtatFermenteur etat) {
        int actif = 0;
        if (etat.getActif()) {
            actif = 1;
        }
        accesBDD.execSQL("UPDATE EtatFermenteur SET " +
                            "texte = '"+ etat.getTexte() +"', " +
                            "couleurTexte = " + etat.getCouleurTexte() + ", " +
                            "couleurFond = " + etat.getCouleurFond() + ", " +
                            "actif = "+ actif + " " +
                            "WHERE id = " + etat.getId());
    }
}
