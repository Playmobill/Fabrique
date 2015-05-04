package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Historique;

public class TableHistorique extends Controle {

    private ArrayList<Historique> historiques;

    private static TableHistorique INSTANCE;

    public static TableHistorique instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableHistorique(contexte);
        }
        return INSTANCE;
    }

    private TableHistorique(Context contexte) {
        super(contexte, "Historique");

        historiques = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            historiques.add(new Historique(tmp.getLong(0), tmp.getString(1), tmp.getLong(2), tmp.getLong(3), tmp.getLong(4), tmp.getLong(5), tmp.getLong(6)));
        }
        Collections.sort(historiques);
    }

    public long ajouter(String texte, long date, long id_fermenteur, long id_cuve, long id_fut, long id_brassin){
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("date", date);
        valeur.put("id_fermenteur", id_fermenteur);
        valeur.put("id_cuve", id_cuve);
        valeur.put("id_fut", id_fut);
        valeur.put("id_brassin", id_brassin);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            historiques.add(new Historique(id, texte, date, id_fermenteur, id_cuve, id_fut, id_brassin));
            Collections.sort(historiques);
        }
        return id;
    }

    public int tailleListe() {
        return historiques.size();
    }

    public Historique recupererIndex(int index){
        try {
            return historiques.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public Historique recupererId(long id) {
        for (int i=0; i<historiques.size() ; i++) {
            if (historiques.get(i).getId() == id) {
                return historiques.get(i);
            }
        }
        return null;
    }

    public void modifier(long id, String texte, long date, long id_fermenteur, long id_cuve, long id_fut, long id_brassin){
        ContentValues valeur = new ContentValues();
        valeur.put("texte", texte);
        valeur.put("date", date);
        valeur.put("id_fermenteur", id_fermenteur);
        valeur.put("id_cuve", id_cuve);
        valeur.put("id_fut", id_fut);
        valeur.put("id_brassin", id_brassin);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Historique historique = recupererId(id);
            historique.setTexte(texte);
            historique.setDate(date);
            Collections.sort(historiques);
        }
    }

    public void supprimer(long id) {
        if (accesBDD.delete(nomTable, "id = ?", new String[] {"" + id}) == 1) {
            historiques.remove(recupererId(id));
            Collections.sort(historiques);
        }
    }

    public ArrayList<Historique> recupererSelonIdFermenteur (long id_fermenteur) {
        ArrayList<Historique> historiqueSelonFermenteur = new ArrayList<>();

        for (int i=0; i<historiques.size() ; i++) {
            if (historiques.get(i).getId_fermenteur() == id_fermenteur) {
                historiqueSelonFermenteur.add(historiques.get(i));
            }
        }
        return historiqueSelonFermenteur;
    }

    public ArrayList<Historique> recupererSelonIdCuve (long id_cuve) {
        ArrayList<Historique> historiqueSelonCuve = new ArrayList<>();

        for (int i=0; i<historiques.size() ; i++) {
            if (historiques.get(i).getId_cuve() == id_cuve) {
                historiqueSelonCuve.add(historiques.get(i));
            }
        }
        return historiqueSelonCuve;
    }

    public ArrayList<Historique> recupererSelonIdFut (long id_fut) {
        ArrayList<Historique> historiqueSelonFut = new ArrayList<>();

        for (int i=0; i<historiques.size() ; i++) {
            if (historiques.get(i).getId_fut() == id_fut) {
                historiqueSelonFut.add(historiques.get(i));
            }
        }
        return historiqueSelonFut;
    }

    public ArrayList<Historique> recupererSelonIdBrassin (long id_brassin) {
        ArrayList<Historique> historiqueSelonBrassin = new ArrayList<>();

        for (int i=0; i<historiques.size() ; i++) {
            if (historiques.get(i).getId_brassin() == id_brassin) {
                historiqueSelonBrassin.add(historiques.get(i));
            }
        }
        return historiqueSelonBrassin;
    }

    @Override
    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        for (int i=0; i<historiques.size(); i++) {
            texte.append(historiques.get(i).sauvegarde());
        }
        return texte.toString();
    }

    @Override
    public void supprimerToutesLaBdd() {
        super.supprimerToutesLaBdd();
        historiques.clear();
    }
}
