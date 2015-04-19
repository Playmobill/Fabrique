package fabrique.gestion;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Recette;

public class VueBrassin extends TableLayout implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Brassin brassin;

    //Description
    private LinearLayout tableauDescription;
    private Spinner editRecette;
    private ArrayList<Recette> listeRecetteActifs;
    private int indexRecette;
    private EditText editNumero, editQuantite, editCommentaire, editDensiteOriginale, editDensiteFinale, editPourcentageAlcool;
    private LinearLayout layoutBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    //DatePicker
    private ImageButton btnDateCreation;
    protected int jour;
    protected int mois;
    protected int annee;
    private EditText editDateCreation;

    //Historique
    private LinearLayout tableauHistorique;

    protected VueBrassin(Context contexte, Brassin brassin) {
        super(contexte);

        this.brassin = brassin;

        RelativeLayout contenantDescription = new RelativeLayout(contexte);
        RelativeLayout.LayoutParams parametreContenantDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addView(contenantDescription, parametreContenantDescription);

        LinearLayout contenantTitreDescription = new LinearLayout(contexte);
        contenantTitreDescription.setBackgroundColor(Color.BLACK);
        contenantTitreDescription.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContenantTitreDescription = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContenantTitreDescription.setMargins(10, 1, 0, 0);
            TextView titreDescription2 = new TextView(contexte);
            titreDescription2.setText(" Description du brassin ");
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
        titreDescription.setText(" Description du brassin ");
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

        TableRow.LayoutParams parametreLigne = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TableRow.LayoutParams parametreElement = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametreElement.setMargins(10, 10, 10, 10);

        TableRow ligneNumeroDateCreation = new TableRow(getContext());
            LinearLayout layoutNumero = new LinearLayout(getContext());
                TextView numero = new TextView(getContext());
                numero.setText("Brassin ");
                numero.setTypeface(null, Typeface.BOLD);

                editNumero = new EditText(getContext());
                editNumero.setText("" + brassin.getNumero());
                editNumero.setInputType(InputType.TYPE_CLASS_NUMBER);
                editNumero.setTypeface(null, Typeface.BOLD);
                editNumero.setEnabled(false);

            LinearLayout layoutDateCreation = new LinearLayout(getContext());
                TextView dateCreation = new TextView(getContext());
                dateCreation.setText("Date de création ");

                Calendar calendrier = Calendar.getInstance();
                jour = calendrier.get(Calendar.DAY_OF_MONTH);
                mois = calendrier.get(Calendar.MONTH);
                annee = calendrier.get(Calendar.YEAR);
                editDateCreation = new EditText(getContext());
                editDateCreation.setText(jour + " / " + (mois + 1) + " / " + annee);
                editDateCreation.setEnabled(false);

                btnDateCreation = new ImageButton(getContext());
                Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.calendar_icon);
                btnDateCreation.setImageBitmap(Bitmap.createScaledBitmap(image, 15, 15, false));
                btnDateCreation.setOnClickListener(this);
                btnDateCreation.setEnabled(false);

        TableRow ligneRecetteQuantite = new TableRow(getContext());
            LinearLayout layoutRecette = new LinearLayout(getContext());
                TextView recette = new TextView(getContext());
                recette.setText("Recette : ");

                listeRecetteActifs = TableRecette.instance(getContext()).recupererRecetteActif();
                editRecette = new Spinner(getContext());
                ArrayAdapter<String> adapteurRecette = new ArrayAdapter<>(getContext(), R.layout.spinner_style, TableRecette.instance(getContext()).recupererRecettesActifs());
                adapteurRecette.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editRecette.setAdapter(adapteurRecette);
                editRecette.setEnabled(false);
                indexRecette = -1;
                ArrayList<String> recettes = TableRecette.instance(getContext()).recupererRecettesActifs();
                for (int i=0; i<recettes.size() ; i++) {
                    if (brassin.getRecette(getContext()).getId() == TableRecette.instance(getContext()).recupererIndex(i).getId()) {
                        indexRecette = i;
                    }
                }
                if (indexRecette != -1) {
                    editRecette.setSelection(indexRecette);
                } else {
                    recettes.add(brassin.getRecette(getContext()).getNom());
                    adapteurRecette.add(TableRecette.instance(getContext()).recupererId(brassin.getRecette(getContext()).getId()).getNom());
                    editRecette.setSelection(editRecette.getLastVisiblePosition());
                }

            LinearLayout layoutQuantite = new LinearLayout(getContext());
                TextView quantite = new TextView(getContext());
                quantite.setText("Quantité : ");

                editQuantite = new EditText(getContext());
                editQuantite.setText("" + brassin.getQuantite());
                editQuantite.setInputType(InputType.TYPE_CLASS_NUMBER);
                editQuantite.setEnabled(false);

        LinearLayout ligneCommentaire = new LinearLayout(getContext());
            LinearLayout layoutCommentaire = new LinearLayout(getContext());
                TextView commentaire = new TextView(getContext());
                commentaire.setText("Commentaire : ");

                editCommentaire = new EditText(getContext());
                editCommentaire.setText(brassin.getCommentaire());
                editCommentaire.setEnabled(false);

        TableRow ligneDensite = new TableRow(getContext());
            LinearLayout layoutDensiteOriginale = new LinearLayout(getContext());
                TextView densiteOriginale = new TextView(getContext());
                densiteOriginale.setText("Densité originale : ");

                editDensiteOriginale = new EditText(getContext());
                editDensiteOriginale.setText("" + brassin.getDensiteOriginale());
                editDensiteOriginale.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editDensiteOriginale.setEnabled(false);

            LinearLayout layoutDensiteFinale = new LinearLayout(getContext());
                TextView densiteFinale = new TextView(getContext());
                densiteFinale.setText("Densité finale : ");

                editDensiteFinale = new EditText(getContext());
                editDensiteFinale.setText("" + brassin.getDensiteFinale());
                editDensiteFinale.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editDensiteFinale.setEnabled(false);

        LinearLayout lignePourcentageAlcool = new LinearLayout(getContext());
            LinearLayout layoutPourcentageAlcool = new LinearLayout(getContext());
                TextView pourcentageAlcool = new TextView(getContext());
                pourcentageAlcool.setText("%Alc/vol : ");

                editPourcentageAlcool = new EditText(getContext());
                editPourcentageAlcool.setText("" + brassin.getPourcentageAlcool());
                editPourcentageAlcool.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editPourcentageAlcool.setEnabled(false);
        LinearLayout ligneBouton = new LinearLayout(getContext());
            layoutBouton = new TableRow(getContext());
                btnModifier = new Button(getContext());
                btnModifier.setText("Modifier");
                btnModifier.setOnClickListener(this);

                btnValider = new Button(getContext());
                btnValider.setText("Valider");
                btnValider.setOnClickListener(this);

                btnAnnuler = new Button(getContext());
                btnAnnuler.setText("Annuler");
                btnAnnuler.setOnClickListener(this);

        tableauDescription.addView(ligneNumeroDateCreation, parametreLigne);
            ligneNumeroDateCreation.addView(layoutNumero);
                layoutNumero.addView(numero, parametreElement);
                layoutNumero.addView(editNumero, parametreElement);
            ligneNumeroDateCreation.addView(layoutDateCreation);
                layoutDateCreation.addView(dateCreation, parametreElement);
                layoutDateCreation.addView(editDateCreation, parametreElement);
                layoutDateCreation.addView(btnDateCreation, parametreElement);
        tableauDescription.addView(ligneRecetteQuantite, parametreLigne);
            ligneRecetteQuantite.addView(layoutRecette);
                layoutRecette.addView(recette, parametreElement);
                layoutRecette.addView(editRecette, parametreElement);
            ligneRecetteQuantite.addView(layoutQuantite);
                layoutQuantite.addView(quantite, parametreElement);
                layoutQuantite.addView(editQuantite, parametreElement);
        tableauDescription.addView(ligneCommentaire, parametreLigne);
            ligneCommentaire.addView(layoutCommentaire);
                layoutCommentaire.addView(commentaire, parametreElement);
                layoutCommentaire.addView(editCommentaire, parametreElement);
        tableauDescription.addView(ligneDensite, parametreLigne);
            ligneDensite.addView(layoutDensiteOriginale);
                layoutDensiteOriginale.addView(densiteOriginale, parametreElement);
                layoutDensiteOriginale.addView(editDensiteOriginale, parametreElement);
            ligneDensite.addView(layoutDensiteFinale);
                layoutDensiteFinale.addView(densiteFinale, parametreElement);
                layoutDensiteFinale.addView(editDensiteFinale, parametreElement);
        tableauDescription.addView(lignePourcentageAlcool, parametreLigne);
            lignePourcentageAlcool.addView(layoutPourcentageAlcool);
                lignePourcentageAlcool.addView(pourcentageAlcool, parametreElement);
                lignePourcentageAlcool.addView(editPourcentageAlcool, parametreElement);
        tableauDescription.addView(ligneBouton, parametreLigne);
            ligneBouton.addView(layoutBouton);
                layoutBouton.addView(btnModifier, parametreElement);
    }

    private void afficherModifier() {
        editNumero.setEnabled(true);
        editRecette.setEnabled(true);
        editQuantite.setEnabled(true);
        editCommentaire.setEnabled(true);
        editDensiteOriginale.setEnabled(true);
        editDensiteFinale.setEnabled(true);
        editPourcentageAlcool.setEnabled(true);
        btnDateCreation.setEnabled(true);
        layoutBouton.removeAllViews();
        layoutBouton.addView(btnValider);
        layoutBouton.addView(btnAnnuler);
    }

    private void modifier() {
        String erreur = "";

        int numero = 0;
        if (editNumero.getText().toString().equals("")) {
            erreur = erreur + "Le brassin doit avoir un numéro.";
        } else {
            try {
                numero = Integer.parseInt(editNumero.getText().toString());
            } catch (NumberFormatException e) {
                erreur = erreur + "Le numéro est trop grand.";
            }
        }

        int quantite = 0;
        try {
            if (!editQuantite.getText().toString().equals("")) {
                quantite = Integer.parseInt(editQuantite.getText().toString());
            }
        } catch(NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La quantité est trop grande.";
        }

        float densiteOriginale = 0;
        try {
            if ((!editDensiteOriginale.getText().toString().equals(""))) {
                densiteOriginale = Float.parseFloat(editDensiteOriginale.getText().toString());
            }
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La densité originale est trop grande.";
        }

        float densiteFinale = 0;
        try {
            if ((editDensiteFinale.getText() != null) && (!editDensiteFinale.getText().toString().equals(""))) {
                densiteFinale = Float.parseFloat(editDensiteFinale.getText().toString());
            }
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La densité final est trop grande.";
        }

        float pourcentageAlcool = 0;
        try {
            if ((editPourcentageAlcool.getText() != null) && (!editPourcentageAlcool.getText().toString().equals(""))) {
                pourcentageAlcool = Float.parseFloat(editPourcentageAlcool.getText().toString());
            }
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "Le pourcentage d'alcool est trop grand.";
        }

        if (erreur.equals("")) {
            long recette = listeRecetteActifs.get(editRecette.getSelectedItemPosition()-1).getId();
            TableBrassin.instance(getContext()).modifier(brassin.getId(), numero, editCommentaire.getText().toString() + "", System.currentTimeMillis(), quantite, recette, densiteOriginale, densiteFinale, pourcentageAlcool);
            indexRecette = (int)recette;
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_LONG).show();
        }
    }

    private void reafficherDescription() {
        editNumero.setEnabled(false);
        editRecette.setEnabled(false);
        editRecette.setSelection(indexRecette);
        editQuantite.setEnabled(false);
        editCommentaire.setEnabled(false);
        editDensiteOriginale.setEnabled(false);
        editDensiteFinale.setEnabled(false);
        editPourcentageAlcool.setEnabled(false);
        btnDateCreation.setEnabled(false);
        layoutBouton.removeAllViews();
        layoutBouton.addView(btnModifier);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            afficherModifier();
        } else if (v.equals(btnValider)) {
            modifier();
            reafficherDescription();
        } else if (v.equals(btnAnnuler)) {
            reafficherDescription();
        }
        if (v.equals(btnDateCreation)){
            new DatePickerDialog(getContext(), this, annee, mois, jour).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
        editDateCreation.setText(jour + " / " + mois + " / " + annee);
    }
}