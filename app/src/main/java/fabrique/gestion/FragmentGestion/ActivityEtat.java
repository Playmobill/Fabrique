package fabrique.gestion.FragmentGestion;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import fabrique.gestion.Vue.VueEtatCuve;
import fabrique.gestion.Vue.VueEtatFermenteur;
import fabrique.gestion.Vue.VueEtatFut;

public class ActivityEtat extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        //Scrolling vertical pour les etats de fermenteur
        ScrollView layoutVerticalScrollFermenteur = new ScrollView(this);
        layoutVerticalScrollFermenteur.addView(new VueEtatFermenteur(this));
        layout.addView(layoutVerticalScrollFermenteur);

        //Scrolling vertical pour les etats de cuve
        ScrollView layoutVerticalScrollCuve = new ScrollView(this);
        layoutVerticalScrollCuve.addView(new VueEtatCuve(this));
        layout.addView(layoutVerticalScrollCuve);

        //Scrolling vertical pour les etats de fut
        ScrollView layoutVerticalScrollFut = new ScrollView(this);
        layoutVerticalScrollFut.addView(new VueEtatFut(this));
        layout.addView(layoutVerticalScrollFut);

        //Scrolling horizontal pour l'activité
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(this);
        layoutHorizontalScroll.addView(layout);

        setContentView(layoutHorizontalScroll);
    }

    @Override
    public void onBackPressed() {}
}
