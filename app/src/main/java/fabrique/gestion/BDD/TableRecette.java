package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Recette;

public class TableRecette extends Controle {

    private ArrayList<Recette> types;

    private static TableRecette INSTANCE;

    public static TableRecette instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableRecette(contexte);
        }
        return INSTANCE;
    }

    private TableRecette(Context contexte){
        super(contexte, "Recette");
        types = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            types.add(new Recette(tmp.getLong(0), tmp.getString(1), tmp.getString(2), tmp.getString(3)));
        }
        Collections.sort(types);
    }

    public void ajouter(String nom, String couleur, String acronyme) {
        types.add(new Recette(types.size(), nom, couleur, acronyme));
        accesBDD.execSQL("INSERT INTO Recette (nom, couleur, acronyme) VALUES ('" + nom + "', '"+ couleur +"', '"+ acronyme +"')");
        Collections.sort(types);
    }

    public Recette recuperer(int index){
        return types.get(index);
    }

    public int tailleListe() {
        return types.size();
    }

    public String[] types () {
        String[] types2 = new String[types.size()];
        for (int i=0; i<types.size(); i++) {
            types2[i] = types.get(i).getNom();
        }
        return types2;
    }
}
