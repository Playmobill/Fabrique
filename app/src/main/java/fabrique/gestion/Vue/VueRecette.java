package fabrique.gestion.Vue;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.BDD.TableTypeBiere;
import fabrique.gestion.ColorPicker.ColorPickerDialog;
import fabrique.gestion.Objets.Recette;
import fabrique.gestion.Objets.TypeBiere;
import fabrique.gestion.R;

public class VueRecette extends TableLayout implements View.OnClickListener {

    private Recette recette;

    private LinearLayout tableauDescription, ligneBoutonCouleur;
    private EditText editNom, editAcronyme;
    private Spinner editTypeBiere;
    private ArrayList<TypeBiere> typeBieres;
    private int indexTypeBiere;
    private TableRow ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler, couleurTexte, couleurFond;
    private EditText couleurAffichage;

    public VueRecette(Context context) {
        super(context);
    }

    public VueRecette(Context contexte, Recette recette) {
        super(contexte);

        this.recette = recette;

        tableauDescription = new TableLayout(contexte);
        addView(cadre(tableauDescription));
        initialiser();
        afficher();
    }

    private RelativeLayout cadre(View view) {
        RelativeLayout contenant = new RelativeLayout(getContext());

        RelativeLayout contour = new RelativeLayout(getContext());
        contour.setBackgroundColor(Color.BLACK);
        contour.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContour = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContour.topMargin=15;
        parametreContour.leftMargin=5;

        view.setBackgroundColor(Color.WHITE);

        contenant.addView(contour, parametreContour);
        contour.addView(view);
        return contenant;
    }

    private void initialiser() {
        TableLayout.LayoutParams parametre=new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 0, 10, 0);

        LinearLayout ligneNom = new LinearLayout(getContext());
        TextView txtNom = new TextView(getContext());
        txtNom.setText("Nom : ");

        editNom = new EditText(getContext());

        LinearLayout ligneAcronyme = new LinearLayout(getContext());
        TextView txtAcronyme = new TextView(getContext());
        txtAcronyme.setText("Acronyme : ");

        editAcronyme = new EditText(getContext());

        LinearLayout ligneCouleur = new LinearLayout(getContext());
        TextView txtTypeBiere = new TextView(getContext());
        txtTypeBiere.setText("Type de bière : ");

        editTypeBiere = new Spinner(getContext());
        TableTypeBiere tableTypeBiere = TableTypeBiere.instance(getContext());
        //Liste des types de biere active
        typeBieres = tableTypeBiere.recupererTypeBiereActif();
        ArrayAdapter<String> adapteurTypeBiere = new ArrayAdapter<>(getContext(), R.layout.spinner_style, tableTypeBiere.recupererNomTypeBieresActifs());
        adapteurTypeBiere.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTypeBiere.setAdapter(adapteurTypeBiere);
        editTypeBiere.setEnabled(false);
        indexTypeBiere = -1;
        //Recherche de l'index du type de bière
        for (int i=0; i<typeBieres.size() ; i++) {
            if (recette.getTypeBiere(getContext()).getId() == typeBieres.get(i).getId()) {
                indexTypeBiere = i;
            }
        }
        //Si il n'a pas été trouvé alors on ajoute le type de biere dans la liste
        if (indexTypeBiere == -1) {
            typeBieres.add(recette.getTypeBiere(getContext()));
            adapteurTypeBiere.add(tableTypeBiere.recupererId(recette.getTypeBiere(getContext()).getId()).getNom());
            indexTypeBiere = adapteurTypeBiere.getCount()-1;
        }

        couleurAffichage = new EditText(getContext());
        couleurAffichage.setText("Couleur d'affichage");
        couleurAffichage.setEnabled(false);

        ligneBoutonCouleur = new TableRow(getContext());
            couleurTexte = new Button(getContext());
            couleurTexte.setText("Couleur du texte");
            couleurTexte.setOnClickListener(this);

            couleurFond = new Button(getContext());
            couleurFond.setText("Couleur de fond");
            couleurFond.setOnClickListener(this);

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

        //ligneID.addView(titre);
        ligneNom.addView(txtNom);
        ligneNom.addView(editNom);
        ligneAcronyme.addView(txtAcronyme);
        ligneAcronyme.addView(editAcronyme);
        ligneCouleur.addView(txtTypeBiere);
        ligneCouleur.addView(editTypeBiere);
        ligneBouton.addView(btnModifier);

        //tableauDescription.addView(ligneID, parametre);
        tableauDescription.addView(ligneNom, parametre);
        tableauDescription.addView(ligneAcronyme, parametre);
        tableauDescription.addView(ligneCouleur, parametre);
        tableauDescription.addView(couleurAffichage);
        tableauDescription.addView(ligneBoutonCouleur, parametre);
        tableauDescription.addView(ligneBouton, parametre);
    }

    private void afficher() {
        editNom.setText("" + recette.getNom());
        editNom.setEnabled(false);

        editAcronyme.setText("" + recette.getAcronyme());
        editAcronyme.setEnabled(false);

        editTypeBiere.setSelection(indexTypeBiere);
        editTypeBiere.setEnabled(false);

        couleurAffichage.setTextColor(recette.getCouleurTexte());
        couleurAffichage.setDrawingCacheBackgroundColor(recette.getCouleurFond());
        couleurAffichage.setBackgroundColor(recette.getCouleurFond());

        ligneBoutonCouleur.removeAllViews();

        ligneBouton.removeAllViews();
            btnModifier = new Button(getContext());
            btnModifier.setText("Modifier");
            btnModifier.setOnClickListener(this);
        ligneBouton.addView(btnModifier);
    }

    private void modifier() {
        editNom.setEnabled(true);
        editAcronyme.setEnabled(true);
        editTypeBiere.setEnabled(true);

        ligneBoutonCouleur.addView(couleurTexte);
        ligneBoutonCouleur.addView(couleurFond);

        ligneBouton.removeAllViews();

        ligneBouton.addView(btnValider);
        ligneBouton.addView(btnAnnuler);
    }

    private void valider() {
        indexTypeBiere = editTypeBiere.getSelectedItemPosition();

        TableRecette.instance(getContext()).modifier(
                recette.getId(),
                editNom.getText().toString(),
                editAcronyme.getText().toString(),
                typeBieres.get(indexTypeBiere).getId(),
                couleurAffichage.getCurrentTextColor(),
                couleurAffichage.getDrawingCacheBackgroundColor(),
                recette.getActif());
        recette = TableRecette.instance(getContext()).recupererId(recette.getId());
        afficher();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifier();
        } else if (v.equals(btnValider)) {
            valider();
        } else if (v.equals(btnAnnuler)) {
            afficher();
        } else if (v.equals(couleurTexte)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Texte", couleurAffichage);
            dialog.show();
        } else if (v.equals(couleurFond)) {
            ColorPickerDialog dialog = new ColorPickerDialog(getContext(), "Fond", couleurAffichage);
            dialog.show();
        }
    }
}
