package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ActivityConfiguration extends Activity implements View.OnClickListener {

    private Button fermenteur, cuve, fut, brassin, configuration, autre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_configuration);

        initialiserBouton();
    }

    public void initialiserBouton() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        fermenteur = (Button)findViewById(R.id.btnFermenteur);
        fermenteur.setWidth(metrics.widthPixels/2);
        fermenteur.setHeight(metrics.heightPixels/3);
        fermenteur.setOnClickListener(this);

        cuve = (Button)findViewById(R.id.btnCuve);
        cuve.setWidth(metrics.widthPixels/2);
        cuve.setHeight(metrics.heightPixels/3);
        cuve.setOnClickListener(this);

        fut = (Button)findViewById(R.id.btnFut);
        fut.setWidth(metrics.widthPixels/2);
        fut.setHeight(metrics.heightPixels / 3);
        fut.setOnClickListener(this);

        brassin = (Button)findViewById(R.id.btnBrassin);
        brassin.setWidth(metrics.widthPixels/2);
        brassin.setHeight(metrics.heightPixels/3);
        brassin.setOnClickListener(this);

        configuration = (Button)findViewById(R.id.btnConfiguration);
        configuration.setWidth(metrics.widthPixels/2);
        configuration.setHeight(metrics.heightPixels/3);
        configuration.setOnClickListener(this);

        autre = (Button)findViewById(R.id.btnAutre);
        autre.setWidth(metrics.widthPixels/2);
        autre.setHeight(metrics.heightPixels/3);
        autre.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fermenteur)) {
            Intent intent = new Intent(this, ActivityAjouterFermenteur.class);
            startActivity(intent);
        } else if (view.equals(cuve)) {

        } else if (view.equals(fut)) {

        } else if (view.equals(brassin)) {

        } else if (view.equals(configuration)) {

        } else if (view.equals(autre)) {

        }
    }
}
