package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Cuve;

public class TableCuve extends Controle{

    /**
     * Liste des cuves
     */
    private ArrayList<Cuve> cuves;

    private static TableCuve INSTANCE;

    /**
     * Constructeur qui prend un objet Context
     * @param contexte
     * @return instance de TableCuve
     */
    public static TableCuve instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableCuve(contexte);
        }
        return INSTANCE;
    }

    /**
     * Constructeur privé qui lit la bdd
     * @param contexte
     */
    private TableCuve(Context contexte){
        super(contexte, "Cuve");

        cuves = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            cuves.add(new Cuve(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getString(7), tmp.getLong(8), tmp.getInt(9) == 1));
        }
        Collections.sort(cuves);
    }

    /**
     * Fonction à utilisé pour ajouter un brassin
     * @param numero numero de la cuve
     * @param capacite capacité de la cuve
     * @param id_emplacement emplacement de la cuve
     * @param dateLavageAcide date de lavage acide de la cuve
     * @param id_noeud noeud dans laquelle la cuve se trouve
     * @param dateEtat date de l'état actuel de la cuve
     * @param commentaireEtat commentaire de l'état (non utilisé) de la cuve
     * @param id_brassin brassin que la cuve contient
     * @param actif si la cuve est actif ou non
     * @return
     */
    public long ajouter(int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_noeud, long dateEtat, String commentaireEtat, long id_brassin, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_noeudCuve", id_noeud);
        valeur.put("dateEtat", dateEtat);
        valeur.put("commentaireEtat", commentaireEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            cuves.add(new Cuve(id, numero, capacite, id_emplacement, dateLavageAcide, id_noeud, dateEtat, commentaireEtat, id_brassin, actif));
            Collections.sort(cuves);
        }
        return id;
    }

    /**
     * Fonction qui retourne la taille de la liste des cuves
     * @return taille de la liste des cuves
     */
    public int tailleListe() {
        return cuves.size();
    }

    /**
     * Fonction qui renvoi une cuve selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     * @param index index de la cuve voulu
     * @return la cuve selon l'index qu'elle a dans la liste et renvoie null si l'index spécifié n'existe pas
     */
    public Cuve recupererIndex(int index){
        try {
            return cuves.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction qui renvoi une cuve selon l'id qu'il a dans la liste et renvoie null si l'id spécifié n'existe pas
     * @param id id de la cuve voulu
     * @return la cuve selon l'id qu'elle a dans la liste et renvoie null si l'id spécifié n'existe pas
     */
    public Cuve recupererId(long id){
        for (int i=0; i<cuves.size() ; i++) {
            if (cuves.get(i).getId() == id) {
                return cuves.get(i);
            }
        }
        return null;
    }

    /**
     * Fonction à utilsé pour modifier une cuve
     * @param id id de la cuve à modifier
     * @param numero nouveau numéro
     * @param capacite nouvelle capacité
     * @param id_emplacement nouvel emplacement
     * @param dateLavageAcide nouvelle date de lavage acide
     * @param id_noeud nouveau noeud dans laquelle elle se trouve
     * @param dateEtat nouvelle date d'état
     * @param commentaireEtat nouveau commentaire (jamais utilisé)
     * @param id_brassin nouveau brassin
     * @param actif si elle est actif ou non
     */
    public void modifier(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_noeud, long dateEtat, String commentaireEtat, long id_brassin, boolean actif){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_noeudCuve", id_noeud);
        valeur.put("dateEtat", dateEtat);
        valeur.put("commentaireEtat", commentaireEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("actif", actif);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Cuve cuve = recupererId(id);
            cuve.setNumero(numero);
            cuve.setCapacite(capacite);
            cuve.setEmplacement(id_emplacement);
            cuve.setDateLavageAcide(dateLavageAcide);
            cuve.setNoeud(id_noeud);
            cuve.setCommentaireEtat(commentaireEtat);
            cuve.setBrassin(id_brassin);
            cuve.setActif(actif);
            Collections.sort(cuves);
        }
    }

    /**
     * Fonction qui retounre la liste des numéros des cuves
     * @return listes des numéros des cuves
     */
    public String[] numeros() {
        String[] numeroCuves = new String[cuves.size()];
        for (int i=0; i<cuves.size() ; i++) {
            numeroCuves[i] = cuves.get(i).getNumero() + "";
        }
        return numeroCuves;
    }

    /**
     * Focntion qui retourne la liste des cuves actives
     * @return la liste des cuves actives
     */
    public ArrayList<Cuve> recupererCuvesActifs() {
        ArrayList<Cuve> listeCuve = new ArrayList<>();

        for (int i=0; i<cuves.size(); i++) {
            if (cuves.get(i).getActif()) {
                listeCuve.add(cuves.get(i));
            }
        }
        return listeCuve;
    }

    /**
     * Fonction qui retourne la liste des cuves actives ayant un brassin
     * @return la liste des cuves actives ayant un brassin
     */
    public ArrayList<Cuve> recupererCuveAvecBrassin() {
        ArrayList<Cuve> listeCuve = new ArrayList<>();
        ArrayList<Cuve> listeCuveActive = recupererCuvesActifs();
        for (int i=0; i<listeCuveActive.size(); i++) {
            if (listeCuveActive.get(i).getIdBrassin() != -1) {
                listeCuve.add(listeCuveActive.get(i));
            }
        }
        return listeCuve;
    }

    /**
     * Fonction qui retourne la liste des cuves actives sans brassin
     * @return la liste des cuves actives sans brassin
     */
    public ArrayList<Cuve> recupererCuveSansBrassin() {
        ArrayList<Cuve> listeCuve = new ArrayList<>();
        ArrayList<Cuve> listeCuveActive = recupererCuvesActifs();
        for (int i=0; i<listeCuveActive.size(); i++) {
            if (listeCuveActive.get(i).getIdBrassin() == -1) {
                listeCuve.add(listeCuveActive.get(i));
            }
        }
        return listeCuve;
    }

    /**
     * Fonction qui retourne la liste des numéros des cuves actives ayant un brassin
     * @return la liste des numéros des cuves actives ayant un brassin
     */
    public ArrayList<String> recupererNumerosCuveAvecBrassin() {
        ArrayList<String> listeCuve = new ArrayList<>();
        ArrayList<Cuve> listeCuveActive = recupererCuvesActifs();
        for (int i=0; i<listeCuveActive.size(); i++) {
            if (listeCuveActive.get(i).getIdBrassin() != -1) {
                listeCuve.add(listeCuveActive.get(i).getNumero()+"");
            }
        }
        return listeCuve;
    }

    /**
     * Fonction qui retourne la liste des numéros des cuves actives sans brassin
     * @return la liste des numéros des cuves actives sans brassin
     */
    public ArrayList<String> recupererNumerosCuveSansBrassin() {
        ArrayList<String> listeCuve = new ArrayList<>();
        ArrayList<Cuve> listeCuveActive = recupererCuvesActifs();
        for (int i=0; i<listeCuveActive.size(); i++) {
            if (listeCuveActive.get(i).getIdBrassin() == -1) {
                listeCuve.add(listeCuveActive.get(i).getNumero()+"");
            }
        }
        return listeCuve;
    }

    /**
     * Tri (tri rapide) par id les brassins pour qu'il soit enregistré dans cet ordre lors de la sauvegarde
     * @param liste liste à trier
     * @param petitIndex index de début de la liste à trier
     * @param grandIndex index de fin de la liste à trier
     * @return la liste trier par ordre croissant d'id
     */
    private ArrayList<Cuve> trierParId(ArrayList<Cuve> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Cuve pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                Cuve temp = liste.get(i);
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
     * Supprime la cuve selon l'id spécifié
     * @param id id de la cuve à supprimer
     */
    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[] {""+id}) == 1) {
            cuves.remove(recupererId(id));
        }
    }

    /**
     * Retourne l'ensemble des cuves sous forme de string
     * @return string regroupant toutes les informations nécessaires à la sauvegarde des cuves
     */
    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        if (cuves.size() > 0) {
            ArrayList<Cuve> trierParId = trierParId(cuves, 0, cuves.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    /**
     * Vide toute la table Cuve de la bdd pour ajouté ensuite les entrées d'un fichier de sauvegarde
     */
    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        cuves.clear();
    }
}
