package fabrique.gestion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.Objets.Fut;

public class VueFut extends TableLayout implements View.OnClickListener {

    private Button btnModifier, btnValider, btnAnnuler;

    private Fut fut;

    private LinearLayout tableauDescription;

    private EditText editTitre, editCapacite;

    private TableRow ligneBouton;

    protected VueFut(Context contexte, Fut fut) {
        super(contexte);

        this.fut = fut;

        tableauDescription = new TableLayout(contexte);
        tableauDescription.setOrientation(LinearLayout.VERTICAL);
        addView(tableauDescription);

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

    private void modifierDescription() {
        editTitre.setEnabled(true);
        editCapacite.setEnabled(true);
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
            TableFut.instance(getContext()).modifier(fut.getId(), Integer.parseInt(editTitre.getText().toString()), Integer.parseInt(editCapacite.getText().toString()),fut.getId_etat(), fut.getLongDateEtat(), fut.getId_brassin(), fut.getDateInspection());
            reafficherDescription();
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_LONG).show();
        }
    }

    private void reafficherDescription() {
        editTitre.setEnabled(false);
        editTitre.setText("" + fut.getNumero());

        editCapacite.setEnabled(false);
        editCapacite.setText("" + fut.getCapacite());

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
