package fabrique.gestion.FragmentTableauDeBord;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.Widget.BoutonCuve;
import fabrique.gestion.Widget.BoutonFermenteur;

public class FragmentTableauDeBord extends FragmentAmeliore {

    private Context contexte;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        contexte = container.getContext();

        ((ActivityAccueil)getActivity()).setVue(this);

        //Tableau pour les elements de la fenetre
        LinearLayout tableau = new LinearLayout(contexte);
        tableau.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10,  0);

        LinearLayout.LayoutParams parametre = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        tableau.addView(nouvelleLigneTexte("Fermenteurs"), marge);
        tableau.addView(intialiserLigneFermenteur(), parametre);

        tableau.addView(nouvelleLigneTexte("Garde"), marge);
        tableau.addView(intialiserLigneGarde(), parametre);

        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(tableau);

        return layoutVerticalScroll;
    }

    public TextView nouvelleLigneTexte(String texte) {
        TextView txt = new TextView(contexte);
        txt.setText(texte);
        txt.setTypeface(null, Typeface.BOLD);
        return txt;
    }

    public HorizontalScrollView intialiserLigneFermenteur() {
        LinearLayout ligne = new LinearLayout(contexte);

        LinearLayout.LayoutParams parametreFermenteur = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametreFermenteur.setMargins(10, 10, 10, 10);

        ArrayList<Fermenteur> listeFermenteur = TableFermenteur.instance(contexte).recupererFermenteursActifs();
        for (int i=0; i<listeFermenteur.size(); i=i+1) {
            BoutonFermenteur boutonFermenteur = new BoutonFermenteur(contexte, this, listeFermenteur.get(i));
            ligne.addView(boutonFermenteur, parametreFermenteur);
        }

        //Layout pour le defilement horizontal
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(contexte);
        layoutHorizontalScroll.addView(ligne);

        return layoutHorizontalScroll;
    }

    public HorizontalScrollView intialiserLigneGarde() {
        LinearLayout ligne = new LinearLayout(contexte);

        LinearLayout.LayoutParams parametreCuve = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametreCuve.setMargins(10, 10, 10, 10);

        ArrayList<Cuve> listeCuve = TableCuve.instance(contexte).recupererCuvesActifs();
        for (int i=0; i<listeCuve.size(); i=i+1) {
            BoutonCuve boutonCuve = new BoutonCuve(contexte, this, listeCuve.get(i));
            ligne.addView(boutonCuve, parametreCuve);
        }

        //Layout pour le defilement horizontal
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(contexte);
        layoutHorizontalScroll.addView(ligne);

        return layoutHorizontalScroll;
    }

    @Override
    public void invalidate() {}

    @Override
    public void onBackPressed() {}
}
