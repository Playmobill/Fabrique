package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.EtatCuve;

public class TableEtatCuve extends Controle{

    private ArrayList<EtatCuve> result;
    private static TableEtatCuve instance;


    public static TableEtatCuve instance(Context ctxt){
        if(instance == null){
            instance = new TableEtatCuve(ctxt);
        }
        return instance;
    }

    private TableEtatCuve(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("EtatCuve");
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            result.add(new EtatCuve(tmp.getInt(0), tmp.getString(1)));
        }
    }

    public void ajout(String texte){
        result.add(new EtatCuve(result.size(), texte));
        BDD.execSQL("INSERT INTO EtatCuve (texte) VALUES ('"+texte+"')");
    }

    public EtatCuve recuperer(int index){
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

}
