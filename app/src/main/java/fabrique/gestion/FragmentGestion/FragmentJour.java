package fabrique.gestion.FragmentGestion;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCalendrier;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Calendrier;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.R;

public class FragmentJour extends FragmentAmeliore implements View.OnClickListener {

    private long date;
    private Context contexte;
    private LinearLayout affichageEvenements, affichageHistorique;
    private Button jourSuivant, jourPrecedent, ajoutEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);
        contexte = container.getContext();

        View view = inflater.inflate(R.layout.activity_vue_jour, container, false);

        affichageEvenements = (LinearLayout)view.findViewById(R.id.listeEvenement);

        date = getArguments().getLong("date");

        TextView titre = (TextView)view.findViewById(R.id.txtJourActuel);
        titre.setText(DateToString.dateToString(date));

        jourPrecedent = (Button)view.findViewById(R.id.btnJourPrecedent);
        jourPrecedent.setText(DateToString.dateToString(date - 1000 * 60 * 60 * 24));
        jourPrecedent.setOnClickListener(this);

        jourSuivant = (Button)view.findViewById(R.id.btnJourSuivant);
        jourSuivant.setText(DateToString.dateToString(date+1000*60*60*24));
        jourSuivant.setOnClickListener(this);

        affichageEvenements = (LinearLayout)view.findViewById(R.id.listeEvenement);
        affichageHistorique = (LinearLayout)view.findViewById(R.id.listeHistorique);

        initialiserTexte();

        ajoutEvent = (Button) view.findViewById(R.id.BtnAjoutEvent);
        ajoutEvent.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v.equals(ajoutEvent)){
            LayoutInflater li = LayoutInflater.from(contexte);
            View promptFormulaire = li.inflate(R.layout.dialog_ajout_evenement, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(contexte);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptFormulaire);

            final EditText nomInput = (EditText) promptFormulaire.findViewById(R.id.editNomEvent);
            final DatePicker dateInput = (DatePicker) promptFormulaire.findViewById(R.id.editDatePicker);

            // set dialog message
            alertDialogBuilder.setCancelable(false).setPositiveButton("Ajouter",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // get user input and set it to result
                            // edit text => result

                            String nomEventET = nomInput.getText().toString();
                            long date = new GregorianCalendar(dateInput.getYear(), dateInput.getMonth(), dateInput.getDayOfMonth()).getTimeInMillis();

                            long result = TableCalendrier.instance(contexte).ajouter(date, nomEventET, 0,0);

                            Log.i("Coucou", ""+result);
                            initialiserTexte();

                        }
                    })
                    .setNegativeButton("Annuler",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
        else if (v.equals(jourPrecedent)) {
            FragmentJour fragmentJour = new FragmentJour();
            Bundle args = new Bundle();
            args.putLong("date", date - 1000 * 60 * 60 * 24);
            fragmentJour.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, fragmentJour);
            transaction.setTransition((FragmentTransaction.TRANSIT_NONE));
            transaction.addToBackStack(null).commit();
        }
        else if (v.equals(jourSuivant)) {
            FragmentJour fragmentJour = new FragmentJour();
            Bundle args = new Bundle();
            args.putLong("date", date+1000*60*60*24);
            fragmentJour.setArguments(args);

            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, fragmentJour);
            transaction.setTransition((FragmentTransaction.TRANSIT_NONE));
            transaction.addToBackStack(null).commit();
        }
    }

    public void initialiserTexte(){
        affichageEvenements.removeAllViews();
        affichageHistorique.removeAllViews();

        ArrayList<Calendrier> calendriers = TableCalendrier.instance(contexte).getEvenements();
        ArrayList<Historique> historiques = TableHistorique.instance(contexte).recupererHistorique();

        for (int i = 0; i < historiques.size(); i++) {
            if(historiques.get(i).getDate() == date){
                TextView textView= new TextView(contexte);
                textView.setText(historiques.get(i).getTexte());
                textView.setPadding(30,0,0,15);
                affichageHistorique.addView(textView);
            }
        }

        for (int i = 0; i < calendriers.size(); i++) {
            if(calendriers.get(i).getDateEvenement() == date) {
                TextView textView= new TextView(contexte);
                textView.setText(calendriers.get(i).getNomEvenement());
                textView.setPadding(30,0,0,15);
                affichageEvenements.addView(textView);
            }
        }
    }
}