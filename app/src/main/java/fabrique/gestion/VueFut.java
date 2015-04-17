package fabrique.gestion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.Objets.Fut;

public class VueFut extends TableLayout implements View.OnClickListener {

    private Fut fut;

    private LinearLayout tableauDescription;
    private EditText editTitre, editCapacite;
    private TableRow ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    protected VueFut(Context contexte, Fut fut) {
        super(contexte);

        this.fut = fut;

        RelativeLayout contenantDescription = new RelativeLayout(contexte);
        RelativeLayout.LayoutParams parametreContenantDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addView(contenantDescription, parametreContenantDescription);

        LinearLayout contenantTitreDescription = new LinearLayout(contexte);
        contenantTitreDescription.setBackgroundColor(Color.BLACK);
        contenantTitreDescription.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContenantTitreDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContenantTitreDescription.setMargins(10, 1, 0, 0);
        TextView titreDescription2 = new TextView(contexte);
        titreDescription2.setText(" Description du fût ");
        titreDescription2.setTypeface(null, Typeface.BOLD);
        titreDescription2.setBackgroundColor(Color.WHITE);
        contenantTitreDescription.addView(titreDescription2);
        contenantDescription.addView(contenantTitreDescription, parametreContenantTitreDescription);

        RelativeLayout contourDescription = new RelativeLayout(contexte);
        contourDescription.setBackgroundColor(Color.BLACK);
        contourDescription.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContourDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContourDescription.topMargin=15;
        parametreContourDescription.leftMargin=5;
        contenantDescription.addView(contourDescription, parametreContourDescription);

        TextView titreDescription = new TextView(contexte);
        titreDescription.setText(" Description du fût ");
        titreDescription.setTypeface(null, Typeface.BOLD);
        titreDescription.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams parametreTitreDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreTitreDescription.setMargins(11, 2, 0, 0);
        contenantDescription.addView(titreDescription, parametreTitreDescription);

        tableauDescription = new TableLayout(contexte);
        tableauDescription.setOrientation(LinearLayout.VERTICAL);
        tableauDescription.setBackgroundColor(Color.WHITE);
        contourDescription.addView(tableauDescription);

        afficherDescription();
    }

    private void afficherDescription() {
        tableauDescription.removeAllViews();

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreInspection = new TableRow(getContext());
        LinearLayout layoutTitre = new LinearLayout(getContext());
        TextView titre = new TextView(getContext());
        titre.setText("Fut ");
        titre.setTypeface(null, Typeface.BOLD);

        editTitre = new EditText(getContext());
        editTitre.setText("" + fut.getNumero());
        editTitre.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTitre.setEnabled(false);

        TextView dateInspection = new TextView(getContext());
        dateInspection.setText("" + fut.getDateInspectionToString());
        if ((System.currentTimeMillis() - fut.getDateInspection()) >= TableGestion.instance(getContext()).delaiInspectionBaril()) {
            dateInspection.setTextColor(Color.RED);
        } else if ((System.currentTimeMillis() - fut.getDateInspection()) >= (TableGestion.instance(getContext()).delaiInspectionBaril()-172800000)) {
            dateInspection.setTextColor(Color.rgb(198, 193, 13));
        } else {
            dateInspection.setTextColor(Color.rgb(34, 177, 76));
        }

        TableRow ligneCapacite = new TableRow(getContext());
        LinearLayout layoutCapacite = new LinearLayout(getContext());
        TextView capacite = new TextView(getContext());
        capacite.setText("Capacité : ");

        editCapacite = new EditText(getContext());
        editCapacite.setText("" + fut.getCapacite());
        editCapacite.setInputType(InputType.TYPE_CLASS_NUMBER);
        editCapacite.setEnabled(false);

        TableRow ligneEtatDate = new TableRow(getContext());
        TextView etat = new TextView(getContext());
        etat.setText("État : " + fut.getEtat(getContext()).getTexte());

        TextView dateEtat = new TextView(getContext());
        dateEtat.setText("Depuis le : " + fut.getDateEtat());

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

                layoutTitre.addView(titre);
                layoutTitre.addView(editTitre);
            ligneTitreInspection.addView(layoutTitre, parametre);
            ligneTitreInspection.addView(dateInspection, parametre);
        tableauDescription.addView(ligneTitreInspection);
                layoutCapacite.addView(capacite);
                layoutCapacite.addView(editCapacite);
            ligneCapacite.addView(layoutCapacite, parametre);
        tableauDescription.addView(ligneCapacite);
            ligneEtatDate.addView(etat, parametre);
            ligneEtatDate.addView(dateEtat, parametre);
        tableauDescription.addView(ligneEtatDate);
            ligneBouton.addView(btnModifier, parametre);
        tableauDescription.addView(ligneBouton);
    }

    private void afficherModifier() {
        editTitre.setEnabled(true);
        editCapacite.setEnabled(true);
        ligneBouton.removeAllViews();
        ligneBouton.addView(btnValider);
        ligneBouton.addView(btnAnnuler);
    }

    private void modifier() {
        String erreur = "";
        int numero = 0;
        if (editTitre.getText().toString().equals("")) {
            erreur = erreur + "Le fût doit avoir un numéro.";
        } else {
            try {
                numero = Integer.parseInt(editTitre.getText().toString());
            } catch (NumberFormatException e) {
                erreur = erreur + "Le numéro est trop grand.";
            }
        }

        int capacite = 0;
        try {
            capacite = Integer.parseInt(editCapacite.getText().toString());
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La quantité est trop grande.";
        }
        if (erreur.equals("")) {
            TableFut.instance(getContext()).modifier(fut.getId(), numero, capacite,fut.getId_etat(), fut.getLongDateEtat(), fut.getId_brassin(), fut.getDateInspection());
            reafficherDescription();
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_LONG).show();
        }
    }

    private void reafficherDescription() {
        editTitre.setEnabled(false);
        editCapacite.setEnabled(false);
        ligneBouton.removeAllViews();
        ligneBouton.addView(btnModifier);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            afficherModifier();
        } else if (v.equals(btnValider)) {
            modifier();
        } else if (v.equals(btnAnnuler)) {
            reafficherDescription();
        }
    }
}
