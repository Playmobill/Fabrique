package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.NoeudFut;

public class TableCheminBrassinFut extends Controle  {

    /**
     * Liste des noeuds du chemin du brassin dans le fut
     */
    private ArrayList<NoeudFut> noeuds;

    private static TableCheminBrassinFut INSTANCE;

    /**
     * Constructeur qui prend un objet Context
     * @param contexte
     * @return instance de TableCheminBrassinFermenteur
     */
    public static TableCheminBrassinFut instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableCheminBrassinFut(contexte);
        }
        return INSTANCE;
    }

    /**
     * Constructeur privé qui lit la bdd
     * @param contexte
     */
    private TableCheminBrassinFut(Context contexte){
        super(contexte, "CheminBrassinFut");
        noeuds = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            noeuds.add(new NoeudFut(tmp.getLong(0), tmp.getLong(1), tmp.getLong(2), tmp.getLong(3), tmp.getLong(4)));
        }
        Collections.sort(noeuds);
    }

    /**
     * Fonction à utilisé pour ajouter un noeud au chemin du brassin dans le fut
     * @param id_noeudPrecedent id du noeud precedent celui-ci
     * @param id_etat id de l'état que contiendra ce noeud
     * @param id_noeudAvecBrassin id du prochain noeud avec brassin
     * @param id_noeudSansBrassin id du prochain noeud sans brassin
     * @return
     */
    public long ajouter(long id_noeudPrecedent, long id_etat, long id_noeudAvecBrassin, long id_noeudSansBrassin) {
        ContentValues valeur = new ContentValues();
        valeur.put("id_noeudPrecedent", id_noeudPrecedent);
        valeur.put("id_etat", id_etat);
        valeur.put("id_noeudAvecBrassin", id_noeudAvecBrassin);
        valeur.put("id_noeudSansBrassin", id_noeudSansBrassin);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            noeuds.add(new NoeudFut(id, id_noeudPrecedent, id_etat, id_noeudAvecBrassin, id_noeudSansBrassin));
            Collections.sort(noeuds);
        }
        return id;
    }

    /**
     * Fonction à utilisé pour modifier un noeud au chemin du brassin dans le fut
     * @param id du noeud à modifier
     * @param id_noeudAvecBrassin nouvelle id du prochain noeud avec brassin
     * @param id_noeudSansBrassin nouvelle id du prochain noeud sans brassin
     */
    public void modifier(long id, long id_noeudAvecBrassin, long id_noeudSansBrassin) {
        ContentValues valeur = new ContentValues();
        valeur.put("id_noeudAvecBrassin", id_noeudAvecBrassin);
        valeur.put("id_noeudSansBrassin", id_noeudSansBrassin);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            NoeudFut noeud = recupererId(id);
            noeud.setId_noeudAvecBrassin(id_noeudAvecBrassin);
            noeud.setId_noeudSansBrassin(id_noeudSansBrassin);
        }
    }

    /**
     * Fonction qui retourne la taille de la liste des noeuds
     * @return taille de la liste des noeuds
     */
    public int tailleListe() {
        return noeuds.size();
    }

    /**
     * Fonction qui renvoi un noeud selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     * @param index index du noeud voulu
     * @return le noeud selon l'index qu'il a dans la liste et renvoie null si l'index spécifié n'existe pas
     */
    public NoeudFut recupererIndex(int index){
        try {
            return noeuds.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Fonction qui renvoi un noeud selon l'id qu'il a dans la liste et renvoie null si l'id spécifié n'existe pas
     * @param id id du noeud voulu
     * @return le noeud selon l'id qu'il a dans la liste et renvoie null si l'id spécifié n'existe pas
     */
    public NoeudFut recupererId(long id) {
        for (int i=0; i< noeuds.size() ; i++) {
            if (noeuds.get(i).getId() == id) {
                return noeuds.get(i);
            }
        }
        return null;
    }

    /**
     * Renvoi le premier noeud du chemin du brassin dans le fut
     * en sachant que le premier noeud n'a pas de precedent
     * @return l'id du premier noeud du chemin du brassin dans le fut
     */
    public long recupererPremierNoeud() {
        for (int i=0; i<noeuds.size(); i++) {
            if (noeuds.get(i).getId_noeudPrecedent() == -1) {
                return noeuds.get(i).getId();
            }
        }
        return -1;
    }

    /**
     * Supprimer le noeud dont l'id est spécifié si il n'a pas de suivant
     * @param id id du noeud à supprimer
     */
    public void supprimer(long id) {
        NoeudFut noeud = recupererId(id);
        if ((noeud.getId_noeudAvecBrassin() == -1) && (noeud.getId_noeudSansBrassin() == -1) && (accesBDD.delete(nomTable, "id = ?", new String[] {""+id}) == 1)) {
            noeuds.remove(noeud);
            NoeudFut noeudPrecedent = recupererId(noeud.getId_noeudPrecedent());
            if (noeudPrecedent != null) {
                if (noeudPrecedent.getId_noeudAvecBrassin() == id) {
                    modifier(noeudPrecedent.getId(), -1, noeudPrecedent.getId_noeudSansBrassin());
                } else if (noeudPrecedent.getId_noeudSansBrassin() == id) {
                    modifier(noeudPrecedent.getId(), noeudPrecedent.getId_noeudAvecBrassin(), -1);
                }
            }
        }
    }

    /**
     * Tri (tri rapide) par id les noeud pour qu'il soit enregistré dans cet ordre lors de la sauvegarde
     * @param liste liste à trier
     * @param petitIndex index de début de la liste à trier
     * @param grandIndex index de fin de la liste à trier
     * @return la liste trier par ordre croissant d'id
     */
    private ArrayList<NoeudFut> trierParId(ArrayList<NoeudFut> liste, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        NoeudFut pivot = liste.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (liste.get(i).getId() < pivot.getId()) {
                i++;
            }
            while (liste.get(j).getId() > pivot.getId()) {
                j--;
            }
            if (i <= j) {
                NoeudFut temp = liste.get(i);
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
     * Retourne l'ensemble des noeuds sous forme de string
     * @return string regroupant toutes les informations nécessaires à la sauvegarde du chemin du brassin dans le fut
     */
    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        if (noeuds.size() > 0) {
            ArrayList<NoeudFut> trierParId = trierParId(noeuds, 0, noeuds.size() - 1);
            for (int i = 0; i < trierParId.size(); i++) {
                texte.append(trierParId.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    /**
     * Supprimer toute la table CheminBrassinFermenteur de la bdd pour ajouté ensuite les entrées d'un fichier de sauvegarde
     */
    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        noeuds.clear();
    }
}
