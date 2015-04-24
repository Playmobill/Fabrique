package fabrique.gestion.FragmentTableauDeBord;

import android.app.FragmentTransaction;
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
import fabrique.gestion.R;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Widget.BoutonCuve;
import fabrique.gestion.Widget.BoutonFermenteur;

public class FragmentTableauDeBord extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private ArrayList<BoutonFermenteur> boutonsFermenteur = new ArrayList<>();

    private ArrayList<BoutonCuve> boutonsCuve = new ArrayList<>();

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
        LinearLayout layout = new LinearLayout(contexte);
        layout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 0, 10,  0);

        LinearLayout.LayoutParams parametre = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layout.addView(nouvelleLigneTexte("Fermenteurs"), marge);
        layout.addView(intialiserLigneFermenteur(), parametre);

        layout.addView(nouvelleLigneTexte("Garde"), marge);
        layout.addView(intialiserLigneGarde(), parametre);

        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(layout);

        return layoutVerticalScroll;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof BoutonFermenteur) {
            for (int i = 0; i < boutonsFermenteur.size(); i++) {
                if (v.equals(boutonsFermenteur.get(i))) {
                    FragmentVueFermenteur fragmentVueFermenteur = new FragmentVueFermenteur();
                    Bundle args = new Bundle();
                    args.putLong("id", TableFermenteur.instance(contexte).recupererIndex(i).getId());
                    fragmentVueFermenteur.setArguments(args);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.onglet, fragmentVueFermenteur);
                    transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
                    transaction.addToBackStack(null).commit();
                }
            }
        } else if (v instanceof BoutonCuve) {
            for (int i = 0; i < boutonsCuve.size(); i++) {
                if (v.equals(boutonsCuve.get(i))) {
                    FragmentVueCuve fragmentVueCuve = new FragmentVueCuve();
                    Bundle args = new Bundle();
                    args.putLong("id", TableCuve.instance(contexte).recupererIndex(i).getId());
                    fragmentVueCuve.setArguments(args);

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.onglet, fragmentVueCuve);
                    transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
                    transaction.addToBackStack(null).commit();
                }
            }
        }
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

        boutonsFermenteur.clear();
        TableFermenteur tableFermenteur = TableFermenteur.instance(contexte);
        for (int i=0; i<tableFermenteur.tailleListe(); i=i+1) {
            BoutonFermenteur boutonFermenteur = new BoutonFermenteur(contexte, tableFermenteur.recupererIndex(i));
            boutonsFermenteur.add(boutonFermenteur);
            boutonFermenteur.setOnClickListener(this);
            boutonFermenteur.setLayoutParams(parametreFermenteur);
            ligne.addView(boutonFermenteur);
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

        boutonsCuve.clear();
        TableCuve tableCuve = TableCuve.instance(contexte);
        for (int i=0; i<tableCuve.tailleListe(); i=i+1) {
            BoutonCuve boutonCuve = new BoutonCuve(contexte, tableCuve.recupererIndex(i));
            boutonCuve.setOnClickListener(this);
            boutonsCuve.add(boutonCuve);
            boutonCuve.setLayoutParams(parametreCuve);
            ligne.addView(boutonCuve);
        }

        //Layout pour le defilement horizontal
        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(contexte);
        layoutHorizontalScroll.addView(ligne);

        return layoutHorizontalScroll;
    }

    @Override
    public void onBackPressed() {}
}
