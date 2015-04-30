package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

/**
 * Created by thibaut on 30/04/15.
 */
public class FragmentCalendrier extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;
    private View view;
    private TableLayout calendrier;
    private TableRow[] ligneJours;
    private ArrayList<BoutonCalendrier> listeBtnJours;
    int longueurBouton, hauteurBouton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);
        contexte = container.getContext();
        View view = inflater.inflate(R.layout.activity_calendrier, container, false);

        calendrier = (TableLayout)view.findViewById(R.id.calendrier);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        longueurBouton = metrics.widthPixels/8;
        hauteurBouton = metrics.heightPixels/5;

        ligneJours = new TableRow[] {new TableRow(contexte), new TableRow(contexte), new TableRow(contexte), new TableRow(contexte)};
        listeBtnJours = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            listeBtnJours.add(new BoutonCalendrier(contexte, i, longueurBouton, hauteurBouton));
        }

        int i = 0;
        int k = 0;
        while(i<4){
            for (int j = 0; j < 8 && k<listeBtnJours.size(); j++) {
                Log.i("Ajout", k + " dans " + i);
                ligneJours[i].addView(listeBtnJours.get(k));
                k++;
            }
            calendrier.addView(ligneJours[i]);
            i++;
        }

        return view;
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {

    }
}
