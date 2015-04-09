package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Widget.BoutonFut;

public class ActivityListeFut extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private int largeurElement, nombreElementParLigne;

    private ArrayList<BoutonFut> btnsFut;

    private ArrayList<Fut> futs;

    private ArrayAdapter<String> adapteurTri;

    private TableLayout tableau;

    private TableRow.LayoutParams parametreFut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Taille ecran
        DisplayMetrics tailleEcran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        largeurElement = 125;
        nombreElementParLigne = tailleEcran.widthPixels/largeurElement;

        LinearLayout layoutTri = new LinearLayout(this);
            TextView texte = new TextView(this);
            texte.setText("Trier / Regrouper par : ");

            Spinner tri = new Spinner(this);
            adapteurTri = new ArrayAdapter<>(this, R.layout.spinner_style, new String[] {"Numéro", "Brassin", "Recette", "État"});
            adapteurTri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tri.setAdapter(adapteurTri);
            tri.setOnItemSelectedListener(this);

        tableau = new TableLayout(this);

        btnsFut = new ArrayList<>();
        futs = new ArrayList<>();

        parametreFut = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        parametreFut.setMargins(10, 10, 10, 10);

            layoutTri.addView(texte);
            layoutTri.addView(tri);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layoutTri, parametreFut);
        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(tableau);
        linearLayout.addView(layoutVerticalScroll);

        setContentView(linearLayout);

        affichageSelonNumero();
    }

    private void affichageSelonNumero() {
        tableau.removeAllViews();

        TableFut tableFut = TableFut.instance(this);

        btnsFut.clear();
        futs.clear();

        TableRow ligne = new TableRow(this);

        int j = 0;
        for (int i=0; i<tableFut.tailleListe(); i++) {
            BoutonFut btnFut = new BoutonFut(this, tableFut.recupererIndex(i));
            btnFut.setOnClickListener(this);
            ligne.addView(btnFut, parametreFut);
            btnsFut.add(btnFut);
            futs.add(tableFut.recupererIndex(i));
            j = j + 1;
            if (j>=nombreElementParLigne) {
                tableau.addView(ligne);
                ligne = new TableRow(this);
                j = 0;
            }
        }
        if (j != 0) {
            tableau.addView(ligne);
        }
        tableau.invalidate();
    }

    private void affichageSelonBrassin() {
        tableau.removeAllViews();

        btnsFut.clear();
        futs.clear();

        ArrayList<ArrayList<Fut>> listeListeFut = TableFut.instance(this).recupererSelonBrassin(this);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
            TableRow ligne = new TableRow(this);
            int j = 0;
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(this, listeFut.get(k));
                btnFut.setOnClickListener(this);
                ligne.addView(btnFut, parametreFut);
                btnsFut.add(btnFut);
                futs.add(listeFut.get(k));
                j = j + 1;
                if (j>=nombreElementParLigne) {
                    tableau.addView(ligne);
                    ligne = new TableRow(this);
                    j = 0;
                }
            }
            if (j != 0) {
                tableau.addView(ligne);
            }
        }
        tableau.invalidate();
    }

    @Override
    public void onClick(View v) {
        for (int i=0; i<btnsFut.size() ; i++) {
            if (btnsFut.get(i).equals(v)) {
                Intent intent = new Intent(this, ActivityVueFut.class);
                intent.putExtra("id", futs.get(i).getId());
                startActivity(intent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityListe.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch((int)id) {
            case 0 : affichageSelonNumero();
                break;
            case 1 : affichageSelonBrassin();
                break;
            case 2 :
                break;
            case 3 :
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
