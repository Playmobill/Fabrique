package fabrique.gestion;

import android.content.Context;
import android.graphics.Color;
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

import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.EtatFermenteur;

public class VueEtatFermenteur extends TableLayout implements View.OnClickListener {

    private TableRow.LayoutParams parametre;

    private ArrayList<TableRow> lignes;
    private ArrayList<Button> btnsModifier;

    //Modifier
    private int indexActif;
    private Button btnCouleurTexte, btnCouleurFond, btnValider, btnAnnuler;
    private CheckBox cbActif;
    private EditText txtEtat, txtHistorique;

    //Ajouter
    private Button btnCouleurTexteAjouter, btnCouleurFondAjouter, btnAjouter;
    private CheckBox cbActifAjouter;
    private EditText txtEtatAjouter, txtHistoriqueAjouter;
    private TableRow ligneAjouter;

    protected VueEtatFermenteur(Context contexte) {
        super(contexte);

        lignes = new ArrayList<>();
        btnsModifier = new ArrayList<>();

        parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);
        parametre.gravity = Gravity.CENTER_VERTICAL;

        remplir();
    }

    private void remplir() {
        removeAllViews();
        lignes.clear();
        btnsModifier.clear();
        ligneEntete();
        TableEtatFermenteur tableEtatFermenteur = TableEtatFermenteur.instance(getContext());
        for (int i=0; i<tableEtatFermenteur.tailleListe(); i++) {
            addView(affichageEtatFermenteur(tableEtatFermenteur.recupererIndex(i)));
        }
        ligneAjouterNouveau();
        invalidate();
    }

    private void ligneEntete() {
        TableRow ligneTitre = new TableRow(getContext());
            TextView txtTitre = new TextView(getContext());
            txtTitre.setText("État pour un Fermenteur");
            txtTitre.setTypeface(null, Typeface.BOLD);
            txtTitre.setLayoutParams(parametre);

        TableRow ligneEntete = new TableRow(getContext());
            TextView txtEtat = new TextView(getContext());
            txtEtat.setText("État");
            txtEtat.setTypeface(null, Typeface.BOLD);
            txtEtat.setLayoutParams(parametre);

            TextView txtHistorique = new TextView(getContext());
            txtHistorique.setText("Historique");
            txtHistorique.setTypeface(null, Typeface.BOLD);
            txtHistorique.setLayoutParams(parametre);

            TextView txtActif = new TextView(getContext());
            txtActif.setText("Actif");
            txtActif.setTypeface(null, Typeface.BOLD);
            txtActif.setLayoutParams(parametre);

            ligneTitre.addView(txtTitre);
        addView(ligneTitre);
            ligneEntete.addView(txtEtat);
            ligneEntete.addView(txtHistorique);
            ligneEntete.addView(txtActif);
        addView(ligneEntete);
    }

    private TableRow affichageEtatFermenteur(EtatFermenteur etat) {
        TableRow ligne = new TableRow(getContext());
        EditText txtEtat = new EditText(getContext());
        txtEtat.setEnabled(false);
        txtEtat.setText(etat.getTexte());
        txtEtat.setTextColor(etat.getCouleurTexte());
        txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
        txtEtat.setBackgroundColor(etat.getCouleurFond());
        txtEtat.setLayoutParams(parametre);

        EditText txtHistorique = new EditText(getContext());
        txtHistorique.setEnabled(false);
        txtHistorique.setText(etat.getHistorique());
        txtHistorique.setLayoutParams(parametre);

        CheckBox cbActif = new CheckBox(getContext());
        cbActif.setChecked(etat.getActif());
        cbActif.setEnabled(false);
        cbActif.setLayoutParams(parametre);

        Button modifier = new Button(getContext());
        modifier.setText("Modifier");
        modifier.setOnClickListener(this);
        btnsModifier.add(modifier);

        ligne.addView(txtEtat);
        ligne.addView(txtHistorique);
        ligne.addView(cbActif);
        ligne.addView(modifier);

        lignes.add(ligne);
        return ligne;
    }

    private void ligneAjouterNouveau() {
        TableRow ligne = new TableRow(getContext());
        txtEtatAjouter = new EditText(getContext());
        txtEtatAjouter.setLayoutParams(parametre);
        txtEtatAjouter.setTextColor(Color.BLACK);
        txtEtatAjouter.setDrawingCacheBackgroundColor(Color.WHITE);
        txtEtatAjouter.setBackgroundColor(Color.WHITE);
        txtEtatAjouter.setLayoutParams(parametre);

        txtHistoriqueAjouter = new EditText(getContext());
        txtHistoriqueAjouter.setLayoutParams(parametre);

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
        ligne.addView(txtHistoriqueAjouter);
        ligne.addView(cbActifAjouter);
        ligne.addView(btnCouleurTexteAjouter);
        ligne.addView(btnCouleurFondAjouter);
        ligne.addView(btnAjouter);

        ligneAjouter = ligne;
        addView(ligneAjouter);
    }

    private void modifierEtatFermenteur() {
        TableRow ligne = lignes.get(indexActif);
        ligne.removeAllViews();

        EtatFermenteur etat = TableEtatFermenteur.instance(getContext()).recupererIndex(indexActif);

        txtEtat = new EditText(getContext());
        txtEtat.setText(etat.getTexte());
        txtEtat.setTextColor(etat.getCouleurTexte());
        txtEtat.setDrawingCacheBackgroundColor(etat.getCouleurFond());
        txtEtat.setBackgroundColor(etat.getCouleurFond());
        txtEtat.setLayoutParams(parametre);

        txtHistorique = new EditText(getContext());
        txtHistorique.setText(etat.getHistorique());
        txtHistorique.setLayoutParams(parametre);

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
        ligne.addView(txtHistorique);
        ligne.addView(cbActif);
        ligne.addView(btnCouleurTexte);
        ligne.addView(btnCouleurFond);
        ligne.addView(btnValider);
        ligne.addView(btnAnnuler);

        ligne.invalidate();
    }

    private void validerModification() {
        EtatFermenteur etat = TableEtatFermenteur.instance(getContext()).recupererIndex(indexActif);
        TableEtatFermenteur.instance(getContext()).modifier(etat.getId(),
                                                            txtEtat.getText().toString(),
                                                            txtHistorique.getText().toString(),
                                                            txtEtat.getCurrentTextColor(),
                                                            txtEtat.getDrawingCacheBackgroundColor(),
                                                            cbActif.isChecked());
        for (int i = 0; i < btnsModifier.size(); i++) {
            btnsModifier.get(i).setEnabled(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnCouleurTexte)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Texte", txtEtat);
            dialog.show();
        }

        else if (v.equals(btnCouleurFond)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Fond", txtEtat);
            dialog.show();
        }

        else if (v.equals(btnCouleurTexteAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Texte", txtEtatAjouter);
            dialog.show();
        }

        else if (v.equals(btnCouleurFondAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Fond", txtEtatAjouter);
            dialog.show();
        }

        else if (v.equals(btnValider)) {
            validerModification();
            remplir();
        }

        else if (v.equals(btnAnnuler)) {
            for (int i = 0; i < btnsModifier.size(); i++) {
                btnsModifier.get(i).setEnabled(true);
            }
            remplir();
        }

        else if (v.equals(btnAjouter)) {
            TableEtatFermenteur tableEtatFermenteur = TableEtatFermenteur.instance(getContext());
            tableEtatFermenteur.ajouter(txtEtatAjouter.getText().toString(),
                                        txtHistoriqueAjouter.getText().toString(),
                                        txtEtatAjouter.getCurrentTextColor(),
                                        txtEtatAjouter.getDrawingCacheBackgroundColor(),
                                        cbActifAjouter.isChecked());
            remplir();
        }

        else {
            for (int i=0; i< btnsModifier.size() ; i++) {
                if (v.equals(btnsModifier.get(i))) {
                    indexActif = i;
                    modifierEtatFermenteur();
                }
                btnsModifier.get(i).setEnabled(false);
            }
        }
    }
}
