package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Widget.BoutonCuve;
import fabrique.gestion.Widget.BoutonFermenteur;

public class ActivityTableauDeBord extends Activity implements View.OnClickListener {

    private DisplayMetrics tailleEcran;

    private ArrayList<BoutonFermenteur> boutonsFermenteur = new ArrayList<>();

    private ArrayList<BoutonCuve> boutonsCuve = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Taille ecran
        tailleEcran = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        //Tableau pour les elements de la fenetre
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);

        //Ajouter
        layout.addView(nouvelleLigneTexte("Fermenteurs"), marge);

        layout.addView(intialiserLigneFermenteur());

        layout.addView(nouvelleLigneTexte("Garde"), marge);

        layout.addView(intialiserLigneGarde());

        ScrollView layoutVerticalScroll = new ScrollView(this);
        layoutVerticalScroll.addView(layout);

        setContentView(layoutVerticalScroll);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof BoutonFermenteur) {
            for (int i = 0; i < boutonsFermenteur.size(); i++) {
                if (v.equals(boutonsFermenteur.get(i))) {
                    Intent intent = new Intent(this, ActivityVueFermenteur.class);
                    intent.putExtra("id", TableFermenteur.instance(this).recupererIndex(i).getId());
                    startActivity(intent);
                }
            }
        } else if (v instanceof BoutonCuve) {
            for (int i = 0; i < boutonsCuve.size(); i++) {
                if (v.equals(boutonsCuve.get(i))) {
                    Intent intent = new Intent(this, ActivityVueCuve.class);
                    intent.putExtra("id", TableCuve.instance(this).recupererIndex(i).getId());
                    startActivity(intent);
                }
            }
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
        txt.setTypeface(null, Typeface.BOLD);
        return txt;
    }

    public HorizontalScrollView intialiserLigneFermenteur() {
        LinearLayout ligne = new LinearLayout(this);

        LinearLayout.LayoutParams parametreFermenteur = new LinearLayout.LayoutParams(tailleEcran.widthPixels/6, tailleEcran.heightPixels*4/10);
        parametreFermenteur.setMargins(10, 10, 10, 10);

        TableFermenteur tableFermenteur = TableFermenteur.instance(this);
        for (int i=0; i<tableFermenteur.tailleListe(); i=i+1) {
            BoutonFermenteur boutonFermenteur = new BoutonFermenteur(this, tableFermenteur.recupererIndex(i));
            boutonsFermenteur.add(boutonFermenteur);
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

        LinearLayout.LayoutParams parametreCuve = new LinearLayout.LayoutParams(tailleEcran.widthPixels/6, tailleEcran.heightPixels*4/10);
        parametreCuve.setMargins(10, 10, 10, 10);

        TableCuve tableCuve = TableCuve.instance(this);
        for (int i=0; i<tableCuve.tailleListe(); i=i+1) {
            BoutonCuve boutonCuve = new BoutonCuve(this, tableCuve.recupererIndex(i));
            boutonCuve.setOnClickListener(this);
            boutonsCuve.add(boutonCuve);
            boutonCuve.setLayoutParams(parametreCuve);
            ligne.addView(boutonCuve);
        }

        //Layout pour le defilement horizontal
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(this);
        layoutHorizontalScroll.addView(ligne);

        return layoutHorizontalScroll;
    }
}