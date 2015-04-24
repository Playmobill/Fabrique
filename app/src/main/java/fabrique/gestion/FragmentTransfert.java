package fabrique.gestion;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Vue.VueBrassinSimple;
import fabrique.gestion.Vue.VueCuveSimple;
import fabrique.gestion.Vue.VueFermenteurSimple;
import fabrique.gestion.Vue.VueFutSimple;

public class FragmentTransfert extends FragmentAmeliore implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private Context contexte;

    private View view;

    Spinner listeTypeOrigine, listeOrigine, listeTypeDestination, listeDestination;
    LinearLayout vueOrigine, vueDestination;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        view = inflater.inflate(R.layout.activity_transfert, container, false);

        listeTypeOrigine = (Spinner)view.findViewById(R.id.listeTypeOrigine);
        ArrayAdapter<String> adapteurTypeOrigine= new ArrayAdapter<>(contexte, R.layout.spinner_style);
        adapteurTypeOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(TableFermenteur.instance(contexte).tailleListe()>0) {
            adapteurTypeOrigine.add("Fermenteur");
        }
        if(TableCuve.instance(contexte).tailleListe()>0) {
            adapteurTypeOrigine.add("Cuve");
        }
        if(TableFut.instance(contexte).tailleListe()>0) {
            adapteurTypeOrigine.add("Fût");
        }
        listeTypeOrigine.setAdapter(adapteurTypeOrigine);
        listeTypeOrigine.setOnItemSelectedListener(this);

        listeOrigine = (Spinner)view.findViewById(R.id.listeOrigine);
        ArrayAdapter<String> adapteurOrigine= new ArrayAdapter<>(contexte, R.layout.spinner_style , TableFermenteur.instance(contexte).numeros());
        adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeOrigine.setAdapter(adapteurOrigine);
        listeOrigine.setOnItemSelectedListener(this);

        listeTypeDestination = (Spinner)view.findViewById(R.id.listeTypeDestination);
        ArrayAdapter<String> adapteurTypeDestination= new ArrayAdapter<>(contexte, R.layout.spinner_style);
        adapteurTypeDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(TableFermenteur.instance(contexte).tailleListe()>0) {
            adapteurTypeDestination.add("Fermenteur");
        }
        if(TableCuve.instance(contexte).tailleListe()>0) {
            adapteurTypeDestination.add("Cuve");
        }
        if(TableFut.instance(contexte).tailleListe()>0) {
            adapteurTypeDestination.add("Fût");
        }
        listeTypeDestination.setAdapter(adapteurTypeOrigine);
        listeTypeDestination.setOnItemSelectedListener(this);

        listeDestination = (Spinner)view.findViewById(R.id.listeDestination);
        ArrayAdapter<String> adapteurDestination= new ArrayAdapter<>(contexte, R.layout.spinner_style , TableCuve.instance(contexte).numeros());
        adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeDestination.setAdapter(adapteurDestination);
        listeDestination.setOnItemSelectedListener(this);

        vueOrigine = (LinearLayout)view.findViewById(R.id.vueOrigine);
        vueDestination = (LinearLayout)view.findViewById(R.id.vueDestination);

        return view;
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(listeOrigine)){
            vueOrigine.removeAllViews();
            if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Fermenteur") && TableFermenteur.instance(contexte).recupererIndex(listeOrigine.getSelectedItemPosition()).getBrassin(contexte)!=null) {
                vueOrigine.addView(new VueBrassinSimple(contexte, TableFermenteur.instance(contexte).recupererIndex(listeOrigine.getSelectedItemPosition()).getBrassin(contexte)));
            }
            else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Fût") && TableFut.instance(contexte).recupererIndex(listeOrigine.getSelectedItemPosition()).getBrassin(contexte) !=null) {
                vueOrigine.addView(new VueBrassinSimple(contexte, TableFut.instance(contexte).recupererIndex(listeOrigine.getSelectedItemPosition()).getBrassin(contexte)));
            }
            else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Cuve") && TableCuve.instance(contexte).recupererIndex(listeOrigine.getSelectedItemPosition()).getBrassin(contexte)!=null) {
                vueOrigine.addView(new VueBrassinSimple(contexte, TableCuve.instance(contexte).recupererIndex(listeOrigine.getSelectedItemPosition()).getBrassin(contexte)));
            }
        }
        if(parent.equals(listeDestination)){
            vueDestination.removeAllViews();

            if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fermenteur")){
                vueDestination.addView(new VueFermenteurSimple(contexte, TableFermenteur.instance(contexte).recupererIndex(position)));
            }
            else if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Cuve")){
                vueDestination.addView(new VueCuveSimple(contexte, TableCuve.instance(contexte).recupererIndex(position)));
            }
            else if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fût")){
                vueDestination.addView(new VueFutSimple(contexte, TableFut.instance(contexte).recupererIndex(position)));
            }
        }
        if(parent.equals(listeTypeOrigine)){
            ArrayAdapter<String> adapteurOrigine;
            if(listeTypeOrigine.getItemAtPosition(position).equals("Cuve")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).numeros());
            }
            else if(listeTypeOrigine.getItemAtPosition(position).equals("Fût")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFut.instance(contexte).numeros());
            }
            else if(listeTypeOrigine.getItemAtPosition(position).equals("Fermenteur")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFermenteur.instance(contexte).numeros());
            }
            else{
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style);
            }
            adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listeOrigine.setAdapter(adapteurOrigine);
            listeOrigine.setOnItemSelectedListener(this);
        }
        if(parent.equals(listeTypeDestination)){
            ArrayAdapter<String> adapteurDestination;
            if(listeTypeDestination.getItemAtPosition(position).equals("Cuve")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).numeros());
            }
            else if(listeTypeDestination.getItemAtPosition(position).equals("Fût")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFut.instance(contexte).numeros());
            }
            else if(listeTypeDestination.getItemAtPosition(position).equals("Fermenteur")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFermenteur.instance(contexte).numeros());
            }
            else{
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style);
            }
            adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listeDestination.setAdapter(adapteurDestination);
            listeDestination.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
