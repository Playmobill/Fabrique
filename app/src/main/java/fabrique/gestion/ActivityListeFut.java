package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Widget.BoutonFut;

public class ActivityListeFut extends Activity {

    private final static int largeurElement = 125;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Taille ecran
        DisplayMetrics tailleEcran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        int nombreElementParLigne = tailleEcran.widthPixels/largeurElement;

        TableLayout tableau = new TableLayout(this);

        TableFut tableFut = TableFut.instance(this);

        TableRow ligne = new TableRow(this);

        TableRow.LayoutParams parametreFut = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        parametreFut.setMargins(10, 10, 10, 10);

        int j = 0;
        for (int i=0; i<tableFut.tailleListe(); i++) {
            ligne.addView(new BoutonFut(this, tableFut.recupererIndex(i)), parametreFut);
            j = j + 1;
            if (j>=nombreElementParLigne) {
                tableau.addView(ligne);
                ligne = new TableRow(this);
                j = 0;
            }
        }
        if (j != 0) {
            tableau.addView(ligne);
        }

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(tableau);

        setContentView(layoutVerticalScroll);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityListe.class);
        startActivity(intent);
    }
}
