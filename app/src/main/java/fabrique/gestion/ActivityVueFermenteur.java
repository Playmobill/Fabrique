package fabrique.gestion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
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

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);

        setContentView(creerInterface(this, index));
    }

    public LinearLayout creerInterface(Context contexte, int index) {
        Fermenteur fermenteur = TableFermenteur.instance(this).recuperer(index);

        LinearLayout layout = new LinearLayout(contexte);

        titre = new TextView(contexte);
        titre.setText("Fermenteur " + fermenteur.getNumero());
        titre.setTextSize(40);
        titre.setTypeface(null, Typeface.BOLD);

        layout.addView(titre);
        return layout;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityTableauDeBord.class);
        startActivity(intent);
    }
}
