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
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableBrassinPere;
import fabrique.gestion.BDD.TableCheminBrassinFermenteur;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.R;

public class FragmentAjouterBrassin extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;
    private ArrayList<Fermenteur> listeFermenteursDisponibles;
    private ArrayList<String> labelsListeDeroulanteFermenteurs;

    private Button btnAjouter;

    private EditText editNumero, editCommentaire, editQuantite,
            editDensiteOriginale, editDensiteFinale;

    private Spinner editRecette, editFermenteur;

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

        editRecette = (Spinner)view.findViewById(R.id.editRecette);
        ArrayAdapter<String> adapteurRecette = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableRecette.instance(contexte).recupererNomRecettesActifs());
        adapteurRecette.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editRecette.setAdapter(adapteurRecette);

        listeFermenteursDisponibles = TableFermenteur.instance(contexte).recupererFermenteursVidesActifs();
        labelsListeDeroulanteFermenteurs = new ArrayList<>();
        for (int i = 0; i < listeFermenteursDisponibles.size(); i++) {
            labelsListeDeroulanteFermenteurs.add(i,listeFermenteursDisponibles.get(i).getNumero()+" / "+listeFermenteursDisponibles.get(i).getCapacite()+"L");
        }

        editFermenteur = (Spinner)view.findViewById(R.id.editFermenteur);
        ArrayAdapter<String> adapteurFermenteur = new ArrayAdapter<>(contexte, R.layout.spinner_style, labelsListeDeroulanteFermenteurs);
        adapteurFermenteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editFermenteur.setAdapter(adapteurFermenteur);

        btnAjouter = (Button)view.findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);

        return view;
    }

    private void ajouter() {
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
            if ((editDensiteOriginale.getText() != null) && (!editDensiteOriginale.getText().toString().equals(""))) {
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
            erreur = erreur + "La densité finale est trop grande.";
        }

        if(!(editFermenteur.isEnabled())){
            erreur = erreur + "Il n'y a pas de fermenteur vide pour accueillir un nouveau brassin.";
        }

        if(erreur.equals("") && quantite > TableFermenteur.instance(contexte).recupererId(listeFermenteursDisponibles.get(editFermenteur.getSelectedItemPosition()).getId()).getCapacite()){
            erreur = erreur + "Le quantité du brassin est trop importante par rapport à la capacité du fermenteur.";
        }



        if (erreur.equals("")) {
            long recette = TableRecette.instance(contexte).recupererIndex(editRecette.getSelectedItemPosition()).getId();

            //Date avec seulement jour, mois, annee
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

            long id_brassinPere = TableBrassinPere.instance(contexte).ajouter(numero, editCommentaire.getText().toString() + "", date, quantite, recette, densiteOriginale, densiteFinale);
            long id_brassin = TableBrassin.instance(contexte).ajouter(contexte, id_brassinPere, quantite);

            Fermenteur fermenteur = TableFermenteur.instance(contexte).recupererId(listeFermenteursDisponibles.get(editFermenteur.getSelectedItemPosition()).getId());
            TableFermenteur.instance(contexte).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), TableCheminBrassinFermenteur.instance(contexte).recupererPremierNoeud(), date, id_brassin, fermenteur.getActif());

            if (fermenteur.getIdNoeud() != -1) {
                String historique = fermenteur.getNoeud(contexte).getEtat(contexte).getHistorique();
                if ((historique != null) && (!historique.equals(""))) {
                    Calendar calendrier2 = Calendar.getInstance();
                    calendrier2.setTimeInMillis(System.currentTimeMillis());
                    long date2 = new GregorianCalendar(calendrier2.get(Calendar.YEAR), calendrier2.get(Calendar.MONTH), calendrier2.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                    TableHistorique.instance(contexte).ajouter(historique, date2, fermenteur.getId(), -1, -1, -1);
                }
            }

            Toast.makeText(contexte, "Brassin ajouté !", Toast.LENGTH_SHORT).show();
            TableBrassin tableBrassin = TableBrassin.instance(contexte);

            int max = 0;
            for (int i=0; i<tableBrassin.tailleListe(); i=i+1) {
                if (max < tableBrassin.recupererIndex(i).getNumero()) {
                    max = tableBrassin.recupererIndex(i).getNumero();
                }
            }
            editNumero.setText("" + (max+1));

            listeFermenteursDisponibles = TableFermenteur.instance(contexte).recupererFermenteursVidesActifs();
            labelsListeDeroulanteFermenteurs = new ArrayList<>();
            for (int i = 0; i < listeFermenteursDisponibles.size(); i++) {
                labelsListeDeroulanteFermenteurs.add(i,listeFermenteursDisponibles.get(i).getNumero()+" / "+listeFermenteursDisponibles.get(i).getCapacite()+"L");
            }

                ArrayAdapter<String> adapteurFermenteur = new ArrayAdapter<>(contexte, R.layout.spinner_style, labelsListeDeroulanteFermenteurs);
                adapteurFermenteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editFermenteur.setAdapter(adapteurFermenteur);
                if(listeFermenteursDisponibles.size()<=0) {
                    editFermenteur.setEnabled(false);
                }

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
}
