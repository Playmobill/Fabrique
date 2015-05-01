package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

/**
 * Created by thibaut on 01/05/15.
 */
public class FragmentJour extends FragmentAmeliore {

    private Context contexte;
    private View view;
    private LinearLayout affichageEvenements;
    private ArrayList<TextView[]> evenements;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);
        contexte = container.getContext();

        view = new View(contexte);
        affichageEvenements = new LinearLayout(contexte);
        affichageEvenements.setOrientation(LinearLayout.VERTICAL);

        evenements = new ArrayList<>();

        return null;
    }

    @Override
    public void onBackPressed() {
        Log.i("Redirection","redirection");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentCalendrier());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }
}
