package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Brassin;

public class TableBrassin extends Controle {

    private ArrayList<Brassin> brassins;

    private static TableBrassin INSTANCE;

    public static TableBrassin instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableBrassin(contexte);
        }
        return INSTANCE;
    }

    private TableBrassin(Context contexte){
        super(contexte, "Brassin");

        brassins = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            brassins.add(new Brassin(tmp.getLong(0), tmp.getInt(1), tmp.getString(2), tmp.getLong(3), tmp.getInt(4), tmp.getLong(5), tmp.getFloat(6), tmp.getFloat(7), tmp.getFloat(8)));
        }
        Collections.sort(brassins);
    }

    public long ajouter(int numero, String commentaire, long dateCreation, int quantite, long id_recette, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("commentaire", commentaire);
        valeur.put("dateCreation", dateCreation);
        valeur.put("quantite", quantite);
        valeur.put("id_recette", id_recette);
        valeur.put("densiteOriginale", densiteOriginale);
        valeur.put("densiteFinale", densiteFinale);
        valeur.put("pourcentageAlcool", pourcentageAlcool);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            brassins.add(new Brassin(id, numero, commentaire, dateCreation, quantite, id_recette, densiteOriginale, densiteFinale, pourcentageAlcool));
            Collections.sort(brassins);
        }
        return id;
    }

    public int tailleListe() {
        return brassins.size();
    }

    public Brassin recupererIndex(int index){
        try {
            return brassins.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public int recupererIndexSelonId(long id) {
        return brassins.indexOf(recupererId(id));
    }

    public Brassin recupererId(long id) {
        for (int i=0; i<brassins.size() ; i++) {
            if (brassins.get(i).getId() == id) {
                return brassins.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, int numero, String commentaire, long dateCreation, int quantite, long id_recette, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("commentaire", commentaire);
        valeur.put("dateCreation", dateCreation);
        valeur.put("quantite", quantite);
        valeur.put("id_recette", id_recette);
        valeur.put("densiteOriginale", densiteOriginale);
        valeur.put("densiteFinale", densiteFinale);
        valeur.put("pourcentageAlcool", pourcentageAlcool);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Brassin brassin = recupererId(id);
            brassin.setNumero(numero);
            brassin.setCommentaire(commentaire);
            brassin.setDateCreation(dateCreation);
            brassin.setQuantite(quantite);
            brassin.setId_recette(id_recette);
            brassin.setDensiteOriginale(densiteOriginale);
            brassin.setDensiteFinale(densiteFinale);
            brassin.setPourcentageAlcool();
            Collections.sort(brassins);
        }
    }

    public String[] numeros() {
        String[] numero = new String[brassins.size()];
        for (int i=0; i<brassins.size() ; i++) {
            numero[i] = brassins.get(i).getNumero() + "";
        }
        return numero;
    }

    public ArrayList<Brassin> trierParNumero(){
        ArrayList<Brassin> result = new ArrayList<>();
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

    public ArrayList<Brassin> trierParRecette() {
        ArrayList<Brassin> result = new ArrayList<>();
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

    public ArrayList<Brassin> trierParDateCreation() {
        ArrayList<Brassin> result = new ArrayList<>();
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

    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        for (int i=0; i<brassins.size(); i++) {
            texte.append(brassins.get(i).sauvegarde());
        }
        return texte.toString();
    }
}
