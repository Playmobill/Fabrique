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
import java.util.Collections;

import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Widget.BoutonFut;

public class ActivityListeFut extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private int nombreElementParLigne;

    private ArrayList<BoutonFut> btnsFut;

    private ArrayList<Fut> futs;

    private TableLayout tableau;

    private TableRow.LayoutParams margeBouton, margeAutre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Taille ecran
        DisplayMetrics tailleEcran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        LinearLayout layoutTri = new LinearLayout(this);
            TextView texte = new TextView(this);
            texte.setText("Trier / Regrouper par : ");

            Spinner tri = new Spinner(this);
            ArrayAdapter adapteurTri = new ArrayAdapter<>(this, R.layout.spinner_style, new String[] {"Numéro", "Brassin", "Recette", "État"});
            adapteurTri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            tri.setAdapter(adapteurTri);
            tri.setOnItemSelectedListener(this);

        tableau = new TableLayout(this);

        nombreElementParLigne = tailleEcran.widthPixels/145;

        btnsFut = new ArrayList<>();
        futs = new ArrayList<>();

        margeBouton = new TableRow.LayoutParams(125, 125);
        margeBouton.setMargins(10, 10, 10, 10);

        margeAutre = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        margeAutre.setMargins(10, 10, 10, 10);

            layoutTri.addView(texte);
            layoutTri.addView(tri);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layoutTri, margeAutre);
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
            ligne.addView(btnFut, margeBouton);
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
        Collections.reverse(listeListeFut);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
            TableRow ligneTitre = new TableRow(this);
                TextView titre = new TextView(this);
                long id_brassin = listeFut.get(0).getId_brassin();
                if (id_brassin == -1) {
                    titre.setText("Sans brassin");
                } else {
                    titre.setText("Brassin #" + id_brassin);
                }
            ligneTitre.addView(titre, margeAutre);
            tableau.addView(ligneTitre);
            TableRow ligneElement = new TableRow(this);
            int j = 0;
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(this, listeFut.get(k));
                btnFut.setOnClickListener(this);
                ligneElement.addView(btnFut, margeBouton);
                btnsFut.add(btnFut);
                futs.add(listeFut.get(k));
                j = j + 1;
                if (j>=nombreElementParLigne) {
                    tableau.addView(ligneElement);
                    ligneElement = new TableRow(this);
                    j = 0;
                }
            }
            if (j != 0) {
                tableau.addView(ligneElement);
            }
        }
        tableau.invalidate();
    }

    private void affichageSelonRecette() {
        tableau.removeAllViews();

        btnsFut.clear();
        futs.clear();

        ArrayList<ArrayList<Fut>> listeListeFut = TableFut.instance(this).recupererSelonRecette(this);
        Collections.reverse(listeListeFut);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
            TableRow ligneTitre = new TableRow(this);
            TextView titre = new TextView(this);
            Brassin brassin = listeFut.get(0).getBrassin(this);
            if (brassin == null) {
                titre.setText("Sans brassin");
            } else {
                titre.setText("" + brassin.getRecette(this).getNom());
            }
            ligneTitre.addView(titre, margeAutre);
            tableau.addView(ligneTitre);
            TableRow ligneElement = new TableRow(this);
            int j = 0;
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(this, listeFut.get(k));
                btnFut.setOnClickListener(this);
                ligneElement.addView(btnFut, margeBouton);
                btnsFut.add(btnFut);
                futs.add(listeFut.get(k));
                j = j + 1;
                if (j>=nombreElementParLigne) {
                    tableau.addView(ligneElement);
                    ligneElement = new TableRow(this);
                    j = 0;
                }
            }
            if (j != 0) {
                tableau.addView(ligneElement);
            }
        }
        tableau.invalidate();
    }

    private void affichageSelonEtat() {
        tableau.removeAllViews();

        btnsFut.clear();
        futs.clear();

        ArrayList<ArrayList<Fut>> listeListeFut = TableFut.instance(this).recupererSelonEtat(this);
        Collections.reverse(listeListeFut);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
            TableRow ligneTitre = new TableRow(this);
            TextView titre = new TextView(this);

            titre.setText("" + TableEtatFut.instance(this).recupererId(listeFut.get(0).getId_etat()).getTexte());

            ligneTitre.addView(titre, margeAutre);
            tableau.addView(ligneTitre);
            TableRow ligneElement = new TableRow(this);
            int j = 0;
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(this, listeFut.get(k));
                btnFut.setOnClickListener(this);
                ligneElement.addView(btnFut, margeBouton);
                btnsFut.add(btnFut);
                futs.add(listeFut.get(k));
                j = j + 1;
                if (j>=nombreElementParLigne) {
                    tableau.addView(ligneElement);
                    ligneElement = new TableRow(this);
                    j = 0;
                }
            }
            if (j != 0) {
                tableau.addView(ligneElement);
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
            case 2 : affichageSelonRecette();
                break;
            case 3 : affichageSelonEtat();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
