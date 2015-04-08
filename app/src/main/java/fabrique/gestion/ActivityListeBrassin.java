package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.Objets.Brassin;

/**
 * Created by thibaut on 07/04/15.
 */
public class ActivityListeBrassin extends Activity implements AdapterView.OnItemSelectedListener{

    private LinearLayout axe, header, body;
    private ScrollView bodyScrollView;
    private Spinner tri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //setContentView(R.layout.activity_liste_brassin);

        axe = new LinearLayout(this);
        axe.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        axe.setOrientation(LinearLayout.VERTICAL);

        header = new LinearLayout(this);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        header.setOrientation(LinearLayout.HORIZONTAL);

        bodyScrollView = new ScrollView(this);
        bodyScrollView.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));

        body = new LinearLayout(this);
        body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        body.setOrientation(LinearLayout.VERTICAL);

        header.addView(initTexteHeader());
        body.addView(initialiserLigne(0));

        bodyScrollView.addView(body);
        axe.addView(header);
        axe.addView(bodyScrollView);
        setContentView(axe);

        Log.i("ListeBrassin", ((TextView)
                                ((LinearLayout)(
                                    ((RelativeLayout)(
                                        (LinearLayout)bodyScrollView.getChildAt(0)).getChildAt(0)).getChildAt(0))).getChildAt(0)).getText().toString());
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

    public RelativeLayout initialiserLigne(int index){
        TableBrassin tableBrassin = TableBrassin.instance(this);
        Brassin brassin = tableBrassin.recupererIndex(index);

        RelativeLayout ligneBrassin = new RelativeLayout(this);

        RelativeLayout.LayoutParams paramsLigne = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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

        Log.i("Coucou maggle", "Posey avec une n°"+brassin.getId_recette());

        return ligneBrassin;
    }

    public RelativeLayout initTexteHeader(){
        RelativeLayout r = new RelativeLayout(this);

        LinearLayout part1, part2;

        RelativeLayout.LayoutParams[] paramsTexte = new RelativeLayout.LayoutParams[2];
        paramsTexte[0]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[0].setMargins(15,0,0,0);
        paramsTexte[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramsTexte[0].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramsTexte[1]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[1].setMargins(0, 0, 15, 0);
        paramsTexte[1].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        paramsTexte[1].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        TextView titre = new TextView(this);
        titre.setText("Liste des brassins :");
        titre.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        TextView txtTri = new TextView(this);
        txtTri.setText("Trier par ");
        txtTri.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        tri = new Spinner(this);
        ArrayList<String> options = new ArrayList<>();
        options.add("Numero");
        options.add("Biere");
        options.add("Date de création");
        ArrayAdapter<String> triAdapter = new ArrayAdapter<>(this, R.layout.spinner_style, options);
        triAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tri.setAdapter(triAdapter);

        part1 = new LinearLayout(this);
        part1.setLayoutParams(paramsTexte[0]);

        part2 = new LinearLayout(this);
        part2.setLayoutParams(paramsTexte[1]);

        part1.addView(titre);
        part2.addView(txtTri);
        part2.addView(tri);

        r.addView(part1);
        r.addView(part2);

        return r;
    }
}
