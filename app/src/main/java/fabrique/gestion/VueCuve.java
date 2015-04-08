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

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.Objets.Emplacement;
import fabrique.gestion.Objets.Cuve;

public class VueCuve extends TableLayout implements View.OnClickListener {

    private Button btnModifier, btnValider, btnAnnuler;

    private Cuve Cuve;

    private TableLayout tableauDescription;

    private Spinner editEmplacement;
    private EditText editCapacite;

    private TableRow ligneBouton;

    private int index;

    private ArrayList<Emplacement> emplacements;

    protected VueCuve(Context contexte, Cuve Cuve) {
        super(contexte);

        this.Cuve = Cuve;

        tableauDescription = new TableLayout(contexte);
        addView(tableauDescription);

        afficherDescription();
    }

    private void afficherDescription() {
        tableauDescription.removeAllViews();

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreLavageAcide = new TableRow(getContext());
        TextView titre = new TextView(getContext());
        titre.setText("Cuve " + Cuve.getNumero());
        titre.setTypeface(null, Typeface.BOLD);

        TextView dateLavageAcide = new TextView(getContext());
        dateLavageAcide.setText("" + Cuve.getDateLavageAcideToString());
        if ((System.currentTimeMillis() - Cuve.getDateLavageAcide()) >= TableGestion.instance(getContext()).delaiLavageAcide()) {
            dateLavageAcide.setTextColor(Color.RED);
        } else if ((System.currentTimeMillis() - Cuve.getDateLavageAcide()) >= (TableGestion.instance(getContext()).delaiLavageAcide()-172800000)) {
            dateLavageAcide.setTextColor(Color.rgb(198, 193, 13));
        } else {
            dateLavageAcide.setTextColor(Color.rgb(34, 177, 76));
        }

        TableRow ligneCapaciteEmplacement = new TableRow(getContext());
        LinearLayout layoutCapacite = new LinearLayout(getContext());
        TextView capacite = new TextView(getContext());
        capacite.setText("Capacité : ");

        editCapacite = new EditText(getContext());
        editCapacite.setText("" + Cuve.getCapacite());
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
        index = -1;
        for (int i=0; i<emplacements.size() ; i++) {
            if (Cuve.getEmplacement(getContext()).getTexte().equals(emplacements.get(i))) {
                index = i;
            }
        }
        if (index != -1) {
            editEmplacement.getItemAtPosition(index);
        } else {
            emplacements.add(Cuve.getEmplacement(getContext()));
            adapteurEmplacement.add(TableEmplacement.instance(getContext()).recupererId(Cuve.getEmplacement(getContext()).getId()).getTexte());
            editEmplacement.getItemAtPosition(editEmplacement.getLastVisiblePosition());
        }


        TableRow ligneEtatDate = new TableRow(getContext());
        TextView etat = new TextView(getContext());
        etat.setText("État : " + Cuve.getEtat(getContext()).getTexte());

        TextView dateEtat = new TextView(getContext());
        dateEtat.setText("Depuis le : " + Cuve.getDateEtat());

        ligneBouton = new TableRow(getContext());
        btnModifier = new Button(getContext());
        btnModifier.setText("Modifier");
        btnModifier.setOnClickListener(this);

        ligneTitreLavageAcide.addView(titre, parametre);
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
        Cuve.setCapacite(Integer.parseInt(editCapacite.getText().toString()));
        index = editEmplacement.getSelectedItemPosition();
        Cuve.setEmplacement(emplacements.get((int)editEmplacement.getSelectedItemId()).getId());
        reafficherDescription();
    }

    private void reafficherDescription() {
        editCapacite.setEnabled(false);
        editCapacite.setText("" + Cuve.getCapacite());

        editEmplacement.setEnabled(false);
        editEmplacement.setSelection(index);

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
