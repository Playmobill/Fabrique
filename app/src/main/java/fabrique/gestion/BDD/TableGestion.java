package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

public class TableGestion extends Controle {

    private long delaiLavageAcide, delaiInspectionBaril;

    private static TableGestion INSTANCE;

    public static TableGestion instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableGestion(contexte);
        }
        return INSTANCE;
    }

    private TableGestion(Context contexte){
        super(contexte, "Gestion");

        Cursor tmp = super.select();
        tmp.moveToFirst();
        delaiLavageAcide = tmp.getLong(0);
        delaiInspectionBaril = tmp.getLong(1);
    }

    @Override
    public int tailleListe() {
        return 0;
    }

    public long delaiLavageAcide() {
        return delaiLavageAcide;
    }

    public long delaiInspectionBaril() {
        return delaiInspectionBaril;
    }
}
