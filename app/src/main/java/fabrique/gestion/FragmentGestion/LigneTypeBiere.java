package fabrique.gestion.FragmentGestion;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.BDD.TableTypeBiere;
import fabrique.gestion.Objets.TypeBiere;

public class LigneTypeBiere extends TableRow implements View.OnClickListener {

    private FragmentTypeBiere parent;
    private TypeBiere typeBiere;

    private TableRow.LayoutParams marge;

    private TextView nom, couleur;
    private EditText editNom, editCouleur;
    private CheckBox actif;
    private Button modifier, valider, annuler;

    public LigneTypeBiere(Context context) {
        super(context);
    }

    public LigneTypeBiere(Context context, FragmentTypeBiere parent, TypeBiere typeBiere) {
        this(context);
        this.parent = parent;
        this.typeBiere = typeBiere;

        initialiser();
        afficher();
    }

    private void initialiser() {
        marge = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10, 0);

        nom = new TextView(getContext());
        editNom = new EditText(getContext());
        couleur = new TextView(getContext());
        editCouleur = new EditText(getContext());
        actif = new CheckBox(getContext());

        modifier = new Button(getContext());
        modifier.setText("Modifier");
        modifier.setOnClickListener(this);

        valider = new Button(getContext());
        valider.setText("Valider");
        valider.setOnClickListener(this);

        annuler = new Button(getContext());
        annuler.setText("Annuler");
        annuler.setOnClickListener(this);
    }

    private void afficher() {
        removeAllViews();
            nom.setText(typeBiere.getNom());
        addView(nom, marge);
            couleur.setText(typeBiere.getCouleur());
        addView(couleur, marge);
            actif.setChecked(typeBiere.getActif());
            actif.setEnabled(false);
        addView(actif, marge);
        addView(modifier);
    }

    private void modifier() {
        removeAllViews();
            editNom.setText(typeBiere.getNom());
        addView(editNom, marge);
            editCouleur.setText(typeBiere.getCouleur());
        addView(editCouleur, marge);
            actif.setEnabled(true);
        addView(actif, marge);
        addView(valider);
        addView(annuler);
    }

    private void valider() {
        TableTypeBiere.instance(getContext()).modifier(typeBiere.getId(), editNom.getText().toString(), editCouleur.getText().toString(), actif.isChecked());
        parent.afficher();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(modifier)) {
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
