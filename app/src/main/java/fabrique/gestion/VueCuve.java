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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Emplacement;

public class VueCuve extends LinearLayout implements View.OnClickListener {

    private Cuve cuve;

    //Description
    private LinearLayout tableauDescription;
    private Spinner editEmplacement;
    private ArrayList<Emplacement> emplacements;
    private int indexEmplacement;
    private EditText editTitre, editCapacite;
    private LinearLayout ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    protected VueCuve(Context contexte, Cuve cuve) {
        super(contexte);

        this.cuve = cuve;

        RelativeLayout contenantDescription = new RelativeLayout(contexte);
        RelativeLayout.LayoutParams parametreContenantDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addView(contenantDescription, parametreContenantDescription);

        LinearLayout contenantTitreDescription = new LinearLayout(contexte);
        contenantTitreDescription.setBackgroundColor(Color.BLACK);
        contenantTitreDescription.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContenantTitreDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContenantTitreDescription.setMargins(10, 1, 0, 0);
        TextView titreDescription2 = new TextView(contexte);
        titreDescription2.setText(" Description de la cuve ");
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
        titreDescription.setText(" Description de la cuve ");
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

        LinearLayout.LayoutParams parametre = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        LinearLayout ligneTitreLavageAcide = new LinearLayout(getContext());
            LinearLayout layoutTitre = new LinearLayout(getContext());
                TextView titre = new TextView(getContext());
                titre.setText("Cuve ");
                titre.setTypeface(null, Typeface.BOLD);

                editTitre = new EditText(getContext());
                editTitre.setText("" + cuve.getNumero());
                editTitre.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTitre.setEnabled(false);

            TextView dateLavageAcide = new TextView(getContext());
            dateLavageAcide.setText("" + cuve.getDateLavageAcideToString());
            if ((System.currentTimeMillis() - cuve.getDateLavageAcide()) >= TableGestion.instance(getContext()).delaiLavageAcide()) {
                dateLavageAcide.setTextColor(Color.RED);
            } else if ((System.currentTimeMillis() - cuve.getDateLavageAcide()) >= (TableGestion.instance(getContext()).delaiLavageAcide()-172800000)) {
                dateLavageAcide.setTextColor(Color.rgb(198, 193, 13));
            } else {
                dateLavageAcide.setTextColor(Color.rgb(34, 177, 76));
            }

        LinearLayout ligneCapaciteEmplacement = new LinearLayout(getContext());
            LinearLayout layoutCapacite = new LinearLayout(getContext());
                TextView capacite = new TextView(getContext());
                capacite.setText("Capacité : ");

                editCapacite = new EditText(getContext());
                editCapacite.setText("" + cuve.getCapacite());
                editCapacite.setInputType(InputType.TYPE_CLASS_NUMBER);
                editCapacite.setEnabled(false);

            LinearLayout layoutEmplacement = new LinearLayout(getContext());
                TextView emplacement = new TextView(getContext());
                emplacement.setText("Emplacement : ");

                editEmplacement = new Spinner(getContext());
                emplacements = TableEmplacement.instance(getContext()).recupererActifs();
                ArrayList<String> texteEmplacements = new ArrayList<>();
                for (int i=0; i<emplacements.size() ; i++) {
                    texteEmplacements.add(emplacements.get(i).getTexte());
                }
                ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(getContext(), R.layout.spinner_style, texteEmplacements);
                adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editEmplacement.setAdapter(adapteurEmplacement);
                editEmplacement.setEnabled(false);
                indexEmplacement = -1;
                for (int i=0; i<emplacements.size() ; i++) {
                    if (cuve.getEmplacement(getContext()).getId() == emplacements.get(i).getId()) {
                        indexEmplacement = i;
                    }
                }
                if (indexEmplacement != -1) {
                    editEmplacement.setSelection(indexEmplacement);
                } else {
                    emplacements.add(cuve.getEmplacement(getContext()));
                    adapteurEmplacement.add(TableEmplacement.instance(getContext()).recupererId(cuve.getEmplacement(getContext()).getId()).getTexte());
                    editEmplacement.setSelection(editEmplacement.getLastVisiblePosition());
                }


        LinearLayout ligneEtatDate = new LinearLayout(getContext());
            TextView etat = new TextView(getContext());
            etat.setText("État : " + cuve.getEtat(getContext()).getTexte());

            TextView dateEtat = new TextView(getContext());
            dateEtat.setText("Depuis le : " + cuve.getDateEtat());

        ligneBouton = new LinearLayout(getContext());
            btnModifier = new Button(getContext());
            btnModifier.setText("Modifier");
            btnModifier.setOnClickListener(this);

                layoutTitre.addView(titre);
                layoutTitre.addView(editTitre);
            ligneTitreLavageAcide.addView(layoutTitre, parametre);
            ligneTitreLavageAcide.addView(dateLavageAcide, parametre);
        tableauDescription.addView(ligneTitreLavageAcide);
                layoutCapacite.addView(capacite);
                layoutCapacite.addView(editCapacite);
            ligneCapaciteEmplacement.addView(layoutCapacite, parametre);
                layoutEmplacement.addView(emplacement);
                layoutEmplacement.addView(editEmplacement);
            ligneCapaciteEmplacement.addView(layoutEmplacement, parametre);
        tableauDescription.addView(ligneCapaciteEmplacement);
            ligneEtatDate.addView(etat, parametre);
            ligneEtatDate.addView(dateEtat, parametre);
        tableauDescription.addView(ligneEtatDate);
            ligneBouton.addView(btnModifier, parametre);
        tableauDescription.addView(ligneBouton);
    }

    private void modifierDescription() {
        editTitre.setEnabled(true);
        editEmplacement.setEnabled(true);
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
            erreur = erreur + "La cuve doit avoir un numéro.";
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
            TableCuve.instance(getContext()).modifier(cuve.getId(), numero, capacite, emplacements.get((int)editEmplacement.getSelectedItemId()).getId(), cuve.getDateLavageAcide(), cuve.getIdEtat(), cuve.getLongDateEtat(), cuve.getCommentaireEtat(), cuve.getIdBrassin());
            indexEmplacement = editEmplacement.getSelectedItemPosition();
            reafficherDescription();
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_LONG).show();
        }
    }

    private void reafficherDescription() {
        editTitre.setEnabled(false);
        editTitre.setText("" + cuve.getNumero());

        editCapacite.setEnabled(false);
        editCapacite.setText("" + cuve.getCapacite());

        editEmplacement.setEnabled(false);
        editEmplacement.setSelection(indexEmplacement);

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
