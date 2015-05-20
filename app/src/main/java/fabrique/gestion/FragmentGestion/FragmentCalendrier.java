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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    private TableLayout calendrier;
    private TableRow[] ligneJours;
    private ArrayList<BoutonCalendrier> listeBtnJours;
    int longueurBouton, hauteurBouton;
    private Button moisPrecedent, moisSuivant;
    private TextView moisActuel;

    private ArrayList<Calendrier> evenements;
    private ArrayList<Historique> historique;

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
        historique = TableHistorique.instance(contexte).recupererHistorique();

        annee = Calendar.getInstance().get(Calendar.YEAR);
        mois = Calendar.getInstance().get(Calendar.MONTH);
        jour = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        moisPrecedent = (Button)view.findViewById(R.id.moisPrecedent);
        moisActuel = (TextView)view.findViewById(R.id.moisActuel);
        moisSuivant = (Button)view.findViewById(R.id.moisSuivant);

        moisPrecedent.setText(DateToString.moisToString(mois-1)+" "+annee);
        moisActuel.setText(DateToString.moisToString(mois)+" "+annee);
        moisSuivant.setText(DateToString.moisToString(mois+1)+" "+annee);

        moisPrecedent.setOnClickListener(this);
        moisSuivant.setOnClickListener(this);

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

            int compt = 0;

            for (int j = 0; j < evenements.size() ; j++) {
                cal.setTimeInMillis(evenements.get(j).getDateEvenement() * 1000);
                if(cal.get(Calendar.MONTH) == mois && cal.get(Calendar.DAY_OF_MONTH) == i && cal.get(Calendar.YEAR) == annee){
                    if(evenement.equals("")) {
                        evenement = evenements.get(j).getNomEvenement();
                    }
                    else{
                        compt++;
                    }
                }
            }

            for (int j = 0; j < historique.size(); j++) {
                cal.setTimeInMillis(historique.get(j).getDate());
                if(cal.get(Calendar.MONTH) == mois && cal.get(Calendar.DAY_OF_MONTH) == i && cal.get(Calendar.YEAR) == annee){
                    if(evenement.equals("")) {
                        evenement = historique.get(j).getTexte();
                    }
                    else{
                        compt++;
                    }
                }
            }

            listeBtnJours.add(new BoutonCalendrier(contexte, i, mois, annee, longueurBouton, hauteurBouton, evenement, compt));
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
        for (int i = 0; i < listeBtnJours.size(); i++) {
            if (v.equals(listeBtnJours.get(i).bouton)){
                FragmentJour fragmentJour = new FragmentJour();
                Bundle args = new Bundle();
                args.putLong("jour", listeBtnJours.get(i).jour);
                args.putLong("mois", listeBtnJours.get(i).mois);
                args.putLong("annee", listeBtnJours.get(i).annee);
                fragmentJour.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.onglet, fragmentJour);
                transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
                transaction.addToBackStack(null).commit();
            }
        }

        if (v.equals(moisPrecedent)){
            String evenement = "";

            Calendar cal = Calendar.getInstance();
            Date date = new Date(cal.getTimeInMillis());
            if(mois<=0){
                annee--;
            }
            date.setMonth(mois-1);
            cal.setTime(date);
            mois=cal.get(Calendar.MONTH);
            Log.i("Coucou", mois+"");

            listeBtnJours.clear();
            ligneJours = new TableRow[] {new TableRow(contexte), new TableRow(contexte), new TableRow(contexte), new TableRow(contexte)};
            calendrier.removeAllViews();

            int joursDansMois = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= joursDansMois; i++) {
                evenement = "";
                int compt = 0;

                for (int j = 0; j < evenements.size() ; j++) {
                    cal.setTimeInMillis(evenements.get(j).getDateEvenement() * 1000);
                    if(cal.get(Calendar.MONTH) == mois && cal.get(Calendar.DAY_OF_MONTH) == i && cal.get(Calendar.YEAR) == annee){
                        if(evenement.equals("")) {
                            evenement = evenements.get(j).getNomEvenement();
                        }
                        else{
                            compt++;
                        }
                    }
                }

                for (int j = 0; j < historique.size(); j++) {
                    cal.setTimeInMillis(historique.get(j).getDate());
                    if(cal.get(Calendar.MONTH) == mois && cal.get(Calendar.DAY_OF_MONTH) == i && cal.get(Calendar.YEAR) == annee){
                        if(evenement.equals("")) {
                            evenement = historique.get(j).getTexte();
                        }
                        else{
                            compt++;
                        }
                    }
                }
                listeBtnJours.add(new BoutonCalendrier(contexte, i, mois, annee, longueurBouton, hauteurBouton, evenement, compt));
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

            if(mois<=0) {
                moisPrecedent.setText(DateToString.moisToString(mois - 1) + " " + (annee-1));
            }
            else{
                moisPrecedent.setText(DateToString.moisToString(mois - 1) + " " + (annee));
            }

            moisActuel.setText(DateToString.moisToString(mois)+" "+annee);

            if(mois>=11){
                moisSuivant.setText(DateToString.moisToString(mois+1)+" "+(annee+1));
            }
            else{
                moisSuivant.setText(DateToString.moisToString(mois+1)+" "+(annee));
            }

        }

        if (v.equals(moisSuivant)){
            String evenement = "";

            Calendar cal = Calendar.getInstance();
            Date date = new Date(cal.getTimeInMillis());
            if(mois>=11){
                annee++;
            }
            date.setMonth(mois+1);
            cal.setTime(date);
            mois=cal.get(Calendar.MONTH);
            Log.i("Coucou", mois+"");

            listeBtnJours.clear();
            ligneJours = new TableRow[] {new TableRow(contexte), new TableRow(contexte), new TableRow(contexte), new TableRow(contexte)};
            calendrier.removeAllViews();

            int joursDansMois = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 1; i <= joursDansMois; i++) {
                evenement = "";
                int compt = 0;

                for (int j = 0; j < evenements.size() ; j++) {
                    cal.setTimeInMillis(evenements.get(j).getDateEvenement() * 1000);
                    if(cal.get(Calendar.MONTH) == mois && cal.get(Calendar.DAY_OF_MONTH) == i && cal.get(Calendar.YEAR) == annee){
                        if(evenement.equals("")) {
                            evenement = evenements.get(j).getNomEvenement();
                        }
                        else{
                            compt++;
                        }
                    }
                }

                for (int j = 0; j < historique.size(); j++) {
                    cal.setTimeInMillis(historique.get(j).getDate());
                    if(cal.get(Calendar.MONTH) == mois && cal.get(Calendar.DAY_OF_MONTH) == i && cal.get(Calendar.YEAR) == annee){
                        if(evenement.equals("")) {
                            evenement = historique.get(j).getTexte();
                        }
                        else{
                            compt++;
                        }
                    }
                }
                listeBtnJours.add(new BoutonCalendrier(contexte, i, mois, annee, longueurBouton, hauteurBouton, evenement, compt));
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

            if(mois<=0) {
                moisPrecedent.setText(DateToString.moisToString(mois - 1) + " " + (annee-1));
            }
            else{
                moisPrecedent.setText(DateToString.moisToString(mois - 1) + " " + (annee));
            }

            moisActuel.setText(DateToString.moisToString(mois)+" "+annee);

            if(mois>=11){
                moisSuivant.setText(DateToString.moisToString(mois+1)+" "+(annee+1));
            }
            else{
                moisSuivant.setText(DateToString.moisToString(mois+1)+" "+(annee));
            }

        }

    }
}
