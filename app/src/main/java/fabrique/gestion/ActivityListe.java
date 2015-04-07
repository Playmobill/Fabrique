package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ActivityListe extends Activity implements View.OnClickListener {

    private Button fermenteur, cuve, fut, brassin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_liste);

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
        //fermenteur.setHeight(metrics.heightPixels/4);
        fermenteur.setOnClickListener(this);

        cuve = (Button)findViewById(R.id.btnCuve);
        //cuve.setWidth(metrics.widthPixels/3);
        //cuve.setHeight(metrics.heightPixels/4);
        cuve.setOnClickListener(this);

        fut = (Button)findViewById(R.id.btnFut);
        //fut.setWidth(metrics.widthPixels/3);
        //fut.setHeight(metrics.heightPixels/4);
        fut.setOnClickListener(this);

        brassin = (Button)findViewById(R.id.btnBrassin);
        //brassin.setWidth(metrics.widthPixels/3);
        //brassin.setHeight(metrics.heightPixels/4);
        brassin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fermenteur)) {
            Intent intent = new Intent(this, ActivityListeFermenteur.class);
            startActivity(intent);
        } else if (view.equals(cuve)) {
            Intent intent = new Intent(this, ActivityListeCuve.class);
            startActivity(intent);
        } else if (view.equals(fut)) {

        } else if (view.equals(brassin)) {
            Intent intent = new Intent(this, ActivityListeBrassin.class);
            startActivity(intent);
        }
    }
}
