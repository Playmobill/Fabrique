package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

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

        ajouter("Blanche");
        ajouter("Blonde");
        ajouter("Brune");
    }

    public void ajouter(String texte){
        types.add(new TypeBiere(types.size(), texte));
        accesBDD.execSQL("INSERT INTO TypeBiere (texte) VALUES ('" + texte + "')");
        Collections.sort(types);
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

    public String[] types () {
        String[] types2 = new String[types.size()];
        for (int i=0; i<types.size(); i++) {
            types2[i] = types.get(i).getTexte();
        }
        return types2;
    }
}
