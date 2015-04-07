package fabrique.gestion;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.EtatCuve;

public class VueEtatCuve extends TableLayout implements View.OnClickListener {

    private TableRow.LayoutParams parametre;

    private ArrayList<TableRow> lignes;
    private ArrayList<EtatCuve> etats;
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

    protected VueEtatCuve(Context contexte) {
        super(contexte);

        lignes = new ArrayList<>();
        etats = new ArrayList<>();
        btnsModifier = new ArrayList<>();

        parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);
        parametre.gravity = Gravity.CENTER_VERTICAL;

        addView(entete());

        TableEtatCuve tableEtatCuve = TableEtatCuve.instance(contexte);
        for (int i=0; i< tableEtatCuve.tailleListe() ; i++) {
            addView(affichageEtatCuve(tableEtatCuve.recuperer(i)));
        }

        addView(ligneAjouterNouveau());
    }

    private TableRow entete() {
        TableRow ligneTitre = new TableRow(getContext());
            TextView txtTitre = new TextView(getContext());
            txtTitre.setText("État pour une cuve");
            txtTitre.setTypeface(null, Typeface.BOLD);
            txtTitre.setLayoutParams(parametre);
            ligneTitre.addView(txtTitre);
        addView(ligneTitre);

        TableRow ligne = new TableRow(getContext());
            TextView txtEtat = new TextView(getContext());
            txtEtat.setText("État");
            txtEtat.setTypeface(null, Typeface.BOLD);
            txtEtat.setLayoutParams(parametre);

            TextView txtActif = new TextView(getContext());
            txtActif.setText("Actif");
            txtActif.setTypeface(null, Typeface.BOLD);
            txtActif.setLayoutParams(parametre);

        ligne.addView(txtEtat);
        ligne.addView(txtActif);
        return ligne;
    }

    private TableRow affichageEtatCuve(EtatCuve etat) {
        TableRow ligne = new TableRow(getContext());
            EditText txtEtat = new EditText(getContext());
            txtEtat.setEnabled(false);
            txtEtat.setText(etat.getTexte());
            txtEtat.setTextColor(etat.getCouleurTexte());
            txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
            txtEtat.setBackgroundColor(etat.getCouleurFond());
            txtEtat.setLayoutParams(parametre);

            CheckBox cbActif = new CheckBox(getContext());
            cbActif.setChecked(etat.getActif());
            cbActif.setEnabled(false);
            cbActif.setLayoutParams(parametre);

            Button modifier = new Button(getContext());
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
        TableRow ligne = new TableRow(getContext());
            txtEtatAjouter = new EditText(getContext());
            txtEtatAjouter.setLayoutParams(parametre);

            cbActifAjouter = new CheckBox(getContext());
            cbActifAjouter.setChecked(true);
            cbActifAjouter.setEnabled(true);
            cbActifAjouter.setLayoutParams(parametre);

            btnCouleurTexteAjouter = new Button(getContext());
            btnCouleurTexteAjouter.setText("Couleur de texte");
            btnCouleurTexteAjouter.setOnClickListener(this);

            btnCouleurFondAjouter = new Button(getContext());
            btnCouleurFondAjouter.setText("Couleur de fond");
            btnCouleurFondAjouter.setOnClickListener(this);

            btnAjouter = new Button(getContext());
            btnAjouter.setText("Ajouter");
            btnAjouter.setOnClickListener(this);

            ligne.addView(txtEtatAjouter);
            ligne.addView(cbActifAjouter);
            ligne.addView(btnCouleurTexteAjouter);
            ligne.addView(btnCouleurFondAjouter);
            ligne.addView(btnAjouter);

        ligneAjouter = ligne;
        return ligne;
    }

    private void remettreAffichageEtatCuve(int index) {
        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

        EtatCuve etat = etats.get(index);

            EditText txtEtat = new EditText(getContext());
            txtEtat.setText(etat.getTexte());
            txtEtat.setEnabled(false);
            txtEtat.setTextColor(etat.getCouleurTexte());
            txtEtat.setBackgroundColor(etat.getCouleurFond());
            txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
            txtEtat.setLayoutParams(parametre);

            CheckBox cbActif = new CheckBox(getContext());
            cbActif.setChecked(etat.getActif());
            cbActif.setEnabled(false);
            cbActif.setLayoutParams(parametre);

            Button btnModifier = btnsModifier.get(index);

        ligne.addView(txtEtat);
        ligne.addView(cbActif);
        ligne.addView(btnModifier);
    }

    private void modifierEtatCuve(int index) {
        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

        EtatCuve etat = etats.get(index);

        txtEtat = new EditText(getContext());
        txtEtat.setText(etat.getTexte());
        txtEtat.setTextColor(etat.getCouleurTexte());
        txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
        txtEtat.setBackgroundColor(etat.getCouleurFond());
        txtEtat.setLayoutParams(parametre);

        cbActif = new CheckBox(getContext());
        cbActif.setChecked(etat.getActif());
        cbActif.setEnabled(true);
        cbActif.setLayoutParams(parametre);

        btnCouleurTexte = new Button(getContext());
        btnCouleurTexte.setText("Couleur de texte");
        btnCouleurTexte.setOnClickListener(this);

        btnCouleurFond = new Button(getContext());
        btnCouleurFond.setText("Couleur de fond");
        btnCouleurFond.setOnClickListener(this);

        btnValider = new Button(getContext());
        btnValider.setText("Valider");
        btnValider.setOnClickListener(this);

        btnAnnuler = new Button(getContext());
        btnAnnuler.setText("Annuler");
        btnAnnuler.setOnClickListener(this);

        ligne.addView(txtEtat);
        ligne.addView(cbActif);
        ligne.addView(btnCouleurTexte);
        ligne.addView(btnCouleurFond);
        ligne.addView(btnValider);
        ligne.addView(btnAnnuler);

        ligne.invalidate();
    }

    private void affichageNouvelEtatCuve(EtatCuve etat) {
        removeView(ligneAjouter);
        addView(affichageEtatCuve(etat));
        addView(ligneAjouterNouveau());
        invalidate();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnCouleurTexte)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Texte", txtEtat);
            dialog.show();
        } else if (v.equals(btnCouleurFond)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Fond", txtEtat);
            dialog.show();
        } else if (v.equals(btnCouleurTexteAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Texte", txtEtatAjouter);
            dialog.show();
        } else if (v.equals(btnCouleurFondAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Fond", txtEtatAjouter);
            dialog.show();
        } else if (v.equals(btnValider)) {
            etats.get(indexActif).setTexte(txtEtat.getText().toString());
            etats.get(indexActif).setCouleurTexte(txtEtat.getCurrentTextColor());
            etats.get(indexActif).setCouleurFond(txtEtat.getDrawingCacheBackgroundColor());
            etats.get(indexActif).setActif(cbActif.isChecked());
            TableEtatCuve.instance(getContext()).modifier(etats.get(indexActif));
            for (int i = 0; i < btnsModifier.size(); i++) {
                btnsModifier.get(i).setEnabled(true);
            }
            remettreAffichageEtatCuve(indexActif);
        } else if (v.equals(btnAnnuler)) {
            for (int i = 0; i < btnsModifier.size(); i++) {
                btnsModifier.get(i).setEnabled(true);
            }
            remettreAffichageEtatCuve(indexActif);
        } else if (v.equals(btnAjouter)) {
            TableEtatCuve tableEtatCuve = TableEtatCuve.instance(getContext());
            EtatCuve etat = tableEtatCuve.ajouter(txtEtatAjouter.getText().toString(),
                                                  txtEtatAjouter.getCurrentTextColor(),
                                                  txtEtatAjouter.getDrawingCacheBackgroundColor(),
                                                  cbActifAjouter.isChecked());
            affichageNouvelEtatCuve(etat);
        } else {
            for (int i=0; i< btnsModifier.size() ; i++) {
                if (v.equals(btnsModifier.get(i))) {
                    modifierEtatCuve(i);
                    indexActif = i;
                }
                btnsModifier.get(i).setEnabled(false);
            }
        }
    }
}
