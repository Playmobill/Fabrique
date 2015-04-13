package fabrique.gestion.BDD;

import android.content.Context;

public class TableHistorique extends Controle {

    //private ArrayList<Historique> historiques;

    private static TableHistorique INSTANCE;

    public static TableHistorique instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableHistorique(contexte);
        }
        return INSTANCE;
    }


    private TableHistorique(Context contexte) {
        super(contexte, "Historique");
    }




}
