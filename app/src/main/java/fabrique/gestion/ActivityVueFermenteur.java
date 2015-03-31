package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Objets.Fermenteur;

public class ActivityVueFermenteur extends Activity {

    private TextView titre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Taille ecran
        DisplayMetrics tailleEcran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);
        Fermenteur fermenteur = TableFermenteur.instance(this).recuperer(index);

        LinearLayout layout = new LinearLayout(this);

        titre = new TextView(this);
        titre.setText("Fermenteur " + fermenteur.getNumero());
        titre.setTextSize(40);
        titre.setTypeface(null, Typeface.BOLD);

        layout.addView(titre);

        setContentView(layout);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityTableauDeBord.class);
        startActivity(intent);
    }
}
