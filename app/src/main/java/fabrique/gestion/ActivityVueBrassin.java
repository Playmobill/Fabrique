package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.TextView;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.Objets.Brassin;

public class ActivityVueBrassin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_vue_brassin);

        //Taille ecran
        DisplayMetrics tailleEcran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        Intent intent = getIntent();
        int index = intent.getIntExtra("index", -1);
        Brassin brassin = TableBrassin.instance(this).recupererIndex(index);

        if (brassin != null) {

            TextView texteNumero = (TextView) findViewById(R.id.texteNumero);
            texteNumero.setText(texteNumero.getText().toString() + brassin.getNumero());

            TextView texteDate = (TextView) findViewById(R.id.texteDate);
            texteDate.setText(texteDate.getText().toString() + brassin.getDateCreation());

            TextView texteCommentaireBrassin = (TextView) findViewById(R.id.texteCommentaireBrassin);
            texteCommentaireBrassin.setText(brassin.getCommentaire());

            String[] listeRecettes = TableRecette.instance(this).types();
            TextView texteRecette = (TextView) findViewById(R.id.texteRecette);
            texteRecette.setText(texteRecette.getText().toString() + listeRecettes[(int)brassin.getId_recette()]);

            TextView texteQuantite = (TextView) findViewById(R.id.texteQuantite);
            texteQuantite.setText(texteQuantite.getText().toString() + brassin.getQuantite());

            TextView texteDensiteOriginale = (TextView) findViewById(R.id.texteDensiteOriginale);
            texteDensiteOriginale.setText(texteDensiteOriginale.getText().toString() + brassin.getDensiteOriginale());

            TextView texteDensiteFinale = (TextView) findViewById(R.id.texteDensiteFinale);
            texteDensiteFinale.setText(texteDensiteFinale.getText().toString() + brassin.getDensiteFinale());

            TextView textePoucentageAlcool = (TextView) findViewById(R.id.textePoucentageAlcool);
            textePoucentageAlcool.setText(textePoucentageAlcool.getText().toString() + brassin.getPourcentageAlcool());
        }
    }
}
