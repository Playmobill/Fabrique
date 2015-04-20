package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.Objets.Cuve;

public class ActivityVueCuve extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);

        Cuve cuve = TableCuve.instance(this).recupererId(id);
        if (cuve != null) {
            if (cuve.getBrassin(this) != null) {
                layout.addView(new VueBrassin(this, cuve.getBrassin(this)));
            }
            layout.addView(new VueCuve(this, cuve));
        } else {
            TextView txtErreur = new TextView(this);
            txtErreur.setText("Aucune cuve sélectionné");
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
