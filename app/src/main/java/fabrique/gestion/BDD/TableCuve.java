package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Cuve;

public class TableCuve extends Controle{

    private ArrayList<Cuve> cuves;

    private static TableCuve INSTANCE;

    public static TableCuve instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableCuve(contexte);
        }
        return INSTANCE;
    }

    private TableCuve(Context contexte){
        super(contexte, "Cuve");

        cuves = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            cuves.add(new Cuve(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getString(7), tmp.getLong(8), tmp.getInt(9) == 1));
        }
        Collections.sort(cuves);
    }

    public long ajouter(int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, String commentaireEtat, long id_brassin, boolean actif) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatCuve", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("commentaireEtat", commentaireEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("actif", actif);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            cuves.add(new Cuve(id, numero, capacite, id_emplacement, dateLavageAcide, id_etat, dateEtat, commentaireEtat, id_brassin, actif));
            Collections.sort(cuves);
        }
        return id;
    }

    public int tailleListe() {
        return cuves.size();
    }

    public Cuve recupererIndex(int index){
        try {
            return cuves.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Cuve recupererId(long id){
        for (int i=0; i<cuves.size() ; i++) {
            if (cuves.get(i).getId() == id) {
                return cuves.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, String commentaireEtat, long id_brassin, boolean actif){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatCuve", id_etat);
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
            cuve.setEtat(id_etat);
            cuve.setCommentaireEtat(commentaireEtat);
            cuve.setBrassin(id_brassin);
            cuve.setActif(actif);
            Collections.sort(cuves);
        }
    }

    public String[] numeros() {
        String[] numeroCuves = new String[cuves.size()];
        for (int i=0; i<cuves.size() ; i++) {
            numeroCuves[i] = cuves.get(i).getNumero() + "";
        }
        return numeroCuves;
    }

    public ArrayList<Cuve> recupererCuvesActifs() {
        ArrayList<Cuve> listeCuve = new ArrayList<>();

        for (int i=0; i<cuves.size(); i++) {
            if (cuves.get(i).getActif()) {
                listeCuve.add(cuves.get(i));
            }
        }
        return listeCuve;
    }

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

    public ArrayList<String> recupererNumerosCuveSansBrassin() {
        ArrayList<String> listeCuve = new ArrayList<>();
        ArrayList<Cuve> listeCuveActive = recupererCuvesActifs();
        for (int i=0; i<listeCuveActive.size(); i++) {
            if (listeCuveActive.get(i).getIdBrassin() != -1) {
                listeCuve.add(listeCuveActive.get(i).getNumero()+"");
            }
        }
        return listeCuve;
    }

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

    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[] {""+id}) == 1) {
            cuves.remove(recupererId(id));
        }
    }

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

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        cuves.clear();
    }
}
