package fabrique.gestion;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ActivityConfiguration extends Activity implements View.OnClickListener {

    private Button fermenteur, cuve, fut, brassin, autre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_acceuil);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        fermenteur = (Button) findViewById(R.id.BtnConfiguration);
        fermenteur.setWidth(metrics.widthPixels/3);
        fermenteur.setHeight(metrics.heightPixels/4);
        fermenteur.setOnClickListener(this);

        brassin = (Button) findViewById(R.id.BtnApplication);
        brassin.setWidth(metrics.widthPixels/3);
        brassin.setHeight(metrics.heightPixels/4);
        brassin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(fermenteur)) {
            /*Intent intent = new Intent(this, ActivityTableauDeBord.class);
            startActivity(intent);*/
        } else if (view.equals(brassin)) {

        }
    }
}
