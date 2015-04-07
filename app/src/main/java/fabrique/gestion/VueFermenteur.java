package fabrique.gestion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.Objets.Fermenteur;

public class VueFermenteur extends TableLayout implements View.OnClickListener {

    private Button btnModifier;

    private Fermenteur fermenteur;

    protected VueFermenteur(Context contexte, Fermenteur fermenteur) {
        super(contexte);

        this.fermenteur = fermenteur;

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreLavageAcide = new TableRow(contexte);
            TextView titre = new TextView(contexte);
            titre.setText("Fermenteur " + fermenteur.getNumero());
            titre.setTypeface(null, Typeface.BOLD);

            TextView dateLavageAcide = new TextView(contexte);
            dateLavageAcide.setText("" + fermenteur.getDateLavageAcideToString());
            if ((System.currentTimeMillis() - fermenteur.getDateLavageAcide()) >= TableGestion.instance(contexte).delaiLavageAcide()) {
                dateLavageAcide.setTextColor(Color.RED);
            } else if ((System.currentTimeMillis() - fermenteur.getDateLavageAcide()) >= (TableGestion.instance(contexte).delaiLavageAcide()-172800000)) {
                dateLavageAcide.setTextColor(Color.YELLOW);
            } else {
                dateLavageAcide.setTextColor(Color.GREEN);
            }

            ligneTitreLavageAcide.addView(titre, parametre);
            ligneTitreLavageAcide.addView(dateLavageAcide, parametre);
        addView(ligneTitreLavageAcide);

        TableRow ligneCapaciteEmplacement = new TableRow(contexte);
            TextView capacite = new TextView(contexte);
            capacite.setText("Capacité : " + fermenteur.getCapacite());

            TextView emplacement = new TextView(contexte);
            emplacement.setText("Emplacement : " + fermenteur.getEmplacement(contexte));

            ligneCapaciteEmplacement.addView(capacite, parametre);
            ligneCapaciteEmplacement.addView(emplacement, parametre);
        addView(ligneCapaciteEmplacement);

        TableRow ligneEtatDate = new TableRow(contexte);
            TextView etat = new TextView(contexte);
            etat.setText("État : " + fermenteur.getEtat(contexte));

            TextView dateEtat = new TextView(contexte);
            dateEtat.setText("Depuis le : " + fermenteur.getDateEtat());

            ligneEtatDate.addView(etat, parametre);
            ligneEtatDate.addView(dateEtat, parametre);
        addView(ligneEtatDate);

        TableRow ligneModifier = new TableRow(contexte);
            Button btnModifier = new Button(contexte);
            btnModifier.setText("Modifier");
            this.btnModifier = btnModifier;
            ligneModifier.addView(btnModifier, parametre);
        addView(ligneModifier);
    }

    private void modifier() {

    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifier();
        }
    }
}
