package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.NoeudFermenteur;

public class TableCheminBrassinFermenteur extends Controle  {

    private ArrayList<NoeudFermenteur> noeuds;

    private static TableCheminBrassinFermenteur INSTANCE;

    public static TableCheminBrassinFermenteur instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableCheminBrassinFermenteur(contexte);
        }
        return INSTANCE;
    }

    private TableCheminBrassinFermenteur(Context contexte){
        super(contexte, "CheminBrassinFermenteur");
        noeuds = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            noeuds.add(new NoeudFermenteur(tmp.getLong(0), tmp.getLong(1), tmp.getLong(2), tmp.getLong(3), tmp.getLong(4)));
        }
        Collections.sort(noeuds);
    }

    public long ajouter(long id_noeudPrecedent, long id_etat, long id_noeudAvecBrassin, long id_noeudSansBrassin) {
        ContentValues valeur = new ContentValues();
        valeur.put("id_noeudPrecedent", id_noeudPrecedent);
        valeur.put("id_etat", id_etat);
        valeur.put("id_noeudAvecBrassin", id_noeudAvecBrassin);
        valeur.put("id_noeudSansBrassin", id_noeudSansBrassin);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            noeuds.add(new NoeudFermenteur(id, id_noeudPrecedent, id_etat, id_noeudAvecBrassin, id_noeudSansBrassin));
            Collections.sort(noeuds);
        }
        return id;
    }

    public void modifier(long id, long id_noeudAvecBrassin, long id_noeudSansBrassin) {
        ContentValues valeur = new ContentValues();
        valeur.put("id_noeudAvecBrassin", id_noeudAvecBrassin);
        valeur.put("id_noeudSansBrassin", id_noeudSansBrassin);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            NoeudFermenteur noeud = recupererId(id);
            noeud.setId_noeudAvecBrassin(id_noeudAvecBrassin);
            noeud.setId_noeudSansBrassin(id_noeudSansBrassin);
        }
    }

    public int tailleListe() {
        return noeuds.size();
    }

    public NoeudFermenteur recupererIndex(int index){
        try {
            return noeuds.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public NoeudFermenteur recupererId(long id) {
        for (int i=0; i< noeuds.size() ; i++) {
            if (noeuds.get(i).getId() == id) {
                return noeuds.get(i);
            }
        }
        return null;
    }

    public NoeudFermenteur recupererPremierNoeud() {
        for (int i=0; i<noeuds.size(); i++) {
            if (noeuds.get(i).getId_noeudPrecedent() == -1) {
                return noeuds.get(i);
            }
        }
        return null;
    }

    public void supprimer(long id) {
        NoeudFermenteur noeud = recupererId(id);
        if ((noeud.getId_noeudAvecBrassin() == -1) && (noeud.getId_noeudSansBrassin() == -1) && (accesBDD.delete(nomTable, "id = ?", new String[] {""+id}) == 1)) {
            noeuds.remove(noeud);
            NoeudFermenteur noeudPrecedent = recupererId(noeud.getId_noeudPrecedent());
            if (noeudPrecedent != null) {
                if (noeudPrecedent.getId_noeudAvecBrassin() == id) {
                    modifier(noeudPrecedent.getId(), -1, noeudPrecedent.getId_noeudSansBrassin());
                } else if (noeudPrecedent.getId_noeudSansBrassin() == id) {
                    modifier(noeudPrecedent.getId(), noeudPrecedent.getId_noeudAvecBrassin(), -1);
                }
            }
        }
    }

    private ArrayList<NoeudFermenteur> trierParId(ArrayList<NoeudFermenteur> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        NoeudFermenteur pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                NoeudFermenteur temp = liste.get(i);
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
        if (noeuds.size() > 0) {
            ArrayList<NoeudFermenteur> trierParId = trierParId(noeuds, 0, noeuds.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        noeuds.clear();
    }
}
