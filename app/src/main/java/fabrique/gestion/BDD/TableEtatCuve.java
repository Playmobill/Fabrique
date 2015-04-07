package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.EtatCuve;

public class TableEtatCuve extends Controle{

    private ArrayList<EtatCuve> etats;

    private static TableEtatCuve INSTANCE;

    public static TableEtatCuve instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableEtatCuve(contexte);
        }
        return INSTANCE;
    }

    private TableEtatCuve(Context contexte){
        super(contexte, "EtatCuve");
        etats = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            etats.add(new EtatCuve(tmp.getInt(0), tmp.getString(1), tmp.getInt(2), tmp.getInt(3), tmp.getInt(4) == 1));
        }
    }

    public EtatCuve ajouter(String texte, int couleurTexte, int couleurFond, boolean actif) {
        int intActif = 0;
        if (actif) {
            intActif = 1;
        }
        accesBDD.execSQL("INSERT INTO EtatCuve (texte, couleurTexte, couleurFond, actif) VALUES ('" + texte + "', " + couleurTexte + ", " + couleurFond +", " + intActif + ")");
        EtatCuve etat = new EtatCuve(etats.size(), texte, couleurTexte, couleurFond, actif);
        etats.add(etat);
        return etat;
    }

    public EtatCuve recuperer(int index){
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

    public void modifier(EtatCuve etat) {
        int actif = 0;
        if (etat.getActif()) {
            actif = 1;
        }
        accesBDD.execSQL("UPDATE EtatCuve SET " +
                            "texte = '"+ etat.getTexte() +"', " +
                            "couleurTexte = " + etat.getCouleurTexte() + ", " +
                            "couleurFond = " + etat.getCouleurFond() + ", " +
                            "actif = "+ actif + " " +
                            "WHERE id = " + etat.getId());
    }
}
