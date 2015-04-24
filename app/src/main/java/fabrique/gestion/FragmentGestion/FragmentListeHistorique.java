package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.R;

public class FragmentListeHistorique extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private TableLayout tableau;

    private TableRow.LayoutParams marge;

    private ArrayList<Button> btnsModifier;
    private ArrayList<Button> btnsSupprimer;

    private ArrayList<ListeHistorique> listeHistoriques;

    private ArrayList<TableRow> lignes;

    //Modifier
    private Button btnValider, btnAnnuler;
    private EditText editText;

    //Ajouter
    private TableRow ligneAjouter;
    private Button btnAjouter;
    private Spinner elementConcerne;
    private EditText texteAjouter;

    private int indexActif;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        contexte = container.getContext();

        marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);

        btnsModifier = new ArrayList<>();
        btnsSupprimer = new ArrayList<>();
        listeHistoriques = new ArrayList<>();
        lignes = new ArrayList<>();

        btnValider = new Button(contexte);
        btnValider.setText("Valider");
        btnValider.setOnClickListener(this);

        btnAnnuler = new Button(contexte);
        btnAnnuler.setText("Annuler");
        btnAnnuler.setOnClickListener(this);

        editText = new EditText(contexte);

        ligneAjouter = new TableRow(contexte);
            texteAjouter = new EditText(contexte);
        ligneAjouter.addView(texteAjouter);
            elementConcerne = new Spinner(contexte);
            ArrayAdapter adapteurTri = new ArrayAdapter<>(contexte, R.layout.spinner_style, new String[] {"Fermenteur", "Cuve", "Fût", "Brassin"});
            adapteurTri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            elementConcerne.setAdapter(adapteurTri);
        ligneAjouter.addView(elementConcerne);
            btnAjouter = new Button(contexte);
            btnAjouter.setText("Ajouter");
            btnAjouter.setOnClickListener(this);
        ligneAjouter.addView(btnAjouter);

        tableau = new TableLayout(contexte);
        tableauListeHistorique();
        ScrollView verticalScroll = new ScrollView(contexte);
        verticalScroll.addView(tableau);

        return verticalScroll;
    }

    private void tableauListeHistorique() {
        tableau.removeAllViews();
            TableRow ligneTitreFermenteur = new TableRow(contexte);
                TextView titreFermenteur = new TextView(contexte);
                titreFermenteur.setText("Texte pour l'historique des fermenteurs :");
                titreFermenteur.setTypeface(null, Typeface.BOLD);
            ligneTitreFermenteur.addView(titreFermenteur, marge);
            tableau.addView(ligneTitreFermenteur);

            ArrayList<ListeHistorique> listeHistoriqueFermenteur = TableListeHistorique.instance(contexte).listeHistoriqueFermenteur();
            for (int i=0; i< listeHistoriqueFermenteur.size(); i++) {
                listeHistoriques.add(listeHistoriqueFermenteur.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueFermenteur.get(i).getTexte()));
            }

            TableRow ligneTitreCuve = new TableRow(contexte);
                TextView titreCuve = new TextView(contexte);
                titreCuve.setText("Texte pour l'historique des cuves :");
                titreCuve.setTypeface(null, Typeface.BOLD);
            ligneTitreCuve.addView(titreCuve, marge);
            tableau.addView(ligneTitreCuve);

            ArrayList<ListeHistorique> listeHistoriqueCuve = TableListeHistorique.instance(contexte).listeHistoriqueCuve();
            for (int i=0; i< listeHistoriqueCuve.size(); i++) {
                listeHistoriques.add(listeHistoriqueCuve.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueCuve.get(i).getTexte()));
            }

            TableRow ligneTitreFut = new TableRow(contexte);
                TextView titreFut = new TextView(contexte);
                titreFut.setText("Texte pour l'historique des fûts :");
                titreFut.setTypeface(null, Typeface.BOLD);
            ligneTitreFut.addView(titreFut, marge);
            tableau.addView(ligneTitreFut);

            ArrayList<ListeHistorique> listeHistoriqueFut = TableListeHistorique.instance(contexte).listeHistoriqueFut();
            for (int i=0; i< listeHistoriqueFut.size(); i++) {
                listeHistoriques.add(listeHistoriqueFut.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueFut.get(i).getTexte()));
            }

            TableRow ligneTitreBrassin = new TableRow(contexte);
                TextView titreBrassin = new TextView(contexte);
                titreBrassin.setText("Texte pour l'historique des Brassins :");
                titreBrassin.setTypeface(null, Typeface.BOLD);
            ligneTitreBrassin.addView(titreBrassin, marge);
            tableau.addView(ligneTitreBrassin);

            ArrayList<ListeHistorique> listeHistoriqueBrassin = TableListeHistorique.instance(contexte).listeHistoriqueBrassin();
            for (int i=0; i< listeHistoriqueBrassin.size(); i++) {
                listeHistoriques.add(listeHistoriqueBrassin.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueBrassin.get(i).getTexte()));
            }

            TableRow ligneTitreAjouter = new TableRow(contexte);
                TextView titreAjouter = new TextView(contexte);
                titreAjouter.setText("Ajouter un texte :");
                titreAjouter.setTypeface(null, Typeface.BOLD);
            ligneTitreAjouter.addView(titreAjouter, marge);
            tableau.addView(ligneTitreAjouter);

            texteAjouter.setText("");
            tableau.addView(ligneAjouter);

        tableau.invalidate();
    }

    private TableRow ligneAffichageElement(String texte) {
        TableRow ligne = new TableRow(contexte);
            TextView texteFermenteur = new TextView(contexte);
            texteFermenteur.setText(texte);
        ligne.addView(texteFermenteur, marge);
            Button btnModifier = new Button(contexte);
            btnModifier.setText("Modifier");
            btnModifier.setOnClickListener(this);
            btnsModifier.add(btnModifier);
        ligne.addView(btnModifier, marge);
            Button btnSupprimer = new Button(contexte);
            btnSupprimer.setText("Supprimer");
            btnSupprimer.setOnClickListener(this);
            btnsSupprimer.add(btnSupprimer);
        ligne.addView(btnSupprimer, marge);
        lignes.add(ligne);
        return ligne;
    }

    private void ligneModifierElement(int index) {
        indexActif = index;
        TableRow ligne = lignes.get(index);

        ligne.removeAllViews();
        editText.setText(listeHistoriques.get(index).getTexte());
        ligne.addView(editText, marge);
        ligne.addView(btnValider, marge);
        ligne.addView(btnAnnuler, marge);
    }

    private void modifierElement() {
        TableListeHistorique.instance(contexte).modifier(listeHistoriques.get(indexActif).getId(), editText.getText().toString());
    }

    private void ligneReafficherElement() {
        for (int i = 0; i < btnsModifier.size(); i++) {
            btnsModifier.get(i).setEnabled(true);
        }

        TableRow ligne = lignes.get(indexActif);

        ligne.removeAllViews();
            TextView texte = new TextView(contexte);
            texte.setText(listeHistoriques.get(indexActif).getTexte());
        ligne.addView(texte, marge);
        ligne.addView(btnsModifier.get(indexActif), marge);
        ligne.addView(btnsSupprimer.get(indexActif), marge);
    }

    private void ajouter() {
        TableListeHistorique.instance(contexte).ajouter(elementConcerne.getSelectedItemPosition(), texteAjouter.getText().toString());
    }

    private void supprimer(int index) {
        TableListeHistorique.instance(contexte).supprimer(listeHistoriques.get(index).getId());
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnValider)) {
            modifierElement();
            ligneReafficherElement();
        } else if (v.equals(btnAnnuler)) {
            ligneReafficherElement();
        } else if (v.equals(btnAjouter)) {
            ajouter();
            tableauListeHistorique();
        } else {
            boolean supprimer = false;
            for (int i=0; i<btnsSupprimer.size() ; i++) {
                if (v.equals(btnsSupprimer.get(i))) {
                    supprimer(i);
                    tableauListeHistorique();
                    supprimer = true;
                }
            }
            for (int i = 0; (i<btnsModifier.size()) && (!supprimer); i++) {
                if (v.equals(btnsModifier.get(i))) {
                    ligneModifierElement(i);
                } else {
                    btnsModifier.get(i).setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        transaction.addToBackStack(null).commit();
    }
}
