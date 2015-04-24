package fabrique.gestion.Vue;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import fabrique.gestion.ActivityVueFut;
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.Objets.EtatFut;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.R;

public class VueFut extends TableLayout implements View.OnClickListener {

    private Fut fut;

    //Description
    private LinearLayout tableauDescription;
    private EditText editTitre, editCapacite;
    private TableRow ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    //Changer Brassin
    private LinearLayout tableauBrassin;
    private Spinner listeBrassin;
    private Button btnChanger;

    //Changer Etat
    private TableLayout tableauEtat;
    private ArrayList<EtatFut> listeEtat;
    private ArrayList<Button> btnsEtat;

    //Historique
    private TableLayout tableauHistorique;

    //Ajouter historique
    private Spinner ajoutListeHistorique;
    private EditText ajoutHistorique;
    private Button btnAjouter;

    public VueFut(Context contexte) {
        super(contexte);
    }

    public VueFut(Context contexte, Fut fut) {
        super(contexte);

        this.fut = fut;

        TableRow ligne = new TableRow(contexte);

        tableauDescription = new TableLayout(contexte);
        ligne.addView(cadre(tableauDescription, " Description "));
        afficherDescription();

        tableauHistorique = new TableLayout(getContext());
        ligne.addView(cadre(tableauHistorique, " Historique "));
        afficherHistorique();

        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(getContext());
        layoutHorizontalScroll.addView(ligne);

        TableRow ligne2 = new TableRow(contexte);

        tableauBrassin = new LinearLayout(contexte);
        ligne2.addView(cadre(tableauBrassin, " Changer brassin "));
        changerBrassin();

        tableauEtat = new TableLayout(contexte);
        ligne2.addView(cadre(tableauEtat, " Changer Etat "));
        changerEtat();

        addView(layoutHorizontalScroll);
        addView(ligne2);
    }

    private RelativeLayout cadre(View view, String texteTitre) {
        RelativeLayout contenant = new RelativeLayout(getContext());

        LinearLayout contourTitre = new LinearLayout(getContext());
        contourTitre.setBackgroundColor(Color.BLACK);
        contourTitre.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContourTitre = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContourTitre.setMargins(10, 1, 0, 0);
        TextView fondTitre = new TextView(getContext());
        fondTitre.setText(texteTitre);
        fondTitre.setTypeface(null, Typeface.BOLD);
        fondTitre.setBackgroundColor(Color.WHITE);

        RelativeLayout contour = new RelativeLayout(getContext());
        contour.setBackgroundColor(Color.BLACK);
        contour.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContour = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContour.topMargin=15;
        parametreContour.leftMargin=5;

        TextView titre = new TextView(getContext());
        titre.setText(texteTitre);
        titre.setTypeface(null, Typeface.BOLD);
        titre.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams parametreTitre = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreTitre.setMargins(11, 2, 0, 0);

        view.setBackgroundColor(Color.WHITE);

        contenant.addView(contourTitre, parametreContourTitre);
        contourTitre.addView(fondTitre);
        contenant.addView(contour, parametreContour);
        contour.addView(view);
        contenant.addView(titre, parametreTitre);
        return contenant;
    }

