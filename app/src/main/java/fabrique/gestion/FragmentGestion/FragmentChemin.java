package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableCheminBrassinFermenteur;
import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.EtatCuve;
import fabrique.gestion.Objets.EtatFermenteur;
import fabrique.gestion.Objets.EtatFut;
import fabrique.gestion.Objets.NoeudCuve;
import fabrique.gestion.Objets.NoeudFermenteur;
import fabrique.gestion.Objets.NoeudFut;
import fabrique.gestion.R;
import fabrique.gestion.Widget.BoutonEtatCuve;
import fabrique.gestion.Widget.BoutonEtatFermenteur;
import fabrique.gestion.Widget.BoutonEtatFut;
import fabrique.gestion.Widget.BoutonNoeudCuve;
import fabrique.gestion.Widget.BoutonNoeudFermenteur;
import fabrique.gestion.Widget.BoutonNoeudFut;

public class FragmentChemin extends FragmentAmeliore implements View.OnClickListener {

    private Context contexte;

    private LinearLayout ligneChemin;

    private Button btnSupprimerEtatFermenteurAvecBrassin, btnSupprimerEtatCuveAvecBrassin, btnSupprimerEtatFutAvecBrassin;

    private BoutonNoeudFermenteur btnNoeudFermenteurSelectionne;
    private BoutonNoeudCuve btnNoeudCuveSelectionne;
    private BoutonNoeudFut btnNoeudFutSelectionne;

    private BoutonEtatFermenteur btnEtatFermenteurAvecBrassin, btnEtatFermenteurSansBrassin;
    private BoutonEtatCuve btnEtatCuveAvecBrassin, btnEtatCuveSansBrassin;
    private BoutonEtatFut btnEtatFutAvecBrassin, btnEtatFutSansBrassin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        contexte = container.getContext();

