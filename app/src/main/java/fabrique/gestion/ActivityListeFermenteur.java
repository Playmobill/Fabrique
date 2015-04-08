package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;

import fabrique.gestion.BDD.TableFermenteur;

public class ActivityListeFermenteur extends Activity implements OnItemSelectedListener {

    private Spinner liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_liste_fermenteur);

        liste = (Spinner)findViewById(R.id.liste);
        TableFermenteur tableFermenteur = TableFermenteur.instance(this);
        ArrayAdapter<String> adapteurFermenteur = new ArrayAdapter<>(this, R.layout.spinner_style, tableFermenteur.numeros());
        adapteurFermenteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        liste.setAdapter(adapteurFermenteur);
        liste.setOnItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityListe.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        liste.setSelection(position);
        TableRow ligne = (TableRow) findViewById(R.id.ligne);
        ligne.removeAllViews();
        ligne.addView(new VueFermenteur(this, TableFermenteur.instance(this).recupererIndex(liste.getSelectedItemPosition())));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {}
}
