package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
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
    boolean listeTypeOrigineVide, listeTypeDestinationVide;
    LinearLayout vueOrigine, vueDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_transfert);

        listeTypeOrigineVide = true;
        listeTypeDestinationVide = true;

        listeTypeOrigine = (Spinner)findViewById(R.id.listeTypeOrigine);
        ArrayAdapter<String> adapteurTypeOrigine= new ArrayAdapter<>(this, R.layout.spinner_style);
        adapteurTypeOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(TableFermenteur.instance(this).recupererNumerosFermenteurAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Fermenteur");
            listeTypeOrigineVide = false;
        }
        if(TableCuve.instance(this).recupererNumerosCuveAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Cuve");
            listeTypeOrigineVide = false;
        }
        if(TableFut.instance(this).recupererNumeroFutAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Fût");
            listeTypeOrigineVide = false;
        }
        listeTypeOrigine.setAdapter(adapteurTypeOrigine);
        listeTypeOrigine.setOnItemSelectedListener(this);

        listeOrigine = (Spinner)findViewById(R.id.listeOrigine);
        ArrayAdapter<String> adapteurOrigine= new ArrayAdapter<>(this, R.layout.spinner_style , TableFermenteur.instance(this).recupererNumerosFermenteurAvecBrassin());
        adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeOrigine.setAdapter(adapteurOrigine);
        listeOrigine.setOnItemSelectedListener(this);

        listeTypeDestination = (Spinner)findViewById(R.id.listeTypeDestination);
        ArrayAdapter<String> adapteurTypeDestination= new ArrayAdapter<>(this, R.layout.spinner_style);
        adapteurTypeDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(TableFermenteur.instance(this).recupererNumerosFermenteurSansBrassin().size()!=0) {
            adapteurTypeDestination.add("Fermenteur");
            listeTypeDestinationVide = false;
        }
        if(TableCuve.instance(this).recupererNumerosCuveSansBrassin().size()!=0) {
            adapteurTypeDestination.add("Cuve");
            listeTypeDestinationVide = false;
        }
        if(TableFut.instance(this).recupererNumeroFutSansBrassin().size()!=0) {
            adapteurTypeDestination.add("Fût");
            listeTypeDestinationVide = false;
        }
        listeTypeDestination.setAdapter(adapteurTypeDestination);
        listeTypeDestination.setOnItemSelectedListener(this);

        listeDestination = (Spinner)findViewById(R.id.listeDestination);
        ArrayAdapter<String> adapteurDestination= new ArrayAdapter<>(this, R.layout.spinner_style , TableFut.instance(this).recupererNumeroFutSansBrassin());
        adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeDestination.setAdapter(adapteurDestination);
        listeDestination.setOnItemSelectedListener(this);

        vueOrigine = (LinearLayout)findViewById(R.id.vueOrigine);
        vueDestination = (LinearLayout)findViewById(R.id.vueDestination);

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
            if(listeTypeOrigineVide == false){
            if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Fermenteur") && TableFermenteur.instance(this).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(this)!=null) {
                vueOrigine.addView(new VueBrassinSimple(this, TableFermenteur.instance(this).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(this)));
            }
            else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Fût") && TableFut.instance(this).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(this) !=null) {
                vueOrigine.addView(new VueBrassinSimple(this, TableFut.instance(this).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(this)));
            }
            else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Cuve") && TableCuve.instance(this).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(this)!=null) {
                vueOrigine.addView(new VueBrassinSimple(this, TableCuve.instance(this).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(this)));
            }}
        }
        if(parent.equals(listeDestination)){
            vueDestination.removeAllViews();
            if(listeTypeDestinationVide == false){
            if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fermenteur")){
                vueDestination.addView(new VueFermenteurSimple(this, TableFermenteur.instance(this).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }
            else if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Cuve")){
                vueDestination.addView(new VueCuveSimple(this, TableCuve.instance(this).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }
            else if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fût")){
                vueDestination.addView(new VueFutSimple(this, TableFut.instance(this).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }}
        }
        if(parent.equals(listeTypeOrigine)){
            ArrayAdapter<String> adapteurOrigine;
            if(listeTypeOrigine.getItemAtPosition(position).equals("Cuve")){
                adapteurOrigine = new ArrayAdapter<>(this, R.layout.spinner_style, TableCuve.instance(this).recupererNumerosCuveAvecBrassin());
            }
            else if(listeTypeOrigine.getItemAtPosition(position).equals("Fût")){
                adapteurOrigine = new ArrayAdapter<>(this, R.layout.spinner_style, TableFut.instance(this).recupererNumeroFutAvecBrassin());
            }
            else if(listeTypeOrigine.getItemAtPosition(position).equals("Fermenteur")){
                adapteurOrigine = new ArrayAdapter<>(this, R.layout.spinner_style, TableFermenteur.instance(this).recupererNumerosFermenteurAvecBrassin());
            }
            else{
                adapteurOrigine = new ArrayAdapter<>(this, R.layout.spinner_style);
            }
            adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listeOrigine.setAdapter(adapteurOrigine);
            listeOrigine.setOnItemSelectedListener(this);
        }
        if(parent.equals(listeTypeDestination)){
            ArrayAdapter<String> adapteurDestination;
            if(listeTypeDestination.getItemAtPosition(position).equals("Cuve")){
                adapteurDestination = new ArrayAdapter<>(this, R.layout.spinner_style, TableCuve.instance(this).recupererNumerosCuveSansBrassin());
            }
            else if(listeTypeDestination.getItemAtPosition(position).equals("Fût")){
                adapteurDestination = new ArrayAdapter<>(this, R.layout.spinner_style, TableFut.instance(this).recupererNumeroFutSansBrassin());
            }
            else if(listeTypeDestination.getItemAtPosition(position).equals("Fermenteur")){
                adapteurDestination = new ArrayAdapter<>(this, R.layout.spinner_style, TableFermenteur.instance(this).recupererNumerosFermenteurSansBrassin());
            }
            else{
                adapteurDestination = new ArrayAdapter<>(this, R.layout.spinner_style);
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
