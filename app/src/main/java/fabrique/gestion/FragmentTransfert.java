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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableBrassinPere;
import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Objets.Objet;
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

    private ArrayList<Objet> origines, destinations;
    private ArrayList<String> labelOrigines, labelDestinations;

    private EditText quantite;

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

        quantite = (EditText)view.findViewById(R.id.editQuantiteTransfere);

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

                int quantiteTransfere = Integer.parseInt(quantite.getText().toString());
                Objet destination;

                if(listeTypeDestination.getText().toString().equals("Cuve")){
                    destination = TableCuve.instance(contexte).recupererId(destinations.get(listeDestination.getSelectedItemPosition()).getId());
                }
                //Fût
                else{
                    destination = TableFut.instance(contexte).recupererId(destinations.get(listeDestination.getSelectedItemPosition()).getId());
                }

                if(quantiteTransfere > 0 && quantiteTransfere <= destination.getCapacite()) {
                    long idBrassinTransfere = -1;

                    //Date avec seulement Jour, mois annee
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                    long idOrigine = 0;

                    if (listeTypeOrigine.getSelectedItem().equals("Fermenteur")) {
                        Fermenteur fermenteur = TableFermenteur.instance(contexte).recupererId(origines.get(listeOrigine.getSelectedItemPosition()).getId());
                        idOrigine = fermenteur.getId();
                        if(quantiteTransfere >= TableBrassin.instance(contexte).recupererId(fermenteur.getIdBrassin()).getQuantite()) {
                            idBrassinTransfere = fermenteur.getIdBrassin();
                            TableFermenteur.instance(contexte).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), fermenteur.getNoeud(contexte).getId_noeudSansBrassin(), date, -1, true);
                            quantiteTransfere = TableBrassin.instance(contexte).recupererId(idBrassinTransfere).getQuantite();
                        }
                        else{
                            Brassin brassinAine = TableBrassin.instance(contexte).recupererId(fermenteur.getIdBrassin());
                            idBrassinTransfere = TableBrassin.instance(contexte).ajouter(contexte, brassinAine.getId_brassinPere(), quantiteTransfere);
                            TableBrassin.instance(contexte).modifier(brassinAine.getId(), brassinAine.getNumero(), brassinAine.getCommentaire(),brassinAine.getDateLong(), (brassinAine.getQuantite()-quantiteTransfere) ,brassinAine.getId_recette(), brassinAine.getDensiteOriginale(),brassinAine.getDensiteFinale());
                        }

                        if (fermenteur.getIdNoeud() != -1) {
                            String historique = fermenteur.getNoeud(contexte).getEtat(contexte).getHistorique();
                            if ((historique != null) && (!historique.equals(""))) {
                                Calendar calendrier2 = Calendar.getInstance();
                                calendrier2.setTimeInMillis(System.currentTimeMillis());
                                long date2 = new GregorianCalendar(calendrier2.get(Calendar.YEAR), calendrier2.get(Calendar.MONTH), calendrier2.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                                TableHistorique.instance(contexte).ajouter(historique, date2, fermenteur.getId(), -1, -1, -1);
                            }
                        }
                    } else if (listeTypeOrigine.getSelectedItem().equals("Cuve")) {
                        Cuve cuve = TableCuve.instance(contexte).recupererId(origines.get(listeOrigine.getSelectedItemPosition()).getId());
                        idOrigine = cuve.getId();
                        if(quantiteTransfere >= TableBrassin.instance(contexte).recupererId(cuve.getIdBrassin()).getQuantite()) {
                            idBrassinTransfere = cuve.getIdBrassin();
                            TableCuve.instance(contexte).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getNoeud(contexte).getId_noeudSansBrassin(), date, cuve.getCommentaireEtat(), -1, true);
                            quantiteTransfere = TableBrassin.instance(contexte).recupererId(idBrassinTransfere).getQuantite();
                        }
                        else{
                            Brassin brassinAine = TableBrassin.instance(contexte).recupererId(cuve.getIdBrassin());
                            idBrassinTransfere = TableBrassin.instance(contexte).ajouter(contexte, brassinAine.getId_brassinPere(), quantiteTransfere);
                            TableBrassin.instance(contexte).modifier(brassinAine.getId(), brassinAine.getNumero(), brassinAine.getCommentaire(),brassinAine.getDateLong(), (brassinAine.getQuantite()-quantiteTransfere) ,brassinAine.getId_recette(), brassinAine.getDensiteOriginale(),brassinAine.getDensiteFinale());
                        }

                        if (cuve.getIdNoeud() != -1) {
                            String historique = cuve.getNoeud(contexte).getEtat(contexte).getHistorique();
                            if ((historique != null) && (!historique.equals(""))) {
                                Calendar calendrier2 = Calendar.getInstance();
                                calendrier2.setTimeInMillis(System.currentTimeMillis());
                                long date2 = new GregorianCalendar(calendrier2.get(Calendar.YEAR), calendrier2.get(Calendar.MONTH), calendrier2.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                                TableHistorique.instance(contexte).ajouter(historique, date2, -1, cuve.getId(), -1, -1);
                            }
                        }
                    }

                    if (listeTypeDestination.getText().toString().equals("Fût")) {
                        long idPremierNoeud = TableCheminBrassinFut.instance(contexte).recupererPremierNoeud();
                        if (idPremierNoeud == -1) {
                            Toast.makeText(contexte, "Le fût n'a pas de chemin du brassin", Toast.LENGTH_SHORT).show();
                        } else {
                            Fut futDest = TableFut.instance(contexte).recupererId(destinations.get(listeDestination.getSelectedItemPosition()).getId());
                            TableFut.instance(contexte).modifier(futDest.getId(), futDest.getNumero(), futDest.getCapacite(), idPremierNoeud, date, idBrassinTransfere, futDest.getDateInspectionToLong(), true);

                            if (futDest.getId_noeud() != -1) {
                                String historique = futDest.getNoeud(contexte).getEtat(contexte).getHistorique();
                                if ((historique != null) && (!historique.equals(""))) {
                                    Calendar calendrier2 = Calendar.getInstance();
                                    calendrier2.setTimeInMillis(System.currentTimeMillis());
                                    long date2 = new GregorianCalendar(calendrier2.get(Calendar.YEAR), calendrier2.get(Calendar.MONTH), calendrier2.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                                    TableHistorique.instance(contexte).ajouter(historique, date2, -1, -1, futDest.getId(), -1);
                                }
                            }

                            String texteTransfert = TableListeHistorique.instance(contexte).recupererId(4).getTexte() + " du brassin n°" + TableBrassinPere.instance(contexte).recupererId(TableBrassin.instance(contexte).recupererId(idBrassinTransfere).getId_brassinPere()).getNumero() + " de la cuve n°" + TableCuve.instance(contexte).recupererId(idOrigine).getNumero() + " au fût n°" + futDest.getNumero();
                            TableHistorique.instance(contexte).ajouter(texteTransfert, date, -1, idOrigine, futDest.getId(), TableBrassin.instance(contexte).recupererId(idBrassinTransfere).getId_brassinPere());
                        }
                    } else if (listeTypeDestination.getText().toString().equals("Cuve")) {
                        long idPremierNoeud = TableCheminBrassinCuve.instance(contexte).recupererPremierNoeud();
                        if (idPremierNoeud == -1) {
                            Toast.makeText(contexte, "La cuve n'a pas de chemin du brassin", Toast.LENGTH_SHORT).show();
                        } else {
                            Cuve cuveDest = TableCuve.instance(contexte).recupererId(destinations.get(listeDestination.getSelectedItemPosition()).getId());
                            TableCuve.instance(contexte).modifier(cuveDest.getId(), cuveDest.getNumero(), cuveDest.getCapacite(), cuveDest.getIdEmplacement(), cuveDest.getDateLavageAcide(), idPremierNoeud, date, cuveDest.getCommentaireEtat(), idBrassinTransfere, true);

                            if (cuveDest.getIdNoeud() != -1) {
                                String historique = cuveDest.getNoeud(contexte).getEtat(contexte).getHistorique();
                                if ((historique != null) && (!historique.equals(""))) {
                                    Calendar calendrier2 = Calendar.getInstance();
                                    calendrier2.setTimeInMillis(System.currentTimeMillis());
                                    long date2 = new GregorianCalendar(calendrier2.get(Calendar.YEAR), calendrier2.get(Calendar.MONTH), calendrier2.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                                    TableHistorique.instance(contexte).ajouter(historique, date2, -1, cuveDest.getId(), -1, -1);
                                }
                            }

                            String texteTransfert = TableListeHistorique.instance(contexte).recupererId(2).getTexte() + " du brassin n°" + TableBrassinPere.instance(contexte).recupererId(TableBrassin.instance(contexte).recupererId(idBrassinTransfere).getId_brassinPere()).getNumero() + " du fermenteur n°" + TableFermenteur.instance(contexte).recupererId(idOrigine).getNumero() + " à la cuve n°" + cuveDest.getNumero();
                            TableHistorique.instance(contexte).ajouter(texteTransfert, date, idOrigine, cuveDest.getId(), -1, TableBrassin.instance(contexte).recupererId(idBrassinTransfere).getId_brassinPere());
                        }

                    }
                    Toast.makeText(contexte, "Brassin transféré !", Toast.LENGTH_SHORT).show();

                    //Mise a Jour du spinner du type d'origine
                    ArrayAdapter<String> adapteurTypeOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style);
                    adapteurTypeOrigine.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    if (TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin().size() != 0) {
                        adapteurTypeOrigine.add("Fermenteur");
                        listeTypeOrigineVide = false;
                    }
                    if (TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin().size() != 0) {
                        adapteurTypeOrigine.add("Cuve");
                        listeTypeOrigineVide = false;
                    }
                    if (TableFermenteur.instance(contexte).recupererNumerosFermenteurAvecBrassin().size() <= 0 && TableCuve.instance(contexte).recupererNumerosCuveAvecBrassin().size() <= 0) {
                        listeTypeOrigineVide = true;
                        vueOrigine.removeAllViews();
                    }

                    if (listeTypeOrigineVide) {
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

                    if (listeTypeOrigine.getSelectedItem() != null && listeTypeOrigine.getSelectedItem().equals("Fermenteur")) {
                        listeTypeDestination.setText("Cuve");
                    } else if (listeTypeOrigine.getSelectedItem() != null && listeTypeOrigine.getSelectedItem().equals("Cuve")) {
                        listeTypeDestination.setText("Fût");
                    } else {
                        listeTypeDestination.setText("Aucune destination disponible");
                        listeDestination.setVisibility(View.INVISIBLE);
                        vueDestination.removeAllViews();
                    }
                    //Fin MaJ type destination
                }
                else{
                    Toast.makeText(contexte, "La quantité que vous souhaitez transférer n'est pas possible.", Toast.LENGTH_SHORT).show();
                }
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
                if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Fermenteur") && TableFermenteur.instance(contexte).recupererId(origines.get(position).getId()).getBrassin(contexte)!=null) {
                    vueOrigine.addView(new VueBrassinSimple(contexte, TableFermenteur.instance(contexte).recupererId(origines.get(position).getId()).getBrassin(contexte)));
                }
                else if(listeTypeOrigine.getItemAtPosition(listeTypeOrigine.getSelectedItemPosition()).equals("Cuve") && TableCuve.instance(contexte).recupererId(origines.get(position).getId()).getBrassin(contexte)!=null) {
                    vueOrigine.addView(new VueBrassinSimple(contexte, TableCuve.instance(contexte).recupererId(origines.get(position).getId()).getBrassin(contexte)));
                }
            }
        }
        if(parent.equals(listeDestination)){
            vueDestination.removeAllViews();
            if (listeTypeDestination.getText().toString().equals("Cuve")){
                vueDestination.addView(new VueCuveSimple(contexte, TableCuve.instance(contexte).recupererId(destinations.get(position).getId())));
            }
            else if (listeTypeDestination.getText().toString().equals("Fût")){
                vueDestination.addView(new VueFutSimple(contexte, TableFut.instance(contexte).recupererId(destinations.get(position).getId())));
            }
        }
        if (parent.equals(listeTypeOrigine)){
            ArrayAdapter<String> adapteurOrigine, adapteurDestination;
            if (listeTypeOrigine.getItemAtPosition(position).equals("Cuve")){

                ArrayList<Cuve> listeCuves = TableCuve.instance(contexte).recupererCuveAvecBrassin();
                origines= new ArrayList<>();
                labelOrigines = new ArrayList<>();
                for (int i = 0; i < listeCuves.size(); i++) {
                    origines.add(i, listeCuves.get(i));
                    labelOrigines.add(i, listeCuves.get(i).getNumero()+" / "+listeCuves.get(i).getCapacite()+"L");
                }

                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, labelOrigines);
                if(TableFut.instance(contexte).recupererNumeroFutSansBrassin().size()!=0){
                    listeTypeDestination.setText("Fût");
                    listeDestination.setVisibility(View.VISIBLE);

                    ArrayList<Fut> listeFuts = TableFut.instance(contexte).recupererFutSansBrassin();
                    destinations= new ArrayList<>();
                    labelDestinations = new ArrayList<>();
                    for (int i = 0; i < listeFuts.size(); i++) {
                        destinations.add(i, listeFuts.get(i));
                        labelDestinations.add(i, listeFuts.get(i).getNumero()+" / "+listeFuts.get(i).getCapacite()+"L");
                    }

                    adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, labelDestinations);
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

                ArrayList<Fermenteur> listeFermenteurs = TableFermenteur.instance(contexte).recupererFermenteursPleinsActifs();
                origines= new ArrayList<>();
                labelOrigines = new ArrayList<>();
                for (int i = 0; i < listeFermenteurs.size(); i++) {
                    origines.add(i, listeFermenteurs.get(i));
                    labelOrigines.add(i, listeFermenteurs.get(i).getNumero()+" / "+listeFermenteurs.get(i).getCapacite()+"L");
                }

                adapteurOrigine = new ArrayAdapter<>(contexte, R.layout.spinner_style, labelOrigines);
                if(TableCuve.instance(contexte).recupererNumerosCuveSansBrassin().size()!=0){
                    listeTypeDestination.setText("Cuve");
                    listeDestination.setVisibility(View.VISIBLE);

                    ArrayList<Cuve> listeCuves = TableCuve.instance(contexte).recupererCuveSansBrassin();
                    destinations= new ArrayList<>();
                    labelDestinations = new ArrayList<>();
                    for (int i = 0; i < listeCuves.size(); i++) {
                        destinations.add(i, listeCuves.get(i));
                        labelDestinations.add(i, listeCuves.get(i).getNumero()+" / "+listeCuves.get(i).getCapacite()+"L");
                    }

                    adapteurDestination = new ArrayAdapter<>(contexte, R.layout.spinner_style, labelDestinations);
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
