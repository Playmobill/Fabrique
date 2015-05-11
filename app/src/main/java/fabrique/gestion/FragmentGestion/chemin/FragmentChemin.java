package fabrique.gestion.FragmentGestion.chemin;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableCheminBrassinFermenteur;
import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.FragmentGestion.FragmentGestion;
import fabrique.gestion.Objets.EtatCuve;
import fabrique.gestion.Objets.EtatFermenteur;
import fabrique.gestion.Objets.EtatFut;
import fabrique.gestion.Objets.NoeudCuve;
import fabrique.gestion.Objets.NoeudFermenteur;
import fabrique.gestion.Objets.NoeudFut;
import fabrique.gestion.R;
import fabrique.gestion.Widget.BoutonNoeudCuve;
import fabrique.gestion.Widget.BoutonNoeudFermenteur;
import fabrique.gestion.Widget.BoutonNoeudFut;

public class FragmentChemin extends FragmentAmeliore {

    private Context contexte;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        contexte = container.getContext();
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(contexte);
            ScrollView verticalScrollView = new ScrollView(contexte);
                LinearLayout ensemble = new LinearLayout(contexte);
                    LinearLayout ligneChemin = new LinearLayout(contexte);
                    ligneChemin.setOrientation(LinearLayout.VERTICAL);
                    ligneChemin.addView(cadre(dansLeFermenteur(), " Dans le fermenteur "));
                    ligneChemin.addView(cadre(dansLaCuve(), " Dans la cuve "));
                    ligneChemin.addView(cadre(dansLeFut(), " Dans le fût "));
                ensemble.addView(ligneChemin);
                    LinearLayout ligneEtat = new LinearLayout(contexte);
                    ligneEtat.setOrientation(LinearLayout.VERTICAL);
                    ligneEtat.addView(cadre(listeEtatFermenteur(), " Liste des états du fermenteur "));
                    ligneEtat.addView(cadre(listeEtatCuve(), " Liste des états de la cuve "));
                    ligneEtat.addView(cadre(listeEtatFut(), " Liste des états du fût "));
                ensemble.addView(ligneEtat);
            verticalScrollView.addView(ensemble);
        horizontalScrollView.addView(verticalScrollView);


