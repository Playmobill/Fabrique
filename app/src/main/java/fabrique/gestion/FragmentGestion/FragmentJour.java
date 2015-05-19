package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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
 * Created by thibaut on 01/05/15.
 */
public class FragmentJour extends FragmentAmeliore {

    long jour, mois, annee;
    private Context contexte;
    private View view;
    private TextView titre;
    private LinearLayout affichageEvenements;
    private ArrayList<LinearLayout> evenements;
    private ArrayList<Calendrier> calendrier;
    private ArrayList<Historique> historique;
    private Button jourSuivant, jourPrecedent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);
        contexte = container.getContext();

        view = inflater.inflate(R.layout.activity_vue_jour, container, false);

        calendrier = TableCalendrier.instance(contexte).getEvenements();
        historique = TableHistorique.instance(contexte).recupererHistorique();

        affichageEvenements = (LinearLayout)view.findViewById(R.id.listeEvenement);

        jour = (long)getArguments().get("jour");
        mois = (long)getArguments().get("mois");
        annee = (long)getArguments().get("annee");

        Calendar calendrierTime = Calendar.getInstance();

        titre = (TextView)view.findViewById(R.id.txtJourActuel);
        titre.setText(jour+" "+DateToString.moisToString(Integer.parseInt(""+mois))+" "+annee);

        jourPrecedent = (Button)view.findViewById(R.id.btnJourPrecedent);
        if(jour-1<=0){
            Date date = new Date(calendrierTime.getTimeInMillis());
            date.setMonth((int)mois-1);
            calendrierTime.setTime(date);
            date.setDate(calendrierTime.getActualMaximum(Calendar.DAY_OF_MONTH));
            calendrierTime.setTime(date);
            jourPrecedent.setText(calendrierTime.get(Calendar.DAY_OF_MONTH)+ " " + DateToString.moisToString(Integer.parseInt(""+calendrierTime.get(Calendar.MONTH))) + " " + annee);
        }else {
            jourPrecedent.setText(jour - 1 + " " + DateToString.moisToString(Integer.parseInt("" + mois)) + " " + annee);
        }

        jourSuivant = (Button)view.findViewById(R.id.btnJourSuivant);
        calendrierTime.setTime(new Date((int)annee, (int)mois, (int)jour));
        if(jour+1>calendrierTime.getActualMaximum(Calendar.DAY_OF_MONTH)){
            Date date = new Date(calendrierTime.getTimeInMillis());
            date.setMonth((int)mois+1);
            date.setDate(1);
            calendrierTime.setTime(date);
            jourSuivant.setText(calendrierTime.get(Calendar.DAY_OF_MONTH)+ " " + DateToString.moisToString(Integer.parseInt(""+calendrierTime.get(Calendar.MONTH))) + " " + annee);
        }else {
            jourSuivant.setText(jour + 1 + " " + DateToString.moisToString(Integer.parseInt("" + mois)) + " " + annee);
        }

        affichageEvenements = (LinearLayout)view.findViewById(R.id.listeEvenement);
        evenements = new ArrayList<>();

        for (int i = 0; i < calendrier.size(); i++) {
            calendrierTime.setTimeInMillis(calendrier.get(i).getDateEvenement()*1000);
            if(calendrierTime.get(Calendar.MONTH) == mois && calendrierTime.get(Calendar.DAY_OF_MONTH) == jour && calendrierTime.get(Calendar.YEAR) == annee){
                TextView textView= new TextView(contexte);
                textView.setText(calendrier.get(i).getNomEvenement());
                textView.setPadding(30,30,0,0);
                affichageEvenements.addView(textView);
            }
        }

        for (int i = 0; i < historique.size(); i++) {
            calendrierTime.setTimeInMillis(historique.get(i).getDate());
            if(calendrierTime.get(Calendar.MONTH) == mois && calendrierTime.get(Calendar.DAY_OF_MONTH) == jour && calendrierTime.get(Calendar.YEAR) == annee){
                TextView textView= new TextView(contexte);
                textView.setText(historique.get(i).getTexte());
                textView.setPadding(30,30,0,0);
                affichageEvenements.addView(textView);
            }
        }

        return view;
    }

    @Override
    public void invalidate() {}

    @Override
    public void onBackPressed() {
        Log.i("Redirection","redirection");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentCalendrier());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }
}
