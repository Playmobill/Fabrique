package fabrique.gestion.FragmentListe;

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
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.R;
import fabrique.gestion.Vue.VueBrassin;
import fabrique.gestion.Vue.VueFut;

public class FragmentVueFut extends FragmentAmeliore {

    private long id_fut;

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

        id_fut = getArguments().getLong("id");
        Fut fut = TableFut.instance(contexte).recupererId(id_fut);
        if (fut != null) {
            if (fut.getBrassin(contexte) != null) {
                layout.addView(new VueBrassin(contexte, fut.getBrassin(contexte)));
            }
            layout.addView(new VueFut(contexte, this, fut));
        } else {
            TextView txtErreur = new TextView(contexte);
            txtErreur.setText("Aucun fut sélectionné");
            layout.addView(txtErreur);
        }

        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(layout);

        return layoutVerticalScroll;
    }

    @Override
    public void invalidate() {
        FragmentVueFut fragmentVueFut = new FragmentVueFut();
        Bundle args = new Bundle();
        args.putLong("id", id_fut);
        fragmentVueFut.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueFut);
        transaction.setTransition((FragmentTransaction.TRANSIT_NONE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListeFut());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }
}
