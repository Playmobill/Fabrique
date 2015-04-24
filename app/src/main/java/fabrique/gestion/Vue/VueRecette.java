package fabrique.gestion.Vue;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.Objets.Recette;

/**
 * Created by thibaut on 09/04/15.
 */
public class VueRecette extends TableLayout implements View.OnClickListener {

    private Recette recette;

    private LinearLayout tableauDescription;
    private EditText editNom, editAcronyme, editCouleur;
    private TableRow ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    public VueRecette(Context context) {
        super(context);
    }

    public VueRecette(Context contexte, Recette recette) {
        super(contexte);

        this.recette = recette;

        tableauDescription = new TableLayout(contexte);
        addView(cadre(tableauDescription, " Recette ") );

        afficherDescription();
    }

    private RelativeLayout cadre(View view, String texteTitre) {
        RelativeLayout contenant = new RelativeLayout(getContext());

        LinearLayout contourTitre = new LinearLayout(getContext());
        contourTitre.setBackgroundColor(Color.BLACK);
        contourTitre.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContourTitre = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContourTitre.setMargins(10, 1, 0, 0);
        TextView fondTitre = new TextView(getContext());
        fondTitre.setText(texteTitre);
        fondTitre.setTypeface(null, Typeface.BOLD);
        fondTitre.setBackgroundColor(Color.WHITE);

        RelativeLayout contour = new RelativeLayout(getContext());
        contour.setBackgroundColor(Color.BLACK);
        contour.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContour = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContour.topMargin=15;
        parametreContour.leftMargin=5;

        TextView titre = new TextView(getContext());
        titre.setText(texteTitre);
        titre.setTypeface(null, Typeface.BOLD);
        titre.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams parametreTitre = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreTitre.setMargins(11, 2, 0, 0);

        view.setBackgroundColor(Color.WHITE);

        contenant.addView(contourTitre, parametreContourTitre);
        contourTitre.addView(fondTitre);
        contenant.addView(contour, parametreContour);
        contour.addView(view);
        contenant.addView(titre, parametreTitre);
        return contenant;
    }

    private void afficherDescription() {
        tableauDescription.removeAllViews();

        TableLayout.LayoutParams parametre = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        LinearLayout ligneID = new LinearLayout(getContext());
            TextView titre = new TextView(getContext());
            titre.setText("Recette "+recette.getId());
            titre.setTypeface(null, Typeface.BOLD);

        LinearLayout ligneNom = new LinearLayout(getContext());
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

            btnValider = new Button(getContext());
            btnValider.setText("Valider");
            btnValider.setOnClickListener(this);

            btnAnnuler = new Button(getContext());
            btnAnnuler.setText("Annuler");
            btnAnnuler.setOnClickListener(this);

        ligneID.addView(titre);
        ligneNom.addView(txtNom);
        ligneNom.addView(editNom);
        ligneAcronyme.addView(txtAcronyme);
        ligneAcronyme.addView(editAcronyme);
        ligneCouleur.addView(txtCouleur);
        ligneCouleur.addView(editCouleur);
        ligneBouton.addView(btnModifier);

        tableauDescription.addView(ligneID, parametre);
        tableauDescription.addView(ligneNom, parametre);
        tableauDescription.addView(ligneAcronyme, parametre);
        tableauDescription.addView(ligneCouleur, parametre);
        tableauDescription.addView(ligneBouton, parametre);
    }

    private void modifierDescription() {
        editNom.setEnabled(true);
        editAcronyme.setEnabled(true);
        editCouleur.setEnabled(true);

        ligneBouton.removeAllViews();

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
