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
    private TableRow ligneTitreFermenteur, ligneTitreCuve, ligneTitreFut, ligneTitreBrassin, ligneTitreAjouter;

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

        initialiser();

        tableau = new TableLayout(contexte);
        afficher();
        ScrollView verticalScroll = new ScrollView(contexte);
        verticalScroll.addView(tableau);

        return verticalScroll;
    }

    private void initialiser() {
        TableLayout.LayoutParams marge = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10, 0);

        ligneTitreFermenteur = new TableRow(contexte);
        ligneTitreFermenteur.setLayoutParams(marge);
            TextView titreFermenteur = new TextView(contexte);
            titreFermenteur.setText("Texte pour l'historique des fermenteurs :");
            titreFermenteur.setTypeface(null, Typeface.BOLD);
        ligneTitreFermenteur.addView(titreFermenteur);

        ligneTitreCuve = new TableRow(contexte);
        ligneTitreCuve.setLayoutParams(marge);
            TextView titreCuve = new TextView(contexte);
            titreCuve.setText("Texte pour l'historique des cuves :");
            titreCuve.setTypeface(null, Typeface.BOLD);
        ligneTitreCuve.addView(titreCuve);

        ligneTitreFut = new TableRow(contexte);
        ligneTitreFut.setLayoutParams(marge);
            TextView titreFut = new TextView(contexte);
            titreFut.setText("Texte pour l'historique des fûts :");
            titreFut.setTypeface(null, Typeface.BOLD);
        ligneTitreFut.addView(titreFut);

        ligneTitreBrassin = new TableRow(contexte);
        ligneTitreBrassin.setLayoutParams(marge);
            TextView titreBrassin = new TextView(contexte);
            titreBrassin.setText("Texte pour l'historique des brassins :");
            titreBrassin.setTypeface(null, Typeface.BOLD);
        ligneTitreBrassin.addView(titreBrassin);

        ligneTitreAjouter = new TableRow(contexte);
        ligneTitreAjouter.setLayoutParams(marge);
            TextView titreAjouter = new TextView(contexte);
            titreAjouter.setText("Ajouter un texte :");
            titreAjouter.setTypeface(null, Typeface.BOLD);
        ligneTitreAjouter.addView(titreAjouter);

        ligneAjouter = new TableRow(contexte);
        ligneAjouter.setLayoutParams(marge);
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
    }

    protected void afficher() {
        tableau.removeAllViews();
        tableau.addView(ligneTitreFermenteur);
        ArrayList<ListeHistorique> listeHistoriqueFermenteur = TableListeHistorique.instance(contexte).listeHistoriqueFermenteur();
        for (int i=0; i< listeHistoriqueFermenteur.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueFermenteur.get(i)));
        }
        tableau.addView(ligneTitreCuve);
        ArrayList<ListeHistorique> listeHistoriqueCuve = TableListeHistorique.instance(contexte).listeHistoriqueCuve();
        for (int i=0; i< listeHistoriqueCuve.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueCuve.get(i)));
        }
        tableau.addView(ligneTitreFut);
        ArrayList<ListeHistorique> listeHistoriqueFut = TableListeHistorique.instance(contexte).listeHistoriqueFut();
        for (int i=0; i< listeHistoriqueFut.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueFut.get(i)));
        }
        tableau.addView(ligneTitreBrassin);
        ArrayList<ListeHistorique> listeHistoriqueBrassin = TableListeHistorique.instance(contexte).listeHistoriqueBrassin();
        for (int i=0; i< listeHistoriqueBrassin.size(); i++) {
            tableau.addView(new LigneListeHistorique(contexte, this, listeHistoriqueBrassin.get(i)));
        }
        tableau.addView(ligneTitreAjouter);
            texteAjouter.setText("");
        tableau.addView(ligneAjouter);
    }

    private void ajouter() {
        TableListeHistorique.instance(contexte).ajouter(elementConcerne.getSelectedItemPosition(), texteAjouter.getText().toString(), 1);
        afficher();
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
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }
}
