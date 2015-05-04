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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCalendrier;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Calendrier;
import fabrique.gestion.Objets.DateToString;
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
    private RelativeLayout layoutTitre;
    private ArrayList<LinearLayout> evenements;
    private ArrayList<Calendrier> calendrier;
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

        affichageEvenements = (LinearLayout)view.findViewById(R.id.listeEvenement);

        jour = (long)getArguments().get("jour");
        mois = (long)getArguments().get("mois");
        annee = (long)getArguments().get("annee");

        titre = (TextView)view.findViewById(R.id.txtJourActuel);
        titre.setText(jour+" "+DateToString.moisToString(Integer.parseInt(""+mois))+" "+annee);
        jourPrecedent = (Button)view.findViewById(R.id.btnJourPrecedent);
        jourPrecedent.setText(jour-1+" "+DateToString.moisToString(Integer.parseInt(""+mois))+" "+annee);
        jourSuivant = (Button)view.findViewById(R.id.btnJourSuivant);
        jourSuivant.setText(jour+1+" "+DateToString.moisToString(Integer.parseInt(""+mois))+" "+annee);

        affichageEvenements = (LinearLayout)view.findViewById(R.id.listeEvenement);
        evenements = new ArrayList<>();

        Calendar calendrierTime = Calendar.getInstance();
        for (int i = 0; i < calendrier.size(); i++) {
            calendrierTime.setTimeInMillis(calendrier.get(i).getDateEvenement()*1000);
            if(calendrierTime.get(Calendar.MONTH) == mois && calendrierTime.get(Calendar.DAY_OF_MONTH) == jour && calendrierTime.get(Calendar.YEAR) == annee){
                TextView textView= new TextView(contexte);
                textView.setText(calendrier.get(i).getNomEvenement());
                affichageEvenements.addView(textView);
            }
        }

        return view;
    }

    @Override
    public void onBackPressed() {
        Log.i("Redirection","redirection");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentCalendrier());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }
}
