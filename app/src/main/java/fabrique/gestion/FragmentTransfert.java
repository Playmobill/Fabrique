package fabrique.gestion;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Vue.VueBrassinSimple;
import fabrique.gestion.Vue.VueCuveSimple;
import fabrique.gestion.Vue.VueFermenteurSimple;
import fabrique.gestion.Vue.VueFutSimple;

public class FragmentTransfert extends FragmentAmeliore implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private Context contexte;

    private View view;

    private Spinner listeTypeOrigine, listeOrigine, listeTypeDestination, listeDestination;
    private boolean listeTypeOrigineVide, listeTypeDestinationVide;
    private LinearLayout vueOrigine, vueDestination;
    private Button transferer;



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
        transferer = (Button)view.findViewById(R.id.btnExeTransfert);
        transferer.setOnClickListener(this);

        listeTypeOrigineVide = true;
        listeTypeDestinationVide = true;

        listeTypeOrigine = (Spinner)view.findViewById(R.id.listeTypeOrigine);
        ArrayAdapter<String> adapteurTypeOrigine= new ArrayAdapter<>(contexte, R.layout.spinner_style);
        adapteurTypeOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Fermenteur");
            listeTypeOrigineVide = false;
        }
        if(TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Cuve");
            listeTypeOrigineVide = false;
        }
        if(TableFut.instance(contexte).recupererNumeroFutAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Fût");
            listeTypeOrigineVide = false;
        }
        listeTypeOrigine.setAdapter(adapteurTypeOrigine);
        listeTypeOrigine.setOnItemSelectedListener(this);

        listeOrigine = (Spinner)view.findViewById(R.id.listeOrigine);

        listeTypeDestination = (Spinner)view.findViewById(R.id.listeTypeDestination);
        ArrayAdapter<String> adapteurTypeDestination= new ArrayAdapter<>(contexte, R.layout.spinner_style);
        adapteurTypeDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(TableFermenteur.instance(contexte).recupererNumerosFermenteurSansBrassin().size()!=0) {
            adapteurTypeDestination.add("Fermenteur");
            listeTypeDestinationVide = false;
        }
        if(TableCuve.instance(contexte).recupererNumerosCuveSansBrassin().size()!=0) {
            adapteurTypeDestination.add("Cuve");
            listeTypeDestinationVide = false;
        }
        if(TableFut.instance(contexte).recupererNumeroFutSansBrassin().size()!=0) {
            adapteurTypeDestination.add("Fût");
            listeTypeDestinationVide = false;
        }
        listeTypeDestination.setAdapter(adapteurTypeDestination);
        listeTypeDestination.setOnItemSelectedListener(this);

        listeDestination = (Spinner)view.findViewById(R.id.listeDestination);

        vueOrigine = (LinearLayout)view.findViewById(R.id.vueOrigine);
        vueDestination = (LinearLayout)view.findViewById(R.id.vueDestination);

        return view;
    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onClick(View v) {
        if(v.equals(transferer)) {
            if (listeTypeOrigine.getSelectedItem() != null && !(listeTypeOrigine.getSelectedItem().equals("")) && listeTypeDestination.getSelectedItem() != null &&  !(listeTypeDestination.getSelectedItem().equals(""))) {
                long idBrassinTransfere = -1;
                if (listeTypeOrigine.getSelectedItem().equals("Fermenteur")) {
                    idBrassinTransfere = TableFermenteur.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem())).getBrassin(contexte).getId();
                    TableFermenteur.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem())).setBrassin(-1);
                } else if (listeTypeOrigine.getSelectedItem().equals("Cuve")) {
                    idBrassinTransfere = TableCuve.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem())).getBrassin(contexte).getId();
                    TableCuve.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem())).setBrassin(-1);
                } else if (listeTypeOrigine.getSelectedItem().equals("Fût")) {
                    idBrassinTransfere = TableFut.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem())).getBrassin(contexte).getId();
                    TableFut.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem())).setBrassin(-1);
                }

                if(listeTypeDestination.getSelectedItem().equals("Fermenteur")){
                    TableFermenteur.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getSelectedItem())).setBrassin(idBrassinTransfere);
                }
                else if(listeTypeDestination.getSelectedItem().equals("Fût")){
                    TableFut.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getSelectedItem())).setBrassin(idBrassinTransfere);
                }
                else if(listeTypeDestination.getSelectedItem().equals("Cuve")){
                    TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getSelectedItem())).setBrassin(idBrassinTransfere);
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(listeOrigine)){
            vueOrigine.removeAllViews();
            if(listeTypeOrigineVide == false){
            if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Fermenteur") && TableFermenteur.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte)!=null) {
                vueOrigine.addView(new VueBrassinSimple(contexte, TableFermenteur.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte)));
            }
            else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Fût") && TableFut.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte) !=null) {
                vueOrigine.addView(new VueBrassinSimple(contexte, TableFut.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte)));

            }
            else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Cuve") && TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte)!=null) {
                vueOrigine.addView(new VueBrassinSimple(contexte, TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte)));
            }}
        }
        if(parent.equals(listeDestination)){
            vueDestination.removeAllViews();
            if(listeTypeDestinationVide == false){
            if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fermenteur")){
                vueDestination.addView(new VueFermenteurSimple(contexte, TableFermenteur.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }
            else if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Cuve")){
                vueDestination.addView(new VueCuveSimple(contexte, TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }
            else if(listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fût")){
                vueDestination.addView(new VueFutSimple(contexte, TableFut.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }}
        }
        if(parent.equals(listeTypeOrigine)){
            ArrayAdapter<String> adapteurOrigine;
            if(listeTypeOrigine.getItemAtPosition(position).equals("Cuve")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin());
            }
            else if(listeTypeOrigine.getItemAtPosition(position).equals("Fût")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFut.instance(contexte).recupererNumeroFutAvecBrassin());
            }
            else if(listeTypeOrigine.getItemAtPosition(position).equals("Fermenteur")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin());

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
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).recupererNumerosCuveSansBrassin());
            }
            else if(listeTypeDestination.getItemAtPosition(position).equals("Fût")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFut.instance(contexte).recupererNumeroFutSansBrassin());
            }
            else if(listeTypeDestination.getItemAtPosition(position).equals("Fermenteur")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFermenteur.instance(contexte).recupererNumerosFermenteurSansBrassin());

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
