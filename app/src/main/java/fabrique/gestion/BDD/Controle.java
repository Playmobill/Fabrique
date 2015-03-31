package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fabrique.gestion.Objets.Objet;

public abstract class Controle {

    protected SQLiteDatabase accesBDD;

    protected BDD bdd;

    protected String nomTable;

    public Controle(Context contexte, String nomTable){
        bdd = new BDD(contexte, "Brasserie", null);
        accesBDD = bdd.getWritableDatabase();
        this.nomTable = nomTable;
    }

    public void fermer() {
        accesBDD.close();
    }

    public Cursor select(){
        return accesBDD.query(nomTable, null, null, null, null, null, null);
    }

    public abstract Objet recuperer(int index);

    public abstract void supprimer(int index);

    public abstract int tailleListe();
}
