package fabrique.gestion.FragmentTableauDeBord;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.R;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Vue.VueBrassin;
import fabrique.gestion.Vue.VueFermenteur;

public class FragmentVueFermenteur extends FragmentAmeliore {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        Context contexte = container.getContext();

        LinearLayout layout = new LinearLayout(contexte);
        layout.setOrientation(LinearLayout.VERTICAL);

        Fermenteur fermenteur = TableFermenteur.instance(contexte).recupererId(getArguments().getLong("id"));
        if (fermenteur != null) {
            if (fermenteur.getBrassin(contexte) != null) {
                layout.addView(new VueBrassin(contexte, fermenteur.getBrassin(contexte)));
            }
            layout.addView(new VueFermenteur(contexte, this, fermenteur));
        } else {
            TextView txtErreur = new TextView(contexte);
            txtErreur.setText("Aucun fermenteur sélectionné");
            layout.addView(txtErreur);
        }

        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(layout);

        return layoutVerticalScroll;
    }

    @Override
    public void invalidate() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentTableauDeBord());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentTableauDeBord());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }
}
