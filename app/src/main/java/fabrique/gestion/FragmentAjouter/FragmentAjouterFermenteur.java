package fabrique.gestion.FragmentAjouter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Objets.Emplacement;
import fabrique.gestion.R;
import fabrique.gestion.FragmentAmeliore;

public class FragmentAjouterFermenteur extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private ArrayList<Emplacement> emplacements;

    private Button btnAjouter;

    private EditText editNumero, editQuantite;

    private Spinner editEmplacement;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        View view = inflater.inflate(R.layout.activity_ajouter_fermenteur, container, false);

        emplacements = TableEmplacement.instance(contexte).recupererActifs();
        if (emplacements.size() == 0) {
            Toast.makeText(contexte, "Il faut avoir au moins UN emplacement ACTIF pour pouvoir ajouter un fermenteur.", Toast.LENGTH_LONG).show();
            onBackPressed();
        }
        ArrayList<String> texteEmplacements = new ArrayList<>();
        for (int i=0; i<emplacements.size() ; i++) {
            texteEmplacements.add(emplacements.get(i).getTexte());
        }

        editNumero = (EditText)view.findViewById(R.id.editNumero);
        TableFermenteur tableFermenteur = TableFermenteur.instance(contexte);
        int numero = 1;
        for (int i=0; i<tableFermenteur.tailleListe(); i++) {
            if (tableFermenteur.recupererIndex(i).getNumero() == numero) {
                numero = numero + 1;
            }
        }
        editNumero.setText("" + numero);

        editQuantite = (EditText)view.findViewById(R.id.editQuantite);

        editEmplacement = (Spinner)view.findViewById(R.id.editEmplacement);
        ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(contexte, R.layout.spinner_style, texteEmplacements);
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEmplacement.setAdapter(adapteurEmplacement);

        btnAjouter = (Button)view.findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);

        return view;
    }

    private void ajouter() {
        String erreur = "";

        int numero = 0;
        if (editNumero.getText().toString().equals("")) {
            erreur = erreur + "Le fermenteur doit avoir un numéro.";
        } else {
            try {
                numero = Integer.parseInt(editNumero.getText().toString());
            } catch (NumberFormatException e) {
                erreur = erreur + "Le numéro est trop grand.";
            }
        }

        int capacite = 0;
        try {
            if (!editQuantite.getText().toString().equals("")) {
                capacite = Integer.parseInt(editQuantite.getText().toString());
            }
        } catch(NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La quantité est trop grande.";
        }

        if (erreur.equals("")) {
            long emplacement = emplacements.get(editEmplacement.getSelectedItemPosition()).getId();

            //Date avec seulement j0our, mois, annee
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

            TableFermenteur.instance(contexte).ajouter(numero, capacite, emplacement, date, 1, date, -1, true);

            Toast.makeText(contexte, "Fermenteur ajouté !", Toast.LENGTH_LONG).show();

            TableFermenteur tableFermenteur = TableFermenteur.instance(contexte);
            int numeroSuivant = 1;
            for (int i=0; i<tableFermenteur.tailleListe(); i++) {
                if (tableFermenteur.recupererIndex(i).getNumero() == numeroSuivant) {
                    numeroSuivant = numeroSuivant + 1;
                }
            }
            editNumero.setText("" + numeroSuivant);
        } else {
            Toast.makeText(contexte, erreur, Toast.LENGTH_LONG).show();
        }
    }

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
}