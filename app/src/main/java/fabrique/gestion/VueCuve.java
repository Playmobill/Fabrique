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
import fabrique.gestion.Objets.Cuve;

public class VueCuve extends TableLayout implements View.OnClickListener {

    private Button btnModifier;

    private Cuve cuve;

    protected VueCuve(Context contexte, Cuve cuve) {
        super(contexte);

        this.cuve = cuve;

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreLavageAcide = new TableRow(contexte);
            TextView titre = new TextView(contexte);
            titre.setText("cuve " + cuve.getNumero());
            titre.setTypeface(null, Typeface.BOLD);

        TextView dateLavageAcide = new TextView(contexte);
        dateLavageAcide.setText("" + cuve.getDateLavageAcideToString());
        if ((System.currentTimeMillis() - cuve.getDateLavageAcide()) >= TableGestion.instance(contexte).delaiLavageAcide()) {
            dateLavageAcide.setTextColor(Color.RED);
        } else if ((System.currentTimeMillis() - cuve.getDateLavageAcide()) >= (TableGestion.instance(contexte).delaiLavageAcide()-172800000)) {
            dateLavageAcide.setTextColor(Color.YELLOW);
        } else {
            dateLavageAcide.setTextColor(Color.GREEN);
        }

            ligneTitreLavageAcide.addView(titre, parametre);
            ligneTitreLavageAcide.addView(dateLavageAcide, parametre);
        addView(ligneTitreLavageAcide);

        TableRow ligneCapaciteEmplacement = new TableRow(contexte);
            TextView capacite = new TextView(contexte);
            capacite.setText("Capacité : " + cuve.getCapacite());

            TextView emplacement = new TextView(contexte);
            emplacement.setText("Emplacement : " + cuve.getEmplacement(contexte));

            ligneCapaciteEmplacement.addView(capacite, parametre);
            ligneCapaciteEmplacement.addView(emplacement, parametre);
        addView(ligneCapaciteEmplacement);

        TableRow ligneEtatDate = new TableRow(contexte);
            TextView etat = new TextView(contexte);
            etat.setText("État : " + cuve.getEtat(contexte));

            TextView dateEtat = new TextView(contexte);
            dateEtat.setText("Depuis le : " + cuve.getDateEtat());

            ligneEtatDate.addView(etat, parametre);
            ligneEtatDate.addView(dateEtat, parametre);
        addView(ligneEtatDate);

        TableRow ligneCommentaire = new TableRow(contexte);
            TableRow.LayoutParams parametreCommentaire = new TableRow.LayoutParams();
            parametreCommentaire.setMargins(10, 10, 10, 10);
            parametreCommentaire.span = 2;
            TextView commentaire = new TextView(contexte);
            commentaire.setText(cuve.getCommentaireEtat());

            ligneCommentaire.addView(commentaire, parametreCommentaire);
        addView(ligneCommentaire);

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
