package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Widget.Bouton;
import fabrique.gestion.Widget.BoutonCuve;
import fabrique.gestion.Widget.BoutonFermenteur;

public class ActivityTableauDeBord extends Activity implements View.OnClickListener {

    private DisplayMetrics tailleEcran;

    private Bouton boutonActif;

    private ArrayList<Bouton> boutons = new ArrayList<Bouton>();

    private int index = -1;

    private Button btnFermenteur, btnCuve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Taille ecran
        tailleEcran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        //Tableau pour les elements de la fenetre
        LinearLayout layout = new TableLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(getResources().getColor(R.color.gris));

        //Ajout
        layout.addView(nouvelleLigneTexte("Fermenteurs"));

        layout.addView(intialiserLigneFermenteur());

        layout.addView(nouvelleLigneTexte("Garde"));

        layout.addView(intialiserLigneGarde());

        TableRow ligne = new TableRow(this);

        btnFermenteur = new Button(this);
        btnFermenteur.setText("Fermenteur");
        btnFermenteur.setOnClickListener(this);

        btnCuve = new Button(this);
        btnCuve.setText("Cuve");
        btnCuve.setOnClickListener(this);

        ligne.addView(btnFermenteur);
        ligne.addView(btnCuve);

        layout.addView(ligne);

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(layout);

        setContentView(layoutVerticalScroll);
    }

    @Override
    public void onClick(View v) {
        if ((v.equals(btnFermenteur)) && (index != -1)) {
            Intent intent = new Intent(this, ActivityVueFermenteur.class);
            intent.putExtra("index", index);
            startActivity(intent);
        } else if ((v.equals(btnCuve)) && (index != -1)) {
            /*Intent intent = new Intent(this, ActivityVueCuve.class);
            intent.putExtra("index", index);
            startActivity(intent);*/
        } else {
            //Bouton boutonClique = null;
            for (int i = 0; i < boutons.size(); i++) {
                if (v.equals(boutons.get(i))) {
                    //boutonClique = boutons.get(i);
                    index = i;
                    boutons.get(i).changerEtat();
                }
            }
            /*if (boutonActif != null) {
                boutonActif.min();
            }
            boutonActif = boutonClique;
            boutonActif.max();*/
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityAccueil.class);
        startActivity(intent);
    }

    public TextView nouvelleLigneTexte(String texte) {
        TextView txt = new TextView(this);
        txt.setText(texte);
        txt.setTextColor(Color.BLACK);
        txt.setTypeface(null, Typeface.BOLD);
        txt.setTextSize(30);
        return txt;
    }

    public HorizontalScrollView intialiserLigneFermenteur() {
        LinearLayout ligne = new LinearLayout(this);

        LinearLayout.LayoutParams parametreFermenteur = new LinearLayout.LayoutParams(tailleEcran.widthPixels/5, tailleEcran.heightPixels*9/20);
        TableFermenteur tableFermenteur = TableFermenteur.instance(this);
        for (int i=0; i<tableFermenteur.tailleListe(); i=i+1) {
            BoutonFermenteur boutonFermenteur = new BoutonFermenteur(this, tableFermenteur.recuperer(i));
            boutons.add(boutonFermenteur);
            boutonFermenteur.setOnClickListener(this);
            boutonFermenteur.setLayoutParams(parametreFermenteur);
            ligne.addView(boutonFermenteur);
        }

        //Layout pour le defilement horizontal
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(this);
        layoutHorizontalScroll.addView(ligne);

        return layoutHorizontalScroll;
    }

    public HorizontalScrollView intialiserLigneGarde() {
        LinearLayout ligne = new LinearLayout(this);

        LinearLayout.LayoutParams parametreCuve = new LinearLayout.LayoutParams(tailleEcran.widthPixels/5, tailleEcran.heightPixels*9/20);

        TableCuve tableCuve = TableCuve.instance(this);
        for (int i=0; i<tableCuve.tailleListe(); i=i+1) {
            BoutonCuve boutonCuve = new BoutonCuve(this, tableCuve.recuperer(i));
            boutonCuve.setOnClickListener(this);
            boutons.add(boutonCuve);
            boutonCuve.setLayoutParams(parametreCuve);
            ligne.addView(boutonCuve);
        }

        //Layout pour le defilement horizontal
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(this);
        layoutHorizontalScroll.addView(ligne);

        return layoutHorizontalScroll;
    }
}