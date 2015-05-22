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
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
 * Created by thibaut on 01/05/15.
 */
public class FragmentJour extends FragmentAmeliore implements View.OnClickListener {

    long jour, mois, annee;
    private Context contexte;
    private View view;
    private TextView titre;
    private LinearLayout affichageEvenements;
    private ArrayList<LinearLayout> evenements;
    private ArrayList<Calendrier> calendrier;
    private ArrayList<Historique> historique;
    private Button jourSuivant, jourPrecedent, ajoutEvent;
    private Calendar calendrierTime;

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

        affichageEvenements = (LinearLayout)view.findViewById(R.id.listeEvenement);

        jour = (long)getArguments().get("jour");
        mois = (long)getArguments().get("mois");
        annee = (long)getArguments().get("annee");

        calendrierTime = Calendar.getInstance();

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
    }

    public void initialiserTexte(){
        affichageEvenements.removeAllViews();

        calendrier = TableCalendrier.instance(contexte).getEvenements();
        historique = TableHistorique.instance(contexte).recupererHistorique();

        for (int i = 0; i < calendrier.size(); i++) {
            calendrierTime.setTimeInMillis(calendrier.get(i).getDateEvenement());
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
    }


}
