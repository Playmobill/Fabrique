package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.Brassin;

/**
 * Created by thibaut on 27/03/15.
 */
public class TableBrassin extends Ctrl{

    public ArrayList<Brassin> result;

    private static TableBrassin instance;

    public static TableBrassin instance(Context ctxt){
        if(instance == null){
            instance = new TableBrassin(ctxt);
        }
        return instance;
    }

    private TableBrassin(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("Brassin", new String[] {"*"});
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            result.add(new Brassin(tmp.getInt(0), tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(4), tmp.getInt(5), tmp.getInt(6), tmp.getString(7), tmp.getFloat(8), tmp.getFloat(9), tmp.getFloat(10)));
        }
    }

    public void ajout(int numero, String commentaire, String acronyme, int dateCreation, int quantite, int id_typeBiere, String couleur, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        result.add(new Brassin(result.size(), numero, commentaire, acronyme, dateCreation, quantite, id_typeBiere, couleur, densiteOriginale, densiteFinale, pourcentageAlcool));
        BDD.execSQL("INSERT INTO Brassin (numero, commentaire, acronyme, dateCreation, quantite, id_typeBiere, couleur, densiteOriginale, densiteFinale, pourcentageAlcool) VALUES ("+numero+", '"+commentaire+"', '"+acronyme+"', "+dateCreation+", "+quantite+", "+id_typeBiere+", '"+couleur+"', "+densiteOriginale+", "+densiteFinale+", "+pourcentageAlcool+")");
    }

}
