package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCalendrier;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Calendrier;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.R;

/**
 * Created by thibaut on 30/04/15.
 */
public class FragmentCalendrier extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;
    private View view;
    private long date;
    private TableLayout tableauCalendrier;
    int longueurBouton, hauteurBouton;
    private Button moisPrecedent, moisSuivant;
    private TextView moisActuel;

    private ArrayList<Calendrier> evenements;
    private ArrayList<Historique> historiques;

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
        historiques = TableHistorique.instance(contexte).recupererHistorique();

        date = System.currentTimeMillis();

        moisPrecedent = (Button)view.findViewById(R.id.moisPrecedent);
        moisPrecedent.setOnClickListener(this);

        moisActuel = (TextView)view.findViewById(R.id.moisActuel);

        moisSuivant = (Button)view.findViewById(R.id.moisSuivant);
        moisSuivant.setOnClickListener(this);

        tableauCalendrier = (TableLayout)view.findViewById(R.id.calendrier);

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        longueurBouton = metrics.widthPixels/8;
        hauteurBouton = metrics.heightPixels/5;

        afficher(date);

        return view;
    }

    private void afficher(long date) {
        tableauCalendrier.removeAllViews();

        Calendar dateDuJour = Calendar.getInstance();
        dateDuJour.setTimeInMillis(date);

        GregorianCalendar calendrier = new GregorianCalendar(dateDuJour.get(Calendar.YEAR), dateDuJour.get(Calendar.MONTH)-1, dateDuJour.get(Calendar.DAY_OF_MONTH));
        moisPrecedent.setText(DateToString.moisToString(calendrier.get(Calendar.MONTH)) + " " + calendrier.get(Calendar.YEAR));

        moisActuel.setText(DateToString.moisToString(dateDuJour.get(Calendar.MONTH))+" "+dateDuJour.get(Calendar.YEAR));

        calendrier = new GregorianCalendar(dateDuJour.get(Calendar.YEAR), dateDuJour.get(Calendar.MONTH)+1, dateDuJour.get(Calendar.DAY_OF_MONTH));
        moisSuivant.setText(DateToString.moisToString(calendrier.get(Calendar.MONTH)) + " " + calendrier.get(Calendar.YEAR));

        TableRow[] ligneJours = new TableRow[] {new TableRow(contexte), new TableRow(contexte), new TableRow(contexte), new TableRow(contexte)};
        String evenement;

        ArrayList<BoutonCalendrier> listeBtnJours = new ArrayList<>();

        int joursDansMois = dateDuJour.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i=1; i<=joursDansMois; i++) {
            calendrier = new GregorianCalendar(dateDuJour.get(Calendar.YEAR), dateDuJour.get(Calendar.MONTH), i);

            evenement = "";

            int compt = 0;

            for (int j=0; j<evenements.size(); j++) {
                if (evenements.get(j).getDateEvenement() == calendrier.getTimeInMillis()) {
                    if (evenement.equals("")) {
                        evenement = evenements.get(j).getNomEvenement();
                    }
                    else {
                        compt++;
                    }
                }
            }

            for (int j = 0; j < historiques.size(); j++) {
                if (historiques.get(j).getDate() == calendrier.getTimeInMillis()) {
                    if (evenement.equals("")) {
                        evenement = historiques.get(j).getTexte();
                    }
                    else {
                        compt++;
                    }
                }
            }
            listeBtnJours.add(new BoutonCalendrier(contexte, this, calendrier.getTimeInMillis(), longueurBouton, hauteurBouton, evenement, compt));
        }

        int k = 0;
        for(int i = 0 ; i<4; i++){
            for (int j = 0; j < 8 && k<listeBtnJours.size(); j++) {
                ligneJours[i].addView(listeBtnJours.get(k));
                k++;
            }
            this.tableauCalendrier.addView(ligneJours[i]);
        }
    }

    @Override
    public void invalidate() {}

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(moisPrecedent)) {
            Calendar dateDuJour = Calendar.getInstance();
            dateDuJour.setTimeInMillis(date);
            GregorianCalendar calendrier = new GregorianCalendar(dateDuJour.get(Calendar.YEAR), dateDuJour.get(Calendar.MONTH)-1, dateDuJour.get(Calendar.DAY_OF_MONTH));
            date = calendrier.getTimeInMillis();
            afficher(date);
        }

        if (v.equals(moisSuivant)){
            Calendar dateDuJour = Calendar.getInstance();
            dateDuJour.setTimeInMillis(date);
            GregorianCalendar calendrier = new GregorianCalendar(dateDuJour.get(Calendar.YEAR), dateDuJour.get(Calendar.MONTH)+1, dateDuJour.get(Calendar.DAY_OF_MONTH));
            date = calendrier.getTimeInMillis();
            afficher(date);
        }

    }
}