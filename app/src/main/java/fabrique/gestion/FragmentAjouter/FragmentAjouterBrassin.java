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

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentAjouterBrassin extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private Button btnAjouter;

    private EditText editNumero, editCommentaire, editQuantite,
            editDensiteOriginale, editDensiteFinale, editPourcentageAlcool;

    private Spinner editRecette;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        View view = inflater.inflate(R.layout.activity_ajouter_brassin, container, false);

        editNumero = (EditText)view.findViewById(R.id.editNumero);
        TableBrassin tableBrassin = TableBrassin.instance(contexte);
        int max = 0;
        for (int i=0; i<tableBrassin.tailleListe(); i=i+1) {
            if (max < tableBrassin.recupererIndex(i).getNumero()) {
                max = tableBrassin.recupererIndex(i).getNumero();
            }
        }
        editNumero.setText("" + (max+1));

        editCommentaire = (EditText)view.findViewById(R.id.editCommentaire);
        editQuantite = (EditText)view.findViewById(R.id.editQuantite);
        editDensiteOriginale = (EditText)view.findViewById(R.id.editDensiteOriginale);
        editDensiteFinale = (EditText)view.findViewById(R.id.editDensiteFinale);
        editPourcentageAlcool = (EditText)view.findViewById(R.id.editPourcentageAlcool);

        editRecette = (Spinner)view.findViewById(R.id.editRecette);
        TableRecette tableRecette = TableRecette.instance(contexte);
        ArrayAdapter<String> adapteurRecette = new ArrayAdapter<>(contexte, R.layout.spinner_style, tableRecette.recupererRecettesActifs());
        adapteurRecette.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editRecette.setAdapter(adapteurRecette);

        btnAjouter = (Button)view.findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAjouter)) {

            String erreur = "";

            int numero = 0;
            if (editNumero.getText().toString().equals("")) {
                erreur = erreur + "Le brassin doit avoir un numéro.";
            } else {
                try {
                    numero = Integer.parseInt(editNumero.getText().toString());
                } catch (NumberFormatException e) {
                    erreur = erreur + "Le numéro est trop grand.";
                }
            }

            int quantite = 0;
            try {
                if (!editQuantite.getText().toString().equals("")) {
                    quantite = Integer.parseInt(editQuantite.getText().toString());
                }
            } catch(NumberFormatException e) {
                if (!erreur.equals("")) {
                    erreur = erreur + "\n";
                }
                erreur = erreur + "La quantité est trop grande.";
            }

            float densiteOriginale = 0;
            try {
                if ((!editDensiteOriginale.getText().toString().equals(""))) {
                    densiteOriginale = Float.parseFloat(editDensiteOriginale.getText().toString());
                }
            } catch (NumberFormatException e) {
                if (!erreur.equals("")) {
                    erreur = erreur + "\n";
                }
                erreur = erreur + "La densité originale est trop grande.";
            }

            float densiteFinale = 0;
            try {
                if ((editDensiteFinale.getText() != null) && (!editDensiteFinale.getText().toString().equals(""))) {
                    densiteFinale = Float.parseFloat(editDensiteFinale.getText().toString());
                }
            } catch (NumberFormatException e) {
                if (!erreur.equals("")) {
                    erreur = erreur + "\n";
                }
                erreur = erreur + "La densité final est trop grande.";
            }

            float pourcentageAlcool = 0;
            try {
                if ((editPourcentageAlcool.getText() != null) && (!editPourcentageAlcool.getText().toString().equals(""))) {
                    pourcentageAlcool = Float.parseFloat(editPourcentageAlcool.getText().toString());
                }
            } catch (NumberFormatException e) {
                if (!erreur.equals("")) {
                    erreur = erreur + "\n";
                }
                erreur = erreur + "Le pourcentage d'alcool est trop grand.";
            }

            if (erreur.equals("")) {
                long recette = TableRecette.instance(contexte).recupererIndex(editRecette.getSelectedItemPosition()).getId();

                //Date avec seulement jour, mois annee
                Calendar calendrier = Calendar.getInstance();
                calendrier.setTimeInMillis(System.currentTimeMillis());
                long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

                TableBrassin.instance(contexte).ajouter(numero, editCommentaire.getText().toString() + "", date, quantite, recette, densiteOriginale, densiteFinale, pourcentageAlcool);
                Toast.makeText(contexte, "Brassin ajouté !", Toast.LENGTH_LONG).show();
                TableBrassin tableBrassin = TableBrassin.instance(contexte);

                int max = 0;
                for (int i=0; i<tableBrassin.tailleListe(); i=i+1) {
                    if (max < tableBrassin.recupererIndex(i).getNumero()) {
                        max = tableBrassin.recupererIndex(i).getNumero();
                    }
                }
                editNumero.setText("" + (max+1));
            } else {
                Toast.makeText(contexte, erreur, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentAjouter());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        transaction.addToBackStack(null).commit();
    }
}
