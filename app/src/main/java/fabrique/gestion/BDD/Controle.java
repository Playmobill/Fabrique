package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class Controle {

    protected SQLiteDatabase accesBDD;

    protected BDD bdd;

    protected String nomTable;

    public Controle(Context contexte, String nomTable){
        bdd = new BDD(contexte, "Brasserie", null);
        accesBDD = bdd.getWritableDatabase();
        this.nomTable = nomTable;
    }

    public Cursor select(){
        return accesBDD.query(nomTable, null, null, null, null, null, null);
    }

    public abstract String sauvegarde();

    public void supprimerToutesLaBdd() {
        accesBDD.delete("sqlite_sequence", "name = ?", new String[] {nomTable});
        accesBDD.delete(nomTable, "1", null);
    }
}
