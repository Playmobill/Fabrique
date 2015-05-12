package fabrique.gestion.Vue;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;

import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.EtatFermenteur;

public class LigneEtatFermenteur extends TableRow implements View.OnClickListener {

    private VueEtatFermenteur parent;
    private EtatFermenteur etatFermenteur;

    private EditText txtEtat, txtHistorique;
    private CheckBox cbAvecBrassin, cbActif;
    private Button modifier, couleurTexte, couleurFond, valider, annuler;

    public LigneEtatFermenteur(Context contexte) {
        super(contexte);
    }

    public LigneEtatFermenteur(Context contexte, VueEtatFermenteur parent, EtatFermenteur etatFermenteur) {
        this(contexte);
        this.parent = parent;
        this.etatFermenteur = etatFermenteur;

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
        txtEtat.setText(etatFermenteur.getTexte());
        txtEtat.setTextColor(etatFermenteur.getCouleurTexte());
        txtEtat.setDrawingCacheBackgroundColor(etatFermenteur.getCouleurFond());
        txtEtat.setBackgroundColor(etatFermenteur.getCouleurFond());
        txtEtat.setEnabled(false);
        addView(txtEtat, marge);
        txtHistorique.setText(etatFermenteur.getHistorique());
        txtHistorique.setEnabled(false);
        addView(txtHistorique);
        cbAvecBrassin.setChecked(etatFermenteur.getAvecBrassin());
        cbAvecBrassin.setEnabled(false);
        addView(cbAvecBrassin, marge);
        cbActif.setChecked(etatFermenteur.getActif());
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
        TableEtatFermenteur.instance(getContext()).modifier(
                etatFermenteur.getId(),
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
