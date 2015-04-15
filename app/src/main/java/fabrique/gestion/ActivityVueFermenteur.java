package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Objets.Fermenteur;

public class ActivityVueFermenteur extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);

        Fermenteur fermenteur = TableFermenteur.instance(this).recupererIndex(index);
        if (fermenteur != null) {
            if (fermenteur.getBrassin(this) != null) {
                layout.addView(new VueBrassin(this, fermenteur.getBrassin(this)));
            }
            layout.addView(new VueFermenteur(this, fermenteur));
        } else {
            TextView txtErreur = new TextView(this);
            txtErreur.setText("Aucun fermenteur sélectionné");
            layout.addView(txtErreur);
        }

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(layout);

        setContentView(layoutVerticalScroll);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityTableauDeBord.class);
        startActivity(intent);
    }
}
