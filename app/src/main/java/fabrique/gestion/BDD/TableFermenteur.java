package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Fermenteur;

public class TableFermenteur extends Controle {

    private ArrayList<Fermenteur> fermenteurs;

    private static TableFermenteur INSTANCE;

    public static TableFermenteur instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableFermenteur(contexte);
        }
        return INSTANCE;
    }

    private TableFermenteur(Context contexte) {
        super(contexte, "Fermenteur");

        fermenteurs = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            fermenteurs.add(new Fermenteur(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getLong(7), tmp.getInt(8) == 1));
        }
        Collections.sort(fermenteurs);
    }

    public long ajouter(int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, long id_brassin, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatFermenteur", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            fermenteurs.add(new Fermenteur(id, numero, capacite, id_emplacement, dateLavageAcide, id_etat, dateEtat, id_brassin, actif));
            Collections.sort(fermenteurs);
        }
        return id;
    }

    public int tailleListe() {
        return fermenteurs.size();
    }

    public Fermenteur recupererIndex(int index) {
        try {
            return fermenteurs.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Fermenteur recupererId(long id){
        for (int i=0; i<fermenteurs.size() ; i++) {
            if (fermenteurs.get(i).getId() == id) {
                return fermenteurs.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, long id_brassin, boolean actif){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatFermenteur", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("actif", actif);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Fermenteur fermenteur = recupererId(id);
            fermenteur.setNumero(numero);
            fermenteur.setCapacite(capacite);
            fermenteur.setEmplacement(id_emplacement);
            fermenteur.setDateLavageAcide(dateLavageAcide);
            fermenteur.setEtat(id_etat);
            fermenteur.setDateEtat(dateEtat);
            fermenteur.setBrassin(id_brassin);
            fermenteur.setActif(actif);
            Collections.sort(fermenteurs);
        }
    }

    public String[] numeros() {
        String[] numeroFermenteurs = new String[fermenteurs.size()];
        for (int i=0; i<fermenteurs.size() ; i++) {
            numeroFermenteurs[i] = fermenteurs.get(i).getNumero() + "";
        }
        return numeroFermenteurs;
    }

    public ArrayList<Fermenteur> recupererFermenteursActifs() {
        ArrayList<Fermenteur> listeFermenteur = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getActif()) {
                listeFermenteur.add(fermenteurs.get(i));
            }
        }
        return listeFermenteur;
    }

    public ArrayList<Fermenteur> recupererFermenteurAvecBrassin() {
        ArrayList<Fermenteur> listeFermenteur = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getIdBrassin() != -1) {
                listeFermenteur.add(fermenteurs.get(i));
            }
        }
        return listeFermenteur;
    }

    public ArrayList<String> recupererNumerosFermenteurAvecBrassin() {
        ArrayList<String> listeFermenteur = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getIdBrassin() != -1) {
                listeFermenteur.add(Integer.toString(fermenteurs.get(i).getNumero()));
            }
        }
        return listeFermenteur;
    }

    public ArrayList<String> recupererNumerosFermenteurSansBrassin() {
        ArrayList<String> listeFermenteur = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getIdBrassin() == -1) {
                listeFermenteur.add(Integer.toString(fermenteurs.get(i).getNumero()));
            }
        }
        return listeFermenteur;
    }

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

    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[] {""+id}) == 1) {
            fermenteurs.remove(recupererId(id));
        }
    }

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

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        fermenteurs.clear();
    }
}
