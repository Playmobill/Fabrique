package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.Brassin;

public class TableBrassin extends Controle {

    private ArrayList<Brassin> brassins;

    private static TableBrassin instance;

    public static TableBrassin instance(Context contexte){
        if(instance == null){
            instance = new TableBrassin(contexte);
        }
        return instance;
    }

    private TableBrassin(Context contexte){
        super(contexte);
        brassins = new ArrayList<>();

        Cursor tmp = super.select("Brassin");
        if (tmp != null) {
            for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
                brassins.add(new Brassin(tmp.getInt(0), tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(4), tmp.getInt(5), tmp.getInt(6), tmp.getString(7), tmp.getFloat(8), tmp.getFloat(9), tmp.getFloat(10)));
            }
        }
    }

    public void ajouter(int numero, String commentaire, String acronyme, long dateCreation, int quantite, int id_typeBiere, String couleur, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        brassins.add(new Brassin(brassins.size(), numero, commentaire, acronyme, dateCreation, quantite, id_typeBiere, couleur, densiteOriginale, densiteFinale, pourcentageAlcool));
        bdd.execSQL("INSERT INTO Brassin (numero, commentaire, acronyme, dateCreation, quantite, id_typeBiere, couleur, densiteOriginale, densiteFinale, pourcentageAlcool) VALUES ("+numero+", '"+commentaire+"', '"+acronyme+"', "+dateCreation+", "+quantite+", "+id_typeBiere+", '"+couleur+"', "+densiteOriginale+", "+densiteFinale+", "+pourcentageAlcool+")");
    }

    public Brassin brassin(int index) {
        return brassins.get(index);
    }
}
