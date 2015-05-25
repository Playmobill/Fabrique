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

import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.FragmentListe.FragmentVueFut;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.Objets.Historique;
import fabrique.gestion.Objets.ListeHistorique;
import fabrique.gestion.Objets.NoeudFut;
import fabrique.gestion.R;

public class VueFut extends TableLayout implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private FragmentVueFut parent;
    private Fut fut;

    //Description
    private LinearLayout tableauDescription;
    private EditText editTitre, editCapacite;
    private CheckBox editActif;
    private TableRow ligneBouton;
    private Button btnModifier, btnValider, btnAnnuler;

    //DatePicker
    private long dateInspection;
    private TextView texteDateInspection;

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

        TableRow ligneTitreInspection = new TableRow(getContext());
        LinearLayout layoutTitre = new LinearLayout(getContext());
        TextView titre = new TextView(getContext());
        titre.setText("Fut ");
        titre.setTypeface(null, Typeface.BOLD);

        editTitre = new EditText(getContext());
        editTitre.setInputType(InputType.TYPE_CLASS_NUMBER);
        editTitre.setEnabled(false);

        texteDateInspection = new TextView(getContext());
        texteDateInspection.setFocusable(false);
        texteDateInspection.setOnClickListener(this);
        texteDateInspection.setGravity(Gravity.CENTER_VERTICAL);

        TableRow ligneCapacite = new TableRow(getContext());
        LinearLayout layoutCapacite = new LinearLayout(getContext());
        TextView capacite = new TextView(getContext());
        capacite.setText("Capacité : ");

        editCapacite = new EditText(getContext());
        editCapacite.setInputType(InputType.TYPE_CLASS_NUMBER);
        editCapacite.setEnabled(false);

        String texteEtat = "État non défini";
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
        ligneTitreInspection.addView(texteDateInspection, parametre);
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

        dateInspection = fut.getDateInspectionToLong();
        afficherDateInspection();
        texteDateInspection.setEnabled(false);
        
        editCapacite.setEnabled(false);
        editCapacite.setText("" + fut.getCapacite());
        
        editActif.setChecked(fut.getActif());
        editActif.setEnabled(false);
        
        ligneBouton.removeAllViews();
        ligneBouton.addView(btnModifier);
    }

    private void afficherDateInspection() {
        texteDateInspection.setText(DateToString.dateToString(dateInspection));
        if ((System.currentTimeMillis() - dateInspection) >= TableGestion.instance(getContext()).delaiInspectionBaril()) {
            texteDateInspection.setTextColor(Color.RED);
        } else if ((System.currentTimeMillis() - dateInspection) >= (TableGestion.instance(getContext()).avertissementInspectionBaril())) {
            texteDateInspection.setTextColor(Color.rgb(198, 193, 13));
        } else {
            texteDateInspection.setTextColor(Color.rgb(34, 177, 76));
        }
    }

    private void modifier() {
        editTitre.setEnabled(true);
        texteDateInspection.setEnabled(true);
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
            if ((!editActif.isChecked()) && (TableHistorique.instance(getContext()).recupererSelonIdFermenteur(fut.getId()).size() == 0)) {
                TableFut.instance(getContext()).supprimer(fut.getId());
                parent.invalidate();
            } else {
                if(dateInspection != fut.getDateInspectionToLong()){
                    String texteTransfert = TableListeHistorique.instance(getContext()).recupererId(5).getTexte();
                    TableHistorique.instance(getContext()).ajouter(texteTransfert, dateInspection, 0, 0, fut.getId(), 0);
                }
                TableFut.instance(getContext()).modifier(fut.getId(), numero, capacite, fut.getId_noeud(), fut.getDateEtatToLong(), fut.getId_brassin(), dateInspection, editActif.isChecked());
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
        ArrayList<Historique> historiques  = TableHistorique.instance(getContext()).recupererSelonIdFut(fut.getId());
        for (int i=0; i<historiques.size() ; i++) {
            tableauHistorique.addView(new LigneHistorique(getContext(), this, historiques.get(i)), margeTableau);
        }
    }

    private void afficherCheminBrassin() {
        tableauCheminBrassin.removeAllViews();

        if (fut.getId_noeud() != -1) {
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

            TableRow ligne = new TableRow(getContext());

            if (fut.getId_brassin() != -1) {
                //Si il y a un prochain etat avec brassin dans ce recipient
                if (noeud.getNoeudAvecBrassin(getContext()) != null) {
                    LinearLayout ligneEtatSuivant = new LinearLayout(getContext());
                    ligneEtatSuivant.setGravity(Gravity.CENTER);
                    btnEtatSuivantAvecBrassin.setText(noeud.getNoeudAvecBrassin(getContext()).getEtat(getContext()).getTexte());
                    ligneEtatSuivant.addView(btnEtatSuivantAvecBrassin);
                    ligne.addView(cadre(ligneEtatSuivant, " État suivant "));
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
        else if (v.equals(texteDateInspection)){
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            new DatePickerDialog(getContext(), this, calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if (v.equals(btnVider)) {
            TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), fut.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), -1, fut.getDateInspectionToLong(), fut.getActif());

            if (fut.getId_noeud() != -1) {
                String historique = fut.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                if ((historique != null) && (!historique.equals(""))) {
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                    TableHistorique.instance(getContext()).ajouter(historique, date, -1, -1, fut.getId(), -1);
                    afficherHistorique();
                }
            }

            parent.invalidate();
        }
        else if (v.equals(btnEtatSuivantAvecBrassin)) {
            TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), fut.getNoeud(getContext()).getId_noeudAvecBrassin(), System.currentTimeMillis(), fut.getId_brassin(), fut.getDateInspectionToLong(), fut.getActif());
            afficher();
            afficherCheminBrassin();

            if (fut.getId_noeud() != -1) {
                String historique = fut.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                if ((historique != null) && (!historique.equals(""))) {
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                    TableHistorique.instance(getContext()).ajouter(historique, date, -1, -1, fut.getId(), -1);
                    afficherHistorique();
                }
            }
        }
        else if (v.equals(btnEtatSuivantSansBrassin)) {
            TableFut.instance(getContext()).modifier(fut.getId(), fut.getNumero(), fut.getCapacite(), fut.getNoeud(getContext()).getId_noeudSansBrassin(), System.currentTimeMillis(), fut.getId_brassin(), fut.getDateInspectionToLong(), fut.getActif());
            afficher();
            afficherCheminBrassin();

            if (fut.getId_noeud() != -1) {
                String historique = fut.getNoeud(getContext()).getEtat(getContext()).getHistorique();
                if ((historique != null) && (!historique.equals(""))) {
                    Calendar calendrier = Calendar.getInstance();
                    calendrier.setTimeInMillis(System.currentTimeMillis());
                    long date = new GregorianCalendar(calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).getTimeInMillis();
                    TableHistorique.instance(getContext()).ajouter(historique, date, -1, -1, fut.getId(), -1);
                    afficherHistorique();
                }
            }
        }
        else if (v.equals(btnAjouterHistorique)) {
            TableHistorique.instance(getContext()).ajouter(ajoutListeHistorique.getSelectedItem() + ajoutHistorique.getText().toString(), System.currentTimeMillis(), -1, -1, fut.getId(), -1);
            afficherHistorique();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
        dateInspection = new GregorianCalendar(annee, mois, jour).getTimeInMillis();
        afficherDateInspection();
    }

    @Override
    public void invalidate() {
        afficherHistorique();
    }
}