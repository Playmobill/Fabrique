package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import fabrique.gestion.BDD.TableRapport;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Rapport;
import fabrique.gestion.R;

public class FragmentRapport extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private long date;

    private Button btnPrecedent, btnSuivant;
    private TextView texteActuel;
    private TableLayout tableau;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        contexte = container.getContext();
        View view = inflater.inflate(R.layout.activity_rapport, container, false);

        btnPrecedent = (Button)view.findViewById(R.id.btnPrecedent);
        btnPrecedent.setOnClickListener(this);
        texteActuel = (TextView)view.findViewById(R.id.texteActuel);
        btnSuivant = (Button)view.findViewById(R.id.btnSuivant);
        btnSuivant.setOnClickListener(this);

        tableau = (TableLayout)view.findViewById(R.id.tableau);

        date = System.currentTimeMillis();

        afficher(date);

        return view;
    }

    private void afficher(long date) {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(date);

        GregorianCalendar nouveauCalendrier = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH)-1, calendrier.get(Calendar.DAY_OF_MONTH));
        btnPrecedent.setText(DateToString.moisToString(nouveauCalendrier.get(Calendar.MONTH)) + " " + nouveauCalendrier.get(Calendar.YEAR));

        texteActuel.setText(DateToString.moisToString(calendrier.get(Calendar.MONTH))+" "+calendrier.get(Calendar.YEAR));

        nouveauCalendrier = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH)+1, calendrier.get(Calendar.DAY_OF_MONTH));
        btnSuivant.setText(DateToString.moisToString(nouveauCalendrier.get(Calendar.MONTH)) + " " + nouveauCalendrier.get(Calendar.YEAR));

        afficherBrassin(calendrier.get(Calendar.MONTH), calendrier.get(Calendar.YEAR));
    }

    private void afficherBrassin(int mois, int annee) {
        ArrayList<Rapport> rapports = TableRapport.instance(contexte).recupererRapport(mois, annee);

        for (int i=0; i<rapports.size(); i++) {
            TableRow ligne = new TableRow(contexte);
                TextView quantiteFermente = new TextView(contexte);
                quantiteFermente.setText(rapports.get(i).getQuantiteFermente());
            ligne.addView(quantiteFermente);
                TextView quantiteTransfere = new TextView(contexte);
                quantiteTransfere.setText(rapports.get(i).getQuantiteTransfere());
            ligne.addView(quantiteTransfere);
                TextView quantiteUtilise = new TextView(contexte);
                quantiteUtilise.setText(rapports.get(i).getQuantiteUtilise());
            ligne.addView(quantiteUtilise);
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
        if (v.equals(btnPrecedent)) {
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(date);
            GregorianCalendar nouveauCalendrier = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH)-1, calendrier.get(Calendar.DAY_OF_MONTH));
            date = nouveauCalendrier.getTimeInMillis();
            afficher(date);
        }

        if (v.equals(btnSuivant)){
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(date);
            GregorianCalendar nouveauCalendrier = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH)+1, calendrier.get(Calendar.DAY_OF_MONTH));
            date = nouveauCalendrier.getTimeInMillis();
            afficher(date);
        }
    }
}