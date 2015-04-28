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

import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.ColorPicker.ColorPickerDialog;

public class VueEtatFut extends TableLayout implements View.OnClickListener {

    //Titre
    private TableRow ligneTitre;
    private TableRow ligneEnTete;

    //Ajouter
    private TableRow ligneAjouter;
    private Button btnCouleurTexteAjouter, btnCouleurFondAjouter, btnAjouter;
    private CheckBox cbActifAjouter;
    private EditText txtEtatAjouter, txtHistoriqueAjouter;

    public VueEtatFut(Context contexte) {
        super(contexte);
        initialiser();
        afficher();
    }

    private void initialiser() {
        TableRow.LayoutParams marge = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 1, 10, 1);

        ligneTitre = new TableRow(getContext());
            TextView txtTitre = new TextView(getContext());
            txtTitre.setText("État pour un fût");
            txtTitre.setTypeface(null, Typeface.BOLD);
        ligneTitre.addView(txtTitre, marge);
        ligneEnTete = new TableRow(getContext());
            TextView txtEtat = new TextView(getContext());
            txtEtat.setText("État");
            txtEtat.setTypeface(null, Typeface.BOLD);
        ligneEnTete.addView(txtEtat, marge);
            TextView txtHistorique = new TextView(getContext());
            txtHistorique.setText("Historique");
            txtHistorique.setTypeface(null, Typeface.BOLD);
        ligneEnTete.addView(txtHistorique, marge);
            TextView txtActif = new TextView(getContext());
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

        ligneTitre();

        TableEtatFut tableEtatFut = TableEtatFut.instance(getContext());
        for (int i=0; i<tableEtatFut.tailleListe(); i++) {
            addView(new LigneEtatFut(getContext(), this, tableEtatFut.recupererIndex(i)));
        }

        ligneAjoute();
    }

    private void ligneTitre() {
        addView(ligneTitre);
        addView(ligneEnTete);
    }

    private void ligneAjoute() {
        txtEtatAjouter.setText("");
        txtHistoriqueAjouter.setText("");
        cbActifAjouter.setChecked(true);
        addView(ligneAjouter);
    }

    private void ajouter() {
        TableEtatFut tableEtatFut = TableEtatFut.instance(getContext());
        tableEtatFut.ajouter(
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
