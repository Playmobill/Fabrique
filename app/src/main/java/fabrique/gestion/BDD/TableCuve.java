package fabrique.gestion.BDD;

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
            if (tmp.getInt(8) != -1) {
                for (int i = 0; i < TableBrassin.instance(contexte).tailleListe(); i++) {
                    if (tmp.getInt(8) == TableBrassin.instance(contexte).recuperer(i).getId()) {
                        cuves.add(new Cuve(tmp.getInt(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getString(7), TableBrassin.instance(contexte).recuperer(i)));
                    }
                }
            } else {
                cuves.add(new Cuve(tmp.getInt(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getString(7), null));
            }
        }
        Collections.sort(cuves);
    }

    public void ajouter(Context contexte, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat, String commentaireEtat, int id_brassin) {
        if (id_brassin != -1) {
            for (int i = 0; i < TableBrassin.instance(contexte).tailleListe(); i++) {
                if (id_brassin == TableBrassin.instance(contexte).recuperer(i).getId()) {
                    cuves.add(new Cuve(cuves.size(), numero, capacite, emplacement, dateLavageAcide, etat, dateEtat, commentaireEtat, TableBrassin.instance(contexte).recuperer(i)));
                }
            }
        } else {
            cuves.add(new Cuve(cuves.size(), numero, capacite, emplacement, dateLavageAcide, etat, dateEtat, commentaireEtat, null));
        }
        accesBDD.execSQL("INSERT INTO Cuve (numero, capacite, id_emplacement, dateLavageAcide, id_etatCuve, dateEtat, commentaireEtat, id_brassin) VALUES (" + numero + ", " + capacite + ", " + emplacement + ", " + dateLavageAcide + ", " + etat + ", " + dateEtat + ", '" + commentaireEtat + "', " + id_brassin + ")");
        Collections.sort(cuves);
    }

    public Cuve recuperer(int index){
        return cuves.get(index);
    }

    public void supprimer(int index){
        cuves.remove(index);
    }

    public int tailleListe() {
        return cuves.size();
    }

    public String[] numeros() {
        String[] numeroFermenteurs = new String[cuves.size()];
        for (int i=0; i<cuves.size() ; i++) {
            numeroFermenteurs[i] = cuves.get(i).getNumero() + "";
        }
        return numeroFermenteurs;
    }
}
