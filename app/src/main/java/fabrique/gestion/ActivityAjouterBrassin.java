package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.BDD.TableFermenteur;

/**
 * Created by thibaut on 31/03/15.
 */
public class ActivityAjouterBrassin extends Activity implements View.OnClickListener{



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_brassin);



    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

}
