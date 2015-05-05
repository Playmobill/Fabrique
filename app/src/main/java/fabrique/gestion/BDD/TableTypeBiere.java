package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.TypeBiere;

public class TableTypeBiere extends Controle {

    private ArrayList<TypeBiere> types;

    private static TableTypeBiere INSTANCE;

    public static TableTypeBiere instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableTypeBiere(contexte);
        }
        return INSTANCE;
    }

    private TableTypeBiere(Context contexte){
        super(contexte, "TypeBiere");
        types = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            types.add(new TypeBiere(tmp.getLong(0), tmp.getString(1), tmp.getString(2), tmp.getInt(3) == 1));
        }
        Collections.sort(types);
    }

    public long ajouter(String nom, String couleur, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("nom", nom);
        valeur.put("couleur", couleur);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            types.add(new TypeBiere(id, nom, couleur, actif));
            Collections.sort(types);
        }
        return id;
    }

    public int tailleListe() {
        return types.size();
    }

    public void modifier(long id, String nom, String couleur, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("nom", nom);
        valeur.put("couleur", couleur);
        valeur.put("actif", actif);
        if(accesBDD.update("TypeBiere", valeur, "id = ?", new String[] {""+id}) == 1) {
            TypeBiere recette = recupererId(id);
            recette.setNom(nom);
            recette.setCouleur(couleur);
            recette.setActif(actif);
            Collections.sort(types);
        }
    }

    public TypeBiere recupererIndex(int index){
        try {
            return types.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public TypeBiere recupererId(long id) {
        for (int i=0; i< types.size() ; i++) {
            if (types.get(i).getId() == id) {
                return types.get(i);
            }
        }
        return null;
    }

    public ArrayList<String> recupererNomTypeBieresActifs() {
        ArrayList<String> listeEtatActif = new ArrayList<>();
        for (int i=0; i< types.size(); i++) {
            if (types.get(i).getActif()) {
                listeEtatActif.add(types.get(i).getNom());
            }
        }
        return listeEtatActif;
    }

    public ArrayList<TypeBiere> recupererTypeBiereActif() {
        ArrayList<TypeBiere> listeTypeBiereActifs = new ArrayList<>();
        for (int i=0; i< types.size(); i++) {
            if (types.get(i).getActif()) {
                listeTypeBiereActifs.add(types.get(i));
            }
        }
        return listeTypeBiereActifs;
    }

    private ArrayList<TypeBiere> trierParId(ArrayList<TypeBiere> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        TypeBiere pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                TypeBiere temp = liste.get(i);
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
            ArrayList<TypeBiere> trierParId = trierParId(types, 0, types.size() - 1);
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
