package fabrique.gestion.BDD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class TableGestion extends Controle {

    private long delaiLavageAcide, avertissementLavageAcide,
            delaiInspectionBaril, avertissementInspectionBaril;

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
        avertissementLavageAcide = tmp.getLong(1);
        delaiInspectionBaril = tmp.getLong(2);
        avertissementInspectionBaril = tmp.getLong(3);
    }

    public long delaiLavageAcide() {
        return delaiLavageAcide;
    }

    public long avertissementLavageAcide() {
        return avertissementLavageAcide;
    }

    public long delaiInspectionBaril() {
        return delaiInspectionBaril;
    }

    public long avertissementInspectionBaril() { return avertissementInspectionBaril; }

    public void modifier (long delaiLA, long avertLA, long delaiIB, long avertIB){
        ContentValues valeur = new ContentValues();
        valeur.put("delaiLavageAcide", delaiLA);
        valeur.put("avertissementLavageAcide", avertLA);
        valeur.put("delaiInspectionBaril", delaiIB);
        valeur.put("avertissementInspectionBaril", avertIB);
        if (accesBDD.update(nomTable, valeur, "", new String[] {}) == 1) {
            delaiLavageAcide = delaiLA;
            avertissementLavageAcide = avertLA;
            delaiInspectionBaril = delaiIB;
            avertissementInspectionBaril = avertIB;
        }
    }

    public String sauvegarde() {
        return ("<Gestion>" +
                    "<delaiLavageAcide>" + delaiLavageAcide + "</delaiLavageAcide>" +
                    "<avertissementLavageAcide>" + avertissementLavageAcide + "</avertissementLavageAcide>" +
                    "<delaiInspectionBaril>" + delaiInspectionBaril + "</delaiInspectionBaril>" +
                    "<avertissementInspectionBaril>" + avertissementInspectionBaril + "</avertissementInspectionBaril>" +
                "</Gestion>");
    }
}























