package fabrique.gestion.Vue;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.ColorPicker.ColorPickerDialog;

public class VueEtatCuve extends TableLayout implements View.OnClickListener {

    TableRow.LayoutParams marge;

    //Titre
    TableRow ligneTitre;
    TableRow ligneEnTete;
    TextView txtTitre, txtEtat, txtHistorique, txtActif;

    //Ajouter
    private TableRow ligneAjouter;
    private Button btnCouleurTexteAjouter, btnCouleurFondAjouter, btnAjouter;
    private CheckBox cbActifAjouter;
    private EditText txtEtatAjouter, txtHistoriqueAjouter;

    public VueEtatCuve(Context contexte) {
        super(contexte);
        initialiser();
        afficher();
    }

    private void initialiser() {
        marge = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 1, 10, 1);

        ligneTitre = new TableRow(getContext());
            txtTitre = new TextView(getContext());
            txtTitre.setText("État pour une cuve");
            txtTitre.setTypeface(null, Typeface.BOLD);
        ligneTitre.addView(txtTitre, marge);
        ligneEnTete = new TableRow(getContext());
            txtEtat = new TextView(getContext());
            txtEtat.setText("État");
            txtEtat.setTypeface(null, Typeface.BOLD);
        ligneEnTete.addView(txtEtat, marge);
            txtHistorique = new TextView(getContext());
            txtHistorique.setText("Historique");
            txtHistorique.setTypeface(null, Typeface.BOLD);
        ligneEnTete.addView(txtHistorique, marge);
            txtActif = new TextView(getContext());
            txtActif.setText("Actif");
            txtActif.setTypeface(null, Typeface.BOLD);
        ligneEnTete.addView(txtActif, marge);

        ligneAjouter = new TableRow(getContext());
            txtEtatAjouter = new EditText(getContext());
            txtEtatAjouter.setTextColor(Color.BLACK);
            txtEtatAjouter.setDrawingCacheBackgroundColor(Color.WHITE);
            txtEtatAjouter.setBackgroundColor(Color.WHITE);
        ligneAjouter.addView(txtEtatAjouter, marge);
            txtHistoriqueAjouter = new EditText(getContext());
        ligneAjouter.addView(txtHistoriqueAjouter);
            cbActifAjouter = new CheckBox(getContext());
            cbActifAjouter.setEnabled(true);
        ligneAjouter.addView(cbActifAjouter, marge);
            btnCouleurTexteAjouter = new Button(getContext());
            btnCouleurTexteAjouter.setText("Couleur de texte");
            btnCouleurTexteAjouter.setOnClickListener(this);
        ligneAjouter.addView(btnCouleurTexteAjouter);
            btnCouleurFondAjouter = new Button(getContext());
            btnCouleurFondAjouter.setText("Couleur de fond");
            btnCouleurFondAjouter.setOnClickListener(this);
        ligneAjouter.addView(btnCouleurFondAjouter);
            btnAjouter = new Button(getContext());
            btnAjouter.setText("Ajouter");
            btnAjouter.setOnClickListener(this);
        ligneAjouter.addView(btnAjouter);
    }

    public void afficher() {
        removeAllViews();

        ligneEntete();

        TableEtatCuve tableEtatCuve = TableEtatCuve.instance(getContext());
        for (int i=0; i<tableEtatCuve.tailleListe(); i++) {
            addView(new LigneEtatCuve(getContext(), this, tableEtatCuve.recupererIndex(i)));
        }

        ligneAjouterNouveau();
    }

    private void ligneEntete() {
        addView(ligneTitre);
        addView(ligneEnTete);
    }

    private void ligneAjouterNouveau() {
        txtEtatAjouter.setText("");
        txtHistoriqueAjouter.setText("");
        cbActifAjouter.setChecked(true);
        addView(ligneAjouter);
    }

    private void ajouter() {
        TableEtatCuve tableEtatCuve = TableEtatCuve.instance(getContext());
        tableEtatCuve.ajouter(
            txtEtatAjouter.getText().toString(),
            txtHistoriqueAjouter.getText().toString(),
            txtEtatAjouter.getCurrentTextColor(),
            txtEtatAjouter.getDrawingCacheBackgroundColor(),
            cbActifAjouter.isChecked());
        afficher();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnCouleurTexteAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Texte", txtEtatAjouter);
            dialog.show();
        }
        else if (v.equals(btnCouleurFondAjouter)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Fond", txtEtatAjouter);
            dialog.show();
        }
        else if (v.equals(btnAjouter)) {
            ajouter();
        }
    }
}
