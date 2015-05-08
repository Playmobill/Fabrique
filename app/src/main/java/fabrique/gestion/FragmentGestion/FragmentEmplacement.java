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

import java.util.ArrayList;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Emplacement;
import fabrique.gestion.R;

public class FragmentEmplacement extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private TableLayout tableau;

    //Titre
    private TableRow ligneTitre;

    //Ajouter
    private TableRow ligneTitreAjouter;
    private TableRow ligneAjouter;
    private CheckBox actifAjouter;
    private Button ajouter;
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
        ligneTitre = new TableRow(contexte);
            TextView titre = new TextView(contexte);
            titre.setText("Emplacements :");
            titre.setTypeface(null, Typeface.BOLD);
        ligneTitre.addView(titre);

        ligneTitreAjouter = new TableRow(contexte);
            TextView titreAjouter = new TextView(contexte);
            titreAjouter.setText("Ajouter un emplacement :");
            titreAjouter.setTypeface(null, Typeface.BOLD);
        ligneTitreAjouter.addView(titreAjouter);

        ligneAjouter = new TableRow(contexte);
            texteAjouter = new EditText(contexte);
        ligneAjouter.addView(texteAjouter);
            actifAjouter = new CheckBox(contexte);
            actifAjouter.setChecked(true);
            actifAjouter.setEnabled(true);
        ligneAjouter.addView(actifAjouter);
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
        ArrayList<Emplacement> emplacements = TableEmplacement.instance(contexte).recupererTous();
        for (int i=0; i< emplacements.size(); i++) {
            tableau.addView(new LigneEmplacement(contexte, this, emplacements.get(i)));
        }

        tableau.addView(ligneTitreAjouter, marge);
            texteAjouter.setText("");
            actifAjouter.setChecked(true);
            actifAjouter.setEnabled(true);
        tableau.addView(ligneAjouter, marge);
    }

    private void ajouter() {
        TableEmplacement.instance(contexte).ajouter(texteAjouter.getText().toString(), actifAjouter.isChecked());
        afficher();
    }

    @Override
    public void invalidate() {}

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
