package fabrique.gestion.FragmentAjouter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentAjouterFut extends FragmentAmeliore implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context contexte;

    private Button btnAjouter;

    private EditText editNumero;

    private Spinner spinnerCapacite;
    private EditText editCapacite;
    private int capacite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        View view = inflater.inflate(R.layout.activity_ajouter_fut, container, false);

        editNumero = (EditText)view.findViewById(R.id.editNumero);
        TableFut tableFut = TableFut.instance(contexte);
        int numero = 1;
        for (int i=0; i<tableFut.tailleListe(); i++) {
            if (tableFut.recupererIndex(i).getNumero() == numero) {
                numero = numero + 1;
            }
        }
        editNumero.setText("" + numero);

        spinnerCapacite = (Spinner)view.findViewById(R.id.spinnerCapacite);
        spinnerCapacite.setOnItemSelectedListener(this);

        editCapacite = (EditText)view.findViewById(R.id.editCapacite);
        editCapacite.setEnabled(true);

        btnAjouter = (Button)view.findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);

        return view;
    }

    private void ajouter() {
        String erreur = "";

        int numero = 0;
        if (editNumero.getText().toString().equals("")) {
            erreur = erreur + "Le fût doit avoir un numéro.";
        } else {
            try {
                numero = Integer.parseInt(editNumero.getText().toString());
            } catch (NumberFormatException e) {
                erreur = erreur + "Le numéro est trop grand.";
            }
        }

        if (spinnerCapacite.getSelectedItemPosition() == 4) {
            capacite = 0;
            try {
                if (!editCapacite.getText().toString().equals("")) {
                    capacite = Integer.parseInt(editCapacite.getText().toString());
                }
            } catch (NumberFormatException e) {
                if (!erreur.equals("")) {
                    erreur = erreur + "\n";
                }
                erreur = erreur + "La quantité est trop grande.";
            }
        }

        if (erreur.equals("")) {
            //Date avec seulement jour, mois, annee
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

            TableFut.instance(contexte).ajouter(numero, capacite, 1, date, -1, date, true);

            Toast.makeText(contexte, "Fut ajouté !", Toast.LENGTH_SHORT).show();

            TableFut tableFut = TableFut.instance(contexte);
            int numeroSuivant = 1;
            for (int i=0; i<tableFut.tailleListe(); i++) {
                if (tableFut.recupererIndex(i).getNumero() == numeroSuivant) {
                    numeroSuivant = numeroSuivant + 1;
                }
            }
            editNumero.setText("" + numeroSuivant);
        } else {
            Toast.makeText(contexte, erreur, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void invalidate() {}

    @Override
    public void onClick(View v) {
        if (v.equals(btnAjouter)) {
            ajouter();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentAjouter());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(id == 0) {
            capacite = 20;
            editCapacite.setVisibility(View.INVISIBLE);
        } else if(id == 1) {
            capacite = 30;
            editCapacite.setVisibility(View.INVISIBLE);
        } else if(id == 2) {
            capacite = 50;
            editCapacite.setVisibility(View.INVISIBLE);
        } else if(id == 3) {
            capacite = 58;
            editCapacite.setVisibility(View.INVISIBLE);
        } else if(id == 4) {
            editCapacite.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}