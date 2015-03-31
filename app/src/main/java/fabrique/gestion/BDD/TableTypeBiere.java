package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.TypeBiere;

public class TableTypeBiere extends Controle {

    private ArrayList<TypeBiere> types;

    private static TableTypeBiere INSTANCE;

    public static TableTypeBiere instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableTypeBiere(contexte);
        }
        return INSTANCE;
    }

    private TableTypeBiere(Context contexte){
        super(contexte, "TypeBiere");
        types = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            types.add(new TypeBiere(tmp.getInt(0), tmp.getString(1)));
        }
    }

    public void ajout(String texte){
        types.add(new TypeBiere(types.size(), texte));
        accesBDD.execSQL("INSERT INTO TypeBiere (texte) VALUES ('" + texte + "')");
    }

    public TypeBiere recuperer(int index){
        return types.get(index);
    }

    public void supprimer(int index){
        types.remove(index);
    }

    public int tailleListe() {
        return types.size();
    }
}
