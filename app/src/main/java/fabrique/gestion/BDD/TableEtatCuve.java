package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.EtatCuve;

public class TableEtatCuve extends Controle{

    private ArrayList<EtatCuve> etats;

    private static TableEtatCuve INSTANCE;

    public static TableEtatCuve instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableEtatCuve(contexte);
        }
        return INSTANCE;
    }

    private TableEtatCuve(Context contexte){
        super(contexte, "EtatCuve");
        etats = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            etats.add(new EtatCuve(tmp.getInt(0), tmp.getString(1)));
        }
    }

    public void ajouter(String texte){
        etats.add(new EtatCuve(etats.size(), texte));
        accesBDD.execSQL("INSERT INTO EtatCuve (texte) VALUES ('"+texte+"')");
    }

    public EtatCuve recuperer(int index){
        return etats.get(index);
    }

    public void supprimer(int index){
        etats.remove(index);
    }

    public int tailleListe() {
        return etats.size();
    }

    public String etat(int numero){
        for (int i = 0; i < etats.size(); i++) {
            if (etats.get(i).getId() == numero){
                return etats.get(i).getTexte();
            }
        }
        return null;
    }
}
