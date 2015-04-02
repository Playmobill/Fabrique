package fabrique.gestion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.Objets.Cuve;

public class ActivityVueCuve extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);

        LinearLayout layout = new LinearLayout(this);
        layout.addView(descriptionCuve(this, index));
        setContentView(layout);
    }

    public TableLayout descriptionCuve(Context contexte, int index) {
        Cuve cuve = TableCuve.instance(contexte).recuperer(index);

        TableLayout tableau = new TableLayout(contexte);

        TextView titre = new TextView(contexte);
        titre.setText("Cuve " + cuve.getNumero());
        titre.setTypeface(null, Typeface.BOLD);

        TableRow ligne1 = new TableRow(contexte);
        TableRow ligne2 = new TableRow(contexte);
        TableRow ligne3 = new TableRow(contexte);
        TableRow ligne4 = new TableRow(contexte);
        TableRow ligne5 = new TableRow(contexte);

        TextView capacite = new TextView(contexte);
        capacite.setText("Capacité : " + cuve.getCapacite());

        TextView emplacement = new TextView(contexte);
        emplacement.setText("Emplacement : " + cuve.getEmplacement(contexte));

        TextView etat = new TextView(contexte);
        etat.setText("État : " + cuve.getEtat(contexte));

        TextView dateEtat = new TextView(contexte);
        dateEtat.setText("Depuis le : " + cuve.getDateEtat());

        TextView commentaire = new TextView(contexte);
        commentaire.setText("Commentaire de l'état : " + cuve.getCommentaireEtat());

        TextView dateLavageAcide = new TextView(contexte);
        dateLavageAcide.setText("Date du dernier lavage acide : " + cuve.getDateLavageAcide());

        ligne1.addView(titre);

        ligne2.addView(capacite);
        ligne2.addView(emplacement);
        ligne3.addView(etat);
        ligne3.addView(dateEtat);
        ligne4.addView(commentaire);
        ligne5.addView(dateLavageAcide);

        tableau.addView(ligne1);
        tableau.addView(ligne2);
        tableau.addView(ligne3);
        tableau.addView(ligne4);
        tableau.addView(ligne5);

        return tableau;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityTableauDeBord.class);
        startActivity(intent);
    }
}
