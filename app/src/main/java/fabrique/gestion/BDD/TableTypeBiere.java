package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.TypeBiere;

public class TableTypeBiere extends Controle {

    public ArrayList<TypeBiere> types;

    private static TableTypeBiere instance;

    public static TableTypeBiere instance(Context contexte){
        if(instance == null){
            instance = new TableTypeBiere(contexte);
        }
        return instance;
    }

    private TableTypeBiere(Context contexte){
        super(contexte);
        types = new ArrayList<>();

        Cursor tmp = super.select("TypeBiere");
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            types.add(new TypeBiere(tmp.getInt(0), tmp.getString(1)));
        }
    }

    public void ajouter(String texte){
        types.add(new TypeBiere(types.size(), texte));
        bdd.execSQL("INSERT INTO TypeBiere (texte) VALUES ('"+texte+"')");
    }

    public String type(int index) {
        return types.get(index).getTexte();
    }
}
