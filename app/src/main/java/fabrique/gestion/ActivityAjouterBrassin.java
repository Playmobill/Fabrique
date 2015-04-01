package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ActivityAjouterBrassin extends Activity implements View.OnClickListener {

    private Button btnAjouter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_brassin);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnAjouter)){

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }

}