    private void afficherDescription() {
        tableauDescription.removeAllViews();

        TableRow.LayoutParams parametre = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreInspection = new TableRow(getContext());
            LinearLayout layoutTitre = new LinearLayout(getContext());
                TextView titre = new TextView(getContext());
                titre.setText("Fut ");
                titre.setTypeface(null, Typeface.BOLD);

                editTitre = new EditText(getContext());
                editTitre.setText("" + fut.getNumero());
                editTitre.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTitre.setEnabled(false);

                TextView dateInspection = new TextView(getContext());
                dateInspection.setText("" + fut.getDateInspectionToString());
                if ((System.currentTimeMillis() - fut.getDateInspection()) >= TableGestion.instance(getContext()).delaiInspectionBaril()) {
                    dateInspection.setTextColor(Color.RED);
                } else if ((System.currentTimeMillis() - fut.getDateInspection()) >= (TableGestion.instance(getContext()).delaiInspectionBaril()-172800000)) {
                    dateInspection.setTextColor(Color.rgb(198, 193, 13));
                } else {
                    dateInspection.setTextColor(Color.rgb(34, 177, 76));
                }

        TableRow ligneCapacite = new TableRow(getContext());
            LinearLayout layoutCapacite = new LinearLayout(getContext());
                TextView capacite = new TextView(getContext());
                capacite.setText("Capacité : ");

                editCapacite = new EditText(getContext());
                editCapacite.setText("" + fut.getCapacite());
                editCapacite.setInputType(InputType.TYPE_CLASS_NUMBER);
                editCapacite.setEnabled(false);

        TableRow ligneEtatDate = new TableRow(getContext());
            TextView etat = new TextView(getContext());
            etat.setText("État : " + fut.getEtat(getContext()).getTexte());

            TextView dateEtat = new TextView(getContext());
            dateEtat.setText("Depuis le : " + fut.getDateEtat());

        ligneBouton = new TableRow(getContext());
            btnModifier = new Button(getContext());
            btnModifier.setText("Modifier");

            btnModifier.setOnClickListener(this);
            btnValider = new Button(getContext());
            btnValider.setText("Valider");

            btnValider.setOnClickListener(this);
            btnAnnuler = new Button(getContext());
            btnAnnuler.setText("Annuler");
            btnAnnuler.setOnClickListener(this);

                layoutTitre.addView(titre);
                layoutTitre.addView(editTitre);
            ligneTitreInspection.addView(layoutTitre, parametre);
            ligneTitreInspection.addView(dateInspection, parametre);
        tableauDescription.addView(ligneTitreInspection);
                layoutCapacite.addView(capacite);
                layoutCapacite.addView(editCapacite);
            ligneCapacite.addView(layoutCapacite, parametre);
        tableauDescription.addView(ligneCapacite);
            ligneEtatDate.addView(etat, parametre);
            ligneEtatDate.addView(dateEtat, parametre);
        tableauDescription.addView(ligneEtatDate);
            ligneBouton.addView(btnModifier, parametre);
        tableauDescription.addView(ligneBouton);
    }

    private void modifierDescription() {
        editTitre.setEnabled(true);
        editCapacite.setEnabled(true);

        ligneBouton.removeAllViews();
        ligneBouton.addView(btnValider);
        ligneBouton.addView(btnAnnuler);
    }

