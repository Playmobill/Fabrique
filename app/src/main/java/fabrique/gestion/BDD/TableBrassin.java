package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.BrassinPere;

public class TableBrassin extends Controle {

    /**
     * Liste des brassins
     */
    private ArrayList<Brassin> brassins;

    private static TableBrassin INSTANCE;

    /**
     * Constructeur qui prend un objet Context
     * @param contexte
     * @return instance de TableBrassin
     */
    public static TableBrassin instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableBrassin(contexte);
        }
        return INSTANCE;
    }

    /**
     * Constructeur privé qui lit la bdd
     * @param contexte
     */
    private TableBrassin(Context contexte) {
        super(contexte, "Brassin");

        brassins = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            brassins.add(new Brassin(tmp.getLong(0), TableBrassinPere.instance(contexte).recupererId(tmp.getLong(1)), tmp.getInt(2)));
        }
        Collections.sort(brassins);
    }

    /**
     * Fonction à utilisé pour ajouter un brassin
     * @param contexte
     * @param id_brassinPere id dans la bdd du brassin père
     * @param quantite quantite du brassin père qu'il prend
     * @return id de ce nouvel ajout dans le bdd
     */
    public long ajouter(Context contexte, long id_brassinPere, int quantite) {
        ContentValues valeur = new ContentValues();
        valeur.put("id_brassinPere", id_brassinPere);
        valeur.put("quantite", quantite);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            BrassinPere brassinPere = TableBrassinPere.instance(contexte).recupererId(id_brassinPere);
            brassins.add(new Brassin(id, brassinPere, quantite));
            Collections.sort(brassins);
        }
        return id;
    }

    /**
     * Fonction qui retourne la taille de la liste des brassins
     * @return taille de la liste des brassins
     */
    public int tailleListe() {
        return brassins.size();
    }

    /**
     * Fonction qui renvoi un brassin selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     * @param index index du brassin voulu
     * @return le brassin selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     */
    public Brassin recupererIndex(int index){
        try {
            return brassins.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction qui renvoi un brassin selon l'id qu'il a dans la bdd et renvoie null si l'id spécifié n'existe pas
     * @param id id du brassin voulu
     * @return le brassin selon l'id qu'il a dans la bdd et renvoie null si l'id spécifié n'existe pas
     */
    public Brassin recupererId(long id) {
        for (int i=0; i<brassins.size() ; i++) {
            if (brassins.get(i).getId() == id) {
                return brassins.get(i);
            }
        }
        return null;
    }

    /**
     * Fonction à utilisé pour modifier un brassin
     * @param id id du brassin à modifier
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
        valeur.put("quantite", quantite);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Brassin brassin = recupererId(id);
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

    public ArrayList<Brassin> recupererFils(long id_brassinPere) {
        ArrayList<Brassin> brassinsFils = new ArrayList<>();
        for (int i=0; i<brassins.size(); i++) {
            if (brassins.get(i).getId_brassinPere() == id_brassinPere) {
                brassinsFils.add(brassins.get(i));
            }
        }
        return brassinsFils;
    }

    /**
     * Tri (tri rapide) par id les brassins pour qu'il soit enregistré dans cet ordre lors de la sauvegarde
     * @param liste liste à trier
     * @param petitIndex index de début de la liste à trier
     * @param grandIndex index de fin de la liste à trier
     * @return la liste trier par ordre croissant d'id
     */
    private ArrayList<Brassin> trierParId(ArrayList<Brassin> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Brassin pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                Brassin temp = liste.get(i);
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
     * Retourne l'ensemble des brassins sous forme de string
     * @return string regroupant toutes les informations nécessaires à la sauvegarde des brassins
     */
    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        if (brassins.size() > 0) {
            ArrayList<Brassin> trierParId = trierParId(brassins, 0, brassins.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    /**
     * Vide toute la table Brassin de la bdd pour ajouté ensuite les entrées d'un fichier de sauvegarde
     */
    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        brassins.clear();
    }
}
