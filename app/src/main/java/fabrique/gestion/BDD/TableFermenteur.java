package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import fabrique.gestion.Objets.Fermenteur;

public class TableFermenteur extends Controle {

    private ArrayList<Fermenteur> result;

    private static TableFermenteur instance;

    public static TableFermenteur instance(Context ctxt){
        if(instance == null){
            instance = new TableFermenteur(ctxt);
        }
        return instance;
    }

    private TableFermenteur(Context ctxt){
        super(ctxt);
        result = new ArrayList<>();

        Cursor tmp = super.select("Fermenteur");
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            for (int i = 0; i < TableBrassin.instance(ctxt).listeSize(); i++) {
                if(tmp.getInt(7) == TableBrassin.instance(ctxt).recuperer(i).getId()){
                    result.add(new Fermenteur(tmp.getInt(0), tmp.getInt(1), tmp.getInt(2), tmp.getInt(3), tmp.getInt(4), tmp.getInt(5), tmp.getInt(6), TableBrassin.instance(ctxt).recuperer(i)));
                }
            }
        }
    }

    public void ajout(Context ctxt, int id, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat, int id_brassin){

        for (int i = 0; i < TableBrassin.instance(ctxt).listeSize(); i++) {
            if(id_brassin == TableBrassin.instance(ctxt).recuperer(i).getId()){
                result.add(new Fermenteur(result.size(), numero, capacite, emplacement, dateLavageAcide, etat, dateEtat, TableBrassin.instance(ctxt).recuperer(i)));
            }
        }

        BDD.execSQL("INSERT INTO Fermenteur (numero,capacite,dateLavageAcide,id_etatFermenteur,dateEtat,id_brassin,id_emplacement) VALUES ("+numero+", "+capacite+", "+dateLavageAcide+", "+etat+","+dateEtat+","+id_brassin+","+emplacement+")");
    }

    public Fermenteur recuperer(int index){
        return result.get(index);
    }

    public void modifier(Context ctxt, int index, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat,int id_brassin){
        result.get(index).setNumero(numero);
        result.get(index).setCapacite(capacite);
        result.get(index).setEmplacement(emplacement);
        result.get(index).setDateLavageAcide(dateLavageAcide);
        result.get(index).setEtat(etat);
        result.get(index).setDateEtat(dateEtat);

        for (int i = 0; i < TableBrassin.instance(ctxt).listeSize(); i++) {
            if(id_brassin == TableBrassin.instance(ctxt).recuperer(i).getId()){
                result.get(index).setBrassin(TableBrassin.instance(ctxt).recuperer(i));
            }
        }
    }

    public void supprimer(int index){
        result.remove(index);
    }

    public ArrayList<Fermenteur> fermenteurs(){
        return result;
    }
}
