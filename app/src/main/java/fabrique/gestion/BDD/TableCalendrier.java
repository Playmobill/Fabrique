package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Calendrier;

/**
 * Created by thibaut on 01/05/15.
 */
public class TableCalendrier extends Controle {

    private ArrayList<Calendrier> evenements;

    private static TableCalendrier INSTANCE;

    public static TableCalendrier instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableCalendrier(contexte);
        }
        return INSTANCE;
    }

    private TableCalendrier(Context contexte) {
        super(contexte, "Calendrier");

        evenements = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            evenements.add(new Calendrier(tmp.getLong(0), tmp.getLong(1), tmp.getString(2), tmp.getLong(3), tmp.getInt(4)));
        }
        Collections.sort(evenements);
    }

    @Override
    public String sauvegarde() {
        return null;
    }

    public ArrayList<Calendrier> getEvenements() {
        return evenements;
    }
}