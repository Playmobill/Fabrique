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

    public Recette recupererIndex(int index){
        return types.get(index);
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
            //if (types.get(i).getActif()) {
                listeEtatActif.add(types.get(i).getNom());
            //}
        }
        return listeEtatActif;
    }

    public String[] numeros() {
        String[] numeroFermenteurs = new String[types.size()];
        for (int i=0; i<types.size() ; i++) {
            numeroFermenteurs[i] = types.get(i).getNom() + "";
        }
        return numeroFermenteurs;
    }
}
