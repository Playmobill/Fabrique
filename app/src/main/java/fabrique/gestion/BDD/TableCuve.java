package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Emplacement;

/**
 * Created by thibaut on 30/03/15.
 */
public class TableCuve extends Controle{

    private ArrayList<Cuve> result;
    private static TableCuve instance;


    public static TableCuve instance(Context ctxt){
        if(instance == null){
            instance = new TableCuve(ctxt);
        }
        return instance;
    }

    private TableCuve(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("Cuve");
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            for (int i = 0; i < TableBrassin.instance(ctxt).listeSize(); i++) {
                if(tmp.getInt(8) == TableBrassin.instance(ctxt).recuperer(i).getId()){
                    result.add(new Cuve(tmp.getInt(0), tmp.getInt(1), tmp.getInt(2),tmp.getInt(3), tmp.getInt(4), tmp.getInt(5), tmp.getInt(6),tmp.getString(7), TableBrassin.instance(ctxt).recuperer(i)));
                }
            }
        }
    }

    private int listeSize() {
        return result.size();
    }

    public void ajout(Context ctxt, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat, String commentaireEtat, int id_brassin){
        for (int i = 0; i < TableBrassin.instance(ctxt).listeSize(); i++) {
            if(id_brassin == TableBrassin.instance(ctxt).recuperer(i).getId()){
                result.add(new Cuve(result.size(), numero, capacite, emplacement, dateLavageAcide, etat, dateEtat, commentaireEtat, TableBrassin.instance(ctxt).recuperer(i)));
            }
        }
        BDD.execSQL("INSERT INTO Cuve (numero, capacite, dateLavageAcide, id_emplacement, id_etatCuve, dateEtat, commentaireEtat, id_brassin) VALUES ("+numero+", "+capacite+", "+dateLavageAcide+", "+emplacement+", "+etat+", "+dateEtat+", '"+commentaireEtat+"', "+id_brassin+")");
    }

    public void ajout(Context ctxt, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat, String commentaireEtat){
        result.add(new Cuve(result.size(), numero, capacite, emplacement, dateLavageAcide, etat, dateEtat, commentaireEtat, null));
        BDD.execSQL("INSERT INTO Cuve (numero, capacite, dateLavageAcide, id_emplacement, id_etatCuve, dateEtat, commentaireEtat) VALUES ("+numero+", "+capacite+", "+dateLavageAcide+", "+emplacement+", "+etat+", "+dateEtat+", '"+commentaireEtat+"')");
    }

    public Cuve recuperer(int index){
        return result.get(index);
    }

    public void modifier(int index){
        result.get(index);
    }

    public void supprimer(int index){
        result.remove(index);
    }

}
