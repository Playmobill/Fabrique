package fabrique.gestion.Vue;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;

import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.EtatFut;

public class LigneEtatFut extends TableRow implements View.OnClickListener {

    private VueEtatFut parent;
    private EtatFut etatFut;

    private EditText txtEtat, txtHistorique;
    private CheckBox cbActif;
    private Button modifier, couleurTexte, couleurFond, valider, annuler;

    public LigneEtatFut(Context contexte) {
        super(contexte);
    }

    public LigneEtatFut(Context contexte, VueEtatFut parent, EtatFut etatFut) {
        this(contexte);
        this.parent = parent;
        this.etatFut = etatFut;

        initialiser();

        afficher();
    }

    private void initialiser() {
        txtEtat = new EditText(getContext());

        txtHistorique = new EditText(getContext());

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
            txtEtat.setText(etatFut.getTexte());
            txtEtat.setTextColor(etatFut.getCouleurTexte());
            txtEtat.setDrawingCacheBackgroundColor(etatFut.getCouleurFond());
            txtEtat.setBackgroundColor(etatFut.getCouleurFond());
            txtEtat.setEnabled(false);
        addView(txtEtat, marge);
            txtHistorique.setText(etatFut.getHistorique());
            txtHistorique.setEnabled(false);
        addView(txtHistorique);
            cbActif.setChecked(etatFut.getActif());
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
            cbActif.setEnabled(true);
        addView(cbActif, marge);
        addView(couleurTexte);
        addView(couleurFond);
        addView(valider);
        addView(annuler);
    }

    private void valider() {
        TableEtatFut.instance(getContext()).modifier(
            etatFut.getId(),
            txtEtat.getText().toString(),
            txtHistorique.getText().toString(),
            txtEtat.getCurrentTextColor(),
            txtEtat.getDrawingCacheBackgroundColor(),
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
