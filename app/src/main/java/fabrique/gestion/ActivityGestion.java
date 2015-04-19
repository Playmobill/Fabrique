package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ActivityGestion extends Activity implements View.OnClickListener {

    private Button fermenteur, cuve, fut, brassin, configuration, etat, listeHistorique, recette, transfert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_gestion);

        initialiserBouton();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityAccueil.class);
        startActivity(intent);
    }

    public void initialiserBouton() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        fermenteur = (Button)findViewById(R.id.btnFermenteur);
        //fermenteur.setWidth(metrics.widthPixels/3);
        //fermenteur.setHeight(metrics.heightPixels/5);
        fermenteur.setOnClickListener(this);

        cuve = (Button)findViewById(R.id.btnCuve);
        //cuve.setWidth(metrics.widthPixels/3);
        //cuve.setHeight(metrics.heightPixels/5);
        cuve.setOnClickListener(this);

        fut = (Button)findViewById(R.id.btnFut);
        //fut.setWidth(metrics.widthPixels/3);
        //fut.setHeight(metrics.heightPixels/5);
        fut.setOnClickListener(this);

        brassin = (Button)findViewById(R.id.btnBrassin);
        //brassin.setWidth(metrics.widthPixels/3);
        //brassin.setHeight(metrics.heightPixels/5);
        brassin.setOnClickListener(this);

        configuration = (Button)findViewById(R.id.btnConfiguration);
        //configuration.setWidth(metrics.widthPixels/3);
        //configuration.setHeight(metrics.heightPixels/5);
        configuration.setOnClickListener(this);

        etat = (Button)findViewById(R.id.btnEtat);
        //etat.setWidth(metrics.widthPixels/3);
        //etat.setHeight(metrics.heightPixels/5);
        etat.setOnClickListener(this);

        listeHistorique = (Button)findViewById(R.id.btnListeHistorique);
        //etat.setWidth(metrics.widthPixels/3);
        //etat.setHeight(metrics.heightPixels/5);
        listeHistorique.setOnClickListener(this);

        recette = (Button)findViewById(R.id.btnRecette);
        //configuration.setWidth(metrics.widthPixels/3);
        //configuration.setHeight(metrics.heightPixels/5);
        recette.setOnClickListener(this);

        transfert = (Button)findViewById(R.id.btnTransfert);
        //configuration.setWidth(metrics.widthPixels/3);
        //configuration.setHeight(metrics.heightPixels/5);
        transfert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fermenteur)) {
            Intent intent = new Intent(this, ActivityAjouterFermenteur.class);
            startActivity(intent);
        } else if (view.equals(cuve)) {
            Intent intent = new Intent(this, ActivityAjouterCuve.class);
            startActivity(intent);
        } else if (view.equals(fut)) {
            Intent intent = new Intent(this, ActivityAjouterFut.class);
            startActivity(intent);
        } else if (view.equals(brassin)) {
            Intent intent = new Intent(this, ActivityAjouterBrassin.class);
            startActivity(intent);
        } else if (view.equals(configuration)) {

        } else if (view.equals(etat)) {
            Intent intent = new Intent(this, ActivityEtat.class);
            startActivity(intent);
        } else if (view.equals(listeHistorique)) {
            Intent intent = new Intent(this, ActivityVueListeHistorique.class);
            startActivity(intent);
        }
        else if (view.equals(recette)) {
            Intent intent = new Intent(this, ActivityAjouterRecette.class);
            startActivity(intent);
        }
        else if (view.equals(transfert)) {
            Intent intent = new Intent(this, ActivityTransfert.class);
            startActivity(intent);
        }
    }
}