        return horizontalScrollView;
    }

    private RelativeLayout cadre(View view, String texteTitre) {
        RelativeLayout contenant = new RelativeLayout(contexte);

        LinearLayout contourTitre = new LinearLayout(contexte);
        contourTitre.setBackgroundColor(Color.BLACK);
        contourTitre.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContourTitre = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContourTitre.setMargins(10, 1, 0, 0);
        TextView fondTitre = new TextView(contexte);
        fondTitre.setText(texteTitre);
        fondTitre.setTypeface(null, Typeface.BOLD);
        fondTitre.setBackgroundColor(Color.WHITE);

        RelativeLayout contour = new RelativeLayout(contexte);
        contour.setBackgroundColor(Color.BLACK);
        contour.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContour = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContour.topMargin=15;
        parametreContour.leftMargin=5;

        TextView titre = new TextView(contexte);
        titre.setText(texteTitre);
        titre.setTypeface(null, Typeface.BOLD);
        titre.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams parametreTitre = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreTitre.setMargins(11, 2, 0, 0);

        view.setBackgroundColor(Color.WHITE);

        contenant.addView(contourTitre, parametreContourTitre);
        contourTitre.addView(fondTitre);
        contenant.addView(contour, parametreContour);
        contour.addView(view);
        contenant.addView(titre, parametreTitre);
        return contenant;
    }

    private TableLayout dansLeFermenteur() {
        NoeudFermenteur noeudPrecedent = null;
        NoeudFermenteur noeudActuel = TableCheminBrassinFermenteur.instance(contexte).recupererPremierNoeud();

        TableLayout tableau = new TableLayout(contexte);

        while(noeudActuel != null) {
                TableRow ligneBouton = new TableRow(contexte);
                ligneBouton.addView(new BoutonNoeudFermenteur(contexte, this, noeudPrecedent, noeudActuel));
            tableau.addView(ligneBouton);
                TableRow ligneImage = new TableRow(contexte);
                    ImageView image = new ImageView(contexte);
                    image.setImageResource(R.drawable.fleche_bas);
                    image.setMaxWidth(50);
                    image.setMaxHeight(50);
                ligneImage.addView(image);
            tableau.addView(ligneImage);

            noeudPrecedent = noeudActuel;
            noeudActuel = noeudActuel.getNoeudAvecBrassin(contexte);
        }
            TableRow ligneAjouterAvecBrassin = new TableRow(contexte);
            ligneAjouterAvecBrassin.addView(new BoutonNoeudFermenteur(contexte, this, noeudPrecedent, null));
        tableau.addView(ligneAjouterAvecBrassin);

        return tableau;
    }

    private TableLayout dansLaCuve() {
        NoeudCuve noeudPrecedent = null;
        NoeudCuve noeudActuel = TableCheminBrassinCuve.instance(contexte).recupererPremierNoeud();

        TableLayout tableau = new TableLayout(contexte);

        while(noeudActuel != null) {
            TableRow ligneBouton = new TableRow(contexte);
            ligneBouton.addView(new BoutonNoeudCuve(contexte, this, noeudPrecedent, noeudActuel));
            tableau.addView(ligneBouton);
            TableRow ligneImage = new TableRow(contexte);
            ImageView image = new ImageView(contexte);
            image.setImageResource(R.drawable.fleche_bas);
            image.setMaxWidth(50);
            image.setMaxHeight(50);
            ligneImage.addView(image);
            tableau.addView(ligneImage);

            noeudPrecedent = noeudActuel;
            noeudActuel = noeudActuel.getNoeudAvecBrassin(contexte);
        }
        TableRow ligneAjouterAvecBrassin = new TableRow(contexte);
        ligneAjouterAvecBrassin.addView(new BoutonNoeudCuve(contexte, this, noeudPrecedent, null));
        tableau.addView(ligneAjouterAvecBrassin);

        return tableau;
    }

    private TableLayout dansLeFut() {
        NoeudFut noeudPrecedent = null;
        NoeudFut noeudActuel = TableCheminBrassinFut.instance(contexte).recupererPremierNoeud();

        TableLayout tableau = new TableLayout(contexte);

        while(noeudActuel != null) {
            TableRow ligneBouton = new TableRow(contexte);
            ligneBouton.addView(new BoutonNoeudFut(contexte, this, noeudPrecedent, noeudActuel));
            tableau.addView(ligneBouton);
            TableRow ligneImage = new TableRow(contexte);
            ImageView image = new ImageView(contexte);
            image.setImageResource(R.drawable.fleche_bas);
            image.setMaxWidth(50);
            image.setMaxHeight(50);
            ligneImage.addView(image);
            tableau.addView(ligneImage);

            noeudPrecedent = noeudActuel;
            noeudActuel = noeudActuel.getNoeudAvecBrassin(contexte);
        }
        TableRow ligneAjouterAvecBrassin = new TableRow(contexte);
        ligneAjouterAvecBrassin.addView(new BoutonNoeudFut(contexte, this, noeudPrecedent, null));
        tableau.addView(ligneAjouterAvecBrassin);

        return tableau;
    }

    private LinearLayout listeEtatFermenteur() {
        LinearLayout ligne = new LinearLayout(contexte);
        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);
        ligne.addView(listeEtatFermenteurAvecBrassin(), marge);
        ligne.addView(listeEtatFermenteurSansBrassin(), marge);
        return ligne;
    }

    private LinearLayout listeEtatFermenteurAvecBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
            TextView texteAvecBrassin = new TextView(contexte);
            texteAvecBrassin.setText("État du fermenteur avec brassin");
        ligne.addView(texteAvecBrassin);
        ArrayList<EtatFermenteur> listeEtat = TableEtatFermenteur.instance(contexte).recupererListeEtatActifs();
        for (int i=0; i<listeEtat.size(); i++) {
                Button texte = new Button(contexte);
                texte.setText(listeEtat.get(i).getTexte());
            ligne.addView(texte);
        }
        return ligne;
    }

    private LinearLayout listeEtatFermenteurSansBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
            TextView texteSansBrassin = new TextView(contexte);
            texteSansBrassin.setText("État du fermenteur sans brassin");
        ligne.addView(texteSansBrassin);
        ArrayList<EtatFermenteur> listeEtat = TableEtatFermenteur.instance(contexte).recupererListeEtatActifs();
        for (int i=0; i<listeEtat.size(); i++) {
                Button texte = new Button(contexte);
                texte.setText(listeEtat.get(i).getTexte());
            ligne.addView(texte);
        }
        return ligne;
    }

    private LinearLayout listeEtatCuve() {
        LinearLayout ligne = new LinearLayout(contexte);
        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);
        ligne.addView(listeEtatCuveAvecBrassin(), marge);
        ligne.addView(listeEtatCuveSansBrassin(), marge);
        return ligne;
    }

    private LinearLayout listeEtatCuveAvecBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteAvecBrassin = new TextView(contexte);
        texteAvecBrassin.setText("État de la cuve avec brassin");
        ligne.addView(texteAvecBrassin);
        ArrayList<EtatCuve> listeEtat = TableEtatCuve.instance(contexte).recupererListeEtatActifs();
        for (int i=0; i<listeEtat.size(); i++) {
            Button texte = new Button(contexte);
            texte.setText(listeEtat.get(i).getTexte());
            ligne.addView(texte);
        }
        return ligne;
    }

    private LinearLayout listeEtatCuveSansBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteSansBrassin = new TextView(contexte);
        texteSansBrassin.setText("État de la cuve sans brassin");
        ligne.addView(texteSansBrassin);
        ArrayList<EtatCuve> listeEtat = TableEtatCuve.instance(contexte).recupererListeEtatActifs();
        for (int i=0; i<listeEtat.size(); i++) {
            Button texte = new Button(contexte);
            texte.setText(listeEtat.get(i).getTexte());
            ligne.addView(texte);
        }
        return ligne;
    }

    private LinearLayout listeEtatFut() {
        LinearLayout ligne = new LinearLayout(contexte);
        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);
        ligne.addView(listeEtatFutAvecBrassin(), marge);
        ligne.addView(listeEtatFutSansBrassin(), marge);
        return ligne;
    }

    private LinearLayout listeEtatFutAvecBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteAvecBrassin = new TextView(contexte);
        texteAvecBrassin.setText("État du fût avec brassin");
        ligne.addView(texteAvecBrassin);
        ArrayList<EtatFut> listeEtat = TableEtatFut.instance(contexte).recupererListeEtatActifs();
        for (int i=0; i<listeEtat.size(); i++) {
            Button texte = new Button(contexte);
            texte.setText(listeEtat.get(i).getTexte());
            ligne.addView(texte);
        }
        return ligne;
    }

    private LinearLayout listeEtatFutSansBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteSansBrassin = new TextView(contexte);
        texteSansBrassin.setText("État du fût sans brassin");
        ligne.addView(texteSansBrassin);
        ArrayList<EtatFut> listeEtat = TableEtatFut.instance(contexte).recupererListeEtatActifs();
        for (int i=0; i<listeEtat.size(); i++) {
            Button texte = new Button(contexte);
            texte.setText(listeEtat.get(i).getTexte());
            ligne.addView(texte);
        }
        return ligne;
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void invalidate() {

    }
}
