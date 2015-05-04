package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.ListeHistorique;

public class TableListeHistorique extends Controle {

    private ArrayList<ListeHistorique> listeHistoriques;

    private static TableListeHistorique INSTANCE;

    public static TableListeHistorique instance(Context contexte) {
        if (INSTANCE == null) {
            INSTANCE = new TableListeHistorique(contexte);
        }
        return INSTANCE;
    }

    private TableListeHistorique(Context contexte) {
        super(contexte, "ListeHistorique");

        listeHistoriques = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            listeHistoriques.add(new ListeHistorique(tmp.getLong(0), tmp.getInt(1), tmp.getString(2)));
        }
        Collections.sort(listeHistoriques);
    }

    public long ajouter(int elementConcerne, String texte){
        ContentValues valeur = new ContentValues();
        valeur.put("elementConcerne", elementConcerne);
        valeur.put("texte", texte);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            listeHistoriques.add(new ListeHistorique(id, elementConcerne, texte));
            Collections.sort(listeHistoriques);
        }
        return id;
    }

    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[] {"" + id}) == 1) {
            listeHistoriques.remove(recupererId(id));
        }
    }

    public int tailleListe() {
        return listeHistoriques.size();
    }

    public ListeHistorique recupererIndex(int index){
        try {
            return listeHistoriques.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public ListeHistorique recupererId(long id) {
        for (int i=0; i<listeHistoriques.size() ; i++) {
            if (listeHistoriques.get(i).getId() == id) {
                return listeHistoriques.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, String texte){
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            ListeHistorique listeHistorique = recupererId(id);
            listeHistorique.setTexte(texte);
        }
    }

    public ArrayList<ListeHistorique> listeHistoriqueFermenteur() {
        ArrayList<ListeHistorique> listeHistoriqueFermenteur = new ArrayList<>();
        for (int i=0; i<listeHistoriques.size() ; i++) {
            if (listeHistoriques.get(i).getElementConcerne() == 0) {
                listeHistoriqueFermenteur.add(listeHistoriques.get(i));
            }
        }
        return listeHistoriqueFermenteur;
    }

    public ArrayList<ListeHistorique> listeHistoriqueCuve() {
        ArrayList<ListeHistorique> listeHistoriqueCuve = new ArrayList<>();
        for (int i=0; i<listeHistoriques.size() ; i++) {
            if (listeHistoriques.get(i).getElementConcerne() == 1) {
                listeHistoriqueCuve.add(listeHistoriques.get(i));
            }
        }
        return listeHistoriqueCuve;
    }

    public ArrayList<ListeHistorique> listeHistoriqueFut() {
        ArrayList<ListeHistorique> listeHistoriqueListeHistorique = new ArrayList<>();
        for (int i=0; i<listeHistoriques.size() ; i++) {
            if (listeHistoriques.get(i).getElementConcerne() == 2) {
                listeHistoriqueListeHistorique.add(listeHistoriques.get(i));
            }
        }
        return listeHistoriqueListeHistorique;
    }

    public ArrayList<ListeHistorique> listeHistoriqueBrassin() {
        ArrayList<ListeHistorique> listeHistoriqueBrassin = new ArrayList<>();
        for (int i=0; i<listeHistoriques.size() ; i++) {
            if (listeHistoriques.get(i).getElementConcerne() == 3) {
                listeHistoriqueBrassin.add(listeHistoriques.get(i));
            }
        }
        return listeHistoriqueBrassin;
    }

    private ArrayList<ListeHistorique> trierParId(ArrayList<ListeHistorique> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        ListeHistorique pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                ListeHistorique temp = liste.get(i);
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
        if (listeHistoriques.size() > 0) {
            ArrayList<ListeHistorique> trierParId = trierParId(listeHistoriques, 0, listeHistoriques.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        listeHistoriques.clear();
    }
}
