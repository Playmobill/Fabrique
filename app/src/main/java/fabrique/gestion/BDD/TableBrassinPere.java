package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.BrassinPere;

public class TableBrassinPere extends Controle {

    private ArrayList<BrassinPere> brassins;

    private static TableBrassinPere INSTANCE;

    public static TableBrassinPere instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableBrassinPere(contexte);
        }
        return INSTANCE;
    }

    private TableBrassinPere(Context contexte){
        super(contexte, "BrassinPere");

        brassins = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            brassins.add(new BrassinPere(tmp.getLong(0), tmp.getInt(1), tmp.getString(2), tmp.getLong(3), tmp.getInt(4), tmp.getLong(5), tmp.getFloat(6), tmp.getFloat(7)));
        }
        Collections.sort(brassins);
    }

    public long ajouter(int numero, String commentaire, long dateCreation, int quantite, long id_recette, float densiteOriginale, float densiteFinale){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("commentaire", commentaire);
        valeur.put("dateCreation", dateCreation);
        valeur.put("quantite", quantite);
        valeur.put("id_recette", id_recette);
        valeur.put("densiteOriginale", densiteOriginale);
        valeur.put("densiteFinale", densiteFinale);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            brassins.add(new BrassinPere(id, numero, commentaire, dateCreation, quantite, id_recette, densiteOriginale, densiteFinale));
            Collections.sort(brassins);
        }
        return id;
    }

    public int tailleListe() {
        return brassins.size();
    }

    public BrassinPere recupererIndex(int index){
        try {
            return brassins.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public BrassinPere recupererId(long id) {
        for (int i=0; i<brassins.size() ; i++) {
            if (brassins.get(i).getId() == id) {
                return brassins.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, int numero, String commentaire, long dateCreation, int quantite, long id_recette, float densiteOriginale, float densiteFinale){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("commentaire", commentaire);
        valeur.put("dateCreation", dateCreation);
        valeur.put("quantite", quantite);
        valeur.put("id_recette", id_recette);
        valeur.put("densiteOriginale", densiteOriginale);
        valeur.put("densiteFinale", densiteFinale);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            BrassinPere brassin = recupererId(id);
            brassin.setNumero(numero);
            brassin.setCommentaire(commentaire);
            brassin.setDateCreation(dateCreation);
            brassin.setQuantite(quantite);
            brassin.setId_recette(id_recette);
            brassin.setDensiteOriginale(densiteOriginale);
            brassin.setDensiteFinale(densiteFinale);
            Collections.sort(brassins);
        }
    }

    public int recupererIndexSelonId(long id) {
        return brassins.indexOf(recupererId(id));
    }

    public String[] numeros() {
        String[] numero = new String[brassins.size()];
        for (int i=0; i<brassins.size() ; i++) {
            numero[i] = brassins.get(i).getNumero() + "";
        }
        return numero;
    }

    public BrassinPere recupererNumero(int numero) {
        for (int i=0; i<brassins.size() ; i++) {
            if (brassins.get(i).getNumero() == numero) {
                return brassins.get(i);
            }
        }
        return null;
    }

    public ArrayList<BrassinPere> trierParNumero(){
        ArrayList<BrassinPere> result = new ArrayList<>();
        int index;
        long value;
        for (int i = 0; i < brassins.size(); i++) {
            index = -1;
            value = brassins.size();
            boolean possible;
            for (int j = 0; j < brassins.size(); j++) {
                possible=true;
                for (int k = 0; k < result.size() && possible; k++) {
                    if(result.get(k).getNumero() == brassins.get(j).getNumero()){
                        possible = false;
                    }
                }
                if((possible) && ((index<0) || (brassins.get(j).getNumero() < value))){
                    index = j;
                    value = brassins.get(index).getNumero();
                }
            }
            if(index>=0 && index<brassins.size()) {
                result.add(brassins.get(index));
            }
        }
        return result;
    }

    public ArrayList<BrassinPere> trierParRecette() {
        ArrayList<BrassinPere> result = new ArrayList<>();
        int index;
        long[] values = new long[2];
        for (int i = 0; i < brassins.size(); i++) {
            index = -1;
            values[0] = -1;
            values[1] = -1;
            boolean possible;
            for (int j = 0; j < brassins.size(); j++) {
                possible=true;
                for (int k = 0; k < result.size() && possible; k++) {
                    if(result.get(k).getNumero() == brassins.get(j).getNumero()){
                        possible = false;
                    }
                }
                if((possible) && ((index<0) || (brassins.get(j).getId_recette() < values[0]))){
                    index = j;
                    values[0] = brassins.get(index).getId_recette();
                    values[1] = brassins.get(index).getNumero();
                }
                else if((possible) && ((index<0) || (brassins.get(j).getId_recette() == values[0] && brassins.get(j).getNumero() < values[1]))){
                    index = j;
                    values[0] = brassins.get(index).getId_recette();
                    values[1] = brassins.get(index).getNumero();
                }
            }
            if(index>=0 && index<brassins.size()) {
                result.add(brassins.get(index));
            }
        }
        return result;
    }

    public ArrayList<BrassinPere> trierParDateCreation() {
        ArrayList<BrassinPere> result = new ArrayList<>();
        int index;
        long[] values = new long[2];
        for (int i = 0; i < brassins.size(); i++) {
            index = -1;
            values[0] = -1;
            values[1] = -1;
            boolean possible;
            for (int j = 0; j < brassins.size(); j++) {
                possible=true;
                for (int k = 0; k < result.size() && possible; k++) {
                    if(result.get(k).getNumero() == brassins.get(j).getNumero()){
                        possible = false;
                    }
                }
                if((possible) && ((index<0) || (brassins.get(j).getDateLong() > values[0]))){
                    index = j;
                    values[0] = brassins.get(index).getDateLong();
                    values[1] = brassins.get(index).getNumero();
                }
                else if((possible) && ((index<0) || (brassins.get(j).getDateLong() == values[0] && brassins.get(j).getNumero() < values[1]))){
                    index = j;
                    values[0] = brassins.get(index).getDateLong();
                    values[1] = brassins.get(index).getNumero();
                }
            }
            if(index>=0 && index<brassins.size()) {
                result.add(brassins.get(index));
            }
        }
        return result;
    }

    private ArrayList<BrassinPere> trierParId(ArrayList<BrassinPere> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        BrassinPere pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                BrassinPere temp = liste.get(i);
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
        if (brassins.size() > 0) {
            ArrayList<BrassinPere> trierParId = trierParId(brassins, 0, brassins.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        brassins.clear();
    }
}
