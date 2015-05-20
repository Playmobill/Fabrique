package fabrique.gestion.Vue;

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

import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.Emplacement;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.Objets.NoeudCuve;
import fabrique.gestion.R;

public class VueCuve extends TableLayout implements View.OnClickListener {

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

    //Chemin du brassin
    private TableLayout tableauCheminBrassin;
    private Button btnEtatSuivantAvecBrassin, btnEtatSuivantSansBrassin, btnTransfere;
    private ArrayList<Fut> listeFutSansBrassin;
    private Spinner spinnerListeFutSansBrassin;

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

        TableRow ligne = new TableRow(contexte);

        tableauDescription = new TableLayout(contexte);
        ligne.addView(cadre(tableauDescription, " Description "));

        tableauHistorique = new TableLayout(getContext());
        ligne.addView(cadre(tableauHistorique, " Historique "));

        initialiser();
        afficher();
        afficherHistorique();

        tableauCheminBrassin = new TableLayout(contexte);
        addView(cadre(tableauCheminBrassin, " Chemin du brassin "));
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
        TextView dateLavageAcide = new TextView(getContext());
        dateLavageAcide.setGravity(Gravity.CENTER_VERTICAL);
        dateLavageAcide.setText("" + cuve.getDateLavageAcideToString());
        if ((System.currentTimeMillis() - cuve.getDateLavageAcide()) >= TableGestion.instance(getContext()).delaiLavageAcide()) {
            dateLavageAcide.setTextColor(Color.RED);
        } else if ((System.currentTimeMillis() - cuve.getDateLavageAcide()) >= (TableGestion.instance(getContext()).avertissementLavageAcide())) {
            dateLavageAcide.setTextColor(Color.rgb(198, 193, 13));
        } else {
            dateLavageAcide.setTextColor(Color.rgb(34, 177, 76));
        }
        layoutDateLavageAcide.addView(dateLavageAcide);
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

