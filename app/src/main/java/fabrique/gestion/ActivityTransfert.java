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
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;

/**
 * Created by thibaut on 19/04/15.
 */
public class ActivityTransfert extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener{


    Spinner listeTypeOrigine, listeOrigine, listeTypeDestination, listeDestination;
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

        listeTypeOrigine = (Spinner)findViewById(R.id.listeTypeOrigine);
        ArrayAdapter<String> adapteurTypeOrigine= new ArrayAdapter<>(this, R.layout.spinner_style);
        adapteurTypeOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapteurTypeOrigine.add("Fermenteur");
        adapteurTypeOrigine.add("Cuve");
        adapteurTypeOrigine.add("Fût");
        listeTypeOrigine.setAdapter(adapteurTypeOrigine);
        listeTypeOrigine.setOnItemSelectedListener(this);

        listeOrigine = (Spinner)findViewById(R.id.listeOrigine);
        ArrayAdapter<String> adapteurOrigine= new ArrayAdapter<>(this, R.layout.spinner_style , TableFermenteur.instance(this).numeros());
        adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeOrigine.setAdapter(adapteurOrigine);
        listeOrigine.setOnItemSelectedListener(this);

        listeTypeDestination = (Spinner)findViewById(R.id.listeTypeDestination);
        ArrayAdapter<String> adapteurTypeDestination= new ArrayAdapter<>(this, R.layout.spinner_style);
        adapteurTypeDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapteurTypeDestination.add("Fermenteur");
        adapteurTypeDestination.add("Cuve");
        adapteurTypeDestination.add("Fût");
        listeTypeDestination.setAdapter(adapteurTypeOrigine);
        listeTypeDestination.setOnItemSelectedListener(this);
        listeTypeDestination.setSelection(1);

        listeDestination = (Spinner)findViewById(R.id.listeDestination);
        ArrayAdapter<String> adapteurDestination= new ArrayAdapter<>(this, R.layout.spinner_style , TableCuve.instance(this).numeros());
        adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

            if(listeTypeDestination.getSelectedItemPosition() == 0){
                vueDestination.addView(new VueFermenteur(this, TableFermenteur.instance(this).recupererIndex(position)));
            }
            else if(listeTypeDestination.getSelectedItemPosition() == 1){
                vueDestination.addView(new VueCuve(this, TableCuve.instance(this).recupererIndex(position)));
            }
            else{
                vueDestination.addView(new VueFut(this, TableFut.instance(this).recupererIndex(position)));
            }
        }
        if(parent.equals(listeTypeOrigine)){
            ArrayAdapter<String> adapteurOrigine;
            if(position==1){
                adapteurOrigine = new ArrayAdapter<>(this, R.layout.spinner_style, TableCuve.instance(this).numeros());
            }
            else if(position==2){
                adapteurOrigine = new ArrayAdapter<>(this, R.layout.spinner_style, TableFut.instance(this).numeros());
            }
            else {
                adapteurOrigine = new ArrayAdapter<>(this, R.layout.spinner_style, TableFermenteur.instance(this).numeros());
            }
            adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listeOrigine.setAdapter(adapteurOrigine);
            listeOrigine.setOnItemSelectedListener(this);
        }
        if(parent.equals(listeTypeDestination)){
            ArrayAdapter<String> adapteurDestination;
            if(position==1){
                adapteurDestination = new ArrayAdapter<>(this, R.layout.spinner_style, TableCuve.instance(this).numeros());
                vueDestination.removeAllViews();
                vueDestination.addView(new VueCuve(this, TableCuve.instance(this).recupererIndex(0)));
            }
            else if(position==2){
                adapteurDestination = new ArrayAdapter<>(this, R.layout.spinner_style, TableFut.instance(this).numeros());
                vueDestination.removeAllViews();
                vueDestination.addView(new VueFut(this, TableFut.instance(this).recupererIndex(0)));
            }
            else {
                adapteurDestination = new ArrayAdapter<>(this, R.layout.spinner_style, TableFermenteur.instance(this).numeros());
                vueDestination.removeAllViews();
                vueDestination.addView(new VueFermenteur(this, TableFermenteur.instance(this).recupererIndex(0)));
            }
            adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listeDestination.setAdapter(adapteurDestination);
            listeDestination.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
