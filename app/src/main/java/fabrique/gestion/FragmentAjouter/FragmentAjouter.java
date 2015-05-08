package fabrique.gestion.FragmentAjouter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.R;
import fabrique.gestion.FragmentAmeliore;

public class FragmentAjouter extends FragmentAmeliore implements View.OnClickListener {

    private View view;

    private Context contexte;

    private Button fermenteur, cuve, fut, brassin, recette;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        contexte = container.getContext();
        view = inflater.inflate(R.layout.activity_ajouter, container, false);

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
    public void invalidate() {}

    @Override
    public void onClick(View view) {
        if (view.equals(fermenteur)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentAjouterFermenteur());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(cuve)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentAjouterCuve());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(fut)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentAjouterFut());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
        else if (view.equals(brassin)) {
            if(TableFermenteur.instance(contexte).recupererNumerosFermenteurSansBrassin().isEmpty()) {
                Toast.makeText(contexte, "Il n'y a pas de fermenteur libre pouvant accueillir un nouveau brassin.", Toast.LENGTH_LONG).show();
            }
            else{
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.onglet, new FragmentAjouterBrassin());
                transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
                transaction.addToBackStack(null).commit();
            }
        }
        else if (view.equals(recette)) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, new FragmentAjouterRecette());
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
    }

    @Override
    public void onBackPressed() {}
}
