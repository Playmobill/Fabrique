package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.Objets.ListeHistorique;

public class ActivityVueListeHistorique extends Activity implements View.OnClickListener {

    private TableRow.LayoutParams marge;

    private ArrayList<Button> btnModifiers;

    private ArrayList<ListeHistorique> listeHistoriques;

    private ArrayList<TableRow> lignes;

    private Button btnValider, btnAnnuler;

    private EditText editText;

    private int indexActif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);

        btnModifiers = new ArrayList<>();
        listeHistoriques = new ArrayList<>();
        lignes = new ArrayList<>();

        btnValider = new Button(this);
        btnValider.setText("Valider");
        btnValider.setOnClickListener(this);

        btnAnnuler = new Button(this);
        btnAnnuler.setText("Annuler");
        btnAnnuler.setOnClickListener(this);

        editText = new EditText(this);

        ScrollView verticalScroll = new ScrollView(this);
        verticalScroll.addView(tableauListeHistorique());

        setContentView(verticalScroll);
    }

    private TableLayout tableauListeHistorique() {
        TableLayout tableau = new TableLayout(this);

            TableRow ligneTitreFermenteur = new TableRow(this);
                TextView titreFermenteur = new TextView(this);
                titreFermenteur.setText("Texte pour l'historique des fermenteurs");
                titreFermenteur.setTypeface(null, Typeface.BOLD);
            ligneTitreFermenteur.addView(titreFermenteur, marge);
            tableau.addView(ligneTitreFermenteur);

            ArrayList<ListeHistorique> listeHistoriqueFermenteur = TableListeHistorique.instance(this).listeHistoriqueFermenteur();
            for (int i=0; i< listeHistoriqueFermenteur.size(); i++) {
                listeHistoriques.add(listeHistoriqueFermenteur.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueFermenteur.get(i).getTexte()));
            }

            TableRow ligneTitreCuve = new TableRow(this);
                TextView titreCuve = new TextView(this);
                titreCuve.setText("Texte pour l'historique des cuves");
                titreCuve.setTypeface(null, Typeface.BOLD);
            ligneTitreCuve.addView(titreCuve, marge);
            tableau.addView(ligneTitreCuve);

            ArrayList<ListeHistorique> listeHistoriqueCuve = TableListeHistorique.instance(this).listeHistoriqueCuve();
            for (int i=0; i< listeHistoriqueCuve.size(); i++) {
                listeHistoriques.add(listeHistoriqueCuve.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueCuve.get(i).getTexte()));
            }

            TableRow ligneTitreFut = new TableRow(this);
                TextView titreFut = new TextView(this);
                titreFut.setText("Texte pour l'historique des fûts");
                titreFut.setTypeface(null, Typeface.BOLD);
            ligneTitreFut.addView(titreFut, marge);
            tableau.addView(ligneTitreFut);

            ArrayList<ListeHistorique> listeHistoriqueFut = TableListeHistorique.instance(this).listeHistoriqueFut();
            for (int i=0; i< listeHistoriqueFut.size(); i++) {
                listeHistoriques.add(listeHistoriqueFut.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueFut.get(i).getTexte()));
            }

            TableRow ligneTitreBrassin = new TableRow(this);
                TextView titreBrassin = new TextView(this);
                titreBrassin.setText("Texte pour l'historique des Brassins");
                titreBrassin.setTypeface(null, Typeface.BOLD);
            ligneTitreBrassin.addView(titreBrassin, marge);
            tableau.addView(ligneTitreBrassin);

            ArrayList<ListeHistorique> listeHistoriqueBrassin = TableListeHistorique.instance(this).listeHistoriqueBrassin();
            for (int i=0; i< listeHistoriqueBrassin.size(); i++) {
                listeHistoriques.add(listeHistoriqueBrassin.get(i));
                tableau.addView(ligneAffichageElement(listeHistoriqueBrassin.get(i).getTexte()));
            }

        return tableau;
    }

    private TableRow ligneAffichageElement(String texte) {
        TableRow ligne = new TableRow(this);
            TextView texteFermenteur = new TextView(this);
            texteFermenteur.setText(texte);
        ligne.addView(texteFermenteur, marge);
            Button btnModifier = new Button(this);
            btnModifier.setText("Modifier");
            btnModifier.setOnClickListener(this);
            btnModifiers.add(btnModifier);
        ligne.addView(btnModifier, marge);
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
        TableListeHistorique.instance(this).modifier(listeHistoriques.get(indexActif).getId(), editText.getText().toString());
    }

    private void ligneReafficherElement() {
        for (int i = 0; i < btnModifiers.size(); i++) {
            btnModifiers.get(i).setEnabled(true);
        }

        TableRow ligne = lignes.get(indexActif);

        ligne.removeAllViews();
            TextView texte = new TextView(this);
            texte.setText(listeHistoriques.get(indexActif).getTexte());
        ligne.addView(texte, marge);
        ligne.addView(btnModifiers.get(indexActif), marge);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnValider)) {
            modifierElement();
            ligneReafficherElement();
        } else if (v.equals(btnAnnuler)) {
            ligneReafficherElement();
        } else {
            for (int i = 0; i < btnModifiers.size(); i++) {
                if (v.equals(btnModifiers.get(i))) {
                    ligneModifierElement(i);
                } else {
                    btnModifiers.get(i).setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityAccueil.class);
        startActivity(intent);
    }







}
