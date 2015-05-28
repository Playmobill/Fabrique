package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Fut;

public class TableFut extends Controle {

    /**
     * Liste des futs
     */
    private ArrayList<Fut> futs;

    private static TableFut INSTANCE;

    /**
     * Constructeur qui prend un objet Context
     * @param contexte
     * @return instance de TableFermenteur
     */
    public static TableFut instance(Context contexte) {
        if (INSTANCE == null) {
            INSTANCE = new TableFut(contexte);
        }
        return INSTANCE;
    }

    /**
     * Constructeur privé qui lit la bdd
     * @param contexte
     */
    private TableFut(Context contexte) {
        super(contexte, "Fut");

        futs = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            futs.add(new Fut(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getLong(3), tmp.getLong(4), tmp.getLong(5), tmp.getLong(6), tmp.getInt(7) == 1));
        }
        Collections.sort(futs);
    }

    /**
     * Fonction à utilisé pour ajouter un fut
     * @param numero numero du fut
     * @param capacite capacité du fut
     * @param id_noeud noeud dans lequel la cuve se trouve
     * @param dateEtat date de l'état actuel du fut
     * @param id_brassin brassin que la cuve contient
     * @param dateInspection date de l'inspection
     * @param actif si le fut est actif ou non
     * @return
     */
    public long ajouter(int numero, int capacite, long id_noeud, long dateEtat, long id_brassin, long dateInspection, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_noeudFut", id_noeud);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("dateInspection", dateInspection);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            futs.add(new Fut(id, numero, capacite, id_noeud, dateEtat, id_brassin, dateInspection, actif));
            Collections.sort(futs);
        }
        return id;
    }

    /**
     * Fonction qui retourne la taille de la liste des futs
     * @return taille de la liste des futs
     */
    public int tailleListe() {
        return futs.size();
    }

    /**
     * Fonction qui renvoi une cuve selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     * @param index index du fut voulu
     * @return la cuve selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     */
    public Fut recupererIndex(int index) {
        try {
            return futs.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction qui renvoi une cuve selon l'id qu'il a dans la liste et renvoie null si l'id spécifié n'existe pas
     * @param id id du fut voulu
     * @return la cuve selon l'id qu'il a dans la liste et renvoie null si l'id spécifié n'existe pas
     */
    public Fut recupererId(long id) {
        for (int i=0; i<futs.size() ; i++) {
            if (futs.get(i).getId() == id) {
                return futs.get(i);
            }
        }
        return null;
    }

    /**
     * Fonction à utilsé pour modifier un fut
     * @param id id du fut à modifier
     * @param numero nouveau numéro
     * @param capacite nouvelle capacité
     * @param id_noeud nouveau noeud dans lequel il se trouve
     * @param dateEtat nouvelle date d'état
     * @param id_brassin nouveau brassin
     * @param dateInspection date de l'inspection
     * @param actif si il est actif ou non
     */
    public void modifier(long id, int numero, int capacite, long id_noeud, long dateEtat, long id_brassin, long dateInspection, boolean actif){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_noeudFut", id_noeud);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("dateInspection", dateInspection);
        valeur.put("actif", actif);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Fut fut = recupererId(id);
            fut.setNumero(numero);
            fut.setCapacite(capacite);
            fut.setNoeud(id_noeud);
            fut.setDateEtat(dateEtat);
            fut.setBrassin(id_brassin);
            fut.setDateInspection(dateInspection);
            fut.setActif(actif);
            Collections.sort(futs);
        }
    }

    /**
     * Focntion qui retourne la liste des futs actifs
     * @return la liste des futs actifs
     */
    public ArrayList<Fut> recupererFutActifs() {
        ArrayList<Fut> listeFut = new ArrayList<>();

        for (int i=0; i<futs.size(); i++) {
            if (futs.get(i).getActif()) {
                listeFut.add(futs.get(i));
            }
        }
        return listeFut;
    }

    /**
     * Focntion qui retourne la liste des futs non-actifs
     * @return la liste des futs non-actifs
     */
    public ArrayList<Fut> recupererFutNonActifs() {
        ArrayList<Fut> listeFut = new ArrayList<>();

        for (int i=0; i<futs.size(); i++) {
            if (!futs.get(i).getActif()) {
                listeFut.add(futs.get(i));
            }
        }
        return listeFut;
    }

    public ArrayList<ArrayList<Fut>> recupererSelonBrassin(Context contexte) {
        ArrayList<ArrayList<Fut>> listeListeFutSelonBrassin = new ArrayList<>();

        ArrayList<Fut> cloneFuts = recupererFutActifs();
        while(cloneFuts.size()!=0) {
            long id;
            try {
                id = TableBrassin.instance(contexte).recupererId(cloneFuts.get(0).getId_brassin()).getId_brassinPere();
            }
            catch (NullPointerException e){
                id = -1;
            }
            ArrayList<Fut> listeFutDeMemeBrassin = new ArrayList<>();
            for(int i=0; i<cloneFuts.size() ; i++) {
                long idTmp;
                try {
                    idTmp = TableBrassin.instance(contexte).recupererId(cloneFuts.get(i).getId_brassin()).getId_brassinPere();
                }
                catch(NullPointerException e){
                    idTmp = -1;
                }
                if (idTmp == id) {
                    listeFutDeMemeBrassin.add(cloneFuts.get(i));
                    cloneFuts.remove(i);
                    i--;
                }
            }
            listeListeFutSelonBrassin.add(listeFutDeMemeBrassin);
        }
        return trierListeParBrassin(contexte, listeListeFutSelonBrassin, 0, listeListeFutSelonBrassin.size() - 1);
    }

    private ArrayList<ArrayList<Fut>> trierListeParBrassin(Context contexte, ArrayList<ArrayList<Fut>> listeListe, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        ArrayList<Fut> pivot = listeListe.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {

            int numeroPivot = -1;
            if (pivot.get(0).getBrassin(contexte) != null) {
                numeroPivot = (int) pivot.get(0).getBrassin(contexte).getId_brassinPere();
            }

            int numeroPetitIndex = -1;
            if (listeListe.get(i).get(0).getBrassin(contexte) != null) {
                numeroPetitIndex = (int) listeListe.get(i).get(0).getBrassin(contexte).getId_brassinPere();
            }

            int numeroGrandIndex = -1;
            if (listeListe.get(j).get(0).getBrassin(contexte) != null) {
                numeroGrandIndex = (int) listeListe.get(j).get(0).getBrassin(contexte).getId_brassinPere();
            }

            while (numeroPetitIndex < numeroPivot) {
                i++;
                numeroPetitIndex = -1;
                if (listeListe.get(i).get(0).getBrassin(contexte) != null) {
                    numeroPetitIndex = (int) listeListe.get(i).get(0).getBrassin(contexte).getId_brassinPere();
                }
            }
            while (numeroGrandIndex > numeroPivot) {
                j--;
                numeroGrandIndex = -1;
                if (listeListe.get(j).get(0).getBrassin(contexte) != null) {
                    numeroGrandIndex = (int) listeListe.get(j).get(0).getBrassin(contexte).getId_brassinPere();
                }
            }
            if (i <= j) {
                ArrayList<Fut> temp = listeListe.get(i);
                listeListe.set(i, listeListe.get(j));
                listeListe.set(j, temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call recursively
        if (petitIndex < j) {
            listeListe = trierListeParBrassin(contexte, listeListe, petitIndex, j);
        }
        if (i < grandIndex) {
            listeListe = trierListeParBrassin(contexte, listeListe, i, grandIndex);
        }
        return listeListe;
    }

    public ArrayList<ArrayList<Fut>> recupererSelonRecette(Context contexte) {
        ArrayList<ArrayList<Fut>> listeListeFutSelonRecette = new ArrayList<>();

        ArrayList<Fut> cloneFuts = recupererFutActifs();
        while(cloneFuts.size() != 0) {
            Brassin brassin = cloneFuts.get(0).getBrassin(contexte);
            long id_recette = -1;
            if (brassin != null) {
                id_recette = brassin.getId_recette();
            }
            ArrayList<Fut> listeFutDeMemeRecette = new ArrayList<>();
            for(int i=0; i<cloneFuts.size() ; i++) {
                brassin = cloneFuts.get(i).getBrassin(contexte);
                long id_recette_temp = -1;
                if (brassin != null) {
                    id_recette_temp = brassin.getId_recette();
                }
                if (id_recette_temp == id_recette) {
                    listeFutDeMemeRecette.add(cloneFuts.get(i));
                    cloneFuts.remove(i);
                    i--;
                }
            }
            listeListeFutSelonRecette.add(listeFutDeMemeRecette);
        }
        return trierListeParRecette(contexte, listeListeFutSelonRecette, 0, listeListeFutSelonRecette.size() - 1);
    }

    private ArrayList<ArrayList<Fut>> trierListeParRecette(Context contexte, ArrayList<ArrayList<Fut>> listeListe, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        ArrayList<Fut> pivot = listeListe.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {

            long idRecettePivot = -1;
            if (pivot.get(0).getBrassin(contexte) != null) {
                idRecettePivot = pivot.get(0).getBrassin(contexte).getId_recette();
            }

            long idRecettePetitIndex = -1;
            if (listeListe.get(i).get(0).getBrassin(contexte) != null) {
                idRecettePetitIndex = listeListe.get(i).get(0).getBrassin(contexte).getId_recette();
            }

            long idRecetteGrandIndex = -1;
            if (listeListe.get(j).get(0).getBrassin(contexte) != null) {
                idRecetteGrandIndex = listeListe.get(j).get(0).getBrassin(contexte).getId_recette();
            }

            while (idRecettePetitIndex < idRecettePivot) {
                i++;
                idRecettePetitIndex = -1;
                if (listeListe.get(i).get(0).getBrassin(contexte) != null) {
                    idRecettePetitIndex = listeListe.get(i).get(0).getBrassin(contexte).getId_recette();
                }
            }
            while (idRecetteGrandIndex > idRecettePivot) {
                j--;
                idRecetteGrandIndex = -1;
                if (listeListe.get(j).get(0).getBrassin(contexte) != null) {
                    idRecetteGrandIndex = listeListe.get(j).get(0).getBrassin(contexte).getId_recette();
                }
            }
            if (i <= j) {
                ArrayList<Fut> temp = listeListe.get(i);
                listeListe.set(i, listeListe.get(j));
                listeListe.set(j, temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call recursively
        if (petitIndex < j) {
            listeListe = trierListeParRecette(contexte, listeListe, petitIndex, j);
        }
        if (i < grandIndex) {
            listeListe = trierListeParRecette(contexte, listeListe, i, grandIndex);
        }
        return listeListe;
    }

    public ArrayList<ArrayList<Fut>> recupererSelonEtat() {
        ArrayList<ArrayList<Fut>> listeListeFutSelonEtat = new ArrayList<>();

        ArrayList<Fut> cloneFuts = recupererFutActifs();
        while(cloneFuts.size()!=0) {
            long id = cloneFuts.get(0).getId_noeud();
            ArrayList<Fut> listeFutDeMemeEtat = new ArrayList<>();
            for(int i=0; i<cloneFuts.size() ; i++) {
                if (cloneFuts.get(i).getId_noeud() == id) {
                    listeFutDeMemeEtat.add(cloneFuts.get(i));
                    cloneFuts.remove(i);
                    i--;
                }
            }
            listeListeFutSelonEtat.add(listeFutDeMemeEtat);
        }
        return trierListeParEtat(listeListeFutSelonEtat, 0, listeListeFutSelonEtat.size() - 1);
    }

    private ArrayList<ArrayList<Fut>> trierListeParEtat(ArrayList<ArrayList<Fut>> listeListe, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        ArrayList<Fut> pivot = listeListe.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (listeListe.get(i).get(0).getId_noeud() < pivot.get(0).getId_noeud()) {
                i++;
            }
            while (listeListe.get(j).get(0).getId_noeud() > pivot.get(0).getId_noeud()) {
                j--;
            }
            if (i <= j) {
                ArrayList<Fut> temp = listeListe.get(i);
                listeListe.set(i, listeListe.get(j));
                listeListe.set(j, temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call recursively
        if (petitIndex < j) {
            listeListe = trierListeParEtat(listeListe, petitIndex, j);
        }
        if (i < grandIndex) {
            listeListe = trierListeParEtat(listeListe, i, grandIndex);
        }
        return listeListe;
    }

    public ArrayList<Fut> recupererFutSansBrassin() {
        ArrayList<Fut> listeCuve = new ArrayList<>();

        for (int i=0; i<futs.size(); i++) {
            if (futs.get(i).getId_brassin() == -1) {
                listeCuve.add(futs.get(i));
            }
        }
        return listeCuve;
    }

    public ArrayList<String> recupererNumeroFutSansBrassin() {
        ArrayList<String> listeCuve = new ArrayList<>();

        for (int i=0; i<futs.size(); i++) {
            if (futs.get(i).getId_brassin() == -1) {
                listeCuve.add(Integer.toString(futs.get(i).getNumero()));
            }
        }
        return listeCuve;
    }

    private ArrayList<Fut> trierParId(ArrayList<Fut> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Fut pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                Fut temp = liste.get(i);
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
     * Retourne l'ensemble des futs sous forme de string
     * @return string regroupant toutes les informations nécessaires à la sauvegarde des futs
     */
    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        if (futs.size() > 0) {
            ArrayList<Fut> trierParId = trierParId(futs, 0, futs.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    /**
     * Supprime la cuve selon l'id spécifié
     * @param id id du fut à supprimer
     */
    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[]{"" + id}) == 1) {
            futs.remove(recupererId(id));
        }
    }

    /**
     * Vide toute la table Fermenteur de la bdd pour ajouté ensuite les entrées d'un fichier de sauvegarde
     */
    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        futs.clear();
    }
}
