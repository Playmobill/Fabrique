package fabrique.gestion;

import android.content.Context;
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
import android.widget.Toast;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.Objets.Fut;
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
                    Fermenteur fermenteur = TableFermenteur.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem()));
                    idBrassinTransfere = fermenteur.getIdBrassin();
                    TableFermenteur.instance(contexte).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), fermenteur.getIdEtat(), fermenteur.getDateEtatToLong(), -1);
                }
                else if (listeTypeOrigine.getSelectedItem().equals("Cuve")) {
                    Cuve cuve = TableCuve.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem()));
                    idBrassinTransfere = cuve.getIdBrassin();
                    TableCuve.instance(contexte).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getIdEtat(), cuve.getLongDateEtat(), cuve.getCommentaireEtat(), -1);
                }
                else if (listeTypeOrigine.getSelectedItem().equals("Fût")) {
                    Fut fut = TableFut.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem()));
                    idBrassinTransfere = fut.getId_brassin();
                    TableFut.instance(contexte).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), fut.getId_etat(), fut.getDateEtatToLong(), -1, fut.getDateInspectionToLong());
                }

                if(listeTypeDestination.getSelectedItem().equals("Fermenteur")){
                    Fermenteur fermenteurDest = TableFermenteur.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getSelectedItem()));
                    TableFermenteur.instance(contexte).modifier(fermenteurDest.getId(), fermenteurDest.getNumero(), fermenteurDest.getCapacite(), fermenteurDest.getIdEmplacement(), fermenteurDest.getDateLavageAcideToLong(), fermenteurDest.getIdEtat(), fermenteurDest.getDateEtatToLong(), idBrassinTransfere);
                }
                else if(listeTypeDestination.getSelectedItem().equals("Fût")){
                    Fut futDest = TableFut.instance(contexte).recupererId(Long.parseLong((String) listeDestination.getSelectedItem()));
                    TableFut.instance(contexte).modifier(futDest.getId(), futDest.getNumero(), futDest.getCapacite(), futDest.getId_etat(), futDest.getDateEtatToLong(), idBrassinTransfere, futDest.getDateInspectionToLong());
                }
                else if(listeTypeDestination.getSelectedItem().equals("Cuve")){
                    Cuve cuveDest = TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getSelectedItem()));
                    TableCuve.instance(contexte).modifier(cuveDest.getId(), cuveDest.getNumero(), cuveDest.getCapacite(), cuveDest.getIdEmplacement(), cuveDest.getDateLavageAcide(), cuveDest.getIdEtat(), cuveDest.getLongDateEtat(), cuveDest.getCommentaireEtat(), idBrassinTransfere);
                }
                Toast.makeText(contexte, "Brassin transféré !", Toast.LENGTH_LONG).show();

                //Mise a Jour du spinner du type d'origine
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
                //Fin MaJ type origine

                //Mise a Jour du spinner du tye de destination
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
                //Fin MaJ type destination
            }
            else{
                Toast.makeText(contexte, "Vous avez besoin d'un contenant d'origine et une destination pour transférer un brassin.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.equals(listeOrigine)){
            vueOrigine.removeAllViews();
            if(!listeTypeOrigineVide){
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
            if (!listeTypeDestinationVide){
                if (listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fermenteur")){
                    vueDestination.addView(new VueFermenteurSimple(contexte, TableFermenteur.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
                }
                else if (listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Cuve")){
                    vueDestination.addView(new VueCuveSimple(contexte, TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
                }
                else if (listeTypeDestination.getItemAtPosition(listeTypeDestination.getSelectedItemPosition()).equals("Fût")){
                    vueDestination.addView(new VueFutSimple(contexte, TableFut.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
                }}
        }
        if (parent.equals(listeTypeOrigine)){
            ArrayAdapter<String> adapteurOrigine;
            if (listeTypeOrigine.getItemAtPosition(position).equals("Cuve")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin());
            }
            else if (listeTypeOrigine.getItemAtPosition(position).equals("Fût")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFut.instance(contexte).recupererNumeroFutAvecBrassin());
            }
            else if (listeTypeOrigine.getItemAtPosition(position).equals("Fermenteur")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin());

            }
            else {
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style);
            }
            adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listeOrigine.setAdapter(adapteurOrigine);
            listeOrigine.setOnItemSelectedListener(this);
        }
        if (parent.equals(listeTypeDestination)){
            ArrayAdapter<String> adapteurDestination;
            if (listeTypeDestination.getItemAtPosition(position).equals("Cuve")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).recupererNumerosCuveSansBrassin());
            }
            else if (listeTypeDestination.getItemAtPosition(position).equals("Fût")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFut.instance(contexte).recupererNumeroFutSansBrassin());
            }
            else if (listeTypeDestination.getItemAtPosition(position).equals("Fermenteur")){
                adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFermenteur.instance(contexte).recupererNumerosFermenteurSansBrassin());

            }
            else {
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
