package fabrique.gestion.Vue;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.BDD.TableRapport;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Emplacement;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.Objets.NoeudFermenteur;
import fabrique.gestion.R;

public class VueFermenteur extends TableLayout implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private FragmentAmeliore parent;
    private Fermenteur fermenteur;

    //Description
    private TableLayout tableauDescription;
    private Spinner editEmplacement;
    private ArrayList<Emplacement> emplacements;
    private int indexEmplacement;
    private EditText editTitre, editCapacite;
    private CheckBox editActif;
    private TableRow ligneBouton;
    private TextView etat, dateEtat;
    private Button btnModifier, btnValider, btnAnnuler;

    //DatePicker
    private long dateLavageAcide;
    private TextView texteDateLavageAcide;

    //Chemin du brassin
    private TableLayout tableauCheminBrassin;
    private Button btnEtatSuivantAvecBrassin, btnEtatSuivantSansBrassin, btnTransfere, btnVider;
    private ArrayList<Cuve> listeCuveSansBrassin;
    private Spinner spinnerListeCuveSansBrassin;
    private EditText quantiteTransfere;

    //Historique
    private LinearLayout tableauHistorique;

    //Ajouter historique
    private TableRow ligneAjouter;
    private Spinner ajoutListeHistorique;
    private EditText ajoutHistorique;
    private TextView btnAjouterHistorique;

    public VueFermenteur(Context contexte) {
        super(contexte);
    }

    public VueFermenteur(Context contexte, FragmentAmeliore parent, Fermenteur fermenteur) {
        super(contexte);

        this.parent = parent;
        this.fermenteur = fermenteur;

        tableauCheminBrassin = new TableLayout(contexte);
        addView(tableauCheminBrassin);

        TableRow ligne = new TableRow(contexte);

        tableauDescription = new TableLayout(contexte);
        ligne.addView(cadre(tableauDescription, " Description "));

        tableauHistorique = new TableLayout(getContext());
        ligne.addView(cadre(tableauHistorique, " Historique "));

        initialiser();
        afficher();
        afficherHistorique();
        afficherCheminBrassin();

        HorizontalScrollView layoutHorizontalScroll = new HorizontalScrollView(getContext());
        layoutHorizontalScroll.addView(ligne);
        addView(layoutHorizontalScroll);
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

            TableRow ligneTitreLavageAcide = new TableRow(getContext());
                LinearLayout layoutTitre = new LinearLayout(getContext());
                layoutTitre.setGravity(Gravity.CENTER_VERTICAL);
                    TextView titre = new TextView(getContext());
                    titre.setText("Fermenteur ");
                    titre.setTypeface(null, Typeface.BOLD);
                layoutTitre.addView(titre);

                    editTitre = new EditText(getContext());
                    editTitre.setInputType(InputType.TYPE_CLASS_NUMBER);
                layoutTitre.addView(editTitre);
            ligneTitreLavageAcide.addView(layoutTitre, parametre);

            LinearLayout layoutDateLavageAcide = new LinearLayout(getContext());
                layoutDateLavageAcide.setGravity(Gravity.CENTER_VERTICAL);
                    texteDateLavageAcide = new TextView(getContext());
                    texteDateLavageAcide.setFocusable(false);
                    texteDateLavageAcide.setOnClickListener(this);
                    texteDateLavageAcide.setGravity(Gravity.CENTER_VERTICAL);
                layoutDateLavageAcide.addView(texteDateLavageAcide);
            ligneTitreLavageAcide.addView(layoutDateLavageAcide, parametre);
        tableauDescription.addView(ligneTitreLavageAcide);

            TableRow ligneCapaciteEmplacement = new TableRow(getContext());
                LinearLayout layoutCapacite = new LinearLayout(getContext());
                layoutCapacite.setGravity(Gravity.CENTER_VERTICAL);
                    TextView capacite = new TextView(getContext());
                    capacite.setText("Capacité : ");
                layoutCapacite.addView(capacite);

                    editCapacite = new EditText(getContext());
                    editCapacite.setInputType(InputType.TYPE_CLASS_NUMBER);
                layoutCapacite.addView(editCapacite);
            ligneCapaciteEmplacement.addView(layoutCapacite, parametre);

                LinearLayout layoutEmplacement = new LinearLayout(getContext());
                layoutEmplacement.setGravity(Gravity.CENTER_VERTICAL);
                    TextView emplacement = new TextView(getContext());
                    emplacement.setText("Emplacement : ");
                layoutEmplacement.addView(emplacement);
                    editEmplacement = new Spinner(getContext());
                        emplacements = TableEmplacement.instance(getContext()).recupererActifs();
                        ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(getContext(), R.layout.spinner_style, TableEmplacement.instance(getContext()).recupererTexteActifs());
                        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    editEmplacement.setAdapter(adapteurEmplacement);
                    indexEmplacement = -1;
                    for (int i=0; i<emplacements.size() ; i++) {
                        if (fermenteur.getEmplacement(getContext()).getId() == emplacements.get(i).getId()) {
                            indexEmplacement = i;
                        }
                    }
                    if (indexEmplacement == -1) {
                        emplacements.add(fermenteur.getEmplacement(getContext()));
                        adapteurEmplacement.add(TableEmplacement.instance(getContext()).recupererId(fermenteur.getEmplacement(getContext()).getId()).getTexte());
                        editEmplacement.setSelection(adapteurEmplacement.getCount()-1);
                    }
                layoutEmplacement.addView(editEmplacement);
            ligneCapaciteEmplacement.addView(layoutEmplacement, parametre);
        tableauDescription.addView(ligneCapaciteEmplacement);


        String texteEtat = "État non défini";
        if ((fermenteur.getNoeud(getContext()) != null) && (fermenteur.getNoeud(getContext()).getEtat(getContext()) != null)) {
            texteEtat = fermenteur.getNoeud(getContext()).getEtat(getContext()).getTexte();
        }

            TableRow ligneEtatDate = new TableRow(getContext());
                etat = new TextView(getContext());
                etat.setGravity(Gravity.CENTER_VERTICAL);
                etat.setText("État : " + texteEtat);
            ligneEtatDate.addView(etat, parametre);
                dateEtat = new TextView(getContext());
                dateEtat.setGravity(Gravity.CENTER_VERTICAL);
                dateEtat.setText("Depuis le : " + fermenteur.getDateEtat());
            ligneEtatDate.addView(dateEtat, parametre);
        tableauDescription.addView(ligneEtatDate);

            TableRow ligneActif = new TableRow(getContext());
                TextView actif = new TextView(getContext());
                actif.setGravity(Gravity.CENTER_VERTICAL);
                actif.setText("Actif : ");
            ligneActif.addView(actif, parametre);
                    editActif = new CheckBox(getContext());
                    editActif.setGravity(Gravity.CENTER_VERTICAL);
            ligneActif.addView(editActif, parametre);
        tableauDescription.addView(ligneActif);

            ligneBouton = new TableRow(getContext());
                btnModifier = new Button(getContext());
                btnModifier.setText("Modifier");
                btnModifier.setOnClickListener(this);
                btnModifier.setLayoutParams(parametre);
        tableauDescription.addView(ligneBouton);

                btnValider = new Button(getContext());
                btnValider.setText("Valider");
                btnValider.setOnClickListener(this);
                btnValider.setLayoutParams(parametre);

                btnAnnuler = new Button(getContext());
                btnAnnuler.setText("Annuler");
                btnAnnuler.setOnClickListener(this);
                btnAnnuler.setLayoutParams(parametre);

        TableRow.LayoutParams margeHistorique = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        margeHistorique.setMargins(10, 0, 10, 0);

        ligneAjouter = new TableRow(getContext());
        LinearLayout sous_ligneAjouter = new LinearLayout(getContext());
        ArrayList<ListeHistorique> listeHistoriques = TableListeHistorique.instance(getContext()).listeHistoriqueFermenteur();
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
        ajoutHistorique.setMinEms(5);
        sous_ligneAjouter.addView(ajoutHistorique);
        ligneAjouter.addView(sous_ligneAjouter);
        btnAjouterHistorique = new TextView(getContext());
        SpannableString ajouter = new SpannableString("Ajouter");
        ajouter.setSpan(new UnderlineSpan(), 0, ajouter.length(), 0);
        btnAjouterHistorique.setText(ajouter);
        btnAjouterHistorique.setOnClickListener(this);
        ligneAjouter.addView(btnAjouterHistorique, margeHistorique);

        btnEtatSuivantAvecBrassin = new Button(getContext());
        btnEtatSuivantAvecBrassin.setOnClickListener(this);

        btnEtatSuivantSansBrassin = new Button(getContext());
        btnEtatSuivantSansBrassin.setOnClickListener(this);

        btnTransfere = new Button(getContext());
        btnTransfere.setText("Transférer");
        btnTransfere.setOnClickListener(this);

        btnVider = new Button(getContext());
        btnVider.setText("Vider");
        btnVider.setOnClickListener(this);

        spinnerListeCuveSansBrassin = new Spinner(getContext());
    }

    private void afficher() {
        editTitre.setText("" + fermenteur.getNumero());
        editTitre.setEnabled(false);

        dateLavageAcide = fermenteur.getDateLavageAcideToLong();
        afficherDateLavageAcide();
        texteDateLavageAcide.setEnabled(false);

        editCapacite.setText("" + fermenteur.getCapacite());
        editCapacite.setEnabled(false);

        editEmplacement.setSelection(indexEmplacement);
        editEmplacement.setEnabled(false);

        editActif.setChecked(fermenteur.getActif());
        editActif.setEnabled(false);

        ligneBouton.removeAllViews();
        ligneBouton.addView(btnModifier);

        String texteEtat = "État non défini";
        if ((fermenteur.getNoeud(getContext()) != null) && (fermenteur.getNoeud(getContext()).getEtat(getContext()) != null)) {
            texteEtat = fermenteur.getNoeud(getContext()).getEtat(getContext()).getTexte();
        }

        etat.setText("État : " + texteEtat);

        dateEtat.setText("Depuis le : " + fermenteur.getDateEtat());
    }

    private void afficherDateLavageAcide() {
        texteDateLavageAcide.setText(DateToString.dateToString(dateLavageAcide));
        if ((System.currentTimeMillis() - dateLavageAcide) >= TableGestion.instance(getContext()).delaiLavageAcide()) {
            texteDateLavageAcide.setTextColor(Color.RED);
        } else if ((System.currentTimeMillis() - dateLavageAcide) >= (TableGestion.instance(getContext()).avertissementLavageAcide())) {
            texteDateLavageAcide.setTextColor(Color.rgb(198, 193, 13));
        } else {
            texteDateLavageAcide.setTextColor(Color.rgb(34, 177, 76));
        }
    }

    private void modifier() {
        editTitre.setEnabled(true);
        texteDateLavageAcide.setEnabled(true);
        editEmplacement.setEnabled(true);
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
            erreur = erreur + "La fermenteur doit avoir un numéro.";
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
            if ((!editActif.isChecked()) && (TableHistorique.instance(getContext()).recupererSelonIdFermenteur(fermenteur.getId()).size() == 0)) {
                TableFermenteur.instance(getContext()).supprimer(fermenteur.getId());
                parent.invalidate();
            } else {
                if(dateLavageAcide != fermenteur.getDateLavageAcideToLong()){
                    String texteTransfert = TableListeHistorique.instance(getContext()).recupererId(6).getTexte();
                    TableHistorique.instance(getContext()).ajouter(texteTransfert, dateLavageAcide, fermenteur.getId(), 0, 0, 0);
                }
                TableFermenteur.instance(getContext()).modifier(fermenteur.getId(), numero, capacite, emplacements.get((int) editEmplacement.getSelectedItemId()).getId(), dateLavageAcide, fermenteur.getIdNoeud(), fermenteur.getDateEtatToLong(), fermenteur.getIdBrassin(), editActif.isChecked());
                indexEmplacement = editEmplacement.getSelectedItemPosition();
                afficher();
                afficherHistorique();
            }
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_SHORT).show();
        }
    }

    private void afficherHistorique() {
        TableLayout.LayoutParams margeTableau = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        margeTableau.setMargins(10, 0, 10, 0);

        tableauHistorique.removeAllViews();
        tableauHistorique.addView(ligneAjouter, margeTableau);

        ArrayList<Historique> historiques  = TableHistorique.instance(getContext()).recupererSelonIdFermenteur(fermenteur.getId());
        for (int i=0; i<historiques.size() ; i++) {
            tableauHistorique.addView(new LigneHistorique(getContext(), this, historiques.get(i)), margeTableau);
        }
    }

    private void afficherCheminBrassin() {
        tableauCheminBrassin.removeAllViews();

        if (fermenteur.getIdNoeud() != -1) {
            if (((ViewGroup) btnEtatSuivantAvecBrassin.getParent()) != null) {
                ((ViewGroup) btnEtatSuivantAvecBrassin.getParent()).removeAllViews();
            }
            if (((ViewGroup) btnEtatSuivantSansBrassin.getParent()) != null) {
                ((ViewGroup) btnEtatSuivantSansBrassin.getParent()).removeAllViews();
            }
            if (((ViewGroup) btnTransfere.getParent()) != null) {
                ((ViewGroup) btnTransfere.getParent()).removeAllViews();
            }
            if (((ViewGroup) btnVider.getParent()) != null) {
                ((ViewGroup) btnVider.getParent()).removeAllViews();
            }
            if (((ViewGroup) spinnerListeCuveSansBrassin.getParent()) != null) {
                ((ViewGroup) spinnerListeCuveSansBrassin.getParent()).removeAllViews();
            }

            NoeudFermenteur noeud = fermenteur.getNoeud(getContext());

            TableRow ligne = new TableRow(getContext());

            if (fermenteur.getIdBrassin() != -1) {
                //Si il y a un prochain etat avec brassin dans ce recipient
                if (noeud.getNoeudAvecBrassin(getContext()) != null) {
                    LinearLayout ligneEtatSuivant = new LinearLayout(getContext());
                    ligneEtatSuivant.setGravity(Gravity.CENTER);
                    btnEtatSuivantAvecBrassin.setText(noeud.getNoeudAvecBrassin(getContext()).getEtat(getContext()).getTexte());
                    ligneEtatSuivant.addView(btnEtatSuivantAvecBrassin);
                    ligne.addView(cadre(ligneEtatSuivant, " État suivant "));
                }

                //Si il y a un prochain etat sans brassin dans ce recipient
                if (noeud.getId_noeudSansBrassin() != -1) {
                    TableRow.LayoutParams marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    marge.setMargins(5, 5, 5, 5);

                    TableLayout tableauRecipientSuivant = new TableLayout(getContext());
                    TableRow ligneEnTete = new TableRow(getContext());
                    TextView texteCuve = new TextView(getContext());
                    texteCuve.setText("Cuve / Capacité");
                    ligneEnTete.addView(texteCuve, marge);
                    TextView texteQuantite = new TextView(getContext());
                    texteQuantite.setText("Quantité");
                    ligneEnTete.addView(texteQuantite, marge);
                    tableauRecipientSuivant.addView(ligneEnTete);
                    TableRow ligneElement = new TableRow(getContext());
                    ArrayAdapter<String> adapteurListeCuveSansBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style);
                    adapteurListeCuveSansBrassin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listeCuveSansBrassin = TableCuve.instance(getContext()).recupererCuveSansBrassin();
                    for (int i = 0; i < listeCuveSansBrassin.size(); i++) {
                        adapteurListeCuveSansBrassin.add(listeCuveSansBrassin.get(i).getNumero() + " / " + listeCuveSansBrassin.get(i).getCapacite() + "L");
                    }
                    spinnerListeCuveSansBrassin.setAdapter(adapteurListeCuveSansBrassin);
                    ligneElement.addView(spinnerListeCuveSansBrassin, marge);
                    quantiteTransfere = new EditText(getContext());
                    quantiteTransfere.setInputType(InputType.TYPE_CLASS_NUMBER);
                    ligneElement.addView(quantiteTransfere, marge);
                    ligneElement.addView(btnTransfere, marge);
                    tableauRecipientSuivant.addView(ligneElement);
                    ligne.addView(cadre(tableauRecipientSuivant, " Récipient suivant "));
                }
                //Si il n'y a ni etat suivant avec brassin ni etat suivant sans brassin dans ce recipient
                if ((noeud.getId_noeudAvecBrassin() == -1) && (noeud.getId_noeudSansBrassin() == -1)) {
                    TableRow.LayoutParams marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    marge.setMargins(5, 5, 5, 5);

                    TableLayout tableauRecipientSuivant = new TableLayout(getContext());
                    TableRow ligneEnTete = new TableRow(getContext());
                    TextView texteCuve = new TextView(getContext());
                    texteCuve.setText("Cuve / Capacité");
                    ligneEnTete.addView(texteCuve, marge);
                    TextView texteQuantite = new TextView(getContext());
                    texteQuantite.setText("Quantité");
                    ligneEnTete.addView(texteQuantite, marge);
                    tableauRecipientSuivant.addView(ligneEnTete);
                    TableRow ligneElement = new TableRow(getContext());
                    ArrayAdapter<String> adapteurListeCuveSansBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style);
                    adapteurListeCuveSansBrassin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    listeCuveSansBrassin = TableCuve.instance(getContext()).recupererCuveSansBrassin();
                    for (int i = 0; i < listeCuveSansBrassin.size(); i++) {
                        adapteurListeCuveSansBrassin.add(listeCuveSansBrassin.get(i).getNumero() + " / " + listeCuveSansBrassin.get(i).getCapacite() + "L");
                    }
                    spinnerListeCuveSansBrassin.setAdapter(adapteurListeCuveSansBrassin);
                    ligneElement.addView(spinnerListeCuveSansBrassin, marge);
                    quantiteTransfere = new EditText(getContext());
                    quantiteTransfere.setInputType(InputType.TYPE_CLASS_NUMBER);
                    ligneElement.addView(quantiteTransfere, marge);
                    ligneElement.addView(btnTransfere, marge);
                    tableauRecipientSuivant.addView(ligneElement);
                    ligne.addView(cadre(tableauRecipientSuivant, " Récipient suivant "));
                }

                LinearLayout ligneVider = new LinearLayout(getContext());
                ligneVider.setGravity(Gravity.CENTER);
                ligneVider.addView(btnVider);
                ligne.addView(cadre(ligneVider, " Vider "));

            } else {
                //Si il y a un prochain etat sans brassin dans ce recipient
                if (noeud.getId_noeudSansBrassin() != -1) {
                    LinearLayout ligneEtatSuivant = new LinearLayout(getContext());
                    ligneEtatSuivant.setGravity(Gravity.CENTER);
                    btnEtatSuivantSansBrassin.setText(noeud.getNoeudSansBrassin(getContext()).getEtat(getContext()).getTexte());
                    ligneEtatSuivant.addView(btnEtatSuivantSansBrassin);
                    ligne.addView(cadre(ligneEtatSuivant, " État suivant "));
                }
            }
            tableauCheminBrassin.addView(ligne);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifier();
        }
        else if (v.equals(btnValider)) {
            valider();
        }
        else if (v.equals(btnAnnuler)) {
            afficher();
        }
        else if (v.equals(texteDateLavageAcide)) {
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            new DatePickerDialog(getContext(), this, calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if (v.equals(btnAjouterHistorique)) {
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

            TableHistorique.instance(getContext()).ajouter(ajoutListeHistorique.getSelectedItem() + ajoutHistorique.getText().toString(), date, fermenteur.getId(), -1, -1, -1);
            afficherHistorique();
        } else if (v.equals(btnEtatSuivantAvecBrassin)) {
            //On change d'état le fermenteur
            TableFermenteur.instance(getContext()).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), fermenteur.getNoeud(getContext()).getId_noeudAvecBrassin(), System.currentTimeMillis(), fermenteur.getIdBrassin(), fermenteur.getActif());

            //On ajoute un historique au fermenteur si le nouvel état à un texte d'historique
            if (fermenteur.getIdNoeud() != -1) {
                String historique = fermenteur.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                if ((historique != null) && (!historique.equals(""))) {
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

                    TableHistorique.instance(getContext()).ajouter(historique, date, fermenteur.getId(), -1, -1, -1);
                    afficherHistorique();
                }
            }

            afficher();
            afficherCheminBrassin();
        } else if (v.equals(btnEtatSuivantSansBrassin)) {
            //On change l'état du fermenteur
            TableFermenteur.instance(getContext()).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), fermenteur.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), fermenteur.getIdBrassin(), fermenteur.getActif());

            //On ajoute un historique au fermenteur si le nouvel état à un texte d'historique
            if (fermenteur.getIdNoeud() != -1) {
                String historique = fermenteur.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                if ((historique != null) && (!historique.equals(""))) {
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

                    TableHistorique.instance(getContext()).ajouter(historique, date, fermenteur.getId(), -1, -1, -1);
                    afficherHistorique();
                }
            }

            afficher();
            afficherCheminBrassin();
        } else if (v.equals(btnTransfere) && spinnerListeCuveSansBrassin.getSelectedItemPosition() != Spinner.INVALID_POSITION) {
            int quantite;
            try {
                quantite = Integer.parseInt(quantiteTransfere.getText().toString());
            } catch (Exception e) {
                quantite = 0;
                Toast.makeText(getContext(), "La quantite inscrite ne peut être lu.", Toast.LENGTH_SHORT).show();
            }

            if (quantite > 0) {
                //On récupère la cuve sélectionné
                Cuve cuve = listeCuveSansBrassin.get(spinnerListeCuveSansBrassin.getSelectedItemPosition());

                //On récupère le brassin
                Brassin brassin = TableBrassin.instance(getContext()).recupererId(fermenteur.getIdBrassin());

                //Si la quantite que l'on veut transferer est plus petit à la quantite du brassin
                //ET que cette quantite est plus petite ou égal à la capacite de la cuve alors on creer un nouveau brassin
                if ((brassin.getQuantite() > quantite) && (quantite <= cuve.getCapacite())) {
                    //Date
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

                    //On ajoute un historique concernant ce transfert
                    String texteTransfert = TableListeHistorique.instance(getContext()).recupererId(2).getTexte() + " du brassin n°" + brassin.getNumero() + " (" + quantite + "L)" + " du fermenteur n°" + fermenteur.getNumero() + " à la cuve n°" + cuve.getNumero();
                    TableHistorique.instance(getContext()).ajouter(texteTransfert, date, fermenteur.getId(), cuve.getId(), -1, brassin.getId_brassinPere());

                    //On modifie le brassin existant
                    TableBrassin.instance(getContext()).modifier(brassin.getId(), brassin.getNumero(), brassin.getCommentaire(), brassin.getDateLong(), brassin.getQuantite() - quantite, brassin.getId_recette(), brassin.getDensiteOriginale(), brassin.getDensiteFinale());

                    //On créer un nouveau brassin
                    long idNouveauBrassin = TableBrassin.instance(getContext()).ajouter(getContext(), brassin.getId_brassinPere(), quantite);

                    //On ajout le nouveau brassin à la cuve et on le change d'état
                    TableCuve.instance(getContext()).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), TableCheminBrassinCuve.instance(getContext()).recupererPremierNoeud(), System.currentTimeMillis(), cuve.getCommentaireEtat(), idNouveauBrassin, cuve.getActif());

                    //On ajout un historique à la cuve
                    if (cuve.getIdNoeud() != -1) {
                        String historique = cuve.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                        if ((historique != null) && (!historique.equals(""))) {
                            TableHistorique.instance(getContext()).ajouter(historique, date, -1, cuve.getId(), -1, -1);
                            afficherHistorique();
                        }
                    }

                    //On ajoute la quantité fermentée au rapport
                    TableRapport.instance(getContext()).ajouter(TableBrassin.instance(getContext()).recupererId(idNouveauBrassin).getId_brassinPere(), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.YEAR), 0, quantite, 0);

                    parent.invalidate();
                }
                //Si la quantite que l'on veut transferer est plus grande ou égal à la quantite du brassin
                //ET que la quantite du brassin est plus petite ou égal à la capacite de la cuve alors on transfere le brassin
                else if ((brassin.getQuantite() <= quantite) && (brassin.getQuantite() <= cuve.getCapacite())) {
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

                    //On ajoute un historique concernant ce transfert
                    String texteTransfert = TableListeHistorique.instance(getContext()).recupererId(2).getTexte() + " du brassin n°" + brassin.getNumero() + " (" + brassin.getQuantite() + "L)" + " du fermenteur n°" + fermenteur.getNumero() + " à la cuve n°" + cuve.getNumero();
                    TableHistorique.instance(getContext()).ajouter(texteTransfert, date, fermenteur.getId(), cuve.getId(), -1, brassin.getId_brassinPere());

                    //On ajoute le brassin à la cuve et on le change d'état
                    TableCuve.instance(getContext()).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), TableCheminBrassinCuve.instance(getContext()).recupererPremierNoeud(), System.currentTimeMillis(), cuve.getCommentaireEtat(), brassin.getId(), cuve.getActif());

                    //On ajoute un historique à la cuve si le nouvel état à un texte d'historique
                    if (cuve.getIdNoeud() != -1) {
                        String historique = cuve.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                        if ((historique != null) && (!historique.equals(""))) {
                            TableHistorique.instance(getContext()).ajouter(historique, date, -1, cuve.getId(), -1, -1);
                            afficherHistorique();
                        }
                    }

                    //On ajoute la quantité fermentée au rapport
                    TableRapport.instance(getContext()).ajouter(brassin.getId_brassinPere(), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.YEAR), 0, brassin.getQuantite(), 0);

                    //On enlève le brassin du fermenteur et on le change d'état
                    TableFermenteur.instance(getContext()).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), fermenteur.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), -1, fermenteur.getActif());

                    //On ajoute un historique au fermenteur si le nouvel état à un texte d'historique
                    if (fermenteur.getIdNoeud() != -1) {
                        String historique = fermenteur.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                        if ((historique != null) && (!historique.equals(""))) {
                            TableHistorique.instance(getContext()).ajouter(historique, date, fermenteur.getId(), -1, -1, -1);
                            afficherHistorique();
                        }
                    }

                    parent.invalidate();
                }
            }
        }
        else if (v.equals(btnVider)) {
            //On récupère le brassin
            Brassin brassin = TableBrassin.instance(getContext()).recupererId(fermenteur.getIdBrassin());

            //On récupère la date
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

            //On ajoute un historique
            String texteTransfert = "Perte de " + brassin.getQuantite() + "L" + " du brassin n°" + brassin.getNumero();
            TableHistorique.instance(getContext()).ajouter(texteTransfert, date, -1, -1, -1, brassin.getId_brassinPere());

            //On enlève le brassin du fermenteur
            TableFermenteur.instance(getContext()).modifier(fermenteur.getId(), fermenteur.getNumero(), fermenteur.getCapacite(), fermenteur.getIdEmplacement(), fermenteur.getDateLavageAcideToLong(), fermenteur.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), -1, fermenteur.getActif());

            //On ajoute un historique au fermenteur si le nouvel état à un texte d'historique
            if (fermenteur.getIdNoeud() != -1) {
                String historique = fermenteur.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                if ((historique != null) && (!historique.equals(""))) {
                    TableHistorique.instance(getContext()).ajouter(historique, date, fermenteur.getId(), -1, -1, -1);
                    afficherHistorique();
                }
            }

            parent.invalidate();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
        dateLavageAcide = new GregorianCalendar(annee, mois, jour).getTimeInMillis();
        afficherDateLavageAcide();
    }

    @Override
    public void invalidate() {
        afficherHistorique();
    }
}
