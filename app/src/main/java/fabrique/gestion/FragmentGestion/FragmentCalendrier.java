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
import java.util.Calendar;
import java.util.Date;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCalendrier;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Calendrier;
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

    private ArrayList<Calendrier> evenements;

    int annee, mois, jour;

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

        evenements = TableCalendrier.instance(contexte).getEvenements();

        annee = Calendar.getInstance().get(Calendar.YEAR);
        mois = Calendar.getInstance().get(Calendar.MONTH);
        jour = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        calendrier = (TableLayout)view.findViewById(R.id.calendrier);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        longueurBouton = metrics.widthPixels/8;
        hauteurBouton = metrics.heightPixels/5;

        ligneJours = new TableRow[] {new TableRow(contexte), new TableRow(contexte), new TableRow(contexte), new TableRow(contexte)};
        listeBtnJours = new ArrayList<>();
        String evenement = "";

        Calendar cal = Calendar.getInstance();
        int joursDansMois = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= joursDansMois; i++) {
            evenement = "";
            for (int j = 0; j < evenements.size() && evenement.equals(""); j++) {
                cal.setTimeInMillis(evenements.get(j).getDateEvenement()*1000);
                if(cal.get(Calendar.YEAR) == annee && cal.get(Calendar.MONTH) == mois && cal.get(Calendar.DAY_OF_MONTH) == i){
                    evenement = evenements.get(j).getNomEvenement();
                }
            }
            listeBtnJours.add(new BoutonCalendrier(contexte, i, longueurBouton, hauteurBouton, evenement));
        }

        int k = 0;
        for(int i = 0 ; i<4; i++){
            for (int j = 0; j < 8 && k<listeBtnJours.size(); j++) {
                ligneJours[i].addView(listeBtnJours.get(k));
                listeBtnJours.get(k).bouton.setOnClickListener(this);
                k++;
            }
            calendrier.addView(ligneJours[i]);
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
        for (int i = 0; i < listeBtnJours.size(); i++) {
            if (v.equals(listeBtnJours.get(i).bouton)){
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.onglet, new FragmentJour());
                transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
                transaction.addToBackStack(null).commit();
            }
        }
    }
}
