package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.BrassinPere;

public class TableBrassinPere extends Controle {

    /**
     * Liste des brassins père
     */
    private ArrayList<BrassinPere> brassins;

    private static TableBrassinPere INSTANCE;

    /**
     * Constructeur qui prend un objet Context
     * @param contexte
     * @return instance de TableBrassinPere
     */
    public static TableBrassinPere instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableBrassinPere(contexte);
        }
        return INSTANCE;
    }

    /**
     * Constructeur privé qui lit la bdd
     * @param contexte
     */
    private TableBrassinPere(Context contexte){
        super(contexte, "BrassinPere");

        brassins = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            brassins.add(new BrassinPere(tmp.getLong(0), tmp.getInt(1), tmp.getString(2), tmp.getLong(3), tmp.getInt(4), tmp.getLong(5), tmp.getFloat(6), tmp.getFloat(7)));
        }
        Collections.sort(brassins);
    }

    /**
     * Fonction à utilisé pour ajouter un brassin pere
     * @param numero numero du brassin père
     * @param commentaire commentaire du brassin père
     * @param dateCreation date de creation du brassin père
     * @param quantite quantite du brassin père
     * @param id_recette recette du brassin père
     * @param densiteOriginale densité originale du brassin père
     * @param densiteFinale densité finale du brassin père
     * @return
     */
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

    /**
     * Fonction qui retourne la taille de la liste des brassins père
     * @return taille de la liste des brassins père
     */
    public int tailleListe() {
        return brassins.size();
    }

    /**
     * Fonction qui renvoi un brassin père selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     * @param index index du brassin père voulu
     * @return le brassin père selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     */
    public BrassinPere recupererIndex(int index){
        try {
            return brassins.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction qui renvoi un brassin père selon l'id qu'il a dans la bdd et renvoie null si l'id spécifié n'existe pas
     * @param id id du brassin père voulu
     * @return le brassin père selon l'id qu'il a dans la bdd et renvoie null si l'id spécifié n'existe pas
     */
    public BrassinPere recupererId(long id) {
        for (int i=0; i<brassins.size() ; i++) {
            if (brassins.get(i).getId() == id) {
                return brassins.get(i);
            }
        }
        return null;
    }

    /**
     * Fonction à utilisé pour modifier un brassin père
     * @param id id du brassin père à modifier
     * @param numero nouveau numero
     * @param commentaire nouveau commentaire
     * @param dateCreation nouvelle date de creation
     * @param quantite nouvelle quantite
     * @param id_recette nouvelle recette
     * @param densiteOriginale nouvelle densite originale
     * @param densiteFinale nouvelle densite finale
     */
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

    /**
     * Fonction qui récupère l'index du brassin père selon l'id qu'il a, retourne -1 si aucun brassin père n'a cette id
     * @param id id du brassin père recherché
     * @return l'index du brassin père selon l'id qu'il a, retourne -1 si aucun brassin père n'a cette id
     */
    public int recupererIndexSelonId(long id) {
        return brassins.indexOf(recupererId(id));
    }

    /**
     * Fonction qui récupère le premier brassin père qui porte le numéro donné en paramètre
     * @param numero numéro du brassin père recherché
     * @return le premier brassin père qui porte le numéro donné en paramètre
     */
    public BrassinPere recupererNumero(int numero) {
        for (int i=0; i<brassins.size() ; i++) {
            if (brassins.get(i).getNumero() == numero) {
                return brassins.get(i);
            }
        }
        return null;
    }

    /**
     * Fonction qui tri les brassins père par ordre croissant selon leur numéro
     * @return liste des brassins père par ordre croissant selon leur numéro
     */
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

    /**
     * Fonction qui tri les brassins père par ordre croissant selon les id de leur recette
     * @return liste des brassins père par ordre croissant selon les id de leur recette
     */
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

    /**
     * Fonction qui tri les brassins père par ordre croissant de leur date de création (du plus vieux au plus jeunes)
     * @return liste des brassins père par ordre croissant de leur date de création (du plus vieux au plus jeunes)
     */
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

    /**
     * Tri (tri rapide) par id les brassins père pour qu'il soit enregistré dans cet ordre lors de la sauvegarde
     * @param liste liste à trier
     * @param petitIndex index de début de la liste à trier
     * @param grandIndex index de fin de la liste à trier
     * @return la liste trier par ordre croissant d'id
     */
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

    /**
     * Retourne l'ensemble des brassins père sous forme de string
     * @return string regroupant toutes les informations nécessaires à la sauvegarde des brassins père
     */
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

    /**
     * Supprimer toute la table BrassinPere de la bdd pour ajouté ensuite les entrées d'un fichier de sauvegarde
     */
    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        brassins.clear();
    }
}
