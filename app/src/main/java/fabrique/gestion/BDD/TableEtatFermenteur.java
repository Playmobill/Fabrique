package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

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
            etats.add(new EtatFermenteur(tmp.getLong(0), tmp.getString(1), tmp.getString(2), tmp.getInt(3), tmp.getInt(4), tmp.getInt(8) == 1));
        }
        Collections.sort(etats);
    }

    public long ajouter(String texte, String historique, int couleurTexte, int couleurFond, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("historique", historique);
        valeur.put("couleurTexte", couleurTexte);
        valeur.put("couleurFond", couleurFond);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            etats.add(new EtatFermenteur(id, texte, historique, couleurTexte, couleurFond, actif));
            Collections.sort(etats);
        }
        return id;
    }

    public int tailleListe() {
        return etats.size();
    }

    public EtatFermenteur recupererIndex(int index) {
        try {
            return etats.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public EtatFermenteur recupererId(long id){
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

    public ArrayList<EtatFermenteur> recupererListeEtatActifs() {
        ArrayList<EtatFermenteur> listeEtatActif = new ArrayList<>();
        for (int i=0; i<etats.size(); i++) {
            if (etats.get(i).getActif()) {
                listeEtatActif.add(etats.get(i));
            }
        }
        return listeEtatActif;
    }

    public void modifier(long id, String texte, String historique, int couleurTexte, int couleurFond, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("historique", historique);
        valeur.put("couleurTexte", couleurTexte);
        valeur.put("couleurFond", couleurFond);
        valeur.put("actif", actif);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            EtatFermenteur etat = recupererId(id);
            etat.setTexte(texte);
            etat.setHistorique(historique);
            etat.setCouleurTexte(couleurTexte);
            etat.setCouleurFond(couleurFond);
            etat.setActif(actif);
            Collections.sort(etats);
        }
    }

    private ArrayList<EtatFermenteur> trierParId(ArrayList<EtatFermenteur> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        EtatFermenteur pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                EtatFermenteur temp = liste.get(i);
                liste.set(i, liste.get(j));
                liste.set(j, temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call recursively
        if (petitIndex < j) {
            liste = trierParId(liste, petitIndex, j);
        }
        if (i < grandIndex) {
            liste = trierParId(liste, i, grandIndex);
        }
        return liste;
    }

    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        if (etats.size() > 0) {
            ArrayList<EtatFermenteur> trierParId = trierParId(etats, 0, etats.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        etats.clear();
    }
}
