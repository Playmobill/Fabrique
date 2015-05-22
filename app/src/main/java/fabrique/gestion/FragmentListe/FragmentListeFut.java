package fabrique.gestion.FragmentListe;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.R;
import fabrique.gestion.Widget.BoutonFut;

public class FragmentListeFut extends FragmentAmeliore implements AdapterView.OnItemSelectedListener {

    private Context contexte;

    private LinearLayout tableau;

    private LinearLayout.LayoutParams marge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        //Taille ecran
        DisplayMetrics tailleEcran = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(tailleEcran);

        LinearLayout layoutTri = new LinearLayout(contexte);
        TextView texte = new TextView(contexte);
        texte.setText("Trier / Regrouper par : ");

        Spinner tri = new Spinner(contexte);
        ArrayAdapter adapteurTri = new ArrayAdapter<>(contexte, R.layout.spinner_style, new String[] {"Numéro", "Brassin", "Recette", "État"});
        adapteurTri.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tri.setAdapter(adapteurTri);
        tri.setOnItemSelectedListener(this);

        tableau = new TableLayout(contexte);
        tableau.setOrientation(LinearLayout.VERTICAL);

        marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);

        layoutTri.addView(texte);
        layoutTri.addView(tri);
        LinearLayout linearLayout = new LinearLayout(contexte);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layoutTri, marge);
        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(tableau);
        linearLayout.addView(layoutVerticalScroll);

        affichageSelonNumero();

        return linearLayout;
    }

    private void affichageSelonNumero() {
        tableau.removeAllViews();

        ArrayList<Fut> listeFutActif = TableFut.instance(contexte).recupererFutActifs();
            PredicateLayout ligneActif = new PredicateLayout(contexte);
            for (int i=0; i<listeFutActif.size(); i++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFutActif.get(i));
                ligneActif.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
        tableau.addView(ligneActif);

            TableRow ligneTitre = new TableRow(contexte);
                TextView titre = new TextView(contexte);
                titre.setText("Désactiver");
            ligneTitre.addView(titre, marge);
        tableau.addView(ligneTitre);
        ArrayList<Fut> listeFutNonActif = TableFut.instance(contexte).recupererFutNonActifs();
            PredicateLayout ligneNonActif = new PredicateLayout(contexte);
            for (int i=0; i<listeFutNonActif.size(); i++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFutNonActif.get(i));
                ligneNonActif.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
        tableau.addView(ligneNonActif);
    }

    private void affichageSelonBrassin() {
        tableau.removeAllViews();

        ArrayList<ArrayList<Fut>> listeListeFut = TableFut.instance(contexte).recupererSelonBrassin(contexte);
        Collections.reverse(listeListeFut);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
                TableRow ligneTitre = new TableRow(contexte);
                    TextView titre = new TextView(contexte);
                    long id_brassin = listeFut.get(0).getId_brassin();
                    if (id_brassin == -1) {
                        titre.setText("Sans brassin");
                    } else {
                        titre.setText("Brassin #" + id_brassin);
                    }
                ligneTitre.addView(titre, marge);
            tableau.addView(ligneTitre);

            PredicateLayout ligne = new PredicateLayout(contexte);
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFut.get(k));
                ligne.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
            tableau.addView(ligne);
        }

            TableRow ligneTitre = new TableRow(contexte);
                TextView titre = new TextView(contexte);
                titre.setText("Désactiver");
            ligneTitre.addView(titre, marge);
        tableau.addView(ligneTitre);
        ArrayList<Fut> listeFutNonActif = TableFut.instance(contexte).recupererFutNonActifs();
            PredicateLayout ligneNonActif = new PredicateLayout(contexte);
            for (int i=0; i<listeFutNonActif.size(); i++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFutNonActif.get(i));
                ligneNonActif.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
        tableau.addView(ligneNonActif);
    }

    private void affichageSelonRecette() {
        tableau.removeAllViews();

        ArrayList<ArrayList<Fut>> listeListeFut = TableFut.instance(contexte).recupererSelonRecette(contexte);
        Collections.reverse(listeListeFut);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
                TableRow ligneTitre = new TableRow(contexte);
                    TextView titre = new TextView(contexte);
                    Brassin brassin = listeFut.get(0).getBrassin(contexte);
                    if (brassin == null) {
                        titre.setText("Sans brassin");
                    } else {
                        titre.setText("" + brassin.getRecette(contexte).getNom());
                    }
                ligneTitre.addView(titre, marge);
            tableau.addView(ligneTitre);
            PredicateLayout ligne = new PredicateLayout(contexte);
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFut.get(k));
                ligne.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
            tableau.addView(ligne);
        }

            TableRow ligneTitre = new TableRow(contexte);
                TextView titre = new TextView(contexte);
                titre.setText("Désactiver");
            ligneTitre.addView(titre, marge);
        tableau.addView(ligneTitre);
        ArrayList<Fut> listeFutNonActif = TableFut.instance(contexte).recupererFutNonActifs();
            PredicateLayout ligneNonActif = new PredicateLayout(contexte);
            for (int i=0; i<listeFutNonActif.size(); i++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFutNonActif.get(i));
                ligneNonActif.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
        tableau.addView(ligneNonActif);
    }

    private void affichageSelonEtat() {
        tableau.removeAllViews();

        ArrayList<ArrayList<Fut>> listeListeFut = TableFut.instance(contexte).recupererSelonEtat();
        Collections.reverse(listeListeFut);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
                TableRow ligneTitre = new TableRow(contexte);
                    TextView titre = new TextView(contexte);
                    String texte = "Non utilisé";
                    if (listeFut.get(0).getId_noeud() != -1) {
                        texte = TableEtatFut.instance(contexte).recupererId(listeFut.get(0).getNoeud(contexte).getId_etat()).getTexte();
                    }
                    titre.setText(texte);
                ligneTitre.addView(titre, marge);
            tableau.addView(ligneTitre);
            PredicateLayout ligne = new PredicateLayout(contexte);
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFut.get(k));
                ligne.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
            tableau.addView(ligne);
        }

            TableRow ligneTitre = new TableRow(contexte);
                TextView titre = new TextView(contexte);
                titre.setText("Désactiver");
            ligneTitre.addView(titre, marge);
        tableau.addView(ligneTitre);
        ArrayList<Fut> listeFutNonActif = TableFut.instance(contexte).recupererFutNonActifs();
            PredicateLayout ligneNonActif = new PredicateLayout(contexte);
            for (int i=0; i<listeFutNonActif.size(); i++) {
                BoutonFut btnFut = new BoutonFut(contexte, this, listeFutNonActif.get(i));
                ligneNonActif.addView(btnFut, new PredicateLayout.LayoutParams(10, 10));
            }
        tableau.addView(ligneNonActif);
    }

    @Override
    public void invalidate() {}

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListe());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
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
