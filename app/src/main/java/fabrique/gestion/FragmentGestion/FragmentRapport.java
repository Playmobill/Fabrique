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
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.R;

public class FragmentRapport extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

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
        texteActuel = (TextView)view.findViewById(R.id.texteActuel);
        btnSuivant = (Button)view.findViewById(R.id.btnSuivant);

        tableau = (TableLayout)view.findViewById(R.id.tableau);

        afficher(System.currentTimeMillis());

        return view;
    }

    public void afficher(long date) {
        Calendar dateDuJour = Calendar.getInstance();
        dateDuJour.setTimeInMillis(date);

        GregorianCalendar calendrier = new GregorianCalendar(dateDuJour.get(Calendar.YEAR), dateDuJour.get(Calendar.MONTH)-1, dateDuJour.get(Calendar.DAY_OF_MONTH));
        btnPrecedent.setText(DateToString.moisToString(calendrier.get(Calendar.MONTH)) + " " + calendrier.get(Calendar.YEAR));

        texteActuel.setText(DateToString.moisToString(dateDuJour.get(Calendar.MONTH))+" "+dateDuJour.get(Calendar.YEAR));

        calendrier = new GregorianCalendar(dateDuJour.get(Calendar.YEAR), dateDuJour.get(Calendar.MONTH)+1, dateDuJour.get(Calendar.DAY_OF_MONTH));
        btnSuivant.setText(DateToString.moisToString(calendrier.get(Calendar.MONTH)) + " " + calendrier.get(Calendar.YEAR));
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

        }

        if (v.equals(btnSuivant)){

        }

    }
}