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
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Vue.VueBrassinSimple;
import fabrique.gestion.Vue.VueCuveSimple;
import fabrique.gestion.Vue.VueFutSimple;

public class FragmentTransfert extends FragmentAmeliore implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private Context contexte;

    private View view;

    private Spinner listeTypeOrigine, listeOrigine, listeDestination;
    private TextView listeTypeDestination;
    private boolean listeTypeOrigineVide;
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

        listeTypeOrigine = (Spinner)view.findViewById(R.id.listeTypeOrigine);
        ArrayAdapter<String> adapteurTypeOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style);
        adapteurTypeOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Fermenteur");
            listeTypeOrigineVide = false;
        }
        if(TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin().size()!=0) {
            adapteurTypeOrigine.add("Cuve");
            listeTypeOrigineVide = false;
        }
        listeTypeOrigine.setAdapter(adapteurTypeOrigine);
        listeTypeOrigine.setOnItemSelectedListener(this);

        listeOrigine = (Spinner)view.findViewById(R.id.listeOrigine);

        listeDestination = (Spinner)view.findViewById(R.id.listeDestination);

        listeTypeDestination = (TextView)view.findViewById(R.id.listeTypeDestination);
        if(listeTypeOrigine.getSelectedItem() != null && listeTypeOrigine.getSelectedItem().equals("Fermenteur")){
            listeTypeDestination.setText("Cuve");
        }
        else if(listeTypeOrigine.getSelectedItem() != null && listeTypeOrigine.getSelectedItem().equals("Cuve")){
            listeTypeDestination.setText("Fût");
        }
        else{
            listeTypeDestination.setText("Aucune destination disponible");
            listeDestination.setVisibility(View.INVISIBLE);
        }


        vueOrigine = (LinearLayout)view.findViewById(R.id.vueOrigine);
        vueDestination = (LinearLayout)view.findViewById(R.id.vueDestination);

        return view;
    }

    @Override
    public void invalidate() {}

    @Override
    public void onBackPressed() {}

    @Override
    public void onClick(View v) {
        if(v.equals(transferer)) {
            if (listeTypeOrigine.getSelectedItem() != null && !(listeTypeOrigine.getSelectedItem().equals("")) && listeTypeDestination != null &&  !(listeTypeDestination.getText().toString().equals("")) && !(listeTypeDestination.getText().toString().equals("Aucune destination disponible"))) {
                long idBrassinTransfere = -1;

                //Date avec seulement Jour, mois annee
                Calendar calendrier = Calendar.getInstance();
                calendrier.setTimeInMillis(System.currentTimeMillis());
                long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                long idOrigine=0;

                if (listeTypeOrigine.getSelectedItem().equals("Fermenteur")) {
                    Fermenteur fermenteur = TableFermenteur.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem()));
                    idBrassinTransfere = fermenteur.getIdBrassin();
                    idOrigine = fermenteur.getId();
                    TableFermenteur.instance(contexte).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), fermenteur.getNoeud(contexte).getId_noeudSansBrassin(), date, -1, true);
                }
                else if (listeTypeOrigine.getSelectedItem().equals("Cuve")) {
                    Cuve cuve = TableCuve.instance(contexte).recupererId(Long.parseLong((String) listeOrigine.getSelectedItem()));
                    idBrassinTransfere = cuve.getIdBrassin();
                    idOrigine = cuve.getId();
                    TableCuve.instance(contexte).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getNoeud(contexte).getId_noeudSansBrassin(), date, cuve.getCommentaireEtat(), -1, true);
                }

                if(listeTypeDestination.getText().toString().equals("Fût")){
                    Fut futDest = TableFut.instance(contexte).recupererId(Long.parseLong((String) listeDestination.getSelectedItem()));
                    TableFut.instance(contexte).modifier(futDest.getId(), futDest.getNumero(), futDest.getCapacite(), TableCheminBrassinFut.instance(contexte).recupererPremierNoeud().getId(), date, idBrassinTransfere, futDest.getDateInspectionToLong(), true);
                    String texteTransfert = TableListeHistorique.instance(contexte).recupererId(4).getTexte()+" du brassin n°"+idBrassinTransfere+" de la cuve n°"+idOrigine+" au fût n°"+futDest.getId();
                    TableHistorique.instance(contexte).ajouter(texteTransfert, date, 0, idOrigine, futDest.getId(), idBrassinTransfere);
                }
                else if(listeTypeDestination.getText().toString().equals("Cuve")){
                    Cuve cuveDest = TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getSelectedItem()));
                    TableCuve.instance(contexte).modifier(cuveDest.getId(), cuveDest.getNumero(), cuveDest.getCapacite(), cuveDest.getIdEmplacement(), cuveDest.getDateLavageAcide(), TableCheminBrassinCuve.instance(contexte).recupererPremierNoeud().getId(), date, cuveDest.getCommentaireEtat(), idBrassinTransfere, true);
                    String texteTransfert = TableListeHistorique.instance(contexte).recupererId(2).getTexte()+" du brassin n°"+idBrassinTransfere+" du fermenteur n°"+idOrigine+" à la cuve n°"+cuveDest.getId();
                    TableHistorique.instance(contexte).ajouter(texteTransfert, date, idOrigine, cuveDest.getId(), 0, idBrassinTransfere);
                }
                Toast.makeText(contexte, "Brassin transféré !", Toast.LENGTH_SHORT).show();

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
                if(TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin().size()<=0 && TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin().size()<=0){
                    listeTypeOrigineVide = true;
                    vueOrigine.removeAllViews();
                }

                if(listeTypeOrigineVide){
                    ArrayAdapter<String> adapteurOrigine;
                    adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style);
                    adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listeOrigine.setAdapter(adapteurOrigine);
                    listeOrigine.setOnItemSelectedListener(this);
                }

                listeTypeOrigine.setAdapter(adapteurTypeOrigine);
                listeTypeOrigine.setOnItemSelectedListener(this);
                //Fin MaJ type origine

                //Mise a Jour du spinner du type de destination

                if(listeTypeOrigine.getSelectedItem() != null && listeTypeOrigine.getSelectedItem().equals("Fermenteur")){
                    listeTypeDestination.setText("Cuve");
                }
                else if(listeTypeOrigine.getSelectedItem() != null && listeTypeOrigine.getSelectedItem().equals("Cuve")){
                    listeTypeDestination.setText("Fût");
                }
                else{
                    listeTypeDestination.setText("Aucune destination disponible");
                    listeDestination.setVisibility(View.INVISIBLE);
                    vueDestination.removeAllViews();
                }
                //Fin MaJ type destination
            }
            else{
                Toast.makeText(contexte, "Vous avez besoin d'un contenant d'origine et une destination pour transférer un brassin.", Toast.LENGTH_SHORT).show();
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
                else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Cuve") && TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte)!=null) {
                    vueOrigine.addView(new VueBrassinSimple(contexte, TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeOrigine.getItemAtPosition(position))).getBrassin(contexte)));
                }
            }
        }
        if(parent.equals(listeDestination)){
            vueDestination.removeAllViews();
            if (listeTypeDestination.getText().toString().equals("Cuve")){
                vueDestination.addView(new VueCuveSimple(contexte, TableCuve.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }
            else if (listeTypeDestination.getText().toString().equals("Fût")){
                vueDestination.addView(new VueFutSimple(contexte, TableFut.instance(contexte).recupererId(Long.parseLong((String)listeDestination.getItemAtPosition(position)))));
            }
        }
        if (parent.equals(listeTypeOrigine)){
            ArrayAdapter<String> adapteurOrigine, adapteurDestination;
            if (listeTypeOrigine.getItemAtPosition(position).equals("Cuve")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin());
                if(TableFut.instance(contexte).recupererNumeroFutSansBrassin().size()!=0){
                    listeTypeDestination.setText("Fût");
                    listeDestination.setVisibility(View.VISIBLE);

                    adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFut.instance(contexte).recupererNumeroFutSansBrassin());
                    adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listeDestination.setAdapter(adapteurDestination);
                    listeDestination.setOnItemSelectedListener(this);
                }
                else{
                    listeTypeDestination.setText("Aucune destination disponible");
                    listeDestination.setVisibility(View.INVISIBLE);
                    vueDestination.removeAllViews();
                }
            }
            else if (listeTypeOrigine.getItemAtPosition(position).equals("Fermenteur")){
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin());
                if(TableCuve.instance(contexte).recupererNumerosCuveSansBrassin().size()!=0){
                    listeTypeDestination.setText("Cuve");
                    listeDestination.setVisibility(View.VISIBLE);

                    adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableCuve.instance(contexte).recupererNumerosCuveSansBrassin());
                    adapteurDestination.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listeDestination.setAdapter(adapteurDestination);
                    listeDestination.setOnItemSelectedListener(this);
                }
                else{
                    listeTypeDestination.setText("Aucune destination disponible");
                    listeDestination.setVisibility(View.INVISIBLE);
                    vueDestination.removeAllViews();
                }
            }
            else {
                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style);
            }
            adapteurOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            listeOrigine.setAdapter(adapteurOrigine);
            listeOrigine.setOnItemSelectedListener(this);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
