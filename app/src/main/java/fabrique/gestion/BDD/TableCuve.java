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
            cuves.add(new Cuve(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getString(7), tmp.getLong(8)));
        }
        Collections.sort(cuves);
    }

    public long ajouter(int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, String commentaireEtat, long id_brassin) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatFermenteur", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("commentaireEtat", commentaireEtat);
        valeur.put("id_brassin", id_brassin);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            cuves.add(new Cuve(id, numero, capacite, id_emplacement, dateLavageAcide, id_etat, dateEtat, commentaireEtat, id_brassin));
            Collections.sort(cuves);
        }
        return id;
    }

    public int tailleListe() {
        return cuves.size();
    }

    public Cuve recupererIndex(int index){
        return cuves.get(index);
    }

    public Cuve recupererId(long id){
        for (int i=0; i<cuves.size() ; i++) {
            if (cuves.get(i).getId() == id) {
                return cuves.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, String commentaireEtat, long id_brassin){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatFermenteur", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("commentaireEtat", commentaireEtat);
        valeur.put("id_brassin", id_brassin);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Cuve cuve = recupererId(id);
            cuve.setNumero(numero);
            cuve.setCapacite(capacite);
            cuve.setEmplacement(id_emplacement);
            cuve.setDateLavageAcide(dateLavageAcide);
            cuve.setEtat(id_etat);
            cuve.setCommentaireEtat(commentaireEtat);
            cuve.setBrassin(id_brassin);
            Collections.sort(cuves);
        }
    }

    public String[] numeros() {
        String[] numeroFermenteurs = new String[cuves.size()];
        for (int i=0; i<cuves.size() ; i++) {
            numeroFermenteurs[i] = cuves.get(i).getNumero() + "";
        }
        return numeroFermenteurs;
    }
}
