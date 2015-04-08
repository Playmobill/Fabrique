package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.Objets.Brassin;

/**
 * Created by thibaut on 07/04/15.
 */
public class ActivityListeBrassin extends Activity implements AdapterView.OnItemSelectedListener{

    private Spinner tri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_liste_brassin);

        tri = (Spinner)findViewById(R.id.editTri);
        ArrayList<String> options = new ArrayList<>();
        options.add("Numero");
        options.add("Biere");
        options.add("Date de création");
        ArrayAdapter<String> triAdapter = new ArrayAdapter<>(this, R.layout.spinner_style, options);
        triAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tri.setAdapter(triAdapter);



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityListe.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public RelativeLayout initialiserLigne(Brassin brassin){
        RelativeLayout ligneBrassin = new RelativeLayout(this);

        RelativeLayout.LayoutParams paramsLigne = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ligneBrassin.setLayoutParams(paramsLigne);

        RelativeLayout.LayoutParams[] paramsTexte = new RelativeLayout.LayoutParams[3];
        paramsTexte[0]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[0].setMargins(15,0,0,0);
        paramsTexte[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramsTexte[0].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramsTexte[1]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[1].addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        paramsTexte[2]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[2].setMargins(0,0,15,0);
        paramsTexte[2].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        paramsTexte[2].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        TextView numero = new TextView(this);
        numero.setText("#"+brassin.getNumero());
        numero.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        numero.setLayoutParams(paramsTexte[0]);

        TextView typeBiere = new TextView(this);
        typeBiere.setText("Recette n°"+brassin.getId_recette()+"");
        typeBiere.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        typeBiere.setLayoutParams(paramsTexte[1]);

        TextView dateCreation = new TextView(this);
        dateCreation.setText(brassin.getDateCreation());
        dateCreation.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        dateCreation.setLayoutParams(paramsTexte[2]);

        ligneBrassin.addView(numero);
        ligneBrassin.addView(typeBiere);
        ligneBrassin.addView(dateCreation);

        return ligneBrassin;
    }
}
