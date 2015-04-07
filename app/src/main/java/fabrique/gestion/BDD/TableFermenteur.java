package fabrique.gestion.BDD;

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
            if (tmp.getInt(7) != -1) {
                for (int i = 0; i < TableBrassin.instance(contexte).tailleListe(); i++) {
                    if (tmp.getInt(7) == TableBrassin.instance(contexte).recuperer(i).getId()) {
                        fermenteurs.add(new Fermenteur(tmp.getInt(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), TableBrassin.instance(contexte).recuperer(i)));
                    }
                }
            } else {
                fermenteurs.add(new Fermenteur(tmp.getInt(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), null));
            }
        }
        Collections.sort(fermenteurs);
    }

    public void ajouter(Context contexte, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat, int id_brassin) {
        if (id_brassin != -1) {
            for (int i = 0; i < TableBrassin.instance(contexte).tailleListe(); i++) {
                if (id_brassin == TableBrassin.instance(contexte).recuperer(i).getId()) {
                    fermenteurs.add(new Fermenteur(fermenteurs.size(), numero, capacite, emplacement, dateLavageAcide, etat, dateEtat, TableBrassin.instance(contexte).recuperer(i)));
                }
            }
        } else {
            fermenteurs.add(new Fermenteur(fermenteurs.size(), numero, capacite, emplacement, dateLavageAcide, etat, dateEtat, null));
        }
        accesBDD.execSQL("INSERT INTO Fermenteur (numero, capacite, id_emplacement, dateLavageAcide, id_etatFermenteur, dateEtat, id_brassin) VALUES ("+numero+", "+capacite+","+emplacement+", "+dateLavageAcide+", "+etat+","+dateEtat+","+id_brassin+")");
        Collections.sort(fermenteurs);
    }

    public Fermenteur recuperer(int index){
        return fermenteurs.get(index);
    }

    public void modifier(Context contexte, int index, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat,int id_brassin){
        fermenteurs.get(index).setNumero(numero);
        fermenteurs.get(index).setCapacite(capacite);
        fermenteurs.get(index).setEmplacement(emplacement);
        fermenteurs.get(index).setDateLavageAcide(dateLavageAcide);
        fermenteurs.get(index).setEtat(etat);
        fermenteurs.get(index).setDateEtat(dateEtat);

        for (int i = 0; i < TableBrassin.instance(contexte).tailleListe(); i++) {
            if(id_brassin == TableBrassin.instance(contexte).recuperer(i).getId()){
                fermenteurs.get(index).setBrassin(TableBrassin.instance(contexte).recuperer(i));
            }
        }
    }

    public void supprimer(int index){
        fermenteurs.remove(index);
    }

    public int tailleListe() {
        return fermenteurs.size();
    }

    public String[] numeros() {
        String[] numeroFermenteurs = new String[fermenteurs.size()];
        for (int i=0; i<fermenteurs.size() ; i++) {
            numeroFermenteurs[i] = fermenteurs.get(i).getNumero() + "";
        }
        return numeroFermenteurs;
    }
}
