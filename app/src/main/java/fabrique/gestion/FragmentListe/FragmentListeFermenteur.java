package fabrique.gestion.FragmentListe;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;
import fabrique.gestion.Vue.VueFermenteur;

public class FragmentListeFermenteur extends FragmentAmeliore implements OnItemSelectedListener {

    private Context contexte;

    private View view;

    private Spinner liste;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        view = inflater.inflate(R.layout.activity_liste_fermenteur, container, false);

        liste = (Spinner)view.findViewById(R.id.liste);
        TableFermenteur tableFermenteur = TableFermenteur.instance(contexte);
        ArrayAdapter<String> adapteurFermenteur = new ArrayAdapter<>(contexte, R.layout.spinner_style, tableFermenteur.numeros());
        adapteurFermenteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste.setAdapter(adapteurFermenteur);
        liste.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListe());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TableRow ligne = (TableRow)this.view.findViewById(R.id.ligne);
        ligne.removeAllViews();
        ligne.addView(new VueFermenteur(contexte, TableFermenteur.instance(contexte).recupererIndex((int)id)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

    @Override
    public void invalidate() {
        TableFermenteur tableFermenteur = TableFermenteur.instance(contexte);
        ArrayAdapter<String> adapteurFermenteur = new ArrayAdapter<>(contexte, R.layout.spinner_style, tableFermenteur.numeros());
        adapteurFermenteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste.setAdapter(adapteurFermenteur);
    }
}