        spinnerListeFutSansBrassin = new Spinner(getContext());
    }

    private void afficher() {
        editTitre.setText("" + cuve.getNumero());
        editTitre.setEnabled(false);

        editCapacite.setText("" + cuve.getCapacite());
        editCapacite.setEnabled(false);

        editEmplacement.setSelection(indexEmplacement);
        editEmplacement.setEnabled(false);

        editActif.setChecked(cuve.getActif());
        editActif.setEnabled(false);

        ligneBouton.removeAllViews();
        ligneBouton.addView(btnModifier);
    }

    private void modifier() {
        editTitre.setEnabled(true);
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
                TableCuve.instance(getContext()).modifier(cuve.getId(), numero, capacite, emplacements.get((int) editEmplacement.getSelectedItemId()).getId(), cuve.getDateLavageAcide(), cuve.getIdNoeud(), cuve.getLongDateEtat(), cuve.getCommentaireEtat(), cuve.getIdBrassin(), editActif.isChecked());
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

        NoeudCuve noeud = cuve.getNoeud(getContext());

        TableRow.LayoutParams margeLigneTableau = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        margeLigneTableau.setMargins(5, 5, 5, 5);

        TableRow ligne = new TableRow(getContext());

        LinearLayout ligneEtatActuel = new LinearLayout(getContext());
        ligneEtatActuel.setGravity(Gravity.CENTER);
        ligneEtatActuel.setOrientation(LinearLayout.VERTICAL);
        TextView etatTexteActuel = new TextView(getContext());
        etatTexteActuel.setText("État actuel :");
        ligneEtatActuel.addView(etatTexteActuel);
        TextView etatActuel = new TextView(getContext());
        etatActuel.setText(cuve.getNoeud(getContext()).getEtat(getContext()).getTexte());
        ligneEtatActuel.addView(etatActuel);
        ligne.addView(ligneEtatActuel, margeLigneTableau);

        LinearLayout ligneChoixFutur = new LinearLayout(getContext());
        ligneChoixFutur.setGravity(Gravity.CENTER);
        ligneChoixFutur.setOrientation(LinearLayout.VERTICAL);

        if (cuve.getIdBrassin() != -1) {
            //Si il y a un prochain etat avec brassin dans ce recipient
            if (noeud.getNoeudAvecBrassin(getContext()) != null) {
                TextView etatSuivant = new TextView(getContext());
                etatSuivant.setText("État suivant :");
                ligneChoixFutur.addView(etatSuivant);
                btnEtatSuivantAvecBrassin.setText(noeud.getNoeudAvecBrassin(getContext()).getEtat(getContext()).getTexte());
                ligneChoixFutur.addView(btnEtatSuivantAvecBrassin);
            }

            //Si il y a un prochain etat sans brassin dans ce recipient
            if (noeud.getNoeudSansBrassin(getContext()) != null) {
                TextView recipientSuivant = new TextView(getContext());
                recipientSuivant.setText("Récipient suivant :");
                ligneChoixFutur.addView(recipientSuivant);
                ligneChoixFutur.addView(btnTransfere);
                ArrayAdapter<String> adapteurListeCuveSansBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style);
                adapteurListeCuveSansBrassin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listeFutSansBrassin = TableFut.instance(getContext()).recupererFutSansBrassin();
                for (int i = 0; i < listeFutSansBrassin.size(); i++) {
                    adapteurListeCuveSansBrassin.add("" + listeFutSansBrassin.get(i).getNumero());
                }
                spinnerListeFutSansBrassin.setAdapter(adapteurListeCuveSansBrassin);
                ligneChoixFutur.addView(spinnerListeFutSansBrassin);
            }
            //Si il n'y a ni etat suivant avec brassin ni etat suivant sans brassin dans ce recipient
            if ((noeud.getNoeudAvecBrassin(getContext()) == null) && (noeud.getNoeudSansBrassin(getContext()) == null)) {
                TextView recipientSuivant = new TextView(getContext());
                recipientSuivant.setText("Récipient suivant :");
                ligneChoixFutur.addView(recipientSuivant);
                ligneChoixFutur.addView(btnTransfere);
                ArrayAdapter<String> adapteurListeCuveSansBrassin = new ArrayAdapter<>(getContext(), R.layout.spinner_style);
                adapteurListeCuveSansBrassin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listeFutSansBrassin = TableFut.instance(getContext()).recupererFutSansBrassin();
                for (int i = 0; i < listeFutSansBrassin.size(); i++) {
                    adapteurListeCuveSansBrassin.add("" + listeFutSansBrassin.get(i).getNumero());
                }
                spinnerListeFutSansBrassin.setAdapter(adapteurListeCuveSansBrassin);
                ligneChoixFutur.addView(spinnerListeFutSansBrassin);
            }
        } else {
            //Si il y a un prochain etat avec brassin dans ce recipient
            if (noeud.getNoeudSansBrassin(getContext()) != null) {
                TextView etatSuivant = new TextView(getContext());
                etatSuivant.setText("État suivant :");
                ligneChoixFutur.addView(etatSuivant);
                btnEtatSuivantSansBrassin.setText(noeud.getNoeudSansBrassin(getContext()).getEtat(getContext()).getTexte());
                ligneChoixFutur.addView(btnEtatSuivantSansBrassin);
            }
        }
        ligne.addView(ligneChoixFutur, margeLigneTableau);
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
            Fut fut = listeFutSansBrassin.get(spinnerListeFutSansBrassin.getSelectedItemPosition());
            TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), TableCheminBrassinFut.instance(getContext()).recupererPremierNoeud().getId(), System.currentTimeMillis(), cuve.getIdBrassin(), fut.getDateInspectionToLong(), fut.getActif());
            TableCuve.instance(getContext()).modifier(cuve.getId(), cuve.getNumero(), cuve.getCapacite(), cuve.getIdEmplacement(), cuve.getDateLavageAcide(), cuve.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), cuve.getCommentaireEtat(), -1, cuve.getActif());
            parent.invalidate();
        }
    }

    @Override
    public void invalidate() {
        afficherHistorique();
    }
}
