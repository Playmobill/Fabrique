package fabrique.gestion.Vue;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import fabrique.gestion.BDD.TableBrassinPere;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.BrassinPere;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.Objets.Recette;
import fabrique.gestion.R;

public class VueBrassin extends LinearLayout implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Brassin brassin;

    //Description
    private LinearLayout tableauDescription;
    private Spinner editRecette;
    private ArrayList<Recette> listeRecetteActifs;
    private int indexRecette;
    private EditText editNumero, editQuantite, editCommentaire, editDensiteOriginale, editDensiteFinale;
    private TextView editPourcentageAlcool;
    private LinearLayout layoutBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    //DatePicker
    private long longDateCreation;
    private EditText editDateCreation;

    //Historique
    private TableLayout tableauHistorique;

    //Ajouter historique
    private TableRow ligneAjouter;
    private Spinner ajoutListeHistorique;
    private EditText ajoutHistorique;
    private TextView btnAjouterHistorique;

    public VueBrassin(Context contexte) {
        super(contexte);
    }

    public VueBrassin(Context contexte, Brassin brassin) {
        super(contexte);

        this.brassin = brassin;

        LinearLayout ligne = new LinearLayout(contexte);

        tableauDescription = new TableLayout(getContext());
        initialiser();
        ligne.addView(cadre(tableauDescription, " Description "));
        afficher();

        tableauHistorique = new TableLayout(getContext());
        ligne.addView(cadre(tableauHistorique, " Historique "));
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
        TableRow.LayoutParams marge = new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);

        TableRow ligneNumeroDateCreation = new TableRow(getContext());
            LinearLayout layoutNumero = new LinearLayout(getContext());
                TextView numero = new TextView(getContext());
                numero.setText("Brassin ");
                numero.setTypeface(null, Typeface.BOLD);

                editNumero = new EditText(getContext());
                editNumero.setInputType(InputType.TYPE_CLASS_NUMBER);
                editNumero.setTypeface(null, Typeface.BOLD);

            LinearLayout layoutDateCreation = new LinearLayout(getContext());
                TextView dateCreation = new TextView(getContext());
                dateCreation.setText("Date de création ");

                editDateCreation = new EditText(getContext());
                editDateCreation.setFocusable(false);
                editDateCreation.setOnClickListener(this);

        TableRow ligneRecetteQuantite = new TableRow(getContext());
            LinearLayout layoutRecette = new LinearLayout(getContext());
                TextView recette = new TextView(getContext());
                recette.setText("Recette : ");

                listeRecetteActifs = TableRecette.instance(getContext()).recupererRecetteActif();
                editRecette = new Spinner(getContext());
                    ArrayAdapter<String> adapteurRecette = new ArrayAdapter<>(getContext(), R.layout.spinner_style, TableRecette.instance(getContext()).recupererNomRecettesActifs());
                    adapteurRecette.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                editRecette.setAdapter(adapteurRecette);
                editRecette.setEnabled(false);
                indexRecette = -1;
                ArrayList<String> recettes = TableRecette.instance(getContext()).recupererNomRecettesActifs();
                for (int i=0; i<recettes.size() ; i++) {
                    if (brassin.getRecette(getContext()).getId() == TableRecette.instance(getContext()).recupererIndex(i).getId()) {
                        indexRecette = i;
                    }
                }
                if (indexRecette != -1) {
                    editRecette.setSelection(indexRecette);
                } else {
                    recettes.add(brassin.getRecette(getContext()).getNom());
                    adapteurRecette.add(TableRecette.instance(getContext()).recupererId(brassin.getRecette(getContext()).getId()).getNom());
                    editRecette.setSelection(editRecette.getLastVisiblePosition());
                }

            LinearLayout layoutQuantite = new LinearLayout(getContext());
                TextView quantite = new TextView(getContext());
                quantite.setText("Quantité : ");

                editQuantite = new EditText(getContext());
                editQuantite.setInputType(InputType.TYPE_CLASS_NUMBER);

        LinearLayout ligneCommentaire = new LinearLayout(getContext());
            LinearLayout layoutCommentaire = new LinearLayout(getContext());
                TextView commentaire = new TextView(getContext());
                commentaire.setText("Commentaire : ");

                editCommentaire = new EditText(getContext());

        TableRow ligneDensite = new TableRow(getContext());
            LinearLayout layoutDensiteOriginale = new LinearLayout(getContext());
                TextView densiteOriginale = new TextView(getContext());
                densiteOriginale.setText("Densité originale : ");

                editDensiteOriginale = new EditText(getContext());
                editDensiteOriginale.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            LinearLayout layoutDensiteFinale = new LinearLayout(getContext());
                TextView densiteFinale = new TextView(getContext());
                densiteFinale.setText("Densité finale : ");

                editDensiteFinale = new EditText(getContext());
                editDensiteFinale.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        TableRow lignePourcentageAlcool = new TableRow(getContext());
            LinearLayout layoutPourcentageAlcool = new LinearLayout(getContext());
                TextView pourcentageAlcool = new TextView(getContext());
                pourcentageAlcool.setText("%Alc/vol : ");

                editPourcentageAlcool = new TextView(getContext());

        layoutBouton = new LinearLayout(getContext());
            btnModifier = new Button(getContext());
            btnModifier.setLayoutParams(marge);
            btnModifier.setText("Modifier");
            btnModifier.setOnClickListener(this);

            btnValider = new Button(getContext());
            btnValider.setLayoutParams(marge);
            btnValider.setText("Valider");
            btnValider.setOnClickListener(this);

            btnAnnuler = new Button(getContext());
            btnAnnuler.setLayoutParams(marge);
            btnAnnuler.setText("Annuler");
            btnAnnuler.setOnClickListener(this);

        tableauDescription.addView(ligneNumeroDateCreation);
            ligneNumeroDateCreation.addView(layoutNumero, marge);
                layoutNumero.addView(numero);
                layoutNumero.addView(editNumero);
            ligneNumeroDateCreation.addView(layoutDateCreation, marge);
                layoutDateCreation.addView(dateCreation);
                layoutDateCreation.addView(editDateCreation);
        tableauDescription.addView(ligneRecetteQuantite);
            ligneRecetteQuantite.addView(layoutRecette, marge);
                layoutRecette.addView(recette);
                layoutRecette.addView(editRecette);
            ligneRecetteQuantite.addView(layoutQuantite, marge);
                layoutQuantite.addView(quantite);
                layoutQuantite.addView(editQuantite);
        tableauDescription.addView(ligneCommentaire);
            ligneCommentaire.addView(layoutCommentaire, marge);
                layoutCommentaire.addView(commentaire);
                layoutCommentaire.addView(editCommentaire);
        tableauDescription.addView(ligneDensite);
            ligneDensite.addView(layoutDensiteOriginale, marge);
                layoutDensiteOriginale.addView(densiteOriginale);
                layoutDensiteOriginale.addView(editDensiteOriginale);
            ligneDensite.addView(layoutDensiteFinale, marge);
                layoutDensiteFinale.addView(densiteFinale);
                layoutDensiteFinale.addView(editDensiteFinale);
        tableauDescription.addView(lignePourcentageAlcool);
            lignePourcentageAlcool.addView(layoutPourcentageAlcool, marge);
                layoutPourcentageAlcool.addView(pourcentageAlcool);
                layoutPourcentageAlcool.addView(editPourcentageAlcool);
        tableauDescription.addView(layoutBouton);
            layoutBouton.addView(btnModifier, marge);

        ligneAjouter = new TableRow(getContext());
            LinearLayout sous_ligneAjouter = new LinearLayout(getContext());
            ArrayList<ListeHistorique> listeHistoriques = TableListeHistorique.instance(getContext()).listeHistoriqueBrassin();
            String[] tabListeHistorique = new String[listeHistoriques.size()+1];
            tabListeHistorique[0] = "";
            for (int i=0; i<listeHistoriques.size() ; i++) {
                tabListeHistorique[i+1] = listeHistoriques.get(i).getTexte();
            }

        TableRow.LayoutParams margeAjouter = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        margeAjouter.setMargins(10, 2, 10, 2);

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
        ligneAjouter.addView(btnAjouterHistorique, margeAjouter);
    }

    private void afficher() {
        editNumero.setText("" + brassin.getNumero());
        editNumero.setEnabled(false);

        longDateCreation = brassin.getDateLong();
        editDateCreation.setText(DateToString.dateToString(longDateCreation));
        editDateCreation.setEnabled(false);

        editRecette.setEnabled(false);
        editRecette.setSelection(indexRecette);

        editQuantite.setText("" + brassin.getQuantite());
        editQuantite.setEnabled(false);

        editCommentaire.setText(brassin.getCommentaire());
        editCommentaire.setEnabled(false);

        editDensiteOriginale.setText("" + brassin.getDensiteOriginale());
        editDensiteOriginale.setEnabled(false);

        editDensiteFinale.setText("" + brassin.getDensiteFinale());
        editDensiteFinale.setEnabled(false);

        editPourcentageAlcool.setText("" + brassin.getPourcentageAlcool());

        layoutBouton.removeAllViews();
        layoutBouton.addView(btnModifier);
    }

    private void modifier() {
        editNumero.setEnabled(true);

        editDateCreation.setEnabled(true);

        editRecette.setEnabled(true);

        editQuantite.setEnabled(true);

        editCommentaire.setEnabled(true);

        editDensiteOriginale.setEnabled(true);

        editDensiteFinale.setEnabled(true);

        layoutBouton.removeAllViews();

        layoutBouton.addView(btnValider);
        layoutBouton.addView(btnAnnuler);
    }

    private void valider() {
        String erreur = "";

        int numero = 0;
        if (editNumero.getText().toString().equals("")) {
            erreur = erreur + "Le brassin doit avoir un numéro.";
        } else {
            try {
                numero = Integer.parseInt(editNumero.getText().toString());
            } catch (NumberFormatException e) {
                erreur = erreur + "Le numéro est trop grand.";
            }
        }

        int quantite = 0;
        try {
            if (!editQuantite.getText().toString().equals("")) {
                quantite = Integer.parseInt(editQuantite.getText().toString());
            }
        } catch(NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La quantité est trop grande.";
        }

        float densiteOriginale = 0;
        try {
            if ((!editDensiteOriginale.getText().toString().equals(""))) {
                densiteOriginale = Float.parseFloat(editDensiteOriginale.getText().toString());
            }
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La densité originale est trop grande.";
        }

        float densiteFinale = 0;
        try {
            if ((editDensiteFinale.getText() != null) && (!editDensiteFinale.getText().toString().equals(""))) {
                densiteFinale = Float.parseFloat(editDensiteFinale.getText().toString());
            }
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "La densité final est trop grande.";
        }

        float pourcentageAlcool = 0;
        try {
            if ((editDensiteFinale.getText() != null) && (!editDensiteFinale.getText().toString().equals(""))  && (editDensiteOriginale.getText() != null) && (!editDensiteOriginale.getText().toString().equals(""))) {
                pourcentageAlcool = Brassin.convertDensiteVersPourcentageAlcool(Float.parseFloat(editDensiteOriginale.getText().toString()) , Float.parseFloat(editDensiteFinale.getText().toString()));
                editPourcentageAlcool.setText(""+pourcentageAlcool);
            }
        } catch (NumberFormatException e) {
            if (!erreur.equals("")) {
                erreur = erreur + "\n";
            }
            erreur = erreur + "Le pourcentage d'alcool est trop grand.";
        }

        if (erreur.equals("")) {
            if(densiteFinale != brassin.getDensiteFinale()) {
                Calendar calendrier = Calendar.getInstance();
                calendrier.setTimeInMillis(System.currentTimeMillis());
                long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();

                String texteTransfert = TableListeHistorique.instance(getContext()).recupererId(1).getTexte() + " : " + editDensiteFinale.getText().toString();
                TableHistorique.instance(getContext()).ajouter(texteTransfert, date, 0, 0, 0, brassin.getId_brassinPere());
            }

            long recette = listeRecetteActifs.get(editRecette.getSelectedItemPosition()).getId();

            TableBrassin.instance(getContext()).modifier(brassin.getId(), brassin.getId_brassinPere(), numero, editCommentaire.getText().toString(), brassin.getDateLong(), quantite, recette, densiteOriginale, densiteFinale);

            BrassinPere brassinPere = TableBrassinPere.instance(getContext()).recupererId(brassin.getId_brassinPere());

            int quantitePere = brassinPere.getQuantite();
            if(quantitePere <= quantite){
                quantitePere = quantite;
            }
            TableBrassinPere.instance(getContext()).modifier(brassinPere.getId(), numero, editCommentaire.getText().toString(), brassinPere.getDateLong(), quantitePere, brassinPere.getId_recette(), densiteOriginale, densiteFinale);

        } else {
            Toast.makeText(getContext(), erreur, Toast.LENGTH_SHORT).show();
        }
    }

    private void afficherHistorique() {
        TableLayout.LayoutParams margeTableau = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        margeTableau.setMargins(10, 0, 10, 0);

        tableauHistorique.removeAllViews();
        tableauHistorique.addView(ligneAjouter);
        ArrayList<Historique> historiques  = TableHistorique.instance(getContext()).recupererSelonIdBrassin(brassin.getId_brassinPere());
        for (int i=0; i<historiques.size() ; i++) {
            tableauHistorique.addView(new LigneHistorique(getContext(), this, historiques.get(i)), margeTableau);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifier();
        }

        else if (v.equals(btnValider)) {
            valider();
            afficher();
            afficherHistorique();
        }

        else if (v.equals(btnAnnuler)) {
            afficher();
        }

        else if (v.equals(editDateCreation)){
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(longDateCreation);
            new DatePickerDialog(getContext(), this, calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).show();
        }

        else if (v.equals(btnAjouterHistorique)) {
            TableHistorique.instance(getContext()).ajouter(ajoutListeHistorique.getSelectedItem() + ajoutHistorique.getText().toString(), System.currentTimeMillis(), -1, -1, -1, brassin.getId());
            afficherHistorique();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
        longDateCreation = new GregorianCalendar(annee, mois, jour).getTimeInMillis();
        editDateCreation.setText(DateToString.dateToString(longDateCreation));
    }

    @Override
    public void invalidate() {
        afficherHistorique();
    }
}
