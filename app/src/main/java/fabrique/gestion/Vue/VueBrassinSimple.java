package fabrique.gestion.Vue;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
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

        tableauDescription = new TableLayout(getContext());
        addView(cadre(tableauDescription, " Brassin "));
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

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneNumeroDateCreation = new TableRow(getContext());
            TextView numero = new TextView(getContext());
            numero.setText("Brassin " + brassin.getNumero());
            numero.setTypeface(null, Typeface.BOLD);

            TextView dateCreation = new TextView(getContext());
            dateCreation.setText("Date de création " + brassin.getDateCreation());

        TableRow ligneRecetteQuantite = new TableRow(getContext());
            TextView recette = new TextView(getContext());
            recette.setText("Recette : " + brassin.getRecette(getContext()).getNom());

            TextView quantite = new TextView(getContext());
            quantite.setText("Quantité : " + brassin.getQuantite());

        TableRow ligneCommentaire = new TableRow(getContext());
            TextView commentaire = new TextView(getContext());
            commentaire.setText("Commentaire : " + brassin.getCommentaire());

        TableRow ligneDensite = new TableRow(getContext());
            TextView densiteOriginale = new TextView(getContext());
            densiteOriginale.setText("Densité originale : " + brassin.getDensiteOriginale());

            TextView densiteFinale = new TextView(getContext());
            densiteFinale.setText("Densité finale : " + brassin.getDensiteFinale());

        TableRow lignePourcentageAlcool = new TableRow(getContext());
            TextView pourcentageAlcool = new TextView(getContext());
            pourcentageAlcool.setText("%Alc/vol : " + brassin.getPourcentageAlcool());


        tableauDescription.addView(ligneNumeroDateCreation);
            ligneNumeroDateCreation.addView(numero, parametre);
            ligneNumeroDateCreation.addView(dateCreation, parametre);
        tableauDescription.addView(ligneRecetteQuantite);
            ligneRecetteQuantite.addView(recette, parametre);
            ligneRecetteQuantite.addView(quantite, parametre);
        tableauDescription.addView(ligneCommentaire);
            ligneCommentaire.addView(commentaire, parametre);
        tableauDescription.addView(ligneDensite);
            ligneDensite.addView(densiteOriginale, parametre);
            ligneDensite.addView(densiteFinale, parametre);
        tableauDescription.addView(lignePourcentageAlcool);
            lignePourcentageAlcool.addView(pourcentageAlcool, parametre);
    }
}
