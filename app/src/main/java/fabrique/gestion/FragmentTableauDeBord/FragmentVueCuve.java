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
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.R;
import fabrique.gestion.Vue.VueBrassin;
import fabrique.gestion.Vue.VueCuve;

public class FragmentVueCuve extends FragmentAmeliore {

    private Cuve cuve;

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

        cuve = TableCuve.instance(contexte).recupererId(getArguments().getLong("id"));
        if (cuve != null) {
            if (cuve.getBrassin(contexte) != null) {
                layout.addView(new VueBrassin(contexte, cuve.getBrassin(contexte)));
            }
            layout.addView(new VueCuve(contexte, this, cuve));
        } else {
            TextView txtErreur = new TextView(contexte);
            txtErreur.setText("Aucune cuve sélectionnée");
        }

        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(layout);

        return layoutVerticalScroll;
    }

    @Override
    public void invalidate() {
        FragmentVueCuve fragmentVueCuve = new FragmentVueCuve();
        Bundle args = new Bundle();
        args.putLong("id", cuve.getId());
        fragmentVueCuve.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueCuve);
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
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
