package fabrique.gestion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.Objets.Recette;

/**
 * Created by thibaut on 09/04/15.
 */
public class VueRecette extends TableLayout implements View.OnClickListener {

    public VueRecette(Context context) {
        super(context);
    }

    private Button btnModifier, btnValider, btnAnnuler;

    private Recette recette;

    private TableLayout tableauDescription;

    private EditText editNom, editAcronyme, editCouleur;

    private TableRow ligneBouton;

    protected VueRecette(Context contexte, Recette recette) {
        super(contexte);

        this.recette = recette;

        tableauDescription = new TableLayout(contexte);
        addView(tableauDescription);

        afficherDescription();
    }

    private void afficherDescription() {
        tableauDescription.removeAllViews();

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneID = new TableRow(getContext());
        TextView titre = new TextView(getContext());
        titre.setText("Recette "+recette.getId());
        titre.setTypeface(null, Typeface.BOLD);

        TableRow ligneNom = new TableRow(getContext());
        TextView txtNom = new TextView(getContext());
        txtNom.setText("Nom : ");

        editNom = new EditText(getContext());
        editNom.setText("" + recette.getNom());
        editNom.setEnabled(false);

        LinearLayout ligneAcronyme = new LinearLayout(getContext());
        TextView txtAcronyme = new TextView(getContext());
        txtAcronyme.setText("Acronyme : ");

        editAcronyme = new EditText(getContext());
        editAcronyme.setText("" + recette.getAcronyme());
        editAcronyme.setEnabled(false);

        LinearLayout ligneCouleur = new LinearLayout(getContext());
        TextView txtCouleur = new TextView(getContext());
        txtCouleur.setText("Couleur : ");

        editCouleur = new EditText(getContext());
        editCouleur.setText("" + recette.getCouleur());
        editCouleur.setEnabled(false);

        ligneBouton = new TableRow(getContext());
        btnModifier = new Button(getContext());
        btnModifier.setText("Modifier");
        btnModifier.setOnClickListener(this);

        ligneID.addView(titre);
        ligneNom.addView(txtNom);
        ligneNom.addView(editNom);
        ligneAcronyme.addView(txtAcronyme);
        ligneAcronyme.addView(editAcronyme);
        ligneCouleur.addView(txtCouleur);
        ligneCouleur.addView(editCouleur);
        ligneBouton.addView(btnModifier);

        tableauDescription.addView(ligneID);
        tableauDescription.addView(ligneNom);
        tableauDescription.addView(ligneAcronyme);
        tableauDescription.addView(ligneCouleur);
        tableauDescription.addView(ligneBouton);
    }

    private void modifierDescription() {
        editNom.setEnabled(true);
        editAcronyme.setEnabled(true);
        editCouleur.setEnabled(true);
        ligneBouton.removeAllViews();
        btnValider = new Button(getContext());
        btnValider.setText("Valider");
        btnValider.setOnClickListener(this);

        btnAnnuler = new Button(getContext());
        btnAnnuler.setText("Annuler");
        btnAnnuler.setOnClickListener(this);
        ligneBouton.addView(btnValider);
        ligneBouton.addView(btnAnnuler);
    }

    private void validerDescription() {
        TableRecette.instance(getContext()).modifier(getContext(), (int)recette.getId(), editNom.getText().toString(), editCouleur.getText().toString(), editAcronyme.getText().toString());
        recette = TableRecette.instance(getContext()).recupererId((int)recette.getId());
        reafficherDescription();
    }

    private void reafficherDescription() {
        editNom.setEnabled(false);
        editNom.setText("" + recette.getNom());

        editAcronyme.setEnabled(false);
        editAcronyme.setText("" + recette.getAcronyme());

        editCouleur.setEnabled(false);
        editCouleur.setText("" + recette.getCouleur());

        ligneBouton.removeAllViews();
        btnModifier = new Button(getContext());
        btnModifier.setText("Modifier");
        btnModifier.setOnClickListener(this);
        ligneBouton.addView(btnModifier);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifierDescription();
        } else if (v.equals(btnValider)) {
            validerDescription();
        } else if (v.equals(btnAnnuler)) {
            reafficherDescription();
        }
    }
}
