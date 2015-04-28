package fabrique.gestion.FragmentGestion;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.Objets.Emplacement;

public class LigneEmplacement extends TableRow implements View.OnClickListener {

    private FragmentEmplacement parent;
    private Emplacement emplacement;

    private TableRow.LayoutParams marge;

    private TextView texte;
    private EditText editText;
    private CheckBox actif;
    private Button modifier, valider, annuler;

    public LigneEmplacement(Context context) {
        super(context);
    }

    public LigneEmplacement(Context context, FragmentEmplacement parent, Emplacement emplacement) {
        this(context);
        this.parent = parent;
        this.emplacement = emplacement;

        initialiser();
        afficher();
    }

    private void initialiser() {
        marge = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10, 0);

        texte = new TextView(getContext());
        editText = new EditText(getContext());
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
            texte.setText(emplacement.getTexte());
        addView(texte, marge);
            actif.setChecked(emplacement.getActif());
            actif.setEnabled(false);
        addView(actif);
        addView(modifier);
    }

    private void modifier() {
        removeAllViews();
            editText.setText(emplacement.getTexte());
        addView(editText, marge);
            actif.setEnabled(true);
        addView(actif);
        addView(valider);
        addView(annuler);
    }

    private void valider() {
        TableEmplacement.instance(getContext()).modifier(emplacement.getId(), editText.getText().toString(), actif.isChecked());
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
