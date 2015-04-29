package fabrique.gestion.FragmentGestion;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.Objets.ListeHistorique;

public class LigneListeHistorique extends TableRow implements View.OnClickListener {

    private FragmentListeHistorique parent;
    private ListeHistorique listeHistorique;

    private TableRow.LayoutParams marge;
    private TextView texte;
    private Button btnModifier, btnSupprimer, btnValider, btnAnnuler;
    private EditText editTexte;

    protected LigneListeHistorique(Context context) {
        super(context);
    }

    protected LigneListeHistorique(Context context, FragmentListeHistorique parent, ListeHistorique listeHistorique) {
        this(context);
        this.parent = parent;
        this.listeHistorique = listeHistorique;

        initialiser();

        afficher();
    }

    private void initialiser() {
        marge = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10, 0);

        texte = new TextView(getContext());
        editTexte = new EditText(getContext());

        btnModifier = new Button(getContext());
        btnModifier.setText("Modifier");
        btnModifier.setOnClickListener(this);

        btnSupprimer = new Button(getContext());
        btnSupprimer.setText("Supprimer");
        btnSupprimer.setOnClickListener(this);

        btnValider = new Button(getContext());
        btnValider.setText("Valider");
        btnValider.setOnClickListener(this);

        btnAnnuler = new Button(getContext());
        btnAnnuler.setText("Annuler");
        btnAnnuler.setOnClickListener(this);
    }

    private void afficher() {
        removeAllViews();
            texte.setText(listeHistorique.getTexte());
        addView(texte, marge);
        addView(btnModifier, marge);
        addView(btnSupprimer, marge);
    }

    private void modifier() {
        removeAllViews();
            editTexte.setText(listeHistorique.getTexte());
        addView(editTexte, marge);
        addView(btnValider, marge);
        addView(btnAnnuler, marge);
    }

    private void valider() {
        TableListeHistorique.instance(getContext()).modifier(listeHistorique.getId(), editTexte.getText().toString());
    }

    private void supprimer() {
        TableListeHistorique.instance(getContext()).supprimer(listeHistorique.getId());
        parent.afficher();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifier();
        }
        else if (v.equals(btnSupprimer)) {
            supprimer();
        }
        else if (v.equals(btnValider)) {
            valider();
            afficher();
        }
        else if (v.equals(btnAnnuler)) {
            afficher();
        }
    }
}
