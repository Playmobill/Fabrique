package fabrique.gestion.FragmentListe;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.R;
import fabrique.gestion.Widget.BoutonBrassin;

public class FragmentListeBrassin extends FragmentAmeliore implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Context contexte;

    private LinearLayout body;
    private Spinner tri;
    private ArrayList<BoutonBrassin> listeBoutonBrassin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        listeBoutonBrassin = new ArrayList<>();

        LinearLayout axe = new LinearLayout(contexte);
        axe.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        axe.setOrientation(LinearLayout.VERTICAL);

        LinearLayout header = new LinearLayout(contexte);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setOrientation(LinearLayout.HORIZONTAL);

        ScrollView bodyScrollView = new ScrollView(contexte);
        bodyScrollView.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        body = new LinearLayout(contexte);
        body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        body.setOrientation(LinearLayout.VERTICAL);

        header.addView(initTexteHeader());
        for (int i = 0; i < TableBrassin.instance(contexte).tailleListe(); i++) {
            body.addView(initialiserLigne(TableBrassin.instance(contexte).recupererIndex(i)));
        }

        for (int i = 0; i < listeBoutonBrassin.size(); i++) {
            listeBoutonBrassin.get(i).setOnClickListener(this);
        }

        bodyScrollView.addView(body);
        axe.addView(header);
        axe.addView(bodyScrollView);

        return axe;
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListe());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
        transaction.addToBackStack(null).commit();
    }

    public RelativeLayout initialiserLigne(Brassin brassin){

        RelativeLayout ligneBrassin = new RelativeLayout(contexte);

        RelativeLayout.LayoutParams paramsLigne = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ligneBrassin.setLayoutParams(paramsLigne);

        RelativeLayout.LayoutParams[] paramsTexte = new RelativeLayout.LayoutParams[3];
        paramsTexte[0]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[0].setMargins(30,15,0,15);
        paramsTexte[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramsTexte[0].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramsTexte[1]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[1].addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        paramsTexte[2]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[2].setMargins(0, 15, 30, 15);
        paramsTexte[2].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        paramsTexte[2].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        BoutonBrassin bouton = new BoutonBrassin(contexte, brassin);
        listeBoutonBrassin.add(bouton);
        bouton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView numero = new TextView(contexte);
        numero.setText("#"+brassin.getNumero());
        numero.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        numero.setLayoutParams(paramsTexte[0]);

        TextView typeBiere = new TextView(contexte);
        typeBiere.setText("" + TableRecette.instance(contexte).recupererId(brassin.getId_recette()).getNom());
        typeBiere.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        typeBiere.setLayoutParams(paramsTexte[1]);

        TextView dateCreation = new TextView(contexte);
        dateCreation.setText(brassin.getDateCreation());
        dateCreation.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        dateCreation.setLayoutParams(paramsTexte[2]);

        ligneBrassin.addView(bouton);
        ligneBrassin.addView(numero);
        ligneBrassin.addView(typeBiere);
        ligneBrassin.addView(dateCreation);

        return ligneBrassin;
    }

    public RelativeLayout initTexteHeader(){
        RelativeLayout r = new RelativeLayout(contexte);

        LinearLayout part1, part2;

        RelativeLayout.LayoutParams[] paramsTexte = new RelativeLayout.LayoutParams[2];
        paramsTexte[0]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[0].setMargins(30,20,0,20);
        paramsTexte[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramsTexte[0].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramsTexte[1]= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[1].setMargins(0, 20, 30, 20);
        paramsTexte[1].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        paramsTexte[1].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        TextView titre = new TextView(contexte);
        titre.setText("Liste des brassins :");
        titre.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        TextView txtTri = new TextView(contexte);
        txtTri.setText("Trier par ");
        txtTri.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);

        tri = new Spinner(contexte);
        ArrayList<String> options = new ArrayList<>();
        options.add("Numero");
        options.add("Biere");
        options.add("Date de création");
        ArrayAdapter<String> triAdapter = new ArrayAdapter<>(contexte, R.layout.spinner_style, options);
        triAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tri.setAdapter(triAdapter);
        tri.setOnItemSelectedListener(this);

        part1 = new LinearLayout(contexte);
        part1.setLayoutParams(paramsTexte[0]);

        part2 = new LinearLayout(contexte);
        part2.setLayoutParams(paramsTexte[1]);

        part1.addView(titre);
        part2.addView(txtTri);
        part2.addView(tri);

        r.addView(part1);
        r.addView(part2);

        return r;
    }

    public void onClick(View v){
        for (int i = 0; i < listeBoutonBrassin.size(); i++) {
            if(v.equals(listeBoutonBrassin.get(i))){
                FragmentVueBrassin fragmentVueBrassin = new FragmentVueBrassin();
                Bundle args = new Bundle();
                args.putLong("id", listeBoutonBrassin.get(i).getBrassin().getId());
                fragmentVueBrassin.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.onglet, fragmentVueBrassin);
                transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_FADE));
                transaction.addToBackStack(null).commit();
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tri.setSelection(position);
        ArrayList<Brassin> listeBrassin;
        if(TableBrassin.instance(contexte).tailleListe()!=0) {
            switch (position) {
                case 0:
                    listeBrassin = trierParNumero(TableBrassin.instance(contexte).cloner());
                    break;
                case 1:
                    listeBrassin = trierParRecette(TableBrassin.instance(contexte).cloner());
                    break;
                case 2:
                    listeBrassin = trierParDateCreation(TableBrassin.instance(contexte).cloner());
                    break;
                default:
                    listeBrassin = new ArrayList<>();
            }
        }
        else{
            listeBrassin = new ArrayList<>();
        }
        listeBoutonBrassin.clear();
        body.removeAllViews();
        for (int i = 0; i < listeBrassin.size(); i++) {
            body.addView(initialiserLigne(listeBrassin.get(i)));
        }
        for (int i = 0; i < listeBoutonBrassin.size(); i++) {
            listeBoutonBrassin.get(i).setOnClickListener(this);
        }
    }

    public ArrayList<Brassin> trierParNumero(ArrayList<Brassin> listeBrassin){
        ArrayList<Brassin> result = new ArrayList<>();
        int index;
        long value;
        for (int i = 0; i < listeBrassin.size(); i++) {
            index = -1;
            value = listeBrassin.size();
            boolean possible;
            for (int j = 0; j < listeBrassin.size(); j++) {
                possible=true;
                for (int k = 0; k < result.size() && possible; k++) {
                    if(result.get(k).getNumero() == listeBrassin.get(j).getNumero()){
                        possible = false;
                    }
                }
                if((possible) && ((index<0) || (listeBrassin.get(j).getNumero() < value))){
                    index = j;
                    value = listeBrassin.get(index).getNumero();
                }
            }
            if(index>=0 && index<listeBrassin.size()) {
                result.add(listeBrassin.get(index));
            }
        }
        return result;

    }

    public ArrayList<Brassin> trierParRecette(ArrayList<Brassin> listeBrassin){
        ArrayList<Brassin> result = new ArrayList<>();
        int index;
        long[] values = new long[2];
        for (int i = 0; i < listeBrassin.size(); i++) {
            index = -1;
            values[0] = -1;
            values[1] = -1;
            boolean possible;
            for (int j = 0; j < listeBrassin.size(); j++) {
                possible=true;
                for (int k = 0; k < result.size() && possible; k++) {
                    if(result.get(k).getNumero() == listeBrassin.get(j).getNumero()){
                        possible = false;
                    }
                }
                if((possible) && ((index<0) || (listeBrassin.get(j).getId_recette() < values[0]))){
                    index = j;
                    values[0] = listeBrassin.get(index).getId_recette();
                    values[1] = listeBrassin.get(index).getNumero();
                }
                else if((possible) && ((index<0) || (listeBrassin.get(j).getId_recette() == values[0] && listeBrassin.get(j).getNumero() < values[1]))){
                    index = j;
                    values[0] = listeBrassin.get(index).getId_recette();
                    values[1] = listeBrassin.get(index).getNumero();
                }
            }
            if(index>=0 && index<listeBrassin.size()) {
                result.add(listeBrassin.get(index));
            }
        }
        return result;
    }

    public ArrayList<Brassin> trierParDateCreation(ArrayList<Brassin> listeBrassin){
        ArrayList<Brassin> result = new ArrayList<>();
        int index;
        long[] values = new long[2];
        for (int i = 0; i < listeBrassin.size(); i++) {
            index = -1;
            values[0] = -1;
            values[1] = -1;
            boolean possible;
            for (int j = 0; j < listeBrassin.size(); j++) {
                possible=true;
                for (int k = 0; k < result.size() && possible; k++) {
                    if(result.get(k).getNumero() == listeBrassin.get(j).getNumero()){
                        possible = false;
                    }
                }
                if((possible) && ((index<0) || (listeBrassin.get(j).getDateLong() > values[0]))){
                    index = j;
                    values[0] = listeBrassin.get(index).getDateLong();
                    values[1] = listeBrassin.get(index).getNumero();
                }
                else if((possible) && ((index<0) || (listeBrassin.get(j).getDateLong() == values[0] && listeBrassin.get(j).getNumero() < values[1]))){
                    index = j;
                    values[0] = listeBrassin.get(index).getDateLong();
                    values[1] = listeBrassin.get(index).getNumero();
                }
            }
            if(index>=0 && index<listeBrassin.size()) {
                result.add(listeBrassin.get(index));
            }
        }
        return result;
    }
}