    private void validerDescription() {
        String erreur = "";
        int numero = 0;
        if (editTitre.getText().toString().equals("")) {
            erreur = erreur + "Le fût doit avoir un numéro.";
        } else {
            try {
                numero = Integer.parseInt(editTitre.getText().toString());
            } catch (NumberFormatException e) {
                erreur = erreur + "Le numéro est trop grand.";
            }
        }

        int capacite = 0;
        try {
            capacite = Integer.parseInt(editCapacite.getText().toString());
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La quantité est trop grande.";
        }
        if (erreur.equals("")) {
            TableFut.instance(getContext()).modifier(fut.getId(), numero, capacite,fut.getId_etat(), fut.getLongDateEtat(), fut.getId_brassin(), fut.getDateInspection());
            reafficherDescription();
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_LONG).show();
        }
    }

    private void reafficherDescription() {
        editTitre.setEnabled(false);
        editTitre.setText("" + fut.getNumero());
        editCapacite.setEnabled(false);
        editCapacite.setText("" + fut.getCapacite());
        ligneBouton.removeAllViews();
        ligneBouton.addView(btnModifier);
    }

    private void changerBrassin() {
        tableauBrassin.removeAllViews();

        listeBrassin = new Spinner(getContext());
        TableBrassin tableBrassin = TableBrassin.instance(getContext());
        ArrayList<String> brassins = new ArrayList<>();
        for (int i=0; i<tableBrassin.tailleListe() ; i++) {
            brassins.add("" + tableBrassin.recupererIndex(i).getNumero());
        }
        ArrayAdapter<String> adapteurBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style, brassins);
        adapteurBrassin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        listeBrassin.setAdapter(adapteurBrassin);

        btnChanger = new Button(getContext());
        btnChanger.setText("Ajouter");
        btnChanger.setOnClickListener(this);

        tableauBrassin.addView(listeBrassin);
        tableauBrassin.addView(btnChanger);
    }

    private void changerEtat() {
        tableauEtat.removeAllViews();

        listeEtat = TableEtatFut.instance(getContext()).recupererListeEtatActifs();

        btnsEtat = new ArrayList<>();
        TableRow ligne = new TableRow(getContext());
        int i;
        for (i=0; i<listeEtat.size() ; i++) {
            Button btnEtat = new Button(getContext());
            btnEtat.setText(listeEtat.get(i).getTexte());
            btnEtat.setOnClickListener((this));
            btnsEtat.add(btnEtat);
            ligne.addView(btnEtat);
            if (i%3 == 2) {
                tableauEtat.addView(ligne);
                ligne = new TableRow(getContext());
            }
        }
        if ((i-1)%3 != 2) {
            tableauEtat.addView(ligne);
        }
    }

    private void afficherHistorique() {
        tableauHistorique.removeAllViews();

        TableRow ligneAjouter = new TableRow(getContext());
        ligneAjouter.setOrientation(LinearLayout.HORIZONTAL);

        ArrayList<ListeHistorique> listeHistoriques = TableListeHistorique.instance(getContext()).listeHistoriqueBrassin();
        String[] tabListeHistorique = new String[listeHistoriques.size()];
        for (int i=0; i<tabListeHistorique.length ; i++) {
            tabListeHistorique[i] = listeHistoriques.get(i).getTexte();
        }
        ajoutListeHistorique = new Spinner(getContext());
        ArrayAdapter<String> adapteurAjoutListeHistorique = new ArrayAdapter<>(getContext(), R.layout.spinner_style, tabListeHistorique);
        adapteurAjoutListeHistorique.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ajoutListeHistorique.setAdapter(adapteurAjoutListeHistorique);

        ajoutHistorique = new EditText(getContext());

        btnAjouter = new Button(getContext());
        btnAjouter.setText("Ajouter");
        btnAjouter.setOnClickListener(this);

        ligneAjouter.addView(ajoutListeHistorique);
        ligneAjouter.addView(ajoutHistorique);
        ligneAjouter.addView(btnAjouter);
        tableauHistorique.addView(ligneAjouter);

        ArrayList<Historique> historiques  = TableHistorique.instance(getContext()).recupererSelonIdCuve(fut.getId());
        for (int i=0; i<historiques.size() ; i++) {
            TableRow ligne = new TableRow(getContext());
            TextView texte = new TextView(getContext());
            texte.setText(historiques.get(i).getDateToString() + " : " + historiques.get(i).getTexte());
            ligne.addView(texte);
            tableauHistorique.addView(ligne);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifierDescription();
        } else if (v.equals(btnValider)) {
            validerDescription();
        } else if (v.equals(btnAnnuler)) {
            reafficherDescription();
        } else if (v.equals(btnChanger)) {
            TableFut.instance(getContext()).modifier(fut.getId(),
                    fut.getNumero(),
                    fut.getCapacite(),
                    fut.getId_etat(),
                    fut.getLongDateEtat(),
                    TableBrassin.instance(getContext()).recupererIndex(listeBrassin.getSelectedItemPosition()).getId(),
                    fut.getDateInspection());
            Intent intent = new Intent(getContext(), ActivityVueFut.class);
            intent.putExtra("id", fut.getId());
            getContext().startActivity(intent);
        } else if (v.equals(btnAjouter)) {
            TableHistorique.instance(getContext()).ajouter(ajoutListeHistorique.getSelectedItem() + ajoutHistorique.getText().toString(), System.currentTimeMillis(), -1, -1, -1, fut.getId());
            afficherHistorique();
        } else {
            for (int i=0; i<btnsEtat.size() ; i++) {
                if (v.equals(btnsEtat.get(i))) {
                    TableFut.instance(getContext()).modifier(fut.getId(),
                            fut.getNumero(),
                            fut.getCapacite(),
                            listeEtat.get(i).getId(),
                            System.currentTimeMillis(),
                            fut.getId_brassin(),
                            fut.getDateInspection());
                    String texte = listeEtat.get(i).getHistorique();
                    if (texte != null) {
                        TableHistorique.instance(getContext()).ajouter(texte, System.currentTimeMillis(), -1, -1, fut.getId(), fut.getId_brassin());
                        Intent intent = new Intent(getContext(), ActivityVueFut.class);
                        intent.putExtra("id", fut.getId());
                        getContext().startActivity(intent);
                    }
                    afficherDescription();
                }
            }
        }
    }
}
