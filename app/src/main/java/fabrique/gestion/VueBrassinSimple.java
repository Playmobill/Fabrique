package fabrique.gestion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.Objets.Brassin;

public class VueBrassinSimple extends LinearLayout {

    private Brassin brassin;

    //Description
    private LinearLayout tableauDescription;

    public VueBrassinSimple(Context contexte) {
        super(contexte);
    }

    public VueBrassinSimple(Context contexte, Brassin brassin) {
        super(contexte);

        this.brassin = brassin;

        LinearLayout ligne = new LinearLayout(contexte);

        tableauDescription = new TableLayout(getContext());
        tableauDescription.setOrientation(LinearLayout.VERTICAL);
        tableauDescription.setBackgroundColor(Color.WHITE);
        ligne.addView(cadre(tableauDescription, " Description "));
        afficherDescription();

        addView(tableauDescription);
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

        contenant.addView(contourTitre, parametreContourTitre);
        contourTitre.addView(fondTitre);
        contenant.addView(contour, parametreContour);
        contour.addView(view);
        contenant.addView(titre, parametreTitre);
        return contenant;
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

                TextView editNumero = new TextView(getContext());
                editNumero.setText("" + brassin.getNumero());
                editNumero.setTypeface(null, Typeface.BOLD);

            LinearLayout layoutDateCreation = new LinearLayout(getContext());
                TextView dateCreation = new TextView(getContext());
                dateCreation.setText("Date de création ");

                TextView editDateCreation = new TextView(getContext());
                editDateCreation.setText(brassin.getDateCreation());

        TableRow ligneRecetteQuantite = new TableRow(getContext());
            LinearLayout layoutRecette = new LinearLayout(getContext());
                TextView recette = new TextView(getContext());
                recette.setText("Recette : ");

                TextView editRecette = new TextView(getContext());
                editRecette.setText(brassin.getRecette(getContext()).getNom());

            LinearLayout layoutQuantite = new LinearLayout(getContext());
                TextView quantite = new TextView(getContext());
                quantite.setText("Quantité : ");

                TextView editQuantite = new TextView(getContext());
                editQuantite.setText("" + brassin.getQuantite());

        LinearLayout ligneCommentaire = new LinearLayout(getContext());
            LinearLayout layoutCommentaire = new LinearLayout(getContext());
                TextView commentaire = new TextView(getContext());
                commentaire.setText("Commentaire : ");

                TextView editCommentaire = new TextView(getContext());
                editCommentaire.setText(brassin.getCommentaire());

        TableRow ligneDensite = new TableRow(getContext());
            LinearLayout layoutDensiteOriginale = new LinearLayout(getContext());
                TextView densiteOriginale = new TextView(getContext());
                densiteOriginale.setText("Densité originale : ");

                TextView editDensiteOriginale = new EditText(getContext());
                editDensiteOriginale.setText("" + brassin.getDensiteOriginale());

            LinearLayout layoutDensiteFinale = new LinearLayout(getContext());
                TextView densiteFinale = new TextView(getContext());
                densiteFinale.setText("Densité finale : ");

                TextView editDensiteFinale = new TextView(getContext());
                editDensiteFinale.setText("" + brassin.getDensiteFinale());

        LinearLayout lignePourcentageAlcool = new LinearLayout(getContext());
            LinearLayout layoutPourcentageAlcool = new LinearLayout(getContext());
                TextView pourcentageAlcool = new TextView(getContext());
                pourcentageAlcool.setText("%Alc/vol : ");

                TextView editPourcentageAlcool = new TextView(getContext());
                editPourcentageAlcool.setText("" + brassin.getPourcentageAlcool());


        tableauDescription.addView(ligneNumeroDateCreation, parametreLigne);
            ligneNumeroDateCreation.addView(layoutNumero);
                layoutNumero.addView(numero, parametreElement);
                layoutNumero.addView(editNumero, parametreElement);
            ligneNumeroDateCreation.addView(layoutDateCreation);
                layoutDateCreation.addView(dateCreation, parametreElement);
                layoutDateCreation.addView(editDateCreation, parametreElement);
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
    }
}
