package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableTypeBiere;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentTypeBiere extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private TableLayout tableau;

    //Titre
    private TableRow ligneTitre, ligneNomColonne;

    //Ajouter
    private TableRow ligneTitreAjouter, ligneNomColonneAjouter;
    private TableRow ligneAjouter;
    private CheckBox cbActifAjouter;
    private Button ajouter;
    private EditText texteNomAjouter, texteCouleurAjouter;

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
        TableRow.LayoutParams marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10, 0);

        ligneTitre = new TableRow(contexte);
            TextView titre = new TextView(contexte);
            titre.setText("Types de bières :");
            titre.setTypeface(null, Typeface.BOLD);
        ligneTitre.addView(titre);

        ligneNomColonne = new TableRow(contexte);
            TextView nom = new TextView(contexte);
            nom.setText("Nom");
            nom.setTypeface(null, Typeface.BOLD);
        ligneNomColonne.addView(nom, marge);
            TextView couleur = new TextView(contexte);
            couleur.setText("Couleur");
            couleur.setTypeface(null, Typeface.BOLD);
        ligneNomColonne.addView(couleur, marge);
            TextView actif = new TextView(contexte);
            actif.setText("Actif");
            actif.setTypeface(null, Typeface.BOLD);
        ligneNomColonne.addView(actif, marge);

        ligneTitreAjouter = new TableRow(contexte);
            TextView titreAjouter = new TextView(contexte);
            titreAjouter.setText("Ajouter un type de bière :");
            titreAjouter.setTypeface(null, Typeface.BOLD);
        ligneTitreAjouter.addView(titreAjouter);

        ligneNomColonneAjouter = new TableRow(contexte);
            TextView nomAjouter = new TextView(contexte);
            nomAjouter.setText("Nom");
            nomAjouter.setTypeface(null, Typeface.BOLD);
        ligneNomColonneAjouter.addView(nomAjouter, marge);
            TextView couleurAjouter = new TextView(contexte);
            couleurAjouter.setText("Couleur");
            couleurAjouter.setTypeface(null, Typeface.BOLD);
        ligneNomColonneAjouter.addView(couleurAjouter, marge);
            TextView actifAjouter = new TextView(contexte);
            actifAjouter.setText("Actif");
            actifAjouter.setTypeface(null, Typeface.BOLD);
        ligneNomColonneAjouter.addView(actifAjouter, marge);

        ligneAjouter = new TableRow(contexte);
            texteNomAjouter = new EditText(contexte);
        ligneAjouter.addView(texteNomAjouter);
            texteCouleurAjouter = new EditText(contexte);
        ligneAjouter.addView(texteCouleurAjouter);
            cbActifAjouter = new CheckBox(contexte);
            cbActifAjouter.setChecked(true);
            cbActifAjouter.setEnabled(true);
        ligneAjouter.addView(cbActifAjouter);
            ajouter = new Button(contexte);
            ajouter.setText("Ajouter");
            ajouter.setOnClickListener(this);
        ligneAjouter.addView(ajouter);
    }

    public void afficher() {
        TableLayout.LayoutParams marge = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10, 0);
        tableau.removeAllViews();
        tableau.addView(ligneTitre, marge);
        tableau.addView(ligneNomColonne, marge);
        TableTypeBiere tableTypeBiere = TableTypeBiere.instance(contexte);
        for (int i=0; i< tableTypeBiere.tailleListe(); i++) {
            tableau.addView(new LigneTypeBiere(contexte, this, tableTypeBiere.recupererIndex(i)));
        }
        tableau.addView(ligneTitreAjouter, marge);
        tableau.addView(ligneNomColonneAjouter, marge);
        texteNomAjouter.setText("");
        cbActifAjouter.setChecked(true);
        cbActifAjouter.setEnabled(true);
        tableau.addView(ligneAjouter, marge);
    }

    private void ajouter() {
        TableTypeBiere.instance(contexte).ajouter(texteNomAjouter.getText().toString(), texteCouleurAjouter.getText().toString(), cbActifAjouter.isChecked());
        afficher();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(ajouter)) {
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
