package fabrique.gestion.FragmentListe;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.R;
import fabrique.gestion.FragmentAmeliore;

public class FragmentListe extends FragmentAmeliore implements View.OnClickListener {

    private View view;

    private Button fermenteur, cuve, fut, brassin, recette;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        view = inflater.inflate(R.layout.activity_liste, container, false);

        initialiserBouton();

        return view;
    }

    public void initialiserBouton() {
        fermenteur = (Button)view.findViewById(R.id.btnFermenteur);
        fermenteur.setOnClickListener(this);

        cuve = (Button)view.findViewById(R.id.btnCuve);
        cuve.setOnClickListener(this);

        fut = (Button)view.findViewById(R.id.btnFut);
        fut.setOnClickListener(this);

        brassin = (Button)view.findViewById(R.id.btnBrassin);
        brassin.setOnClickListener(this);

        recette = (Button)view.findViewById(R.id.btnRecette);
        recette.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fermenteur)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentListeFermenteur());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(cuve)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentListeCuve());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(fut)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentListeFut());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(brassin)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentListeBrassin());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(recette)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentListeRecette());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {}
}
