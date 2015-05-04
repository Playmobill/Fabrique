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

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.R;
import fabrique.gestion.Widget.BoutonBrassin;

public class FragmentListeBrassin extends FragmentAmeliore implements AdapterView.OnItemSelectedListener {

    private Context contexte;

    private LinearLayout body;
    private Spinner tri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        contexte = container.getContext();

        ((ActivityAccueil)getActivity()).setVue(this);

        LinearLayout axe = new LinearLayout(contexte);
        axe.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        axe.setOrientation(LinearLayout.VERTICAL);

        LinearLayout header = new LinearLayout(contexte);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setOrientation(LinearLayout.HORIZONTAL);

        header.addView(initTexteHeader());

        ScrollView bodyScrollView = new ScrollView(contexte);
        bodyScrollView.setLayoutParams(new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        body = new LinearLayout(contexte);
        body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        body.setOrientation(LinearLayout.VERTICAL);

        bodyScrollView.addView(body);
        axe.addView(header);
        axe.addView(bodyScrollView);

        TableBrassin tableBrassin = TableBrassin.instance(contexte);
        for (int i=0; i<tableBrassin.tailleListe(); i++) {
            ajouterBoutonBrassin(tableBrassin.recupererIndex(i));
        }

        return axe;
    }

    public void ajouterBoutonBrassin(Brassin brassin) {
        body.addView(new BoutonBrassin(contexte, this, brassin));
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

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListe());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        tri.setSelection(position);
        ArrayList<Brassin> listeBrassin;
        switch (position) {
            case 0:
                listeBrassin = TableBrassin.instance(contexte).trierParNumero();
                break;
            case 1:
                listeBrassin = TableBrassin.instance(contexte).trierParRecette();
                break;
            case 2:
                listeBrassin = TableBrassin.instance(contexte).trierParDateCreation();
                break;
            default:
                listeBrassin = new ArrayList<>();
        }
        body.removeAllViews();
        for (int i=0; i<listeBrassin.size(); i++) {
            ajouterBoutonBrassin(listeBrassin.get(i));
        }
    }
}
