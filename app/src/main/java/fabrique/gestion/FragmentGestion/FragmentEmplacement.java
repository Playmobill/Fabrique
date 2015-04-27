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

    private ArrayList<Button> btnsModifier;

    private ArrayList<Emplacement> emplacements;

    private ArrayList<TableRow> lignes;

    //Modifier
    private Button btnValider, btnAnnuler;
    private CheckBox editActif;
    private EditText editText;

    //Ajouter
    private TableRow ligneAjouter;
    private CheckBox actifAjouter;
    private Button btnAjouter;
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

        btnsModifier = new ArrayList<>();
        emplacements = new ArrayList<>();
        lignes = new ArrayList<>();

        btnValider = new Button(contexte);
        btnValider.setText("Valider");
        btnValider.setOnClickListener(this);

        btnAnnuler = new Button(contexte);
        btnAnnuler.setText("Annuler");
        btnAnnuler.setOnClickListener(this);

        ligneAjouter = new TableRow(contexte);
            texteAjouter = new EditText(contexte);
        ligneAjouter.addView(texteAjouter);
            actifAjouter = new CheckBox(contexte);
            actifAjouter.setChecked(true);
            actifAjouter.setEnabled(true);
        ligneAjouter.addView(actifAjouter);

        btnAjouter = new Button(contexte);
        btnAjouter.setText("Ajouter");
        btnAjouter.setOnClickListener(this);
        ligneAjouter.addView(btnAjouter);

        tableau = new TableLayout(contexte);
        tableauEmplacement();

        ScrollView verticalScroll = new ScrollView(contexte);
        verticalScroll.addView(tableau);

        return verticalScroll;
    }

    private void tableauEmplacement() {
        tableau.removeAllViews();
            TableRow ligneTitreFermenteur = new TableRow(contexte);
                TextView titreFermenteur = new TextView(contexte);
                titreFermenteur.setText("Emplacements :");
                titreFermenteur.setTypeface(null, Typeface.BOLD);
            ligneTitreFermenteur.addView(titreFermenteur);
        tableau.addView(ligneTitreFermenteur);

            emplacements = TableEmplacement.instance(contexte).recupererTous();
            for (int i=0; i< emplacements.size(); i++) {
                tableau.addView(ligneAffichageElement(emplacements.get(i)));
            }

            TableRow ligneTitreAjouter = new TableRow(contexte);
                TextView titreAjouter = new TextView(contexte);
                titreAjouter.setText("Ajouter un emplacement :");
                titreAjouter.setTypeface(null, Typeface.BOLD);
            ligneTitreAjouter.addView(titreAjouter);
        tableau.addView(ligneTitreAjouter);

            texteAjouter.setText("");
            actifAjouter.setChecked(true);
            actifAjouter.setEnabled(true);
        tableau.addView(ligneAjouter);

        tableau.invalidate();
    }

    private TableRow ligneAffichageElement(Emplacement emplacement) {
        TableRow ligne = new TableRow(contexte);

            TextView texteEmplacement = new TextView(contexte);
            texteEmplacement.setText(emplacement.getTexte());
        ligne.addView(texteEmplacement);

            CheckBox actif = new CheckBox(contexte);
            actif.setChecked(emplacement.getActif());
            actif.setEnabled(false);
        ligne.addView(actif);

            Button btnModifier = new Button(contexte);
            btnModifier.setText("Modifier");
            btnModifier.setOnClickListener(this);
            btnsModifier.add(btnModifier);
        ligne.addView(btnModifier);

        lignes.add(ligne);
        return ligne;
    }

    private void ligneModifierElement(int index) {
        indexActif = index;
        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

            editText = new EditText(contexte);
            editText.setText(emplacements.get(index).getTexte());

            editActif = new CheckBox(contexte);
            editActif.setChecked(emplacements.get(index).getActif());
            editActif.setEnabled(true);
        ligne.addView(editText);
        ligne.addView(editActif);
        ligne.addView(btnValider);
        ligne.addView(btnAnnuler);
    }

    private void modifierElement() {
        TableEmplacement.instance(contexte).modifier(emplacements.get(indexActif).getId(), editText.getText().toString(), editActif.isChecked());
    }

    private void ligneReafficherElement() {
        for (int i = 0; i < btnsModifier.size(); i++) {
            btnsModifier.get(i).setEnabled(true);
        }

        TableRow ligne = lignes.get(indexActif);

        ligne.removeAllViews();
            TextView texte = new TextView(contexte);
            texte.setText(emplacements.get(indexActif).getTexte());
        ligne.addView(texte);
            CheckBox actif = new CheckBox(contexte);
            actif.setChecked(emplacements.get(indexActif).getActif());
        ligne.addView(actif);
        ligne.addView(btnsModifier.get(indexActif));
    }

    private void ajouter() {
        TableEmplacement.instance(contexte).ajouter(texteAjouter.getText().toString(), actifAjouter.isChecked());
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
            tableauEmplacement();
        } else {
            for (int i = 0; i<btnsModifier.size(); i++) {
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
