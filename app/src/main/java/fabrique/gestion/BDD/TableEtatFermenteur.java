package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.EtatFermenteur;

public class TableEtatFermenteur extends Controle {

    private ArrayList<EtatFermenteur> etats;

    private static TableEtatFermenteur INSTANCE;

    public static TableEtatFermenteur instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableEtatFermenteur(contexte);
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

    public EtatFermenteur ajouter(String texte, int couleurTexte, int couleurFond, boolean actif) {
        int intActif = 0;
        if (actif) {
            intActif = 1;
        }
        accesBDD.execSQL("INSERT INTO EtatFermenteur (texte, couleurTexte, couleurFond, actif) VALUES ('" + texte + "', " + couleurTexte + ", " + couleurFond +", " + intActif + ")");
        EtatFermenteur etat = new EtatFermenteur(etats.size(), texte, couleurTexte, couleurFond, actif);
        etats.add(etat);
        return etat;
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

    public String etat(int id) {
        for (int i=0; i<etats.size() ; i++) {
            if (etats.get(i).getId() == id) {
                return etats.get(i).getTexte();
            }
        }
        return etats.get(0).getTexte();
    }

    public int couleurTexteEtat(int id){
        for (int i=0; i<etats.size() ; i++) {
            if (etats.get(i).getId() == id) {
                return etats.get(i).getCouleurTexte();
            }
        }
        return etats.get(0).getCouleurTexte();
    }

    public int couleurFondEtat(int id){
        for (int i=0; i<etats.size() ; i++) {
            if (etats.get(i).getId() == id) {
                return etats.get(i).getCouleurFond();
            }
        }
        return etats.get(0).getCouleurFond();
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
