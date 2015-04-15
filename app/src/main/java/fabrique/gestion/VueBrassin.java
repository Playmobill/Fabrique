package fabrique.gestion;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.Objets.Brassin;

public class VueBrassin extends TableLayout implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Brassin brassin;

    //Description
    private LinearLayout tableauDescription;
    private Spinner editRecette;
    private EditText editTitre, editQuantite, editDensiteOriginale, editDensiteFinale, editPourcentageAlcool;
    private int indexRecette;

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

        tableauDescription = new TableLayout(contexte);
        tableauDescription.setOrientation(LinearLayout.VERTICAL);
        addView(tableauDescription);

        afficherDescription();
    }

    private void afficherDescription() {
        tableauDescription.removeAllViews();

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreDateCreation = new TableRow(getContext());
            LinearLayout layoutTitre = new LinearLayout(getContext());
                TextView titre = new TextView(getContext());
                titre.setText("Brassin ");
                titre.setTypeface(null, Typeface.BOLD);

                editTitre = new EditText(getContext());
                editTitre.setText("" + brassin.getNumero());
                editTitre.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTitre.setTypeface(null, Typeface.BOLD);
                editTitre.setEnabled(false);

            LinearLayout layoutDateCreation = new LinearLayout(getContext());
                TextView dateCreation = new TextView(getContext());
                dateCreation.setText("Date du brassin ");

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

        EditText editCommentaire = new EditText(getContext());
        editCommentaire.setText(brassin.getCommentaire());

        TableRow ligneRecetteQuantite = new TableRow(getContext());
            LinearLayout layoutRecette = new LinearLayout(getContext());
                TextView recette = new TextView(getContext());
                recette.setText("Recette : ");

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
                editQuantite.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
                editQuantite.setEnabled(false);

        TableRow ligneDensite = new TableRow(getContext());
            LinearLayout layoutDensiteOriginale = new LinearLayout(getContext());
                TextView densiteOriginale = new TextView(getContext());
                densiteOriginale.setText("Densité originale : ");

                editDensiteOriginale = new EditText(getContext());
                editDensiteOriginale.setText("" + brassin.getDensiteOriginale());
                editDensiteOriginale.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editDensiteOriginale.setEnabled(false);

            LinearLayout layoutDensiteFinale = new LinearLayout(getContext());
                TextView densiteFinale = new TextView(getContext());
                densiteFinale.setText("Densité finale : ");

                editDensiteFinale = new EditText(getContext());
                editDensiteFinale.setText("" + brassin.getDensiteFinale());
                editDensiteFinale.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editDensiteFinale.setEnabled(false);

        LinearLayout lignePourcentageAlcool = new LinearLayout(getContext());
            LinearLayout layoutPourcentageAlcool = new LinearLayout(getContext());
                TextView pourcentageAlcool = new TextView(getContext());
                pourcentageAlcool.setText("%Alc/vol : ");

                editPourcentageAlcool = new EditText(getContext());
                editPourcentageAlcool.setText("" + brassin.getPourcentageAlcool());
                editPourcentageAlcool.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editPourcentageAlcool.setEnabled(false);

        tableauDescription.addView(ligneTitreDateCreation);
            ligneTitreDateCreation.addView(layoutTitre);
                layoutTitre.addView(titre, parametre);
                layoutTitre.addView(editTitre, parametre);
            ligneTitreDateCreation.addView(layoutDateCreation);
                layoutDateCreation.addView(dateCreation, parametre);
                layoutDateCreation.addView(editDateCreation, parametre);
                layoutDateCreation.addView(btnDateCreation, parametre);
        tableauDescription.addView(ligneRecetteQuantite);
            ligneRecetteQuantite.addView(layoutRecette);
                layoutRecette.addView(recette, parametre);
                layoutRecette.addView(editRecette, parametre);
            ligneRecetteQuantite.addView(layoutQuantite);
                layoutQuantite.addView(quantite, parametre);
                layoutQuantite.addView(editQuantite, parametre);
        tableauDescription.addView(ligneDensite);
            ligneDensite.addView(layoutDensiteOriginale);
                layoutDensiteOriginale.addView(densiteOriginale, parametre);
                layoutDensiteOriginale.addView(editDensiteOriginale, parametre);
            ligneDensite.addView(layoutDensiteFinale);
                layoutDensiteFinale.addView(densiteFinale, parametre);
                layoutDensiteFinale.addView(editDensiteFinale, parametre);
        tableauDescription.addView(lignePourcentageAlcool);
            lignePourcentageAlcool.addView(layoutPourcentageAlcool);
                lignePourcentageAlcool.addView(pourcentageAlcool, parametre);
                lignePourcentageAlcool.addView(editPourcentageAlcool, parametre);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnDateCreation)){
            new DatePickerDialog(getContext(), this, annee, mois, jour).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
        this.annee = annee;
        this.mois = mois;
        this.jour = jour;
        editDateCreation.setText(jour  + " / " + mois + " / " + annee);
    }
}
