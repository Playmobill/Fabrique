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

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.FragmentListe.FragmentVueFut;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.Objets.NoeudFut;
import fabrique.gestion.R;

public class VueFut extends TableLayout implements View.OnClickListener {

    private FragmentVueFut parent;
    private Fut fut;

    //Description
    private LinearLayout tableauDescription;
    private EditText editTitre, editCapacite;
    private CheckBox editActif;
    private TableRow ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    //Chemin du brassin
    private TableLayout tableauCheminBrassin;
    private Button btnEtatSuivantAvecBrassin, btnEtatSuivantSansBrassin, btnVider;

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

    public VueFut(Context contexte, FragmentVueFut parent, Fut fut) {
        super(contexte);

        this.parent = parent;
        this.fut = fut;

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

        String texteEtat = "Non utilisé";
        if ((fut.getNoeud(getContext()) != null) && (fut.getNoeud(getContext()).getEtat(getContext()) != null)) {
            texteEtat = fut.getNoeud(getContext()).getEtat(getContext()).getTexte();
        }
        
        TableRow ligneEtatDate = new TableRow(getContext());
        TextView etat = new TextView(getContext());
        etat.setText("État : " + texteEtat);

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

        btnEtatSuivantAvecBrassin = new Button(getContext());
        btnEtatSuivantAvecBrassin.setOnClickListener(this);

        btnEtatSuivantSansBrassin = new Button(getContext());
        btnEtatSuivantSansBrassin.setOnClickListener(this);

        btnVider = new Button(getContext());
        btnVider.setText("Vider");
        btnVider.setOnClickListener(this);
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
            TableFut.instance(getContext()).modifier(fut.getId(), numero, capacite, fut.getId_noeud(), fut.getDateEtatToLong(), fut.getId_brassin(), fut.getDateInspectionToLong(), editActif.isChecked());
            afficher();
        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_SHORT).show();
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

    private void afficherCheminBrassin() {
        tableauCheminBrassin.removeAllViews();

        if (((ViewGroup) btnEtatSuivantAvecBrassin.getParent()) != null) {
            ((ViewGroup) btnEtatSuivantAvecBrassin.getParent()).removeAllViews();
        }
        if (((ViewGroup) btnEtatSuivantSansBrassin.getParent()) != null) {
            ((ViewGroup) btnEtatSuivantSansBrassin.getParent()).removeAllViews();
        }
        if (((ViewGroup) btnVider.getParent()) != null) {
            ((ViewGroup) btnVider.getParent()).removeAllViews();
        }

        NoeudFut noeud = fut.getNoeud(getContext());

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
        etatActuel.setText(fut.getNoeud(getContext()).getEtat(getContext()).getTexte());
        ligneEtatActuel.addView(etatActuel);
        ligne.addView(ligneEtatActuel, margeLigneTableau);

        LinearLayout ligneChoixFutur = new LinearLayout(getContext());
        ligneChoixFutur.setGravity(Gravity.CENTER);
        ligneChoixFutur.setOrientation(LinearLayout.VERTICAL);

        if (fut.getId_brassin() != -1) {
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
                TextView vider = new TextView(getContext());
                vider.setText("Vider :");
                ligneChoixFutur.addView(vider);
                ligneChoixFutur.addView(btnVider);
            }
            //Si il n'y a ni etat suivant avec brassin ni etat suivant sans brassin dans ce recipient
            if ((noeud.getNoeudAvecBrassin(getContext()) == null) && (noeud.getNoeudSansBrassin(getContext()) == null)) {
                TextView vider = new TextView(getContext());
                vider.setText("Vider :");
                ligneChoixFutur.addView(vider);
                ligneChoixFutur.addView(btnVider);
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
        else if (v.equals(btnVider)) {
            TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), fut.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), -1, fut.getDateInspectionToLong(), fut.getActif());
            parent.invalidate();
        }
        else if (v.equals(btnEtatSuivantAvecBrassin)) {
            TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), fut.getNoeud(getContext()).getId_noeudAvecBrassin(), System.currentTimeMillis(), fut.getId_brassin(), fut.getDateInspectionToLong(), fut.getActif());
            afficherCheminBrassin();
        }
        else if (v.equals(btnEtatSuivantSansBrassin)) {
            TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), fut.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), fut.getId_brassin(), fut.getDateInspectionToLong(), fut.getActif());
            afficherCheminBrassin();
        }
        else if (v.equals(btnAjouterHistorique)) {
            TableHistorique.instance(getContext()).ajouter(ajoutListeHistorique.getSelectedItem() + ajoutHistorique.getText().toString(), System.currentTimeMillis(), -1, -1, fut.getId(), -1);
            afficherHistorique();
        }
    }

    @Override
    public void invalidate() {
        afficherHistorique();
    }
}