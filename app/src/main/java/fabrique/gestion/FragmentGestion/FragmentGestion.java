package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentGestion extends FragmentAmeliore implements View.OnClickListener {

    private View view;

    private Button etat, listeHistorique, temps, emplacement;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        view = inflater.inflate(R.layout.activity_gestion, container, false);

        initialiserBouton();

        return view;
    }

    public void initialiserBouton() {
        etat = (Button)view.findViewById(R.id.btnEtat);
        etat.setOnClickListener(this);

        listeHistorique = (Button)view.findViewById(R.id.btnListeHistorique);
        listeHistorique.setOnClickListener(this);

        temps = (Button)view.findViewById(R.id.btnTemps);
        temps.setOnClickListener(this);

        emplacement = (Button)view.findViewById(R.id.btnEmplacement);
        emplacement.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(etat)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentEtat());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(listeHistorique)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentListeHistorique());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(temps)) {
            /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentTemps());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
            transaction.addToBackStack(null).commit();*/
        }
        else if (view.equals(emplacement)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentEmplacement());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
            transaction.addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {}
}
