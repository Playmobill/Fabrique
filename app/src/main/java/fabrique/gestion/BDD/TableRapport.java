package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Rapport;

public class TableRapport extends Controle {

    private ArrayList<Rapport> rapports;

    private static TableRapport INSTANCE;

    public static TableRapport instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableRapport(contexte);
        }
        return INSTANCE;
    }

    private TableRapport(Context contexte) {
        super(contexte, "Rapport");

        rapports = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            rapports.add(new Rapport(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getInt(4), tmp.getInt(5)));
        }
        Collections.sort(rapports);
    }

    public long ajouter(int mois, int annee, int quantiteFermente, int quantiteTransfere, int quantiteUtilise) {
        ContentValues valeur = new ContentValues();
        valeur.put("mois", mois);
        valeur.put("annee", annee);
        valeur.put("quantiteFermente", quantiteFermente);
        valeur.put("quantiteTransfere", quantiteTransfere);
        valeur.put("quantiteUtilise", quantiteUtilise);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            rapports.add(new Rapport(id, mois, annee, quantiteFermente, quantiteTransfere, quantiteUtilise));
            Collections.sort(rapports);
        }
        return id;
    }

    public int tailleListe() {
        return rapports.size();
    }

    public Rapport recupererIndex(int index) {
        try {
            return rapports.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Rapport recupererId(long id){
        for (int i=0; i<rapports.size() ; i++) {
            if (rapports.get(i).getId() == id) {
                return rapports.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, int mois, int annee, int quantiteFermente, int quantiteTransfere, int quantiteUtilise){
        ContentValues valeur = new ContentValues();
        valeur.put("mois", mois);
        valeur.put("annee", annee);
        valeur.put("quantiteFermente", quantiteFermente);
        valeur.put("quantiteTransfere", quantiteTransfere);
        valeur.put("quantiteUtilise", quantiteUtilise);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Rapport rapport = recupererId(id);
            rapport.setMois(mois);
            rapport.setAnnee(annee);
            rapport.setQuantiteFermente(quantiteFermente);
            rapport.setQuantiteTransfere(quantiteTransfere);
            rapport.setQuantiteTransfere(quantiteTransfere);
            rapport.setQuantiteUtilise(quantiteUtilise);
            Collections.sort(rapports);
        }
    }

    private ArrayList<Rapport> trierParId(ArrayList<Rapport> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Rapport pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                Rapport temp = liste.get(i);
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
        if (rapports.size() > 0) {
            ArrayList<Rapport> trierParId = trierParId(rapports, 0, rapports.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        rapports.clear();
    }
}
