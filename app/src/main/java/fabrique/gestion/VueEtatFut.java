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

import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.EtatFut;

public class VueEtatFut extends TableLayout implements View.OnClickListener {

    private TableRow.LayoutParams parametre;

    private ArrayList<TableRow> lignes;
    private ArrayList<EtatFut> etats;
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

    protected VueEtatFut(Context contexte) {
        super(contexte);

        lignes = new ArrayList<>();
        etats = new ArrayList<>();
        btnsModifier = new ArrayList<>();

        parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);
        parametre.gravity = Gravity.CENTER_VERTICAL;

        addView(entete());

        TableEtatFut tableEtatFut = TableEtatFut.instance(contexte);
        for (int i=0; i< tableEtatFut.tailleListe() ; i++) {
            addView(affichageEtatFut(tableEtatFut.recuperer(i)));
        }

        addView(ligneAjouterNouveau());
    }

    private TableRow entete() {
        TableRow ligneTitre = new TableRow(getContext());
            TextView txtTitre = new TextView(getContext());
            txtTitre.setText("État pour un fût");
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

    private TableRow affichageEtatFut(EtatFut etat) {
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
            EditText txtEtatAjouter = new EditText(getContext());
            txtEtatAjouter.setLayoutParams(parametre);
            this.txtEtatAjouter = txtEtatAjouter;

            CheckBox cbActifAjouter = new CheckBox(getContext());
            cbActifAjouter.setChecked(true);
            cbActifAjouter.setEnabled(true);
            cbActifAjouter.setLayoutParams(parametre);
            this.cbActifAjouter = cbActifAjouter;

            Button btnCouleurTexteAjouter = new Button(getContext());
            btnCouleurTexteAjouter.setText("Couleur de texte");
            btnCouleurTexteAjouter.setOnClickListener(this);
            this.btnCouleurTexteAjouter = btnCouleurTexteAjouter;

            Button btnCouleurFondAjouter = new Button(getContext());
            btnCouleurFondAjouter.setText("Couleur de fond");
            btnCouleurFondAjouter.setOnClickListener(this);
            this.btnCouleurFondAjouter = btnCouleurFondAjouter;


            Button btnAjouter = new Button(getContext());
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

    private void remettreAffichageEtatFut(int index) {
        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

        EtatFut etat = etats.get(index);

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

    private void modifierEtatFut(int index) {
        TableRow ligne = lignes.get(index);
        ligne.removeAllViews();

        EtatFut etat = etats.get(index);

        EditText txtEtat = new EditText(getContext());
        txtEtat.setText(etat.getTexte());
        txtEtat.setTextColor(etat.getCouleurTexte());
        txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
        txtEtat.setBackgroundColor(etat.getCouleurFond());
        txtEtat.setLayoutParams(parametre);
        this.txtEtat = txtEtat;

        CheckBox cbActif = new CheckBox(getContext());
        cbActif.setChecked(etat.getActif());
        cbActif.setEnabled(true);
        cbActif.setLayoutParams(parametre);
        this.cbActif = cbActif;

        Button btnCouleurTexte = new Button(getContext());
        btnCouleurTexte.setText("Couleur de texte");
        btnCouleurTexte.setOnClickListener(this);
        this.btnCouleurTexte = btnCouleurTexte;

        Button btnCouleurFond = new Button(getContext());
        btnCouleurFond.setText("Couleur de fond");
        btnCouleurFond.setOnClickListener(this);
        this.btnCouleurFond = btnCouleurFond;

        Button btnValider = new Button(getContext());
        btnValider.setText("Valider");
        btnValider.setOnClickListener(this);
        this.btnValider = btnValider;

        Button btnAnnuler = new Button(getContext());
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

    private void affichageNouvelEtatFut(EtatFut etat) {
        removeView(ligneAjouter);
        addView(affichageEtatFut(etat));
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
            TableEtatFut.instance(getContext()).modifier(etats.get(indexActif));
            for (int i = 0; i < btnsModifier.size(); i++) {
                btnsModifier.get(i).setEnabled(true);
            }
            remettreAffichageEtatFut(indexActif);
        } else if (v.equals(btnAnnuler)) {
            for (int i = 0; i < btnsModifier.size(); i++) {
                btnsModifier.get(i).setEnabled(true);
            }
            remettreAffichageEtatFut(indexActif);
        } else if (v.equals(btnAjouter)) {
            TableEtatFut tableEtatFut = TableEtatFut.instance(getContext());
            EtatFut etat = tableEtatFut.ajouter(txtEtatAjouter.getText().toString(),
                                                  txtEtatAjouter.getCurrentTextColor(),
                                                  txtEtatAjouter.getDrawingCacheBackgroundColor(),
                                                  cbActifAjouter.isChecked());
            affichageNouvelEtatFut(etat);
        } else {
            for (int i=0; i< btnsModifier.size() ; i++) {
                if (v.equals(btnsModifier.get(i))) {
                    modifierEtatFut(i);
                    indexActif = i;
                }
                btnsModifier.get(i).setEnabled(false);
            }
        }
    }
}
