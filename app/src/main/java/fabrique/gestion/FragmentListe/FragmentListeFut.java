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

public class FragmentListeFut extends FragmentAmeliore implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private Context contexte;

    private int nombreElementParLigne;

    private ArrayList<BoutonFut> btnsFut;

    private ArrayList<Fut> futs;

    private LinearLayout tableau;

    private LinearLayout.LayoutParams margeBouton, margeAutre;

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

        nombreElementParLigne = tailleEcran.widthPixels/160;

        btnsFut = new ArrayList<>();
        futs = new ArrayList<>();

        margeBouton = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        margeBouton.setMargins(10, 10, 10, 10);

        margeAutre = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        margeAutre.setMargins(10, 10, 10, 10);

        layoutTri.addView(texte);
        layoutTri.addView(tri);
        LinearLayout linearLayout = new LinearLayout(contexte);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layoutTri, margeAutre);
        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(tableau);
        linearLayout.addView(layoutVerticalScroll);

        affichageSelonNumero();

        return linearLayout;
    }

    private void affichageSelonNumero() {
        tableau.removeAllViews();

        TableFut tableFut = TableFut.instance(contexte);

        btnsFut.clear();
        futs.clear();

        TableRow ligne = new TableRow(contexte);

        int j = 0;
        for (int i=0; i<tableFut.tailleListe(); i++) {
            BoutonFut btnFut = new BoutonFut(contexte, tableFut.recupererIndex(i));
            btnFut.setOnClickListener(this);
            ligne.addView(btnFut, margeBouton);
            btnsFut.add(btnFut);
            futs.add(tableFut.recupererIndex(i));
            j = j + 1;
            if (j>=nombreElementParLigne) {
                tableau.addView(ligne);
                ligne = new TableRow(contexte);
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
            ligneTitre.addView(titre, margeAutre);
            tableau.addView(ligneTitre);
            TableRow ligneElement = new TableRow(contexte);
            int j = 0;
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(contexte, listeFut.get(k));
                btnFut.setOnClickListener(this);
                ligneElement.addView(btnFut, margeBouton);
                btnsFut.add(btnFut);
                futs.add(listeFut.get(k));
                j = j + 1;
                if (j>=nombreElementParLigne) {
                    tableau.addView(ligneElement);
                    ligneElement = new TableRow(contexte);
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
            ligneTitre.addView(titre, margeAutre);
            tableau.addView(ligneTitre);
            TableRow ligneElement = new TableRow(contexte);
            int j = 0;
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(contexte, listeFut.get(k));
                btnFut.setOnClickListener(this);
                ligneElement.addView(btnFut, margeBouton);
                btnsFut.add(btnFut);
                futs.add(listeFut.get(k));
                j = j + 1;
                if (j>=nombreElementParLigne) {
                    tableau.addView(ligneElement);
                    ligneElement = new TableRow(contexte);
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

        ArrayList<ArrayList<Fut>> listeListeFut = TableFut.instance(contexte).recupererSelonEtat(contexte);
        Collections.reverse(listeListeFut);
        for (int i=0; i<listeListeFut.size(); i++) {
            ArrayList<Fut> listeFut = listeListeFut.get(i);
            TableRow ligneTitre = new TableRow(contexte);
            TextView titre = new TextView(contexte);

            titre.setText("" + TableEtatFut.instance(contexte).recupererId(listeFut.get(0).getId_etat()).getTexte());

            ligneTitre.addView(titre, margeAutre);
            tableau.addView(ligneTitre);
            TableRow ligneElement = new TableRow(contexte);
            int j = 0;
            for (int k=0; k<listeFut.size(); k++) {
                BoutonFut btnFut = new BoutonFut(contexte, listeFut.get(k));
                btnFut.setOnClickListener(this);
                ligneElement.addView(btnFut, margeBouton);
                btnsFut.add(btnFut);
                futs.add(listeFut.get(k));
                j = j + 1;
                if (j>=nombreElementParLigne) {
                    tableau.addView(ligneElement);
                    ligneElement = new TableRow(contexte);
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
                FragmentVueFut fragmentVueFut = new FragmentVueFut();
                Bundle args = new Bundle();
                args.putLong("id", futs.get(i).getId());
                fragmentVueFut.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.onglet, fragmentVueFut);
                transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
                transaction.addToBackStack(null).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListe());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
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
