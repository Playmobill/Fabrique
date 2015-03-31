package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Widget.BoutonCuve;
import fabrique.gestion.Widget.BoutonFermenteur;

public class ActivityTableauDeBord extends Activity /*implements View.OnClickListener*/ {

    private DisplayMetrics tailleEcran;

    private Cuve[] cuves;

    /*private Bouton boutonActif;

    private ArrayList<Bouton> boutons = new ArrayList<Bouton>();*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        initialiser();

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

        setContentView(layout);
    }

    /*@Override
    public void onClick(View v) {
        Bouton boutonClique = null;
        for (int i=0; i<boutons.size(); i++) {
            if (v.equals(boutons.get(i))) {
                boutonClique = boutons.get(i);
            }
        }
        if (boutonActif != null) {
            boutonActif.min();
        }
        boutonActif = boutonClique;
        boutonActif.max(this);
    }*/

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
            //boutons.add(boutonFermenteur);
            //boutonFermenteur.setOnClickListener(this);
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
            //boutonCuve.setOnClickListener(this);
            //boutons.add(boutonCuve);
            boutonCuve.setLayoutParams(parametreCuve);
            ligne.addView(boutonCuve);
        }

        //Layout pour le defilement horizontal
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(this);
        layoutHorizontalScroll.addView(ligne);

        return layoutHorizontalScroll;
    }

    public void initialiser() {
        /*Brassin 0
        TableBrassin.instance(this).ajouter(313, null, null, System.currentTimeMillis(), 20, 0, null, 1.0F, 1.0F, 1.0F);

        //Brassin 1
        TableBrassin.instance(this).ajouter(314, null, null, System.currentTimeMillis(), 20, 0, null, 1.0F, 1.0F, 1.0F);

        //Cuve 1 contenant brassin 0
        cuves = new Cuve[4];
        cuves[0] = new Cuve();
        cuves[0].setId(0);
        cuves[0].setNumero(1);
        cuves[0].setEtat(1);
        cuves[0].setCommentaireEtat("2psi");
        cuves[0].setBrassin(0);
        cuves[0].setDateEtat(System.currentTimeMillis() - 1000 * 60 * 60 * 24);

        //Cuve 2
        cuves[1] = new Cuve();
        cuves[1].setId(1);
        cuves[1].setNumero(2);

        //Cuve 3 contenant Brassin 1
        cuves[2] = new Cuve();
        cuves[2].setId(2);
        cuves[2].setNumero(3);
        cuves[2].setEtat(2);
        cuves[2].setBrassin(1);

        //Cuve 4
        cuves[3] = new Cuve();
        cuves[3].setId(3);
        cuves[3].setNumero(4);
        cuves[3].setEtat(3);*/
    }
}