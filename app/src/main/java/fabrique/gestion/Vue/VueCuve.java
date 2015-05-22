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
import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Emplacement;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.Objets.NoeudCuve;
import fabrique.gestion.R;

public class VueCuve extends TableLayout implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private FragmentAmeliore parent;
    private Cuve cuve;

    //Description
    private TableLayout tableauDescription;
    private Spinner editEmplacement;
    private ArrayList<Emplacement> emplacements;
    private int indexEmplacement;
    private EditText editTitre, editCapacite;
    private CheckBox editActif;
    private TableRow ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    //DatePicker
    private long dateLavageAcide;
    private TextView texteDateLavageAcide;

    //Chemin du brassin
    private TableLayout tableauCheminBrassin;
    private Button btnEtatSuivantAvecBrassin, btnEtatSuivantSansBrassin, btnTransfere, btnVider;
    private ArrayList<Fut> listeFutSansBrassin;
    private Spinner spinnerListeFutSansBrassin;
    private EditText quantiteTransfere;

    //Historique
    private LinearLayout tableauHistorique;

    //Ajouter historique
    private TableRow ligneAjouter;
    private Spinner ajoutListeHistorique;
    private EditText ajoutHistorique;
    private TextView btnAjouterHistorique;

    public VueCuve(Context contexte) {
        super(contexte);
    }

    public VueCuve(Context contexte, FragmentAmeliore parent, Cuve cuve) {
        super(contexte);

        this.parent = parent;
        this.cuve = cuve;

        tableauCheminBrassin = new TableLayout(contexte);
        addView(tableauCheminBrassin);

        TableRow ligne = new TableRow(contexte);

        tableauDescription = new TableLayout(contexte);
        ligne.addView(cadre(tableauDescription, " Description "));

        tableauHistorique = new TableLayout(getContext());
        ligne.addView(cadre(tableauHistorique, " Historique "));

        initialiser();
        afficherCheminBrassin();
        afficher();
        afficherHistorique();

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
        parametreContour.topMargin = 15;
        parametreContour.leftMargin = 5;

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
        titre.setText("Cuve ");
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
        for (int i = 0; i < emplacements.size(); i++) {
            if (cuve.getEmplacement(getContext()).getId() == emplacements.get(i).getId()) {
                indexEmplacement = i;
            }
        }
        if (indexEmplacement == -1) {
            emplacements.add(cuve.getEmplacement(getContext()));
            adapteurEmplacement.add(TableEmplacement.instance(getContext()).recupererId(cuve.getEmplacement(getContext()).getId()).getTexte());
            editEmplacement.setSelection(adapteurEmplacement.getCount() - 1);
        }
        layoutEmplacement.addView(editEmplacement);
        ligneCapaciteEmplacement.addView(layoutEmplacement, parametre);
        tableauDescription.addView(ligneCapaciteEmplacement);

        String texteEtat = "Non utilisé";
        if ((cuve.getNoeud(getContext()) != null) && (cuve.getNoeud(getContext()).getEtat(getContext()) != null)) {
            texteEtat = cuve.getNoeud(getContext()).getEtat(getContext()).getTexte();
        }

        TableRow ligneEtatDate = new TableRow(getContext());
        TextView etat = new TextView(getContext());
        etat.setGravity(Gravity.CENTER_VERTICAL);
        etat.setText("État : " + texteEtat);
        ligneEtatDate.addView(etat, parametre);
        TextView dateEtat = new TextView(getContext());
        dateEtat.setGravity(Gravity.CENTER_VERTICAL);
        dateEtat.setText("Depuis le : " + cuve.getDateEtat());
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
        ArrayList<ListeHistorique> listeHistoriques = TableListeHistorique.instance(getContext()).listeHistoriqueCuve();
        String[] tabListeHistorique = new String[listeHistoriques.size() + 1];
        tabListeHistorique[0] = "";
        for (int i = 0; i < listeHistoriques.size(); i++) {
            tabListeHistorique[i + 1] = listeHistoriques.get(i).getTexte();
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

        spinnerListeFutSansBrassin = new Spinner(getContext());
    }

    private void afficher() {
        editTitre.setText("" + cuve.getNumero());
        editTitre.setEnabled(false);

        dateLavageAcide = cuve.getDateLavageAcide();
        afficherDateLavageAcide();
        texteDateLavageAcide.setEnabled(false);

        editCapacite.setText("" + cuve.getCapacite());
        editCapacite.setEnabled(false);

        editEmplacement.setSelection(indexEmplacement);
        editEmplacement.setEnabled(false);

        editActif.setChecked(cuve.getActif());
        editActif.setEnabled(false);

        ligneBouton.removeAllViews();
        ligneBouton.addView(btnModifier);
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
            erreur = erreur + "La cuve doit avoir un numéro.";
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
            if ((!editActif.isChecked()) && (TableHistorique.instance(getContext()).recupererSelonIdCuve(cuve.getId()).size() == 0)) {
                TableCuve.instance(getContext()).supprimer(cuve.getId());
                parent.invalidate();
            } else {
                TableCuve.instance(getContext()).modifier(cuve.getId(), numero, capacite, emplacements.get((int) editEmplacement.getSelectedItemId()).getId(), dateLavageAcide, cuve.getIdNoeud(), cuve.getLongDateEtat(), cuve.getCommentaireEtat(), cuve.getIdBrassin(), editActif.isChecked());
                indexEmplacement = editEmplacement.getSelectedItemPosition();
                afficher();
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

        ArrayList<Historique> historiques = TableHistorique.instance(getContext()).recupererSelonIdCuve(cuve.getId());
        for (int i = 0; i < historiques.size(); i++) {
            tableauHistorique.addView(new LigneHistorique(getContext(), this, historiques.get(i)), margeTableau);
        }
    }

    private void afficherCheminBrassin() {
        tableauCheminBrassin.removeAllViews();

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

        NoeudCuve noeud = cuve.getNoeud(getContext());

        TableRow ligne = new TableRow(getContext());

        if (cuve.getIdBrassin() != -1) {
            //Si il y a un prochain etat sans brassin dans ce recipient
            if (noeud.getNoeudSansBrassin(getContext()) != null) {
                TableRow.LayoutParams marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                marge.setMargins(5, 5, 5, 5);

                TableLayout tableauRecipientSuivant = new TableLayout(getContext());
                TableRow ligneEnTete = new TableRow(getContext());
                TextView texteFut = new TextView(getContext());
                texteFut.setText("Fut / Capacité");
                ligneEnTete.addView(texteFut, marge);
                TextView texteQuantite = new TextView(getContext());
                texteQuantite.setText("Quantité");
                ligneEnTete.addView(texteQuantite, marge);
                tableauRecipientSuivant.addView(ligneEnTete);
                TableRow ligneElement = new TableRow(getContext());
                ArrayAdapter<String> adapteurListeFutSansBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style);
                adapteurListeFutSansBrassin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listeFutSansBrassin = TableFut.instance(getContext()).recupererFutSansBrassin();
                for (int i = 0; i < listeFutSansBrassin.size(); i++) {
                    adapteurListeFutSansBrassin.add(listeFutSansBrassin.get(i).getNumero() + " / " + listeFutSansBrassin.get(i).getCapacite() + "L");
                }
                spinnerListeFutSansBrassin.setAdapter(adapteurListeFutSansBrassin);
                ligneElement.addView(spinnerListeFutSansBrassin, marge);
                quantiteTransfere = new EditText(getContext());
                ligneElement.addView(quantiteTransfere, marge);
                ligneElement.addView(btnTransfere, marge);
                tableauRecipientSuivant.addView(ligneElement);
                ligne.addView(cadre(tableauRecipientSuivant, " Récipient suivant "));
            }
            //Si il n'y a ni etat suivant avec brassin ni etat suivant sans brassin dans ce recipient
            if ((noeud.getNoeudAvecBrassin(getContext()) == null) && (noeud.getNoeudSansBrassin(getContext()) == null)) {
                TableRow.LayoutParams marge = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                marge.setMargins(5, 5, 5, 5);

                TableLayout tableauRecipientSuivant = new TableLayout(getContext());
                TableRow ligneEnTete = new TableRow(getContext());
                TextView texteFut = new TextView(getContext());
                texteFut.setText("Fut / Capacité");
                ligneEnTete.addView(texteFut, marge);
                TextView texteQuantite = new TextView(getContext());
                texteQuantite.setText("Quantité");
                ligneEnTete.addView(texteQuantite, marge);
                tableauRecipientSuivant.addView(ligneEnTete);
                TableRow ligneElement = new TableRow(getContext());
                ArrayAdapter<String> adapteurListeFutSansBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style);
                adapteurListeFutSansBrassin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listeFutSansBrassin = TableFut.instance(getContext()).recupererFutSansBrassin();
                for (int i = 0; i < listeFutSansBrassin.size(); i++) {
                    adapteurListeFutSansBrassin.add(listeFutSansBrassin.get(i).getNumero() + " / " + listeFutSansBrassin.get(i).getCapacite() + "L");
                }
                spinnerListeFutSansBrassin.setAdapter(adapteurListeFutSansBrassin);
                ligneElement.addView(spinnerListeFutSansBrassin, marge);
                quantiteTransfere = new EditText(getContext());
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
            if (noeud.getNoeudSansBrassin(getContext()) != null) {
                LinearLayout ligneEtatSuivant = new LinearLayout(getContext());
                ligneEtatSuivant.setGravity(Gravity.CENTER);
                btnEtatSuivantSansBrassin.setText(noeud.getNoeudSansBrassin(getContext()).getEtat(getContext()).getTexte());
                ligneEtatSuivant.addView(btnEtatSuivantSansBrassin);
                ligne.addView(cadre(ligneEtatSuivant, " État suivant "));
            }
        }
        tableauCheminBrassin.addView(ligne);
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
        else if (v.equals(texteDateLavageAcide)){
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(dateLavageAcide);
            new DatePickerDialog(getContext(), this, calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if (v.equals(btnAjouterHistorique)) {
            TableHistorique.instance(getContext()).ajouter(ajoutListeHistorique.getSelectedItem() + ajoutHistorique.getText().toString(), System.currentTimeMillis(), -1, cuve.getId(), -1, -1);
            afficherHistorique();
        }
        else if (v.equals(btnEtatSuivantAvecBrassin)) {
            TableCuve.instance(getContext()).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getNoeud(getContext()).getId_noeudAvecBrassin(), System.currentTimeMillis(), cuve.getCommentaireEtat(), cuve.getIdBrassin(), cuve.getActif());
            afficherCheminBrassin();
        }
        else if (v.equals(btnEtatSuivantSansBrassin)) {
            TableCuve.instance(getContext()).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), cuve.getCommentaireEtat(), cuve.getIdBrassin(), cuve.getActif());
            afficherCheminBrassin();
        }
        else if (v.equals(btnTransfere) && spinnerListeFutSansBrassin.getSelectedItemPosition() != Spinner.INVALID_POSITION) {
            int quantite;
            try {
                quantite = Integer.parseInt(quantiteTransfere.getText().toString());
            } catch (Exception e) {
                quantite = 0;
                Toast.makeText(getContext(), "La quantite inscrite ne peut être lu.", Toast.LENGTH_SHORT).show();
            }
            if (quantite != 0) {
                Fut fut = listeFutSansBrassin.get(spinnerListeFutSansBrassin.getSelectedItemPosition());
                Brassin brassin = TableBrassin.instance(getContext()).recupererId(cuve.getIdBrassin());
                //Si la quantite que l'on veut transferer est plus petit à la quantite du brassin
                //ET que cette quantite est plus petite ou égal à la capacite de la cuve alors on creer un nouveau brassin
                if ((brassin.getQuantite() > quantite) && (quantite <= cuve.getCapacite())) {
                    brassin.setQuantite(brassin.getQuantite() - quantite);
                    long idNouveauBrassin = TableBrassin.instance(getContext()).ajouter(getContext(), brassin.getId_brassinPere(), quantite);
                    TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), TableCheminBrassinFut.instance(getContext()).recupererPremierNoeud().getId(), System.currentTimeMillis(), idNouveauBrassin, fut.getDateInspectionToLong(), fut.getActif());
                    parent.invalidate();
                }
                //Si la quantite que l'on veut transferer est plus grande ou égal à la quantite du brassin
                //ET que la quantite du brassin est plus petite ou égal à la capacite de la cuve alors on transfere le brassin
                else if ((brassin.getQuantite() <= quantite) && (brassin.getQuantite() <= cuve.getCapacite())) {
                    TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), TableCheminBrassinFut.instance(getContext()).recupererPremierNoeud().getId(), System.currentTimeMillis(), cuve.getIdBrassin(), fut.getDateInspectionToLong(), fut.getActif());
                    TableCuve.instance(getContext()).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), cuve.getCommentaireEtat(), -1, cuve.getActif());
                    parent.invalidate();
                }
            }
        }
        else if (v.equals(btnVider)) {
            TableCuve.instance(getContext()).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), cuve.getCommentaireEtat(), -1, cuve.getActif());
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
