package fabrique.gestion.Vue;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox editActif;
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
    private TableRow ligneAjouter;
    private Spinner ajoutListeHistorique;
    private EditText ajoutHistorique;
    private TextView btnAjouterHistorique;

    public VueFut(Context contexte) {
        super(contexte);
    }

    public VueFut(Context contexte, Fut fut) {
        super(contexte);

        this.fut = fut;

        TableRow ligne = new TableRow(contexte);

        tableauDescription = new TableLayout(contexte);
        ligne.addView(cadre(tableauDescription, " Description "));

        tableauHistorique = new TableLayout(getContext());
        ligne.addView(cadre(tableauHistorique, " Historique "));

        initialiser();
        afficher();
        afficherHistorique();

        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(getContext());
        layoutHorizontalScroll.addView(ligne);
        addView(layoutHorizontalScroll);

        //Interface pour tester les états et les brassins
        TableRow ligne2 = new TableRow(contexte);

        tableauBrassin = new LinearLayout(contexte);
        ligne2.addView(cadre(tableauBrassin, " Changer brassin "));
        changerBrassin();

        tableauEtat = new TableLayout(contexte);
        ligne2.addView(cadre(tableauEtat, " Changer Etat "));
        changerEtat();

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

    private void initialiser() {
        TableRow.LayoutParams parametre = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        parametre.setMargins(10, 10, 10, 10);

        TableRow ligneTitreInspection = new TableRow(getContext());
        LinearLayout layoutTitre = new LinearLayout(getContext());
        TextView titre = new TextView(getContext());
        titre.setText("Fut ");
        titre.setTypeface(null, Typeface.BOLD);

        editTitre = new EditText(getContext());
        editTitre.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTitre.setEnabled(false);

        TextView dateInspection = new TextView(getContext());
        dateInspection.setText("" + fut.getDateInspection());
        if ((System.currentTimeMillis() - fut.getDateInspectionToLong()) >= TableGestion.instance(getContext()).delaiInspectionBaril()) {
            dateInspection.setTextColor(Color.RED);
        } else if ((System.currentTimeMillis() - fut.getDateInspectionToLong()) >= (TableGestion.instance(getContext()).avertissementInspectionBaril())) {
            dateInspection.setTextColor(Color.rgb(198, 193, 13));
        } else {
            dateInspection.setTextColor(Color.rgb(34, 177, 76));
        }

        TableRow ligneCapacite = new TableRow(getContext());
        LinearLayout layoutCapacite = new LinearLayout(getContext());
        TextView capacite = new TextView(getContext());
        capacite.setText("Capacité : ");

        editCapacite = new EditText(getContext());
        editCapacite.setInputType(InputType.TYPE_CLASS_NUMBER);
        editCapacite.setEnabled(false);

        TableRow ligneEtatDate = new TableRow(getContext());
        TextView etat = new TextView(getContext());
        etat.setText("État : " + fut.getEtat(getContext()).getTexte());

        TextView dateEtat = new TextView(getContext());
        dateEtat.setText("Depuis le : " + fut.getDateEtat());

        TableRow ligneActif = new TableRow(getContext());
        TextView actif = new TextView(getContext());
        actif.setGravity(Gravity.CENTER_VERTICAL);
        actif.setText("Actif : ");
        ligneActif.addView(actif, parametre);
        editActif = new CheckBox(getContext());
        editActif.setGravity(Gravity.CENTER_VERTICAL);
        ligneActif.addView(editActif, parametre);

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
        tableauDescription.addView(ligneActif);
        ligneBouton.addView(btnModifier, parametre);
        tableauDescription.addView(ligneBouton);

        TableRow.LayoutParams margeCase = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        margeCase.setMargins(10, 0, 10, 0);

        ligneAjouter = new TableRow(getContext());
        LinearLayout sous_ligneAjouter = new LinearLayout(getContext());
        ArrayList<ListeHistorique> listeHistoriques = TableListeHistorique.instance(getContext()).listeHistoriqueFut();
        String[] tabListeHistorique = new String[listeHistoriques.size()+1];
        tabListeHistorique[0] = "";
        for (int i=0; i<listeHistoriques.size() ; i++) {
            tabListeHistorique[i+1] = listeHistoriques.get(i).getTexte();
        }
        ajoutListeHistorique = new Spinner(getContext());
        ArrayAdapter<String> adapteurAjoutListeHistorique = new ArrayAdapter<>(getContext(), R.layout.spinner_style, tabListeHistorique);
        adapteurAjoutListeHistorique.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ajoutListeHistorique.setAdapter(adapteurAjoutListeHistorique);
        sous_ligneAjouter.addView(ajoutListeHistorique);
        ajoutHistorique = new EditText(getContext());
        sous_ligneAjouter.addView(ajoutHistorique);
        ligneAjouter.addView(sous_ligneAjouter);
        btnAjouterHistorique = new TextView(getContext());
        SpannableString ajouter = new SpannableString("Ajouter");
        ajouter.setSpan(new UnderlineSpan(), 0, ajouter.length(), 0);
        btnAjouterHistorique.setText(ajouter);
        btnAjouterHistorique.setOnClickListener(this);
        ligneAjouter.addView(btnAjouterHistorique, margeCase);
    }

    private void afficher() {
        editTitre.setEnabled(false);
        editTitre.setText("" + fut.getNumero());
        editCapacite.setEnabled(false);
        editCapacite.setText("" + fut.getCapacite());
        editActif.setChecked(fut.getActif());
        editActif.setEnabled(false);
        ligneBouton.removeAllViews();
        ligneBouton.addView(btnModifier);
    }

    private void modifier() {
        editTitre.setEnabled(true);
        editCapacite.setEnabled(true);
        editActif.setEnabled(true);
        ligneBouton.removeAllViews();
        ligneBouton.addView(btnValider);
        ligneBouton.addView(btnAnnuler);
    }

    private void valider() {
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
            TableFut.instance(getContext()).modifier(fut.getId(), numero, capacite,fut.getId_etat(), fut.getDateEtatToLong(), fut.getId_brassin(), fut.getDateInspectionToLong(), editActif.isChecked());
            afficher();
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_LONG).show();
        }
    }

    private void changerBrassin() {
        tableauBrassin.removeAllViews();

        TableBrassin tableBrassin = TableBrassin.instance(getContext());
        ArrayList<String> listeNumeroBrassin = new ArrayList<>();
        for (int i=0; i<tableBrassin.tailleListe() ; i++) {
            listeNumeroBrassin.add("" + tableBrassin.recupererIndex(i).getNumero());
        }
        listeBrassin = new Spinner(getContext());
            ArrayAdapter<String> adapteurBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style, listeNumeroBrassin);
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
        TableLayout.LayoutParams margeTableau = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        margeTableau.setMargins(10, 0, 10, 0);

        tableauHistorique.removeAllViews();
        tableauHistorique.addView(ligneAjouter, margeTableau);
        ArrayList<Historique> historiques  = TableHistorique.instance(getContext()).recupererSelonIdFut(fut.getId());
        for (int i=0; i<historiques.size() ; i++) {
            tableauHistorique.addView(new LigneHistorique(getContext(), this, historiques.get(i)), margeTableau);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifier();
        } else if (v.equals(btnValider)) {
            valider();
        } else if (v.equals(btnAnnuler)) {
            afficher();
        } else if (v.equals(btnChanger)) {
            TableFut.instance(getContext()).modifier(fut.getId(),
                    fut.getNumero(),
                    fut.getCapacite(),
                    fut.getId_etat(),
                    fut.getDateEtatToLong(),
                    TableBrassin.instance(getContext()).recupererIndex(listeBrassin.getSelectedItemPosition()).getId(),
                    fut.getDateInspectionToLong(),
                    fut.getActif());
        } else if (v.equals(btnAjouterHistorique)) {
            TableHistorique.instance(getContext()).ajouter(ajoutListeHistorique.getSelectedItem() + ajoutHistorique.getText().toString(), System.currentTimeMillis(), -1, -1, fut.getId(), -1);
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
                            fut.getDateInspectionToLong(),
                            fut.getActif());
                    String texte = listeEtat.get(i).getHistorique();
                    if ((texte != null) && (!texte.equals(""))) {
                        TableHistorique.instance(getContext()).ajouter(texte, System.currentTimeMillis(), -1, -1, fut.getId(), fut.getId_brassin());
                        afficherHistorique();
                    }
                    afficher();
                }
            }
        }
    }

    @Override
    public void invalidate() {
        afficherHistorique();
    }
}