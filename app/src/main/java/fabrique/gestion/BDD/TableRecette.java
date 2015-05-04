package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Recette;

public class TableRecette extends Controle {

    private ArrayList<Recette> types;

    private static TableRecette INSTANCE;

    public static TableRecette instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableRecette(contexte);
        }
        return INSTANCE;
    }

    private TableRecette(Context contexte){
        super(contexte, "Recette");
        types = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            types.add(new Recette(tmp.getLong(0), tmp.getString(1), tmp.getString(2), tmp.getString(3), tmp.getInt(4) == 1));
        }
        Collections.sort(types);
    }

    public long ajouter(String nom, String couleur, String acronyme, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("nom", nom);
        valeur.put("couleur", couleur);
        valeur.put("acronyme", acronyme);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            types.add(new Recette(id, nom, couleur, acronyme, actif));
            Collections.sort(types);
        }
        return id;
    }

    public int tailleListe() {
        return types.size();
    }

    public void modifier(Context contexte, int id, String nom, String couleur, String acronyme) {
        ContentValues valeur = new ContentValues();
        valeur.put("nom", nom);
        valeur.put("couleur", couleur);
        valeur.put("acronyme", acronyme);
        if(accesBDD.update("Recette", valeur, "id = ?", new String[] {""+id}) == 1){
            TableRecette.instance(contexte).recupererId(id).setAcronyme(acronyme);
            TableRecette.instance(contexte).recupererId(id).setNom(nom);
            TableRecette.instance(contexte).recupererId(id).setCouleur(couleur);
            Collections.sort(types);
        }
    }

    public Recette recupererIndex(int index){
        try {
            return types.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Recette recupererId(long id) {
        for (int i=0; i<types.size() ; i++) {
            if (types.get(i).getId() == id) {
                return types.get(i);
            }
        }
        return null;
    }

    public ArrayList<String> recupererRecettesActifs() {
        ArrayList<String> listeEtatActif = new ArrayList<>();
        for (int i=0; i<types.size(); i++) {
            if (types.get(i).getActif()) {
                listeEtatActif.add(types.get(i).getNom());
            }
        }
        return listeEtatActif;
    }

    public ArrayList<Recette> recupererRecetteActif() {
        ArrayList<Recette> listeRecetteActifs = new ArrayList<>();
        for (int i=0; i<types.size(); i++) {
            if (types.get(i).getActif()) {
                listeRecetteActifs.add(types.get(i));
            }
        }
        return listeRecetteActifs;
    }

    public String[] noms() {
        String[] numeroFermenteurs = new String[types.size()];
        for (int i=0; i<types.size() ; i++) {
            numeroFermenteurs[i] = types.get(i).getNom() + "";
        }
        return numeroFermenteurs;
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
        if (types.size() > 0) {
            ArrayList<Recette> trierParId = trierParId(types, 0, types.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        types.clear();
    }
}
