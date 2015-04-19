package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.Objets.Brassin;

public class ActivityVueBrassin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LinearLayout layout = new LinearLayout(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("index", -1);

        Brassin brassin = TableBrassin.instance(this).recupererId(id);
        if (brassin != null) {
            layout.addView(new VueBrassin(this, brassin));
        } else {
            TextView txtErreur = new TextView(this);
            txtErreur.setText("Aucun brassin sélectionné");
        }

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(layout);

        setContentView(layoutVerticalScroll);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityListeBrassin.class);
        startActivity(intent);
    }
}
