package fabrique.gestion.Vue;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;

import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.EtatCuve;

public class LigneEtatCuve extends TableRow implements View.OnClickListener {

    private VueEtatCuve parent;
    private EtatCuve etatCuve;

    private EditText txtEtat, txtHistorique;
    private CheckBox cbAvecBrassin, cbActif;
    private Button modifier, couleurTexte, couleurFond, valider, annuler;

    public LigneEtatCuve(Context contexte) {
        super(contexte);
    }

    public LigneEtatCuve(Context contexte, VueEtatCuve parent, EtatCuve etatCuve) {
        this(contexte);
        this.parent = parent;
        this.etatCuve = etatCuve;

        initialiser();

        afficher();
    }

    private void initialiser() {
        txtEtat = new EditText(getContext());

        txtHistorique = new EditText(getContext());

        cbAvecBrassin = new CheckBox(getContext());

        cbActif = new CheckBox(getContext());

        modifier = new Button(getContext());
        modifier.setText("Modifier");
        modifier.setOnClickListener(this);

        couleurTexte = new Button(getContext());
        couleurTexte.setText("Couleur du texte");
        couleurTexte.setOnClickListener(this);

        couleurFond = new Button(getContext());
        couleurFond.setText("Couleur de fond");
        couleurFond.setOnClickListener(this);

        valider = new Button(getContext());
        valider.setText("Valider");
        valider.setOnClickListener(this);

        annuler = new Button(getContext());
        annuler.setText("Annuler");
        annuler.setOnClickListener(this);
    }

    private void afficher() {
        TableRow.LayoutParams marge = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 1, 10, 1);
        removeAllViews();
            txtEtat.setText(etatCuve.getTexte());
            txtEtat.setTextColor(etatCuve.getCouleurTexte());
            txtEtat.setDrawingCacheBackgroundColor(etatCuve.getCouleurFond());
            txtEtat.setBackgroundColor(etatCuve.getCouleurFond());
            txtEtat.setEnabled(false);
        addView(txtEtat, marge);
            txtHistorique.setText(etatCuve.getHistorique());
            txtHistorique.setEnabled(false);
        addView(txtHistorique);
            cbAvecBrassin.setChecked(etatCuve.getAvecBrassin());
            cbAvecBrassin.setEnabled(false);
        addView(cbAvecBrassin, marge);
            cbActif.setChecked(etatCuve.getActif());
            cbActif.setEnabled(false);
        addView(cbActif, marge);
        addView(modifier);
    }

    private void modifier() {
        TableRow.LayoutParams marge = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 1, 10, 1);
        removeAllViews();
            txtEtat.setEnabled(true);
        addView(txtEtat, marge);
            txtHistorique.setEnabled(true);
        addView(txtHistorique);
            cbAvecBrassin.setEnabled(true);
        addView(cbAvecBrassin, marge);
            cbActif.setEnabled(true);
        addView(cbActif, marge);
        addView(couleurTexte);
        addView(couleurFond);
        addView(valider);
        addView(annuler);
    }

    private void valider() {
        TableEtatCuve.instance(getContext()).modifier(
                etatCuve.getId(),
                txtEtat.getText().toString(),
                txtHistorique.getText().toString(),
                txtEtat.getCurrentTextColor(),
                txtEtat.getDrawingCacheBackgroundColor(),
                cbAvecBrassin.isChecked(),
                cbActif.isChecked());
        parent.afficher();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(couleurTexte)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Texte", txtEtat);
            dialog.show();
        }
        else if (v.equals(couleurFond)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Fond", txtEtat);
            dialog.show();
        }
        else if (v.equals(modifier)) {
            modifier();
        }
        else if (v.equals(valider)) {
            valider();
            afficher();
        }
        else if (v.equals(annuler)) {
            afficher();
        }
    }
}
