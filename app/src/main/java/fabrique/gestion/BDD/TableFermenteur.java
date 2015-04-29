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
            fermenteurs.add(new Fermenteur(tmp.getLong(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getLong(4), tmp.getInt(5), tmp.getLong(6), tmp.getLong(7)));
        }
        Collections.sort(fermenteurs);
    }

    public long ajouter(int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, long id_brassin) {
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatFermenteur", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        long id = accesBDD.insert(nomTable, null, valeur);
        if (id != -1) {
            fermenteurs.add(new Fermenteur(id, numero, capacite, id_emplacement, dateLavageAcide, id_etat, dateEtat, id_brassin));
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

    public void modifier(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, long id_brassin){
        ContentValues valeur = new ContentValues();
        valeur.put("numero", numero);
        valeur.put("capacite", capacite);
        valeur.put("id_emplacement", id_emplacement);
        valeur.put("dateLavageAcide", dateLavageAcide);
        valeur.put("id_etatFermenteur", id_etat);
        valeur.put("dateEtat", dateEtat);
        valeur.put("id_brassin", id_brassin);
        if (accesBDD.update(nomTable, valeur, "id = ?", new String[] {"" + id}) == 1) {
            Fermenteur fermenteur = recupererId(id);
            fermenteur.setNumero(numero);
            fermenteur.setCapacite(capacite);
            fermenteur.setEmplacement(id_emplacement);
            fermenteur.setDateLavageAcide(dateLavageAcide);
            fermenteur.setEtat(id_etat);
            fermenteur.setDateEtat(dateEtat);
            fermenteur.setBrassin(id_brassin);
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

    public ArrayList<Fermenteur> recupererFermenteurAvecBrassin() {
        ArrayList<Fermenteur> listeCuve = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getIdBrassin() != -1) {
                listeCuve.add(fermenteurs.get(i));
            }
        }
        return listeCuve;
    }

    public ArrayList<String> recupererNumerosFermenteurAvecBrassin() {
        ArrayList<String> listeCuve = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getIdBrassin() != -1) {
                listeCuve.add(Integer.toString(fermenteurs.get(i).getNumero()));
            }
        }
        return listeCuve;
    }

    public ArrayList<String> recupererNumerosFermenteurSansBrassin() {
        ArrayList<String> listeCuve = new ArrayList<>();

        for (int i=0; i<fermenteurs.size(); i++) {
            if (fermenteurs.get(i).getIdBrassin() == -1) {
                listeCuve.add(Integer.toString(fermenteurs.get(i).getNumero()));
            }
        }
        return listeCuve;
    }

    public String sauvegarde() {
        StringBuilder texte = new StringBuilder();
        texte.append("<Fermenteurs>");
        for (int i=0; i<fermenteurs.size(); i++) {
            texte.append(fermenteurs.get(i).sauvegarde());
        }
        texte.append("</Fermenteurs>");
        return texte.toString();
    }
}
