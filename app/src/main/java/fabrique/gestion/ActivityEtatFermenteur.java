package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.EtatFermenteur;

public class ActivityEtatFermenteur extends Activity implements View.OnClickListener {

    private TableLayout tableau;

    private TableRow.LayoutParams parametre;

    private ArrayList<TableRow> lignes;
    private ArrayList<EtatFermenteur> etats;
    private ArrayList<Button> btnsModifier;

    //Modifier
    private int indexActif;
    private Button btnCouleurTexte, btnCouleurFond, btnValider, btnAnnuler;
    private CheckBox cbActif;
    private EditText txtEtat;

    //Ajouter
    private Button btnCouleurTexteAjouter, btnCouleurFondAjouter, btnAjouter;
    private CheckBox cbActifAjouter;
    private EditText txtEtatAjouter;
    private TableRow ligneAjouter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        lignes = new ArrayList<>();
        etats = new ArrayList<>();
        btnsModifier = new ArrayList<>();

        tableau = new TableLayout(this);

        parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);
        parametre.gravity = Gravity.CENTER_VERTICAL;

        tableau.addView(entete());

        TableEtatFermenteur tableEtatFermenteur = TableEtatFermenteur.instance(this);
        for (int i=0; i< tableEtatFermenteur.tailleListe() ; i++) {
            tableau.addView(affichageEtatFermenteur(tableEtatFermenteur.recuperer(i)));
        }

        tableau.addView(ligneAjouterNouveau());

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(tableau);

        setContentView(layoutVerticalScroll);
    }

    private TableRow entete() {
        TableRow ligne = new TableRow(this);
            TextView txtEtat = new TextView(this);
            txtEtat.setText("État");
            txtEtat.setTypeface(null, Typeface.BOLD);
            txtEtat.setLayoutParams(parametre);

            TextView txtActif = new TextView(this);
            txtActif.setText("Actif");
            txtActif.setTypeface(null, Typeface.BOLD);
            txtActif.setLayoutParams(parametre);

            ligne.addView(txtEtat);
            ligne.addView(txtActif);
        return ligne;
    }

    private TableRow affichageEtatFermenteur(EtatFermenteur etat) {
        TableRow ligne = new TableRow(this);
            EditText txtEtat = new EditText(this);
            txtEtat.setEnabled(false);
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
            btnsModifier.add(modifier);

            ligne.addView(txtEtat);
            ligne.addView(cbActif);
            ligne.addView(modifier);

        etats.add(etat);
        lignes.add(ligne);
        return ligne;
    }

    private TableRow ligneAjouterNouveau() {
        TableRow ligne = new TableRow(this);
            EditText txtEtatAjouter = new EditText(this);
            txtEtatAjouter.setLayoutParams(parametre);
            this.txtEtatAjouter = txtEtatAjouter;

            CheckBox cbActifAjouter = new CheckBox(this);
            cbActifAjouter.setChecked(true);
            cbActifAjouter.setEnabled(true);
            cbActifAjouter.setLayoutParams(parametre);
            this.cbActifAjouter = cbActifAjouter;

            Button btnCouleurTexteAjouter = new Button(this);
            btnCouleurTexteAjouter.setText("Couleur de texte");
            btnCouleurTexteAjouter.setOnClickListener(this);
            this.btnCouleurTexteAjouter = btnCouleurTexteAjouter;

            Button btnCouleurFondAjouter = new Button(this);
            btnCouleurFondAjouter.setText("Couleur de fond");
            btnCouleurFondAjouter.setOnClickListener(this);
            this.btnCouleurFondAjouter = btnCouleurFondAjouter;


            Button btnAjouter = new Button(this);
            btnAjouter.setText("Ajouter");
            btnAjouter.setOnClickListener(this);
            this.btnAjouter = btnAjouter;

            ligne.addView(txtEtatAjouter);
            ligne.addView(cbActifAjouter);
            ligne.addView(btnCouleurTexteAjouter);
            ligne.addView(btnCouleurFondAjouter);
            ligne.addView(btnAjouter);

        ligneAjouter = ligne;
        return ligne;
    }

    private void remettreAffichageEtatFermenteur(int index) {
        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

        EtatFermenteur etat = etats.get(index);

            EditText txtEtat = new EditText(this);
            txtEtat.setText(etat.getTexte());
            txtEtat.setEnabled(false);
            txtEtat.setTextColor(etat.getCouleurTexte());
            txtEtat.setBackgroundColor(etat.getCouleurFond());
            txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
            txtEtat.setLayoutParams(parametre);

            CheckBox cbActif = new CheckBox(this);
            cbActif.setChecked(etat.getActif());
            cbActif.setEnabled(false);
            cbActif.setLayoutParams(parametre);

            Button btnModifier = btnsModifier.get(index);

        ligne.addView(txtEtat);
        ligne.addView(cbActif);
        ligne.addView(btnModifier);
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

            Button btnCouleurTexte = new Button(this);
            btnCouleurTexte.setText("Couleur de texte");
            btnCouleurTexte.setOnClickListener(this);
            this.btnCouleurTexte = btnCouleurTexte;

            Button btnCouleurFond = new Button(this);
            btnCouleurFond.setText("Couleur de fond");
            btnCouleurFond.setOnClickListener(this);
            this.btnCouleurFond = btnCouleurFond;

            Button btnValider = new Button(this);
            btnValider.setText("Valider");
            btnValider.setOnClickListener(this);
            this.btnValider = btnValider;

            Button btnAnnuler = new Button(this);
            btnAnnuler.setText("Annuler");
            btnAnnuler.setOnClickListener(this);
            this.btnAnnuler = btnAnnuler;

            ligne.addView(txtEtat);
            ligne.addView(cbActif);
            ligne.addView(btnCouleurTexte);
            ligne.addView(btnCouleurFond);
            ligne.addView(btnValider);
            ligne.addView(btnAnnuler);

        ligne.invalidate();
    }

    private void affichageNouvelEtatFermenteur(EtatFermenteur etat) {
        tableau.removeView(ligneAjouter);
        tableau.addView(affichageEtatFermenteur(etat));
        tableau.addView(ligneAjouterNouveau());
        tableau.invalidate();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnCouleurTexte)) {
            ColorPickerDialog dialog = new ColorPickerDialog(this, "Texte", txtEtat);
            dialog.show();
        } else if (v.equals(btnCouleurFond)) {
            ColorPickerDialog dialog = new ColorPickerDialog(this, "Fond", txtEtat);
            dialog.show();
        } else if (v.equals(btnCouleurTexteAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(this, "Texte", txtEtatAjouter);
            dialog.show();
        } else if (v.equals(btnCouleurFondAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(this, "Fond", txtEtatAjouter);
            dialog.show();
        } else if (v.equals(btnValider)) {
            etats.get(indexActif).setTexte(txtEtat.getText().toString());
            etats.get(indexActif).setCouleurTexte(txtEtat.getCurrentTextColor());
            etats.get(indexActif).setCouleurFond(txtEtat.getDrawingCacheBackgroundColor());
            etats.get(indexActif).setActif(cbActif.isChecked());
            TableEtatFermenteur.instance(this).modifier(etats.get(indexActif));
            for (int i = 0; i < btnsModifier.size(); i++) {
                btnsModifier.get(i).setEnabled(true);
            }
            remettreAffichageEtatFermenteur(indexActif);
        } else if (v.equals(btnAnnuler)) {
            for (int i = 0; i < btnsModifier.size(); i++) {
                btnsModifier.get(i).setEnabled(true);
            }
            remettreAffichageEtatFermenteur(indexActif);
        } else if (v.equals(btnAjouter)) {
            TableEtatFermenteur tableEtatFermenteur = TableEtatFermenteur.instance(this);
            EtatFermenteur etat = tableEtatFermenteur.ajouter(txtEtatAjouter.getText().toString(),
                                                              txtEtatAjouter.getCurrentTextColor(),
                                                              txtEtatAjouter.getDrawingCacheBackgroundColor(),
                                                              cbActifAjouter.isChecked());
            affichageNouvelEtatFermenteur(etat);
        } else {
            for (int i=0; i< btnsModifier.size() ; i++) {
                if (v.equals(btnsModifier.get(i))) {
                    modifierEtatFermenteur(i);
                    indexActif = i;
                }
                btnsModifier.get(i).setEnabled(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }
}
