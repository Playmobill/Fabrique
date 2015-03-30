package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.TypeBiere;

/**
 * Created by thibaut on 26/03/15.
 */
public class TableTypeBiere extends Controle {

    private ArrayList<TypeBiere> result;

    private static TableTypeBiere instance;

    public static TableTypeBiere instance(Context ctxt){
        if(instance == null){
            instance = new TableTypeBiere(ctxt);
        }
        return instance;
    }

    private TableTypeBiere(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("TypeBiere");
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            result.add(new TypeBiere(tmp.getInt(0), tmp.getString(1)));
        }
    }

    public void ajout(String texte){
        result.add(new TypeBiere(result.size(), texte));
        BDD.execSQL("INSERT INTO TypeBiere (texte) VALUES ('"+texte+"')");
    }

    public TypeBiere recuperer(int index){
        return result.get(index);
    }

    public void modifier(int index, String texte){
        result.get(index).setTexte(texte);
    }

    public void supprimer(int index){
        result.remove(index);
    }
}
