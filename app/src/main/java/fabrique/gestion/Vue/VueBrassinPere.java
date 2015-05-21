package fabrique.gestion.Vue;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.Objets.BrassinPere;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;

public class VueBrassinPere extends LinearLayout {

    private BrassinPere brassin;

    //Description
    private LinearLayout tableauDescription;
    private EditText editNumero, editQuantite, editRecette, editCommentaire, editDensiteOriginale, editDensiteFinale;
    private TextView editPourcentageAlcool;

    //DatePicker
    private EditText editDateCreation;

    //Historique
    private TableLayout tableauHistorique;

    //Ajouter historique
    private TableRow ligneAjouter;

    public VueBrassinPere(Context contexte) {
        super(contexte);
    }

    public VueBrassinPere(Context contexte, BrassinPere brassin) {
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

        TableRow ligneRecetteQuantite = new TableRow(getContext());
        LinearLayout layoutRecette = new LinearLayout(getContext());
        TextView recette = new TextView(getContext());
        recette.setText("Recette : ");

        editRecette = new EditText(getContext());

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


        ligneAjouter.addView(sous_ligneAjouter);
    }

    private void afficher() {
        editNumero.setText("" + brassin.getNumero());
        editNumero.setEnabled(false);

        editDateCreation.setText(DateToString.dateToString(brassin.getDateLong()));
        editDateCreation.setEnabled(false);

        editRecette.setText(brassin.getRecette(getContext()).getNom());
        editRecette.setEnabled(false);

        editQuantite.setText("" + brassin.getQuantite());
        editQuantite.setEnabled(false);

        editCommentaire.setText(brassin.getCommentaire());
        editCommentaire.setEnabled(false);

        editDensiteOriginale.setText("" + brassin.getDensiteOriginale());
        editDensiteOriginale.setEnabled(false);

        editDensiteFinale.setText("" + brassin.getDensiteFinale());
        editDensiteFinale.setEnabled(false);

        editPourcentageAlcool.setText("" + brassin.getPourcentageAlcool());
    }

    private void afficherHistorique() {
        TableLayout.LayoutParams margeTableau = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        margeTableau.setMargins(10, 0, 10, 0);

        tableauHistorique.removeAllViews();
        tableauHistorique.addView(ligneAjouter);
        ArrayList<Historique> historiques  = TableHistorique.instance(getContext()).recupererSelonIdBrassin(brassin.getId());
        for (int i=0; i<historiques.size() ; i++) {
            tableauHistorique.addView(new LigneHistorique(getContext(), this, historiques.get(i)), margeTableau);
        }
    }

    @Override
    public void invalidate() {}
}
