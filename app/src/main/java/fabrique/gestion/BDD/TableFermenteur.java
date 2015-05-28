package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Fermenteur;

public class TableFermenteur extends Controle {

    /**
     * Liste des fermenteurs
     */
    private ArrayList<Fermenteur> fermenteurs;

    private static TableFermenteur INSTANCE;

    /**
     * Constructeur qui prend un objet Context
     * @param contexte
     * @return instance de TableFermenteur
     */
    public static TableFermenteur instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableFermenteur(contexte);
        }
        return INSTANCE;
    }

    /**
     * Constructeur privé qui lit la bdd
     * @param contexte
     */
    private TableFermenteur(Context contexte) {
        super(contexte, "Fermenteur");

        fermenteurs = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            fermenteurs.add(new Fermenteur(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getLong(7), tmp.getInt(8) == 1));
        }
        Collections.sort(fermenteurs);
    }

    /**
     * Fonction à utilisé pour ajouter un brassin
     * @param numero numero du fermenteur
     * @param capacite capacité du fermenteur
     * @param id_emplacement emplacement du fermenteur
     * @param dateLavageAcide date de lavage acide du fermenteur
     * @param id_noeud noeud dans lequel la cuve se trouve
     * @param dateEtat date de l'état actuel du fermenteur
     * @param id_brassin brassin que la cuve contient
     * @param actif si le fermenteur est actif ou non
     * @return
     */
    public long ajouter(int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_noeud, long dateEtat, long id_brassin, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_noeudFermenteur", id_noeud);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            fermenteurs.add(new Fermenteur(id, numero, capacite, id_emplacement, dateLavageAcide, id_noeud, dateEtat, id_brassin, actif));
            Collections.sort(fermenteurs);
        }
        return id;
    }

    /**
     * Fonction qui retourne la taille de la liste des fermenteurs
     * @return taille de la liste des fermenteurs
     */
    public int tailleListe() {
        return fermenteurs.size();
    }

    /**
     * Fonction qui renvoi une cuve selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     * @param index index du fermenteur voulu
     * @return la cuve selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     */
    public Fermenteur recupererIndex(int index) {
        try {
            return fermenteurs.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction qui renvoi une cuve selon l'id qu'il a dans la liste et renvoie null si l'id spécifié n'existe pas
     * @param id id du fermenteur voulu
     * @return la cuve selon l'id qu'il a dans la liste et renvoie null si l'id spécifié n'existe pas
     */
    public Fermenteur recupererId(long id){
        for (int i=0; i<fermenteurs.size() ; i++) {
            if (fermenteurs.get(i).getId() == id) {
                return fermenteurs.get(i);
            }
        }
        return null;
    }

    /**
     * Fonction à utilsé pour modifier une cuve
     * @param id id du fermenteur à modifier
     * @param numero nouveau numéro
     * @param capacite nouvelle capacité
     * @param id_emplacement nouvel emplacement
     * @param dateLavageAcide nouvelle date de lavage acide
     * @param id_noeud nouveau noeud dans lequel il se trouve
     * @param dateEtat nouvelle date d'état
     * @param id_brassin nouveau brassin
     * @param actif si il est actif ou non
     */
    public void modifier(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_noeud, long dateEtat, long id_brassin, boolean actif){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_noeudFermenteur", id_noeud);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("actif", actif);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Fermenteur fermenteur = recupererId(id);
            fermenteur.setNumero(numero);
            fermenteur.setCapacite(capacite);
            fermenteur.setEmplacement(id_emplacement);
            fermenteur.setDateLavageAcide(dateLavageAcide);
            fermenteur.setNoeud(id_noeud);
            fermenteur.setDateEtat(dateEtat);
            fermenteur.setBrassin(id_brassin);
            fermenteur.setActif(actif);
            Collections.sort(fermenteurs);
        }
    }

    /**
     * Fonction qui retounre la liste des numéros des fermenteurs
     * @return listes des numéros des fermenteurs
     */
    public String[] numeros() {
        String[] numeroFermenteurs = new String[fermenteurs.size()];
        for (int i=0; i<fermenteurs.size() ; i++) {
            numeroFermenteurs[i] = fermenteurs.get(i).getNumero() + "";
        }
        return numeroFermenteurs;
    }

    /**
     * Focntion qui retourne la liste des fermenteurs actives
     * @return la liste des fermenteurs actives
     */
    public ArrayList<Fermenteur> recupererFermenteursActifs() {
        ArrayList<Fermenteur> listeFermenteur = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getActif()) {
                listeFermenteur.add(fermenteurs.get(i));
            }
        }
        return listeFermenteur;
    }

    /**
     * Fonction qui retourne la liste des fermenteurs actives ayant un brassin
     * @return la liste des fermenteurs actives ayant un brassin
     */
    public ArrayList<Fermenteur> recupererFermenteursVidesActifs() {
        ArrayList<Fermenteur> listeFermenteur = new ArrayList<>();
        ArrayList<Fermenteur> listeFermenteurActif = recupererFermenteursActifs();
        for (int i=0; i<listeFermenteurActif.size(); i++) {
            if (listeFermenteurActif.get(i).getIdBrassin() == -1) {
                listeFermenteur.add(listeFermenteurActif.get(i));
            }
        }
        return listeFermenteur;
    }

    /**
     * Fonction qui retourne la liste des fermenteurs actives sans brassin
     * @return la liste des fermenteurs actives sans brassin
     */
    public ArrayList<Fermenteur> recupererFermenteursPleinsActifs() {
        ArrayList<Fermenteur> listeFermenteur = new ArrayList<>();
        ArrayList<Fermenteur> listeFermenteurActif = recupererFermenteursActifs();
        for (int i=0; i<listeFermenteurActif.size(); i++) {
            if (listeFermenteurActif.get(i).getIdBrassin() != -1) {
                listeFermenteur.add(listeFermenteurActif.get(i));
            }
        }
        return listeFermenteur;
    }

    /**
     * Fonction qui retourne la liste des numéros des fermenteurs actives ayant un brassin
     * @return la liste des numéros des fermenteurs actives ayant un brassin
     */
    public ArrayList<String> recupererNumerosFermenteurAvecBrassin() {
        ArrayList<String> listeFermenteur = new ArrayList<>();
        ArrayList<Fermenteur> listeFermenteurActif = recupererFermenteursActifs();
        for (int i=0; i<listeFermenteurActif.size(); i++) {
            if (listeFermenteurActif.get(i).getIdBrassin() != -1) {
                listeFermenteur.add(listeFermenteurActif.get(i).getNumero()+"");
            }
        }
        return listeFermenteur;
    }

    /**
     * Fonction qui retourne la liste des numéros des fermenteurs actives sans brassin
     * @return la liste des numéros des fermenteurs actives sans brassin
     */
    public ArrayList<String> recupererNumerosFermenteurSansBrassin() {
        ArrayList<String> listeFermenteur = new ArrayList<>();
        ArrayList<Fermenteur> listeFermenteurActif = recupererFermenteursActifs();
        for (int i=0; i<listeFermenteurActif.size(); i++) {
            if (listeFermenteurActif.get(i).getIdBrassin() == -1) {
                listeFermenteur.add(listeFermenteurActif.get(i).getNumero()+"");
            }
        }
        return listeFermenteur;
    }

    /**
     * Tri (tri rapide) par id les brassins pour qu'il soit enregistré dans cet ordre lors de la sauvegarde
     * @param liste liste à trier
     * @param petitIndex index de début de la liste à trier
     * @param grandIndex index de fin de la liste à trier
     * @return la liste trier par ordre croissant d'id
     */
    private ArrayList<Fermenteur> trierParId(ArrayList<Fermenteur> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        Fermenteur pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                Fermenteur temp = liste.get(i);
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
     * @param id id du fermenteur à supprimer
     */
    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[] {""+id}) == 1) {
            fermenteurs.remove(recupererId(id));
        }
    }

    /**
     * Retourne l'ensemble des fermenteurs sous forme de string
     * @return string regroupant toutes les informations nécessaires à la sauvegarde des fermenteurs
     */
    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        if (fermenteurs.size() > 0) {
            ArrayList<Fermenteur> trierParId = trierParId(fermenteurs, 0, fermenteurs.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    /**
     * Vide toute la table Fermenteur de la bdd pour ajouté ensuite les entrées d'un fichier de sauvegarde
     */
    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        fermenteurs.clear();
    }
}
