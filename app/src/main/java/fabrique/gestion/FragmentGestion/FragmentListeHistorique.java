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

    //Ajouter
    private TableRow ligneAjouter;
    private Button btnAjouter;
    private Spinner elementConcerne;
    private EditText texteAjouter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        contexte = container.getContext();

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

    protected void tableauListeHistorique() {
        TableRow.LayoutParams marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10, 0);

        tableau.removeAllViews();
            TableRow ligneTitreFermenteur = new TableRow(contexte);
                TextView titreFermenteur = new TextView(contexte);
                titreFermenteur.setText("Texte pour l'historique des fermenteurs :");
                titreFermenteur.setTypeface(null, Typeface.BOLD);
            ligneTitreFermenteur.addView(titreFermenteur, marge);
        tableau.addView(ligneTitreFermenteur);

        ArrayList<ListeHistorique> listeHistoriqueFermenteur = TableListeHistorique.instance(contexte).listeHistoriqueFermenteur();
        for (int i=0; i< listeHistoriqueFermenteur.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueFermenteur.get(i)));
        }

            TableRow ligneTitreCuve = new TableRow(contexte);
                TextView titreCuve = new TextView(contexte);
                titreCuve.setText("Texte pour l'historique des cuves :");
                titreCuve.setTypeface(null, Typeface.BOLD);
            ligneTitreCuve.addView(titreCuve, marge);
        tableau.addView(ligneTitreCuve);

        ArrayList<ListeHistorique> listeHistoriqueCuve = TableListeHistorique.instance(contexte).listeHistoriqueCuve();
        for (int i=0; i< listeHistoriqueCuve.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueCuve.get(i)));
        }

            TableRow ligneTitreFut = new TableRow(contexte);
                TextView titreFut = new TextView(contexte);
                titreFut.setText("Texte pour l'historique des fûts :");
                titreFut.setTypeface(null, Typeface.BOLD);
            ligneTitreFut.addView(titreFut, marge);
        tableau.addView(ligneTitreFut);

        ArrayList<ListeHistorique> listeHistoriqueFut = TableListeHistorique.instance(contexte).listeHistoriqueFut();
        for (int i=0; i< listeHistoriqueFut.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueFut.get(i)));
        }

            TableRow ligneTitreBrassin = new TableRow(contexte);
                TextView titreBrassin = new TextView(contexte);
                titreBrassin.setText("Texte pour l'historique des Brassins :");
                titreBrassin.setTypeface(null, Typeface.BOLD);
            ligneTitreBrassin.addView(titreBrassin, marge);
        tableau.addView(ligneTitreBrassin);

        ArrayList<ListeHistorique> listeHistoriqueBrassin = TableListeHistorique.instance(contexte).listeHistoriqueBrassin();
        for (int i=0; i< listeHistoriqueBrassin.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueBrassin.get(i)));
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

    private void ajouter() {
        TableListeHistorique.instance(contexte).ajouter(elementConcerne.getSelectedItemPosition(), texteAjouter.getText().toString());
        tableauListeHistorique();
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
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        transaction.addToBackStack(null).commit();
    }
}
