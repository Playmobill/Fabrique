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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Widget.BoutonBrassin;

/**
 * Created by thibaut on 07/04/15.
 */
public class ActivityListeBrassin extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private LinearLayout axe, header, body;
    private ScrollView bodyScrollView;
    private Spinner tri;
    private ArrayList<BoutonBrassin> listeBoutonBrassin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //setContentView(R.layout.activity_liste_brassin);

        listeBoutonBrassin = new ArrayList<>();

        axe = new LinearLayout(this);
        axe.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        axe.setOrientation(LinearLayout.VERTICAL);

        header = new LinearLayout(this);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setOrientation(LinearLayout.HORIZONTAL);

        bodyScrollView = new ScrollView(this);
        bodyScrollView.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        body = new LinearLayout(this);
        body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        body.setOrientation(LinearLayout.VERTICAL);

        header.addView(initTexteHeader());
        for (int i = 0; i < TableBrassin.instance(this).tailleListe(); i++) {
            body.addView(initialiserLigne(TableBrassin.instance(this).recupererIndex(i)));
        }

        bodyScrollView.addView(body);
        axe.addView(header);
        axe.addView(bodyScrollView);
        setContentView(axe);

        for (int i = 0; i < listeBoutonBrassin.size(); i++) {
            listeBoutonBrassin.get(i).setOnClickListener(this);
        }

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
        paramsTexte[0].setMargins(30,15,0,15);
        paramsTexte[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramsTexte[0].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramsTexte[1]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[1].addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        paramsTexte[2]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[2].setMargins(0, 15, 30, 15);
        paramsTexte[2].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        paramsTexte[2].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        BoutonBrassin bouton = new BoutonBrassin(this, brassin);
        listeBoutonBrassin.add(bouton);
        bouton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView numero = new TextView(this);
        numero.setText("#"+brassin.getNumero());
        numero.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        numero.setLayoutParams(paramsTexte[0]);

        TextView typeBiere = new TextView(this);
        typeBiere.setText("" + TableRecette.instance(this).recupererId(brassin.getId_recette()).getNom());
        typeBiere.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        typeBiere.setLayoutParams(paramsTexte[1]);

        TextView dateCreation = new TextView(this);
        dateCreation.setText(brassin.getDateCreation());
        dateCreation.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        dateCreation.setLayoutParams(paramsTexte[2]);

        ligneBrassin.addView(bouton);
        ligneBrassin.addView(numero);
        ligneBrassin.addView(typeBiere);
        ligneBrassin.addView(dateCreation);

        return ligneBrassin;
    }

    public RelativeLayout initTexteHeader(){
        RelativeLayout r = new RelativeLayout(this);

        LinearLayout part1, part2;

        RelativeLayout.LayoutParams[] paramsTexte = new RelativeLayout.LayoutParams[2];
        paramsTexte[0]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[0].setMargins(30,20,0,20);
        paramsTexte[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramsTexte[0].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramsTexte[1]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[1].setMargins(0, 20, 30, 20);
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

    public void onClick(View v){
        for (int i = 0; i < listeBoutonBrassin.size(); i++) {
            if(v.equals(listeBoutonBrassin.get(i))){
                Intent intent = new Intent(this, ActivityVueBrassin.class);
                intent.putExtra("index", i);
                startActivity(intent);
            }
        }
    }
}
