package fabrique.gestion;

import android.app.Activity;
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

import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Fermenteur;
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

        initialiserCuves();

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
        boutonActif.max();
    }*/

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

        LinearLayout.LayoutParams parametreFermenteur = new LinearLayout.LayoutParams(tailleEcran.widthPixels/5, tailleEcran.heightPixels/2);

        for (final Fermenteur fermenteur : TableFermenteur.instance().fermenteurs()) {
            BoutonFermenteur boutonFermenteur = new BoutonFermenteur(this, fermenteur);
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

        LinearLayout.LayoutParams parametreCuve = new LinearLayout.LayoutParams(tailleEcran.widthPixels/5, tailleEcran.heightPixels/2);

        for (final Cuve cuve : cuves) {
            BoutonCuve boutonCuve = new BoutonCuve(this, cuve);
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

    public void initialiserCuves() {
        //Cuve 1 contenant brassin 2
        cuves = new Cuve[8];
        cuves[0] = new Cuve();
        cuves[0].setId(0);
        cuves[0].setNumero(1);
        cuves[0].setEtat(1);
        cuves[0].setCommentaireEtat("2psi");
        //Brassin 2
            Brassin brassin2 = new Brassin();
            brassin2.setId(1);
            brassin2.setNumero(313);
            cuves[0].setBrassin(brassin2);
        cuves[0].setDateEtat(System.currentTimeMillis() - 1000 * 60 * 60 * 24);

        //Cuve 2
        cuves[1] = new Cuve();
        cuves[1].setId(1);
        cuves[1].setNumero(2);

        //Cuve 3 contenant Brassin 3
        cuves[2] = new Cuve();
        cuves[2].setId(2);
        cuves[2].setNumero(3);
        cuves[2].setEtat(2);
            //Brassin 3
            Brassin brassin3 = new Brassin();
            brassin3.setId(2);
            brassin3.setNumero(314);
            cuves[2].setBrassin(brassin3);

        //Cuve 4
        cuves[3] = new Cuve();
        cuves[3].setId(3);
        cuves[3].setNumero(4);
        cuves[3].setEtat(3);

        //Cuve 5 contenant brassin2
        cuves[4] = new Cuve();
        cuves[4].setId(0);
        cuves[4].setNumero(1);
        cuves[4].setEtat(1);
        cuves[4].setCommentaireEtat("2psi");
        cuves[4].setBrassin(brassin2);
        cuves[4].setDateEtat(System.currentTimeMillis() - 1000 * 60 * 60 * 24);

        //Cuve 6
        cuves[5] = new Cuve();
        cuves[5].setId(1);
        cuves[5].setNumero(2);

        //Cuve 7 contenant Brassin 3
        cuves[6] = new Cuve();
        cuves[6].setId(2);
        cuves[6].setNumero(3);
        cuves[6].setEtat(2);
        cuves[6].setBrassin(brassin3);

        //Cuve 8
        cuves[7] = new Cuve();
        cuves[7].setId(3);
        cuves[7].setNumero(4);
        cuves[7].setEtat(3);
    }
}