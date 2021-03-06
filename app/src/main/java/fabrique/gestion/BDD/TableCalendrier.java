package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Calendrier;

/**
 * Created by thibaut on 01/05/15.
 */
public class TableCalendrier extends Controle {

    private ArrayList<Calendrier> evenements;

    private static TableCalendrier INSTANCE;

    public static TableCalendrier instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableCalendrier(contexte);
        }
        return INSTANCE;
    }

    private TableCalendrier(Context contexte) {
        super(contexte, "Calendrier");

        evenements = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            evenements.add(new Calendrier(tmp.getLong(0), tmp.getLong(1), tmp.getString(2), tmp.getInt(3), tmp.getLong(4)));
        }
        Collections.sort(evenements);
    }

    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        if (evenements.size() > 0) {
            for (int i = 0; i < evenements.size(); i++) {
                texte.append(evenements.get(i).sauvegarde());
            }
        }
        return texte.toString();
    }

    public ArrayList<Calendrier> getEvenements() {
        return evenements;
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        evenements.clear();
    }

    public long ajouter(long date, String nom, int type, long idObjet){
        ContentValues valeur = new ContentValues();
        valeur.put("dateEvenement", date);
        valeur.put("nomEvenement", nom);
        valeur.put("typeObjet", type);
        valeur.put("idObjet", idObjet);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            evenements.add(new Calendrier(id, date, nom, 0, 0));
            Collections.sort(evenements);
        }
        return id;
    }

    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[]{"" + id}) == 1) {
            evenements.remove(recupererId(id));
            Collections.sort(evenements);
        }
    }

    public Calendrier recupererId(long id) {
        for (int i=0; i<evenements.size() ; i++) {
            if (evenements.get(i).getId() == id) {
                return evenements.get(i);
            }
        }
        return null;
    }
}
