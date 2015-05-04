package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Emplacement;

public class TableEmplacement extends Controle {

    private ArrayList<Emplacement> emplacements;

    private static TableEmplacement INSTANCE;

    public static TableEmplacement instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableEmplacement(contexte);
        }
        return INSTANCE;
    }

    private TableEmplacement(Context contexte){
        super(contexte, "Emplacement");

        emplacements = new ArrayList<>();
        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            emplacements.add(new Emplacement(tmp.getLong(0), tmp.getString(1), tmp.getInt(2) == 1));
        }
        Collections.sort(emplacements);
    }

    public long ajouter(String texte, boolean actif){
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            emplacements.add(new Emplacement(id, texte, actif));
            Collections.sort(emplacements);
        }
        return id;
    }

    public int tailleListe() {
        return emplacements.size();
    }

    public Emplacement recupererIndex(int index) {
        try {
            return emplacements.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Emplacement recupererId(long id){
        for (int i=0; i<emplacements.size() ; i++) {
            if (emplacements.get(i).getId() == id) {
                return emplacements.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, String texte, boolean actif){
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("actif", actif);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Emplacement emplacement = recupererId(id);
            emplacement.setTexte(texte);
            emplacement.setActif(actif);
            Collections.sort(emplacements);
        }
    }

    public ArrayList<Emplacement> recupererTous() {
        return emplacements;
    }

    public ArrayList<Emplacement> recupererActifs() {
        ArrayList<Emplacement> emplacementsActif = new ArrayList<>();
        for (int i=0; i<emplacements.size(); i++) {
            if (emplacements.get(i).getActif()) {
                emplacementsActif.add(emplacements.get(i));
            }
        }
        return emplacementsActif;
    }

    private ArrayList<Emplacement> trierParId(ArrayList<Emplacement> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Emplacement pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                Emplacement temp = liste.get(i);
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
        if (emplacements.size() > 0) {
            ArrayList<Emplacement> trierParId = trierParId(emplacements, 0, emplacements.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        emplacements.clear();
    }
}
