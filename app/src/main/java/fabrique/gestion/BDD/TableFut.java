package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Fut;

public class TableFut extends Controle {

    private ArrayList<Fut> futs;

    private static TableFut INSTANCE;

    public static TableFut instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableFut(contexte);
        }
        return INSTANCE;
    }

    private TableFut(Context contexte) {
        super(contexte, "Fut");

        futs = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            futs.add(new Fut(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getLong(3), tmp.getLong(4), tmp.getLong(5), tmp.getLong(6)));
            long id_brassin = (long)(Math.random()*4);
            if (id_brassin == 0) {
                id_brassin = -1;
            }
            modifier(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getLong(3), tmp.getLong(4), id_brassin, tmp.getLong(6));
        }
        Collections.sort(futs);
    }

    public long ajouter(int numero, int capacite, long id_etat, long dateEtat, long id_brassin, long dateInspection) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_etatFut", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("dateInspection", dateInspection);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            futs.add(new Fut(id, numero, capacite, id_etat, dateEtat, id_brassin, dateInspection));
            Collections.sort(futs);
        }
        return id;
    }

    public int tailleListe() {
        return futs.size();
    }

    public Fut recupererIndex(int index) {
        try {
            return futs.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Fut recupererId(long id) {
        for (int i=0; i<futs.size() ; i++) {
            if (futs.get(i).getId() == id) {
                return futs.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, int numero, int capacite, long id_etat, long dateEtat, long id_brassin, long dateInspection){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_etatFut", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        valeur.put("dateInspection", dateInspection);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Fut fut = recupererId(id);
            fut.setNumero(numero);
            fut.setCapacite(capacite);
            fut.setEtat(id_etat);
            fut.setDateEtat(dateEtat);
            fut.setBrassin(id_brassin);
            fut.setDateInspection(dateInspection);
            Collections.sort(futs);
        }
    }

    public String[] numeros() {
        String[] numeroFuts = new String[futs.size()];
        for (int i=0; i<futs.size() ; i++) {
            numeroFuts[i] = futs.get(i).getNumero() + "";
        }
        return numeroFuts;
    }

    public ArrayList<ArrayList<Fut>> recupererSelonBrassin(Context contexte) {
        ArrayList<ArrayList<Fut>> listeListeFutSelonBrassin = new ArrayList<>();

        ArrayList<Fut> cloneFuts = (ArrayList<Fut>)futs.clone();
        while(cloneFuts.size()!=0) {
            long id = cloneFuts.get(0).getId_brassin();
            ArrayList<Fut> listeFutDeMemeBrassin = new ArrayList<Fut>();
            for(int i=0; i<cloneFuts.size() ; i++) {
                if (cloneFuts.get(i).getId_brassin() == id) {
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
                numeroPivot = pivot.get(0).getBrassin(contexte).getNumero();
            }

            int numeroPetitIndex = -1;
            if (listeListe.get(i).get(0).getBrassin(contexte) != null) {
                numeroPetitIndex = listeListe.get(i).get(0).getBrassin(contexte).getNumero();
            }

            int numeroGrandIndex = -1;
            if (listeListe.get(j).get(0).getBrassin(contexte) != null) {
                numeroGrandIndex = listeListe.get(j).get(0).getBrassin(contexte).getNumero();
            }

            while (numeroPetitIndex < numeroPivot) {
                i++;
                numeroPetitIndex = -1;
                if (listeListe.get(i).get(0).getBrassin(contexte) != null) {
                    numeroPetitIndex = listeListe.get(i).get(0).getBrassin(contexte).getNumero();
                }
            }
            while (numeroGrandIndex > numeroPivot) {
                j--;
                numeroGrandIndex = -1;
                if (listeListe.get(j).get(0).getBrassin(contexte) != null) {
                    numeroGrandIndex = listeListe.get(j).get(0).getBrassin(contexte).getNumero();
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

        ArrayList<Fut> cloneFuts = (ArrayList<Fut>)futs.clone();
        while(cloneFuts.size() != 0) {
            Brassin brassin = cloneFuts.get(0).getBrassin(contexte);
            long id_recette = -1;
            if (brassin != null) {
                id_recette = brassin.getId_recette();
            }
            ArrayList<Fut> listeFutDeMemeRecette = new ArrayList<Fut>();
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

    public ArrayList<ArrayList<Fut>> recupererSelonEtat(Context contexte) {
        ArrayList<ArrayList<Fut>> listeListeFutSelonEtat = new ArrayList<>();

        ArrayList<Fut> cloneFuts = (ArrayList<Fut>)futs.clone();
        while(cloneFuts.size()!=0) {
            long id = cloneFuts.get(0).getId_etat();
            ArrayList<Fut> listeFutDeMemeEtat = new ArrayList<Fut>();
            for(int i=0; i<cloneFuts.size() ; i++) {
                if (cloneFuts.get(i).getId_etat() == id) {
                    listeFutDeMemeEtat.add(cloneFuts.get(i));
                    cloneFuts.remove(i);
                    i--;
                }
            }
            listeListeFutSelonEtat.add(listeFutDeMemeEtat);
        }
        return trierListeParEtat(contexte, listeListeFutSelonEtat, 0, listeListeFutSelonEtat.size() - 1);
    }

    private ArrayList<ArrayList<Fut>> trierListeParEtat(Context contexte, ArrayList<ArrayList<Fut>> listeListe, int petitIndex, int grandIndex) {
        int i = petitIndex;
        int j = grandIndex;
        // calculate pivot number, I am taking pivot as middle index number
        ArrayList<Fut> pivot = listeListe.get(petitIndex+(grandIndex-petitIndex)/2);
        // Divide into two arrays
        while (i <= j) {
            while (listeListe.get(i).get(0).getId_etat() < pivot.get(0).getId_etat()) {
                i++;
            }
            while (listeListe.get(j).get(0).getId_etat() > pivot.get(0).getId_etat()) {
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
            listeListe = trierListeParEtat(contexte, listeListe, petitIndex, j);
        }
        if (i < grandIndex) {
            listeListe = trierListeParEtat(contexte, listeListe, i, grandIndex);
        }
        return listeListe;
    }

    public ArrayList<Fut> recupererFutAvecBrassin() {
        ArrayList<Fut> listeCuve = new ArrayList<>();

        for (int i=0; i<futs.size(); i++) {
            if (futs.get(i).getId_brassin() != -1) {
                listeCuve.add(futs.get(i));
            }
        }
        return listeCuve;
    }
}
