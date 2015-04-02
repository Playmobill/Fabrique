package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ActivityAccueil extends Activity implements View.OnClickListener {

    Button btnApplication, btnConfiguration, btnListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_acceuil);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        btnApplication = (Button) findViewById(R.id.btnApplication);
        //btnApplication.setWidth(metrics.widthPixels/3);
        //btnApplication.setHeight(metrics.heightPixels/3);
        btnApplication.setOnClickListener(this);

        btnConfiguration = (Button) findViewById(R.id.btnConfiguration);
        //btnConfiguration.setWidth(metrics.widthPixels/3);
        //btnConfiguration.setHeight(metrics.heightPixels/3);
        btnConfiguration.setOnClickListener(this);

        btnListe = (Button) findViewById(R.id.btnListe);
        //btnListe.setWidth(metrics.widthPixels/3);
        //btnListe.setHeight(metrics.heightPixels/3);
        btnListe.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnApplication)) {
            Intent intent = new Intent(this, ActivityTableauDeBord.class);
            startActivity(intent);
        } else if (view.equals(btnConfiguration)) {
            Intent intent = new Intent(this, ActivityGestion.class);
            startActivity(intent);
        }  else if (view.equals(btnListe)) {
            Intent intent = new Intent(this, ActivityListe.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
    }
}