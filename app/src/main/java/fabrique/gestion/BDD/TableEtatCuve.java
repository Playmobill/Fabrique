package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.EtatCuve;

public class TableEtatCuve extends Controle {

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
            etats.add(new EtatCuve(tmp.getLong(0), tmp.getString(1), tmp.getInt(2), tmp.getInt(3), tmp.getInt(4) == 1));
        }
        Collections.sort(etats);
    }

    public long ajouter(String texte, int couleurTexte, int couleurFond, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("couleurTexte", couleurTexte);
        valeur.put("couleurFond", couleurFond);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            etats.add(new EtatCuve(id, texte, couleurTexte, couleurFond, actif));
            Collections.sort(etats);
        }
        return id;
    }

    public int tailleListe() {
        return etats.size();
    }

    public EtatCuve recupererIndex(int index){
        return etats.get(index);
    }

    public EtatCuve recupererId(long id){
        for (int i=0; i<etats.size() ; i++) {
            if (etats.get(i).getId() == id) {
                return etats.get(i);
            }
        }
        return null;
    }

    public ArrayList<String> recupererEtatsActifs() {
        ArrayList<String> listeEtatActif = new ArrayList<>();
        for (int i=0; i<etats.size(); i++) {
            if (etats.get(i).getActif()) {
                listeEtatActif.add(etats.get(i).getTexte());
            }
        }
        return listeEtatActif;
    }

    public void modifier(long id, String texte, int couleurTexte, int couleurFond, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("couleurTexte", couleurTexte);
        valeur.put("couleurFond", couleurFond);
        valeur.put("actif", actif);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            EtatCuve etat = recupererId(id);
            etat.setTexte(texte);
            etat.setCouleurTexte(couleurTexte);
            etat.setCouleurFond(couleurFond);
            etat.setActif(actif);
            Collections.sort(etats);
        }
    }
}
