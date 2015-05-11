package fabrique.gestion.FragmentGestion.chemin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableCheminBrassinFermenteur;
import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.FragmentAmeliore;
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

        ScrollView scroll = new ScrollView(contexte);
            LinearLayout ligne = new LinearLayout(contexte);
            ligne.setOrientation(LinearLayout.VERTICAL);
            ligne.addView(cadre(dansLeFermenteur(), "Dans le fermenteur"));
            ligne.addView(cadre(dansLaCuve(), "Dans la cuve"));
            ligne.addView(cadre(dansLeFut(), "Dans le fût"));
        scroll.addView(ligne);

        return scroll;
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

    @Override
    public void onBackPressed() {

    }

    @Override
    public void invalidate() {

    }
}
