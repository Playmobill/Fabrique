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

import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.Objets.Fermenteur;

public class VueFermenteurSimple extends LinearLayout {

    private Fermenteur fermenteur;

    //Description
    private LinearLayout tableauDescription;

    public VueFermenteurSimple(Context contexte) {
        super(contexte);
    }

    public VueFermenteurSimple(Context contexte, Fermenteur fermenteur) {
        super(contexte);

        this.fermenteur = fermenteur;

        TableRow ligne = new TableRow(contexte);

        tableauDescription = new TableLayout(contexte);
        tableauDescription.setOrientation(LinearLayout.VERTICAL);
        tableauDescription.setBackgroundColor(Color.WHITE);
        ligne.addView(cadre(tableauDescription, " Description "));
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

        TableRow ligneTitreLavageAcide = new TableRow(getContext());
            LinearLayout layoutTitre = new LinearLayout(getContext());
                TextView titre = new TextView(getContext());
                titre.setText("Fermenteur ");
                titre.setTypeface(null, Typeface.BOLD);

                TextView editTitre = new TextView(getContext());
                editTitre.setText("" + fermenteur.getNumero());

                TextView dateLavageAcide = new TextView(getContext());
                dateLavageAcide.setText("" + fermenteur.getDateLavageAcideToString());
                if ((System.currentTimeMillis() - fermenteur.getDateLavageAcide()) >= TableGestion.instance(getContext()).delaiLavageAcide()) {
                    dateLavageAcide.setTextColor(Color.RED);
                } else if ((System.currentTimeMillis() - fermenteur.getDateLavageAcide()) >= (TableGestion.instance(getContext()).delaiLavageAcide()-172800000)) {
                    dateLavageAcide.setTextColor(Color.rgb(198, 193, 13));
                } else {
                    dateLavageAcide.setTextColor(Color.rgb(34, 177, 76));
                }

        TableRow ligneCapaciteEmplacement = new TableRow(getContext());
            LinearLayout layoutCapacite = new LinearLayout(getContext());
                TextView capacite = new TextView(getContext());
                capacite.setText("Capacité : ");

                TextView editCapacite = new EditText(getContext());
                editCapacite.setText("" + fermenteur.getCapacite());

            LinearLayout layoutEmplacement = new LinearLayout(getContext());
                TextView emplacement = new TextView(getContext());
                emplacement.setText("Emplacement : ");

                TextView editEmplacement = new TextView(getContext());
                editEmplacement.setText(fermenteur.getEmplacement(getContext()).getTexte());

        TableRow ligneEtatDate = new TableRow(getContext());
            TextView etat = new TextView(getContext());
            etat.setText("État : " + fermenteur.getEtat(getContext()).getTexte());

            TextView dateEtat = new TextView(getContext());
            dateEtat.setText("Depuis le : " + fermenteur.getDateEtat());

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
    }
}