        LinearLayout.LayoutParams parametre = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);

        HorizontalScrollView.LayoutParams parametreHorizontalScrollView = new HorizontalScrollView.LayoutParams(HorizontalScrollView.LayoutParams.MATCH_PARENT, HorizontalScrollView.LayoutParams.MATCH_PARENT);

        ScrollView.LayoutParams parametreVerticalScrollView = new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT);

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(contexte);
        horizontalScrollView.setLayoutParams(parametreHorizontalScrollView);
            ScrollView verticalScrollView = new ScrollView(contexte);
            verticalScrollView.setLayoutParams(parametreVerticalScrollView);
                LinearLayout ensemble = new LinearLayout(contexte);
                ensemble.setLayoutParams(parametre);
                    ligneChemin = new LinearLayout(contexte);
                    ligneChemin.setOrientation(LinearLayout.VERTICAL);
                ensemble.addView(ligneChemin, marge);
                    LinearLayout ligneEtat = new LinearLayout(contexte);
                    ligneEtat.setOrientation(LinearLayout.VERTICAL);
                    ligneEtat.addView(cadre(listeEtatFermenteur(), " Liste des états du fermenteur "));
                        btnSupprimerEtatFermenteurAvecBrassin = new Button(contexte);
                        btnSupprimerEtatFermenteurAvecBrassin.setText("Supprimer l'état du fermenteur sélectionné");
                        btnSupprimerEtatFermenteurAvecBrassin.setOnClickListener(this);
                    ligneEtat.addView(btnSupprimerEtatFermenteurAvecBrassin);
                    ligneEtat.addView(cadre(listeEtatCuve(), " Liste des états de la cuve "));
                        btnSupprimerEtatCuveAvecBrassin = new Button(contexte);
                        btnSupprimerEtatCuveAvecBrassin.setText("Supprimer l'état de la cuve sélectionnée");
                        btnSupprimerEtatCuveAvecBrassin.setOnClickListener(this);
                    ligneEtat.addView(btnSupprimerEtatCuveAvecBrassin);
                    ligneEtat.addView(cadre(listeEtatFut(), " Liste des états du fût "));
                        btnSupprimerEtatFutAvecBrassin = new Button(contexte);
                        btnSupprimerEtatFutAvecBrassin.setText("Supprimer l'état du fût sélectionné");
                        btnSupprimerEtatFutAvecBrassin.setOnClickListener(this);
                    ligneEtat.addView(btnSupprimerEtatFutAvecBrassin);
                ensemble.addView(ligneEtat, marge);
            verticalScrollView.addView(ensemble);
        horizontalScrollView.addView(verticalScrollView);

        afficher();

        return horizontalScrollView;
    }

    private void afficher() {
        ligneChemin.removeAllViews();
        ligneChemin.addView(cadre(cheminDansLeFermenteur(), " Dans le fermenteur "));
        ligneChemin.addView(cadre(cheminDansLaCuve(), " Dans la cuve "));
        ligneChemin.addView(cadre(cheminDansLeFut(), " Dans le fût "));
    }

    private RelativeLayout cadre(View view, String texteTitre) {
        RelativeLayout contenant = new RelativeLayout(contexte);

        LinearLayout contourTitre = new LinearLayout(contexte);
        contourTitre.setBackgroundColor(Color.BLACK);
        contourTitre.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContourTitre = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContourTitre.setMargins(10, 1, 0, 0);
        TextView fondTitre = new TextView(contexte);
        fondTitre.setText(texteTitre);
        fondTitre.setTypeface(null, Typeface.BOLD);
        fondTitre.setBackgroundColor(Color.WHITE);

        RelativeLayout contour = new RelativeLayout(contexte);
        contour.setBackgroundColor(Color.BLACK);
        contour.setPadding(1, 1, 1, 1);
        RelativeLayout.LayoutParams parametreContour = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        parametreContour.topMargin=15;
        parametreContour.leftMargin=5;

        TextView titre = new TextView(contexte);
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

    private TableLayout cheminDansLeFermenteur() {
        TableLayout tableau = new TableLayout(contexte);

                //Détermination de l'état avec brassin suivant
                NoeudFermenteur noeudPrecedent = null;
                NoeudFermenteur noeudActuel = TableCheminBrassinFermenteur.instance(contexte).recupererId(TableCheminBrassinFermenteur.instance(contexte).recupererPremierNoeud());
                while(noeudActuel != null) {
                    //ligneEtatAvecBrassin : Ligne vertical qui contiendra 1 état avec brassin et des états sans brassin séparés par une flêche
                    TableRow ligneEtatAvecBrassin = new TableRow(contexte);
                    ligneEtatAvecBrassin.setGravity(Gravity.CENTER);
                    //Ajout de l'état avec brassin
                    ligneEtatAvecBrassin.addView(new BoutonNoeudFermenteur(contexte, this, noeudPrecedent, noeudActuel, true));

                    //Détermination de l'état sans brassin suivant
                    NoeudFermenteur noeudPrecedentSansBrassin = noeudActuel;
                    NoeudFermenteur noeudSuivantSansBrassin = noeudActuel.getNoeudSansBrassin(contexte);
                    //Tant qu'il y a des états sans brassin on continu de les ajouter
                    while(noeudSuivantSansBrassin != null) {
                        //Ajout de la flêche directrice vers la droite
                            ImageView imageDroite = new ImageView(contexte);
                            imageDroite.setImageResource(R.drawable.fleche_droite);
                            imageDroite.setMaxWidth(50);
                            imageDroite.setMaxHeight(50);
                        ligneEtatAvecBrassin.addView(imageDroite);
                        //Ajout de l'état sans brassin
                        ligneEtatAvecBrassin.addView(new BoutonNoeudFermenteur(contexte, this, noeudPrecedentSansBrassin, noeudSuivantSansBrassin, false));
                        //Détermination de l'état sans brassin suivant
                        noeudPrecedentSansBrassin = noeudSuivantSansBrassin;
                        noeudSuivantSansBrassin = noeudSuivantSansBrassin.getNoeudSansBrassin(contexte);
                    }
                    //Ajout de la flêche directrice vers la droite
                        ImageView imageDroite = new ImageView(contexte);
                        imageDroite.setImageResource(R.drawable.fleche_droite);
                        imageDroite.setMaxWidth(50);
                        imageDroite.setMaxHeight(50);
                    ligneEtatAvecBrassin.addView(imageDroite);
                    //Ajout d'un état sans brassin vide pour accueillir un prochain état sans brassin
                    ligneEtatAvecBrassin.addView(new BoutonNoeudFermenteur(contexte, this, noeudPrecedentSansBrassin, null, false));
                tableau.addView(ligneEtatAvecBrassin);

                    //Ajout d'une ligne pour afficher l'image de la flêche directrice vers le bas
                    TableRow ligneImage = new TableRow(contexte);
                        ImageView imageBas = new ImageView(contexte);
                        imageBas.setImageResource(R.drawable.fleche_bas);
                        imageBas.setMaxWidth(50);
                        imageBas.setMaxHeight(50);
                    ligneImage.addView(imageBas);
                tableau.addView(ligneImage);

                //Détermination de l'état avec brassin suivant
                noeudPrecedent = noeudActuel;
                noeudActuel = noeudActuel.getNoeudAvecBrassin(contexte);
            }
            TableRow ligneAjouterEtatAvecBrassin = new TableRow(contexte);

            //Ajout d'un état avec brassin vide pour accueillir un prochain état avec brassin
            ligneAjouterEtatAvecBrassin.addView(new BoutonNoeudFermenteur(contexte, this, noeudPrecedent, null, true));

        tableau.addView(ligneAjouterEtatAvecBrassin);

        return tableau;
    }

    public void setBtnNoeudFermenteurSelectionne(BoutonNoeudFermenteur btnEtatFermenteurAvecBrassinSelectionne) {
        if (this.btnNoeudFermenteurSelectionne != null) {
            this.btnNoeudFermenteurSelectionne.setBackgroundColor(Color.LTGRAY);
            if ((this.btnNoeudFermenteurSelectionne != null) && (this.btnNoeudFermenteurSelectionne.equals(btnEtatFermenteurAvecBrassinSelectionne))) {
                btnEtatFermenteurAvecBrassinSelectionne.setBackgroundColor(Color.LTGRAY);
                this.btnNoeudFermenteurSelectionne = null;
            } else {
                this.btnNoeudFermenteurSelectionne = btnEtatFermenteurAvecBrassinSelectionne;
                this.btnNoeudFermenteurSelectionne.setBackgroundColor(Color.RED);
            }
        } else {
            this.btnNoeudFermenteurSelectionne = btnEtatFermenteurAvecBrassinSelectionne;
            this.btnNoeudFermenteurSelectionne.setBackgroundColor(Color.RED);
        }
    }

    public void ajouterCheminDansFermenteur(long id_noeudPrecedent, boolean avecBrassin) {
        if ((btnEtatFermenteurAvecBrassin != null) && avecBrassin) {
            NoeudFermenteur noeudPrecedent = TableCheminBrassinFermenteur.instance(contexte).recupererId(id_noeudPrecedent);
            long idSuivantSansBrassin = -1;
            if (noeudPrecedent != null) {
                idSuivantSansBrassin = noeudPrecedent.getId_noeudSansBrassin();
            }
            TableCheminBrassinFermenteur.instance(contexte).modifier(id_noeudPrecedent, TableCheminBrassinFermenteur.instance(contexte).ajouter(id_noeudPrecedent, btnEtatFermenteurAvecBrassin.getEtat().getId(), -1, -1), idSuivantSansBrassin);
            afficher();
        } else if ((btnEtatFermenteurSansBrassin != null) && !avecBrassin) {
            NoeudFermenteur noeudPrecedent = TableCheminBrassinFermenteur.instance(contexte).recupererId(id_noeudPrecedent);
            long idSuivantAvecBrassin = -1;
            if (noeudPrecedent != null) {
                idSuivantAvecBrassin = noeudPrecedent.getId_noeudAvecBrassin();
            }
            TableCheminBrassinFermenteur.instance(contexte).modifier(id_noeudPrecedent, idSuivantAvecBrassin, TableCheminBrassinFermenteur.instance(contexte).ajouter(id_noeudPrecedent, btnEtatFermenteurSansBrassin.getEtat().getId(), -1, -1));
            afficher();
        }
    }

    private TableLayout cheminDansLaCuve() {
        TableLayout tableau = new TableLayout(contexte);

        //Détermination de l'état avec brassin suivant
        NoeudCuve noeudPrecedent = null;
        NoeudCuve noeudActuel = TableCheminBrassinCuve.instance(contexte).recupererId(TableCheminBrassinCuve.instance(contexte).recupererPremierNoeud());

        while(noeudActuel != null) {
            //ligneEtatAvecBrassin : Ligne vertical qui contiendra 1 état avec brassin et des états sans brassin séparés par une flêche
            TableRow ligneEtatAvecBrassin = new TableRow(contexte);
            ligneEtatAvecBrassin.setGravity(Gravity.CENTER);
            //Ajout de l'état avec brassin
            ligneEtatAvecBrassin.addView(new BoutonNoeudCuve(contexte, this, noeudPrecedent, noeudActuel, true));

            //Détermination de l'état sans brassin suivant
            NoeudCuve noeudPrecedentSansBrassin = noeudActuel;
            NoeudCuve noeudSuivantSansBrassin = noeudActuel.getNoeudSansBrassin(contexte);
            //Tant qu'il y a des états sans brassin on continu de les ajouter
            while(noeudSuivantSansBrassin != null) {
                //Ajout de la flêche directrice vers la droite
                ImageView imageDroite = new ImageView(contexte);
                imageDroite.setImageResource(R.drawable.fleche_droite);
                imageDroite.setMaxWidth(50);
                imageDroite.setMaxHeight(50);
                ligneEtatAvecBrassin.addView(imageDroite);
                //Ajout de l'état sans brassin
                ligneEtatAvecBrassin.addView(new BoutonNoeudCuve(contexte, this, noeudPrecedentSansBrassin, noeudSuivantSansBrassin, false));
                //Détermination de l'état sans brassin suivant
                noeudPrecedentSansBrassin = noeudSuivantSansBrassin;
                noeudSuivantSansBrassin = noeudSuivantSansBrassin.getNoeudSansBrassin(contexte);
            }
            //Ajout de la flêche directrice vers la droite
            ImageView imageDroite = new ImageView(contexte);
            imageDroite.setImageResource(R.drawable.fleche_droite);
            imageDroite.setMaxWidth(50);
            imageDroite.setMaxHeight(50);
            ligneEtatAvecBrassin.addView(imageDroite);
            //Ajout d'un état sans brassin vide pour accueillir un prochain état sans brassin
            ligneEtatAvecBrassin.addView(new BoutonNoeudCuve(contexte, this, noeudPrecedentSansBrassin, null, false));
            tableau.addView(ligneEtatAvecBrassin);

            //Ajout d'une ligne pour afficher l'image de la flêche directrice vers le bas
            TableRow ligneImage = new TableRow(contexte);
            ImageView imageBas = new ImageView(contexte);
            imageBas.setImageResource(R.drawable.fleche_bas);
            imageBas.setMaxWidth(50);
            imageBas.setMaxHeight(50);
            ligneImage.addView(imageBas);
            tableau.addView(ligneImage);

            //Détermination de l'état avec brassin suivant
            noeudPrecedent = noeudActuel;
            noeudActuel = noeudActuel.getNoeudAvecBrassin(contexte);
        }
        TableRow ligneAjouterEtatAvecBrassin = new TableRow(contexte);

        //Ajout d'un état avec brassin vide pour accueillir un prochain état avec brassin
        ligneAjouterEtatAvecBrassin.addView(new BoutonNoeudCuve(contexte, this, noeudPrecedent, null, true));

        tableau.addView(ligneAjouterEtatAvecBrassin);

        return tableau;
    }

    public void setBtnNoeudCuveSelectionne(BoutonNoeudCuve btnEtatCuveAvecBrassinSelectionne) {
        if (this.btnNoeudCuveSelectionne != null) {
            this.btnNoeudCuveSelectionne.setBackgroundColor(Color.LTGRAY);
            if (this.btnNoeudCuveSelectionne.equals(btnEtatCuveAvecBrassinSelectionne)) {
                btnEtatCuveAvecBrassinSelectionne.setBackgroundColor(Color.LTGRAY);
                this.btnNoeudCuveSelectionne = null;
            } else {
                this.btnNoeudCuveSelectionne = btnEtatCuveAvecBrassinSelectionne;
                this.btnNoeudCuveSelectionne.setBackgroundColor(Color.RED);
            }
        } else {
            this.btnNoeudCuveSelectionne = btnEtatCuveAvecBrassinSelectionne;
            this.btnNoeudCuveSelectionne.setBackgroundColor(Color.RED);
        }
    }

    public void ajouterCheminDansCuve(long id_noeudPrecedent, boolean avecBrassin) {
        if ((btnEtatCuveAvecBrassin != null) && avecBrassin) {
            NoeudCuve noeudPrecedent = TableCheminBrassinCuve.instance(contexte).recupererId(id_noeudPrecedent);
            long idSuivantSansBrassin = -1;
            if (noeudPrecedent != null) {
                idSuivantSansBrassin = noeudPrecedent.getId_noeudSansBrassin();
            }
            TableCheminBrassinCuve.instance(contexte).modifier(id_noeudPrecedent, TableCheminBrassinCuve.instance(contexte).ajouter(id_noeudPrecedent, btnEtatCuveAvecBrassin.getEtat().getId(), -1, -1), idSuivantSansBrassin);
            afficher();
        } else if ((btnEtatCuveSansBrassin != null) && !avecBrassin) {
            NoeudCuve noeudPrecedent = TableCheminBrassinCuve.instance(contexte).recupererId(id_noeudPrecedent);
            long idSuivantAvecBrassin = -1;
            if (noeudPrecedent != null) {
                idSuivantAvecBrassin = noeudPrecedent.getId_noeudAvecBrassin();
            }
            TableCheminBrassinCuve.instance(contexte).modifier(id_noeudPrecedent, idSuivantAvecBrassin, TableCheminBrassinCuve.instance(contexte).ajouter(id_noeudPrecedent, btnEtatCuveSansBrassin.getEtat().getId(), -1, -1));
            afficher();
        }
    }

    private TableLayout cheminDansLeFut() {
        TableLayout tableau = new TableLayout(contexte);

        //Détermination de l'état avec brassin suivant
        NoeudFut noeudPrecedent = null;
        NoeudFut noeudActuel = TableCheminBrassinFut.instance(contexte).recupererId(TableCheminBrassinFut.instance(contexte).recupererPremierNoeud());

        while(noeudActuel != null) {
            //ligneEtatAvecBrassin : Ligne vertical qui contiendra 1 état avec brassin et des états sans brassin séparés par une flêche
            TableRow ligneEtatAvecBrassin = new TableRow(contexte);
            ligneEtatAvecBrassin.setGravity(Gravity.CENTER);
            //Ajout de l'état avec brassin
            ligneEtatAvecBrassin.addView(new BoutonNoeudFut(contexte, this, noeudPrecedent, noeudActuel, true));

            //Détermination de l'état sans brassin suivant
            NoeudFut noeudPrecedentSansBrassin = noeudActuel;
            NoeudFut noeudSuivantSansBrassin = noeudActuel.getNoeudSansBrassin(contexte);
            //Tant qu'il y a des états sans brassin on continu de les ajouter
            while(noeudSuivantSansBrassin != null) {
                //Ajout de la flêche directrice vers la droite
                ImageView imageDroite = new ImageView(contexte);
                imageDroite.setImageResource(R.drawable.fleche_droite);
                imageDroite.setMaxWidth(50);
                imageDroite.setMaxHeight(50);
                ligneEtatAvecBrassin.addView(imageDroite);
                //Ajout de l'état sans brassin
                ligneEtatAvecBrassin.addView(new BoutonNoeudFut(contexte, this, noeudPrecedentSansBrassin, noeudSuivantSansBrassin, false));
                //Détermination de l'état sans brassin suivant
                noeudPrecedentSansBrassin = noeudSuivantSansBrassin;
                noeudSuivantSansBrassin = noeudSuivantSansBrassin.getNoeudSansBrassin(contexte);
            }
            //Ajout de la flêche directrice vers la droite
            ImageView imageDroite = new ImageView(contexte);
            imageDroite.setImageResource(R.drawable.fleche_droite);
            imageDroite.setMaxWidth(50);
            imageDroite.setMaxHeight(50);
            ligneEtatAvecBrassin.addView(imageDroite);
            //Ajout d'un état sans brassin vide pour accueillir un prochain état sans brassin
            ligneEtatAvecBrassin.addView(new BoutonNoeudFut(contexte, this, noeudPrecedentSansBrassin, null, false));
            tableau.addView(ligneEtatAvecBrassin);

            //Ajout d'une ligne pour afficher l'image de la flêche directrice vers le bas
            TableRow ligneImage = new TableRow(contexte);
            ImageView imageBas = new ImageView(contexte);
            imageBas.setImageResource(R.drawable.fleche_bas);
            imageBas.setMaxWidth(50);
            imageBas.setMaxHeight(50);
            ligneImage.addView(imageBas);
            tableau.addView(ligneImage);

            //Détermination de l'état avec brassin suivant
            noeudPrecedent = noeudActuel;
            noeudActuel = noeudActuel.getNoeudAvecBrassin(contexte);
        }
        TableRow ligneAjouterEtatAvecBrassin = new TableRow(contexte);

        //Ajout d'un état avec brassin vide pour accueillir un prochain état avec brassin
        ligneAjouterEtatAvecBrassin.addView(new BoutonNoeudFut(contexte, this, noeudPrecedent, null, true));

        tableau.addView(ligneAjouterEtatAvecBrassin);

        return tableau;
    }

    public void setBtnNoeudFutSelectionne(BoutonNoeudFut btnEtatFutAvecBrassinSelectionne) {
        if (this.btnNoeudFutSelectionne != null) {
            this.btnNoeudFutSelectionne.setBackgroundColor(Color.LTGRAY);
            if (this.btnNoeudFutSelectionne.equals(btnEtatFutAvecBrassinSelectionne)) {
                btnEtatFutAvecBrassinSelectionne.setBackgroundColor(Color.LTGRAY);
                this.btnNoeudFutSelectionne = null;
            } else {
                this.btnNoeudFutSelectionne = btnEtatFutAvecBrassinSelectionne;
                this.btnNoeudFutSelectionne.setBackgroundColor(Color.RED);
            }
        } else {
            this.btnNoeudFutSelectionne = btnEtatFutAvecBrassinSelectionne;
            this.btnNoeudFutSelectionne.setBackgroundColor(Color.RED);
        }
    }

    public void ajouterCheminDansFut(long id_noeudPrecedent, boolean avecBrassin) {
        if ((btnEtatFutAvecBrassin != null) && avecBrassin) {
            NoeudFut noeudPrecedent = TableCheminBrassinFut.instance(contexte).recupererId(id_noeudPrecedent);
            long idSuivantSansBrassin = -1;
            if (noeudPrecedent != null) {
                idSuivantSansBrassin = noeudPrecedent.getId_noeudSansBrassin();
            }
            TableCheminBrassinFut.instance(contexte).modifier(id_noeudPrecedent, TableCheminBrassinFut.instance(contexte).ajouter(id_noeudPrecedent, btnEtatFutAvecBrassin.getEtat().getId(), -1, -1), idSuivantSansBrassin);
            afficher();
        } else if ((btnEtatFutSansBrassin != null) && !avecBrassin) {
            NoeudFut noeudPrecedent = TableCheminBrassinFut.instance(contexte).recupererId(id_noeudPrecedent);
            long idSuivantAvecBrassin = -1;
            if (noeudPrecedent != null) {
                idSuivantAvecBrassin = noeudPrecedent.getId_noeudAvecBrassin();
            }
            TableCheminBrassinFut.instance(contexte).modifier(id_noeudPrecedent, idSuivantAvecBrassin, TableCheminBrassinFut.instance(contexte).ajouter(id_noeudPrecedent, btnEtatFutSansBrassin.getEtat().getId(), -1, -1));
            afficher();
        }
    }

    private LinearLayout listeEtatFermenteur() {
        LinearLayout ligne = new LinearLayout(contexte);
        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);
        ligne.addView(listeEtatFermenteurAvecBrassin(), marge);
        ligne.addView(listeEtatFermenteurSansBrassin(), marge);
        return ligne;
    }

    private LinearLayout listeEtatFermenteurAvecBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
            TextView texteAvecBrassin = new TextView(contexte);
            texteAvecBrassin.setText("État du fermenteur avec brassin");
        ligne.addView(texteAvecBrassin);
        ArrayList<EtatFermenteur> listeEtat = TableEtatFermenteur.instance(contexte).recupererListeEtatsActifsAvecBrassin();
        for (int i=0; i<listeEtat.size(); i++) {
            ligne.addView(new BoutonEtatFermenteur(contexte, this, listeEtat.get(i)));
        }
        return ligne;
    }

    public void setBtnEtatFermenteurAvecBrassin(BoutonEtatFermenteur btnEtatFermenteurAvecBrassin) {
        if (this.btnEtatFermenteurAvecBrassin != null) {
            this.btnEtatFermenteurAvecBrassin.setBackgroundColor(Color.LTGRAY);
            if (this.btnEtatFermenteurAvecBrassin.equals(btnEtatFermenteurAvecBrassin)) {
                btnEtatFermenteurAvecBrassin.setBackgroundColor(Color.LTGRAY);
                this.btnEtatFermenteurAvecBrassin = null;
            } else {
                this.btnEtatFermenteurAvecBrassin = btnEtatFermenteurAvecBrassin;
                this.btnEtatFermenteurAvecBrassin.setBackgroundColor(Color.RED);
            }
        } else {
            this.btnEtatFermenteurAvecBrassin = btnEtatFermenteurAvecBrassin;
            this.btnEtatFermenteurAvecBrassin.setBackgroundColor(Color.RED);
        }
    }

    private LinearLayout listeEtatFermenteurSansBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
            TextView texteSansBrassin = new TextView(contexte);
            texteSansBrassin.setText("État du fermenteur sans brassin");
        ligne.addView(texteSansBrassin);
        ArrayList<EtatFermenteur> listeEtat = TableEtatFermenteur.instance(contexte).recupererListeEtatsActifsSansBrassin();
        for (int i=0; i<listeEtat.size(); i++) {
            ligne.addView(new BoutonEtatFermenteur(contexte, this, listeEtat.get(i)));
        }
        return ligne;
    }

    public void setBtnEtatFermenteurSansBrassin(BoutonEtatFermenteur btnEtatFermenteurSansBrassin) {
        if (this.btnEtatFermenteurSansBrassin != null) {
            this.btnEtatFermenteurSansBrassin.setBackgroundColor(Color.LTGRAY);
            if (this.btnEtatFermenteurSansBrassin.equals(btnEtatFermenteurSansBrassin)) {
                btnEtatFermenteurSansBrassin.setBackgroundColor(Color.LTGRAY);
                this.btnEtatFermenteurSansBrassin = null;
            } else {
                this.btnEtatFermenteurSansBrassin = btnEtatFermenteurSansBrassin;
                this.btnEtatFermenteurSansBrassin.setBackgroundColor(Color.BLUE);
            }
        } else {
            this.btnEtatFermenteurSansBrassin = btnEtatFermenteurSansBrassin;
            this.btnEtatFermenteurSansBrassin.setBackgroundColor(Color.BLUE);
        }
    }

    private LinearLayout listeEtatCuve() {
        LinearLayout ligne = new LinearLayout(contexte);
        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);
        ligne.addView(listeEtatCuveAvecBrassin(), marge);
        ligne.addView(listeEtatCuveSansBrassin(), marge);
        return ligne;
    }

    private LinearLayout listeEtatCuveAvecBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteAvecBrassin = new TextView(contexte);
        texteAvecBrassin.setText("État de la cuve avec brassin");
        ligne.addView(texteAvecBrassin);
        ArrayList<EtatCuve> listeEtat = TableEtatCuve.instance(contexte).recupererListeEtatsActifsAvecBrassin();
        for (int i=0; i<listeEtat.size(); i++) {
            ligne.addView(new BoutonEtatCuve(contexte, this, listeEtat.get(i)));
        }
        return ligne;
    }

    public void setBtnEtatCuveAvecBrassin(BoutonEtatCuve btnEtatCuveAvecBrassin) {
        if (this.btnEtatCuveAvecBrassin != null) {
            this.btnEtatCuveAvecBrassin.setBackgroundColor(Color.LTGRAY);
            if (this.btnEtatCuveAvecBrassin.equals(btnEtatCuveAvecBrassin)) {
                btnEtatCuveAvecBrassin.setBackgroundColor(Color.LTGRAY);
                this.btnEtatCuveAvecBrassin = null;
            } else {
                this.btnEtatCuveAvecBrassin = btnEtatCuveAvecBrassin;
                this.btnEtatCuveAvecBrassin.setBackgroundColor(Color.RED);
            }
        } else {
            this.btnEtatCuveAvecBrassin = btnEtatCuveAvecBrassin;
            this.btnEtatCuveAvecBrassin.setBackgroundColor(Color.RED);
        }
    }

    private LinearLayout listeEtatCuveSansBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteSansBrassin = new TextView(contexte);
        texteSansBrassin.setText("État de la cuve sans brassin");
        ligne.addView(texteSansBrassin);
        ArrayList<EtatCuve> listeEtat = TableEtatCuve.instance(contexte).recupererListeEtatsActifsSansBrassin();
        for (int i=0; i<listeEtat.size(); i++) {
            ligne.addView(new BoutonEtatCuve(contexte, this, listeEtat.get(i)));
        }
        return ligne;
    }

    public void setBtnEtatCuveSansBrassin(BoutonEtatCuve btnEtatCuveSansBrassin) {
        if (this.btnEtatCuveSansBrassin != null) {
            this.btnEtatCuveSansBrassin.setBackgroundColor(Color.LTGRAY);
            if (this.btnEtatCuveSansBrassin.equals(btnEtatCuveSansBrassin)) {
                btnEtatCuveSansBrassin.setBackgroundColor(Color.LTGRAY);
                this.btnEtatCuveSansBrassin = null;
            } else {
                this.btnEtatCuveSansBrassin = btnEtatCuveSansBrassin;
                this.btnEtatCuveSansBrassin.setBackgroundColor(Color.BLUE);
            }
        } else {
            this.btnEtatCuveSansBrassin = btnEtatCuveSansBrassin;
            this.btnEtatCuveSansBrassin.setBackgroundColor(Color.BLUE);
        }
    }

    private LinearLayout listeEtatFut() {
        LinearLayout ligne = new LinearLayout(contexte);
        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 10, 10, 10);
        ligne.addView(listeEtatFutAvecBrassin(), marge);
        ligne.addView(listeEtatFutSansBrassin(), marge);
        return ligne;
    }

    private LinearLayout listeEtatFutAvecBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteAvecBrassin = new TextView(contexte);
        texteAvecBrassin.setText("État du fût avec brassin");
        ligne.addView(texteAvecBrassin);
        ArrayList<EtatFut> listeEtat = TableEtatFut.instance(contexte).recupererListeEtatsActifsAvecBrassin();
        for (int i=0; i<listeEtat.size(); i++) {
            ligne.addView(new BoutonEtatFut(contexte, this, listeEtat.get(i)));
        }
        return ligne;
    }

    public void setBtnEtatFutAvecBrassin(BoutonEtatFut btnEtatFutAvecBrassin) {
        if (this.btnEtatFutAvecBrassin != null) {
            this.btnEtatFutAvecBrassin.setBackgroundColor(Color.LTGRAY);
            if (this.btnEtatFutAvecBrassin.equals(btnEtatFutAvecBrassin)) {
                btnEtatFutAvecBrassin.setBackgroundColor(Color.LTGRAY);
                this.btnEtatFutAvecBrassin = null;
            } else {
                this.btnEtatFutAvecBrassin = btnEtatFutAvecBrassin;
                this.btnEtatFutAvecBrassin.setBackgroundColor(Color.RED);
            }
        } else {
            this.btnEtatFutAvecBrassin = btnEtatFutAvecBrassin;
            this.btnEtatFutAvecBrassin.setBackgroundColor(Color.RED);
        }
    }

    private LinearLayout listeEtatFutSansBrassin() {
        LinearLayout ligne = new LinearLayout(contexte);
        ligne.setOrientation(LinearLayout.VERTICAL);
        TextView texteSansBrassin = new TextView(contexte);
        texteSansBrassin.setText("État du fût sans brassin");
        ligne.addView(texteSansBrassin);
        ArrayList<EtatFut> listeEtat = TableEtatFut.instance(contexte).recupererListeEtatsActifsSansBrassin();
        for (int i=0; i<listeEtat.size(); i++) {
            ligne.addView(new BoutonEtatFut(contexte, this, listeEtat.get(i)));
        }
        return ligne;
    }

    public void setBtnEtatFutSansBrassin(BoutonEtatFut btnEtatFutSansBrassin) {
        if (this.btnEtatFutSansBrassin != null) {
            this.btnEtatFutSansBrassin.setBackgroundColor(Color.LTGRAY);
            if (this.btnEtatFutSansBrassin.equals(btnEtatFutSansBrassin)) {
                btnEtatFutSansBrassin.setBackgroundColor(Color.LTGRAY);
                this.btnEtatFutSansBrassin = null;
            } else {
                this.btnEtatFutSansBrassin = btnEtatFutSansBrassin;
                this.btnEtatFutSansBrassin.setBackgroundColor(Color.BLUE);
            }
        } else {
            this.btnEtatFutSansBrassin = btnEtatFutSansBrassin;
            this.btnEtatFutSansBrassin.setBackgroundColor(Color.BLUE);
        }
    }

    @Override
    public void onClick(View v) {
        if ((btnNoeudFermenteurSelectionne != null) && (v.equals(btnSupprimerEtatFermenteurAvecBrassin))) {
            TableCheminBrassinFermenteur.instance(contexte).supprimer(btnNoeudFermenteurSelectionne.getNoeudActif().getId());
            afficher();
        } else if ((btnNoeudCuveSelectionne != null) && (v.equals(btnSupprimerEtatCuveAvecBrassin))) {
            TableCheminBrassinCuve.instance(contexte).supprimer(btnNoeudCuveSelectionne.getNoeudActif().getId());
            afficher();
        } else if ((btnNoeudFutSelectionne != null) && (v.equals(btnSupprimerEtatFutAvecBrassin))) {
            TableCheminBrassinFut.instance(contexte).supprimer(btnNoeudFutSelectionne.getNoeudActif().getId());
            afficher();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void invalidate() {

    }
}
