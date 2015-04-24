package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Vue.VueBrassin;
import fabrique.gestion.Vue.VueFut;

public class ActivityVueFut extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Intent intent = getIntent();
        long id = intent.getLongExtra("id", -1);

        Fut fut = TableFut.instance(this).recupererId(id);
        if (fut != null) {
            if (fut.getBrassin(this) != null) {
                layout.addView(new VueBrassin(this, fut.getBrassin(this)));
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
}
