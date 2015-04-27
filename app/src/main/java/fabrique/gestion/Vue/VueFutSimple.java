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

import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.Objets.Fut;

public class VueFutSimple extends LinearLayout {

    private Fut fut;

    //Description
    private TableLayout tableauDescription;

    public VueFutSimple(Context contexte) {
        super(contexte);
    }

    public VueFutSimple(Context contexte, Fut fut) {
        super(contexte);

        this.fut = fut;

        tableauDescription = new TableLayout(contexte);
        tableauDescription.setOrientation(LinearLayout.VERTICAL);
        tableauDescription.setBackgroundColor(Color.WHITE);
        addView(cadre(tableauDescription, " Description "));
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
        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreInspection = new TableRow(getContext());
            TextView titre = new TextView(getContext());
            titre.setText("Fut " + fut.getNumero());
            titre.setTypeface(null, Typeface.BOLD);

            TextView dateInspection = new TextView(getContext());
            dateInspection.setText("" + fut.getDateInspection());
            if ((System.currentTimeMillis() - fut.getDateInspectionToLong()) >= TableGestion.instance(getContext()).delaiInspectionBaril()) {
                dateInspection.setTextColor(Color.RED);
            } else if ((System.currentTimeMillis() - fut.getDateInspectionToLong()) >= (TableGestion.instance(getContext()).delaiInspectionBaril()-172800000)) {
                dateInspection.setTextColor(Color.rgb(198, 193, 13));
            } else {
                dateInspection.setTextColor(Color.rgb(34, 177, 76));
            }

        TableRow ligneCapacite = new TableRow(getContext());
            TextView capacite = new TextView(getContext());
            capacite.setText("Capacité : " + fut.getCapacite());

        TableRow ligneEtatDate = new TableRow(getContext());
            TextView etat = new TextView(getContext());
            etat.setText("État : " + fut.getEtat(getContext()).getTexte());

            TextView dateEtat = new TextView(getContext());
            dateEtat.setText("Depuis le : " + fut.getDateEtat());

            ligneTitreInspection.addView(titre, parametre);
            ligneTitreInspection.addView(dateInspection, parametre);
        tableauDescription.addView(ligneTitreInspection);
            ligneCapacite.addView(capacite, parametre);
        tableauDescription.addView(ligneCapacite, parametre);
            ligneEtatDate.addView(etat, parametre);
            ligneEtatDate.addView(dateEtat, parametre);
        tableauDescription.addView(ligneEtatDate);
    }
}
