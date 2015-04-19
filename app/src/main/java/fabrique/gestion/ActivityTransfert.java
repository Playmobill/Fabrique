package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCuve;

/**
 * Created by thibaut on 19/04/15.
 */
public class ActivityTransfert extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener{


    Spinner listeOrigine, listeDestination;
    LinearLayout vueOrigine, vueDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_transfert);

        vueOrigine = (LinearLayout)findViewById(R.id.vueOrigine);
        vueOrigine.addView(new VueBrassin(this, TableBrassin.instance(this).recupererIndex(0)));
        vueDestination = (LinearLayout)findViewById(R.id.vueDestination);
        vueDestination.addView(new VueCuve(this, TableCuve.instance(this).recupererIndex(0)));

        listeOrigine = (Spinner)findViewById(R.id.listeOrigine);
        ArrayAdapter<String> adapteurOrigine= new ArrayAdapter<>(this, R.layout.spinner_style);
        adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapteurOrigine.add("Fermenteur #1");
        adapteurOrigine.add("Fermenteur #2");
        adapteurOrigine.add("Fermenteur #3");
        listeOrigine.setAdapter(adapteurOrigine);
        listeOrigine.setOnItemSelectedListener(this);

        listeDestination = (Spinner)findViewById(R.id.listeDestination);
        ArrayAdapter<String> adapteurDestination= new ArrayAdapter<>(this, R.layout.spinner_style);
        adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapteurDestination.add("Cuve #1");
        adapteurDestination.add("Cuve #2");
        adapteurDestination.add("Cuve #3");
        listeDestination.setAdapter(adapteurDestination);
        listeDestination.setOnItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(listeOrigine)){
            vueOrigine.removeAllViews();
            vueOrigine.addView(new VueBrassin(this, TableBrassin.instance(this).recupererIndex(position)));
        }
        if(parent.equals(listeDestination)){
            vueDestination.removeAllViews();
            vueDestination.addView(new VueCuve(this, TableCuve.instance(this).recupererIndex(position)));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
