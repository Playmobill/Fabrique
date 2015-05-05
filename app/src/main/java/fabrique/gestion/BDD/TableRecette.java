package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Recette;

public class TableRecette extends Controle {

    private ArrayList<Recette> recettes;

    private static TableRecette INSTANCE;

    public static TableRecette instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableRecette(contexte);
        }
        return INSTANCE;
    }

    private TableRecette(Context contexte){
        super(contexte, "Recette");
        recettes = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            recettes.add(new Recette(tmp.getLong(0), tmp.getString(1), tmp.getString(2), tmp.getLong(3), tmp.getInt(4), tmp.getInt(5), tmp.getInt(6) == 1));
        }
        Collections.sort(recettes);
    }

    public long ajouter(String nom, String acronyme, long id_biere, int couleurTexte, int couleurFond, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("nom", nom);
        valeur.put("acronyme", acronyme);
        valeur.put("id_typeBiere", id_biere);
        valeur.put("couleurTexte", couleurTexte);
        valeur.put("couleurFond", couleurFond);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            recettes.add(new Recette(id, nom, acronyme, id_biere, couleurTexte, couleurFond, actif));
            Collections.sort(recettes);
        }
        return id;
    }

    public int tailleListe() {
        return recettes.size();
    }

    public void modifier(long id, String nom, String acronyme, long id_biere, int couleurTexte, int couleurFond, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("nom", nom);
        valeur.put("acronyme", acronyme);
        valeur.put("id_typeBiere", id_biere);
        valeur.put("couleurTexte", couleurTexte);
        valeur.put("couleurFond", couleurFond);
        valeur.put("actif", actif);
        if(accesBDD.update("Recette", valeur, "id = ?", new String[] {""+id}) == 1) {
            Recette recette = recupererId(id);
            recette.setNom(nom);
            recette.setAcronyme(acronyme);
            recette.setId_biere(id_biere);
            recette.setCouleurTexte(couleurTexte);
            recette.setCouleurFond(couleurFond);
            recette.setActif(actif);
            Collections.sort(recettes);
        }
    }

    public Recette recupererIndex(int index){
        try {
            return recettes.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Recette recupererId(long id) {
        for (int i=0; i< recettes.size() ; i++) {
            if (recettes.get(i).getId() == id) {
                return recettes.get(i);
            }
        }
        return null;
    }

    public ArrayList<String> recupererNomRecettesActifs() {
        ArrayList<String> listeEtatActif = new ArrayList<>();
        for (int i=0; i< recettes.size(); i++) {
            if (recettes.get(i).getActif()) {
                listeEtatActif.add(recettes.get(i).getNom());
            }
        }
        return listeEtatActif;
    }

    public ArrayList<Recette> recupererRecetteActif() {
        ArrayList<Recette> listeRecetteActifs = new ArrayList<>();
        for (int i=0; i< recettes.size(); i++) {
            if (recettes.get(i).getActif()) {
                listeRecetteActifs.add(recettes.get(i));
            }
        }
        return listeRecetteActifs;
    }

    private ArrayList<Recette> trierParId(ArrayList<Recette> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Recette pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                Recette temp = liste.get(i);
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
        if (recettes.size() > 0) {
            ArrayList<Recette> trierParId = trierParId(recettes, 0, recettes.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        recettes.clear();
    }
}
