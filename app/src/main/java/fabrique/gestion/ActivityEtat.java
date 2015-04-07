package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ActivityEtat extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        //Scrolling vertical pour les etats de fermenteur
        ScrollView layoutVerticalScrollFermenteur = new ScrollView(this);
        layoutVerticalScrollFermenteur.addView(new VueEtatFermenteur(this));

        //Scrolling vertical pour les etats de cuve
        ScrollView layoutVerticalScrollCuve = new ScrollView(this);
        layoutVerticalScrollCuve.addView(new VueEtatCuve(this));

        //Scrolling vertical pour les etats de fut
        ScrollView layoutVerticalScrollFut = new ScrollView(this);
        layoutVerticalScrollFut.addView(new VueEtatFut(this));


        layout.addView(layoutVerticalScrollFermenteur);
        layout.addView(layoutVerticalScrollCuve);
        layout.addView(layoutVerticalScrollFut);

        //Scrolling horizontal pour l'activité
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(this);
        layoutHorizontalScroll.addView(layout);

        setContentView(layoutHorizontalScroll);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }
}
