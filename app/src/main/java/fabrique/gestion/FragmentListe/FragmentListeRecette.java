package fabrique.gestion.FragmentListe;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;
import fabrique.gestion.Vue.VueRecette;

public class FragmentListeRecette extends FragmentAmeliore implements AdapterView.OnItemSelectedListener{

    private Context contexte;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        view = inflater.inflate(R.layout.activity_liste_recette, container, false);

        Spinner liste = (Spinner)view.findViewById(R.id.liste);
        TableRecette tableRecette = TableRecette.instance(contexte);
        ArrayAdapter<String> adapteurRecette= new ArrayAdapter<>(contexte, R.layout.spinner_style, tableRecette.noms());
        adapteurRecette.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste.setAdapter(adapteurRecette);
        liste.setOnItemSelectedListener(this);

        return view;
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListe());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        TableRow ligne = (TableRow)this.view.findViewById(R.id.ligne);
        ligne.removeAllViews();
        ligne.addView(new VueRecette(contexte, TableRecette.instance(contexte).recupererIndex((int)id)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}

}
