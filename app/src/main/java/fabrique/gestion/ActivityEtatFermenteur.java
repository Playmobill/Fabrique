package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.ColorPicker.ColorPickerDialogEtatFermenteur;
import fabrique.gestion.Objets.EtatFermenteur;

public class ActivityEtatFermenteur extends Activity implements View.OnClickListener {

    private TableRow.LayoutParams parametre;
    private ArrayList<TableRow> lignes;
    private ArrayList<EtatFermenteur> etats;
    private ArrayList<Button> boutonModifier;

    private Button couleurTexte, couleurFond, valider;
    private CheckBox cbActif;
    private EditText txtEtat;

    private int indexActif;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        lignes = new ArrayList<>();
        etats = new ArrayList<>();
        boutonModifier = new ArrayList<>();

        TableLayout tableau = new TableLayout(this);

        parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);
        parametre.gravity = Gravity.CENTER_VERTICAL;

        tableau.addView(entete());

        TableEtatFermenteur tableEtatFermenteur = TableEtatFermenteur.instance(this);
        for (int i=0; i< tableEtatFermenteur.tailleListe() ; i++) {
            tableau.addView(affichageEtatFermenteur(tableEtatFermenteur.recuperer(i)));
        }

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(tableau);

        setContentView(layoutVerticalScroll);
    }

    private TableRow entete() {
        TableRow ligne = new TableRow(this);
            TextView txtEtat = new TextView(this);
            txtEtat.setText("État");
            txtEtat.setLayoutParams(parametre);

            TextView txtActif = new TextView(this);
            txtActif.setText("Actif");
            txtActif.setLayoutParams(parametre);

            ligne.addView(txtEtat);
            ligne.addView(txtActif);
        return ligne;
    }

    private TableRow affichageEtatFermenteur(EtatFermenteur etat) {
        TableRow ligne = new TableRow(this);
            TextView txtEtat = new TextView(this);
            txtEtat.setText(etat.getTexte());
            txtEtat.setTextColor(etat.getCouleurTexte());
            txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
            txtEtat.setBackgroundColor(etat.getCouleurFond());
            txtEtat.setLayoutParams(parametre);

            CheckBox cbActif = new CheckBox(this);
            cbActif.setChecked(etat.getActif());
            cbActif.setEnabled(false);
            cbActif.setLayoutParams(parametre);

            Button modifier = new Button(this);
            modifier.setText("Modifier");
            modifier.setOnClickListener(this);
            boutonModifier.add(modifier);

            ligne.addView(txtEtat);
            ligne.addView(cbActif);
            ligne.addView(modifier);

        etats.add(etat);
        lignes.add(ligne);
        return ligne;
    }

    private void remettreAffichageEtatFermenteur(int index) {

        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

        EtatFermenteur etat = etats.get(index);

        TextView txtEtat = new TextView(this);
        txtEtat.setText(etat.getTexte());
        txtEtat.setTextColor(etat.getCouleurTexte());
        txtEtat.setBackgroundColor(etat.getCouleurFond());
        txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
        txtEtat.setLayoutParams(parametre);

        CheckBox cbActif = new CheckBox(this);
        cbActif.setChecked(etat.getActif());
        cbActif.setEnabled(false);
        cbActif.setLayoutParams(parametre);

        Button modifier = boutonModifier.get(index);

        ligne.addView(txtEtat);
        ligne.addView(cbActif);
        ligne.addView(modifier);
    }

    private void modifierEtatFermenteur(int index) {

        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

        EtatFermenteur etat = etats.get(index);

        EditText txtEtat = new EditText(this);
        txtEtat.setText(etat.getTexte());
        txtEtat.setTextColor(etat.getCouleurTexte());
        txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
        txtEtat.setBackgroundColor(etat.getCouleurFond());
        txtEtat.setLayoutParams(parametre);
        this.txtEtat = txtEtat;

        CheckBox cbActif = new CheckBox(this);
        cbActif.setChecked(etat.getActif());
        cbActif.setEnabled(true);
        cbActif.setLayoutParams(parametre);
        this.cbActif = cbActif;

        Button couleurTexte = new Button(this);
        couleurTexte.setText("Couleur de texte");
        couleurTexte.setOnClickListener(this);
        this.couleurTexte = couleurTexte;

        Button couleurFond = new Button(this);
        couleurFond.setText("Couleur de fond");
        couleurFond.setOnClickListener(this);
        this.couleurFond = couleurFond;

        Button valider = new Button(this);
        valider.setText("Valider");
        valider.setOnClickListener(this);
        this.valider = valider;

        ligne.addView(txtEtat);
        ligne.addView(cbActif);
        ligne.addView(couleurTexte);
        ligne.addView(couleurFond);
        ligne.addView(valider);

        ligne.invalidate();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(couleurTexte)) {
            ColorPickerDialogEtatFermenteur dialog = new ColorPickerDialogEtatFermenteur(this, "Texte", txtEtat);
            dialog.show();
        } else if (v.equals(couleurFond)) {
            ColorPickerDialogEtatFermenteur dialog = new ColorPickerDialogEtatFermenteur(this, "Fond", txtEtat);
            dialog.show();
        } else if (v.equals(valider)) {
            etats.get(indexActif).setTexte(txtEtat.getText().toString());
            etats.get(indexActif).setCouleurTexte(txtEtat.getCurrentTextColor());
            etats.get(indexActif).setCouleurFond(txtEtat.getDrawingCacheBackgroundColor());
            etats.get(indexActif).setActif(cbActif.isChecked());
            TableEtatFermenteur.instance(this).modifier(etats.get(indexActif));
            for (int i=0; i<boutonModifier.size() ; i++) {
                boutonModifier.get(i).setEnabled(true);
            }
            remettreAffichageEtatFermenteur(indexActif);
        } else {
            for (int i=0; i<boutonModifier.size() ; i++) {
                if (v.equals(boutonModifier.get(i))) {
                    modifierEtatFermenteur(i);
                    indexActif = i;
                } else {
                    boutonModifier.get(i).setEnabled(false);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }
}
