package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.NoeudCuve;

public class TableCheminBrassinCuve extends Controle  {

    private ArrayList<NoeudCuve> noeuds;

    private static TableCheminBrassinCuve INSTANCE;

    public static TableCheminBrassinCuve instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableCheminBrassinCuve(contexte);
        }
        return INSTANCE;
    }

    private TableCheminBrassinCuve(Context contexte){
        super(contexte, "CheminBrassinCuve");
        noeuds = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            noeuds.add(new NoeudCuve(tmp.getLong(0), tmp.getLong(1), tmp.getLong(2), tmp.getLong(3), tmp.getLong(4)));
        }
        Collections.sort(noeuds);
    }

    public long ajouter(long id_etat, long id_noeudPrecedent, long id_noeudAvecBrassin, long id_noeudSansBrassin) {
        ContentValues valeur = new ContentValues();
        valeur.put("id_etat", id_etat);
        valeur.put("id_noeudAvecBrassin", id_noeudAvecBrassin);
        valeur.put("id_noeudSansBrassin", id_noeudSansBrassin);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            noeuds.add(new NoeudCuve(id, id_noeudPrecedent, id_etat, id_noeudAvecBrassin, id_noeudSansBrassin));
            Collections.sort(noeuds);
        }
        return id;
    }

    public int tailleListe() {
        return noeuds.size();
    }

    public NoeudCuve recupererIndex(int index){
        try {
            return noeuds.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public NoeudCuve recupererId(long id) {
        for (int i=0; i< noeuds.size() ; i++) {
            if (noeuds.get(i).getId() == id) {
                return noeuds.get(i);
            }
        }
        return null;
    }

    public NoeudCuve recupererPremierNoeud() {
        for (int i=0; i<noeuds.size(); i++) {
            if (noeuds.get(i).getId_noeudPrecedent() == -1) {
                return noeuds.get(i);
            }
        }
        return null;
    }

    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[] {""+id}) == 1) {
            noeuds.remove(recupererId(id));
        }
    }

    @Override
    public String sauvegarde() {
        return null;
    }
}
