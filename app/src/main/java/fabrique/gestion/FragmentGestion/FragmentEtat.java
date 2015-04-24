package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;
import fabrique.gestion.Vue.VueEtatCuve;
import fabrique.gestion.Vue.VueEtatFermenteur;
import fabrique.gestion.Vue.VueEtatFut;

public class FragmentEtat extends FragmentAmeliore {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        Context contexte = container.getContext();

        LinearLayout layout = new LinearLayout(contexte);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        //Scrolling vertical pour les etats de fermenteur
        ScrollView layoutVerticalScrollFermenteur = new ScrollView(contexte);
        layoutVerticalScrollFermenteur.addView(new VueEtatFermenteur(contexte));
        layout.addView(layoutVerticalScrollFermenteur);

        //Scrolling vertical pour les etats de cuve
        ScrollView layoutVerticalScrollCuve = new ScrollView(contexte);
        layoutVerticalScrollCuve.addView(new VueEtatCuve(contexte));
        layout.addView(layoutVerticalScrollCuve);

        //Scrolling vertical pour les etats de fut
        ScrollView layoutVerticalScrollFut = new ScrollView(contexte);
        layoutVerticalScrollFut.addView(new VueEtatFut(contexte));
        layout.addView(layoutVerticalScrollFut);

        //Scrolling horizontal pour l'activité
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(contexte);
        layoutHorizontalScroll.addView(layout);

        return layoutHorizontalScroll;
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        transaction.addToBackStack(null).commit();
    }
}
