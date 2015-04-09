package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Objets.Fut;

public class ActivityVueFut extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);

        LinearLayout layout = new LinearLayout(this);

        Fut fut = TableFut.instance(this).recupererId(id);
        if (fut != null) {
            if (fut.getBrassin(this) != null) {
                //layout.addView(new VueBrassin(this, fermenteur.getBrassin()));
            }
            layout.addView(new VueFut(this, fut));
        } else {
            TextView txtErreur = new TextView(this);
            txtErreur.setText("Aucun fut sélectionné");
            layout.addView(txtErreur);
        }

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(layout);

        setContentView(layoutVerticalScroll);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityListeFut.class);
        startActivity(intent);
    }
}
