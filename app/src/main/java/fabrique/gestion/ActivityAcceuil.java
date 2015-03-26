package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ActivityAcceuil extends Activity implements View.OnClickListener {

    Button btnApplication, btnConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_acceuil);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        btnApplication = (Button) findViewById(R.id.BtnApplication);
        btnApplication.setWidth(metrics.widthPixels/3);
        btnApplication.setHeight(metrics.heightPixels/3);
        btnApplication.setOnClickListener(this);

        btnConfiguration = (Button) findViewById(R.id.BtnConfiguration);
        btnConfiguration.setWidth(metrics.widthPixels/3);
        btnConfiguration.setHeight(metrics.heightPixels/3);
        btnConfiguration.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnApplication)) {
            Intent intent = new Intent(this, ActivityTableauDeBord.class);
            startActivity(intent);
        } else if (view.equals(btnConfiguration)) {

        }
    }
}