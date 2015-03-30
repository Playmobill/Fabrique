package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.EtatFermenteur;

public class TableEtatFermenteur extends Controle {

    private ArrayList<EtatFermenteur> result;

    private static TableEtatFermenteur instance;

    public static TableEtatFermenteur instance(Context ctxt){
        if(instance == null){
            instance = new TableEtatFermenteur(ctxt);
        }
        return instance;
    }

    private TableEtatFermenteur(Context ctxt){
        super(ctxt);

        result = new ArrayList<>();

        Cursor tmp = super.select("EtatFermenteur");
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            result.add(new EtatFermenteur(tmp.getInt(0), tmp.getString(1)));
        }

        ajout("Vide");
        ajout("Fermentation");
        ajout("Lavé");
    }

    public void ajout(String texte){
        BDD.beginTransaction();
        try {
            BDD.execSQL("INSERT INTO EtatFermenteur (texte) VALUES ('"+texte+"')");
            result.add(new EtatFermenteur(result.size(), texte));

            BDD.setTransactionSuccessful();
        } catch(Exception e) {
            System.out.println(e);
        } finally {
            BDD.endTransaction();
        }
    }

    public EtatFermenteur recuperer(int index){
        return result.get(index);
    }

    public void modifier(int index, String texte){
        result.get(index).setTexte(texte);
    }

    public void supprimer(int index){
        result.remove(index);
    }

    public String etat(int numero){
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getId() == numero){
                return result.get(i).getTexte();
            }
        }
        return null;
    }

    public String[] etats () {
        String[] etats = new String[result.size()];
        for (int i=0; i<result.size(); i++) {
            etats[i] = etat(i);
        }
        return etats;
    }
}
