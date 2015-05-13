package fabrique.gestion.FragmentSauvegarde;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCalendrier;
import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableCheminBrassinFermenteur;
import fabrique.gestion.BDD.TableCheminBrassinFut;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.BDD.TableTypeBiere;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentSauvegarde extends FragmentAmeliore implements View.OnClickListener {

    private View view;

    private Context contexte;

    private LinearLayout tableau;

    private Button sauvegarde, charger, envoi;

    private LigneSauvegarde ligne;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        contexte = container.getContext();

        ((ActivityAccueil) getActivity()).setVue(this);

        view = inflater.inflate(R.layout.activity_sauvegarde_lecture, container, false);

        initialiser();

        afficherListeFichier();

        return view;
    }

    private void initialiser() {
        LinearLayout bordure = (LinearLayout)view.findViewById(R.id.tableau);
            ScrollView scrollView = new ScrollView(contexte);
                tableau = new LinearLayout(contexte);
                tableau.setOrientation(LinearLayout.VERTICAL);
                tableau.setBackgroundColor(Color.WHITE);
            scrollView.addView(tableau);
        bordure.addView(scrollView);

        sauvegarde = (Button)view.findViewById(R.id.sauvegarde);
        sauvegarde.setOnClickListener(this);

        charger = (Button)view.findViewById(R.id.charger);
        charger.setOnClickListener(this);

        envoi = (Button)view.findViewById(R.id.envoi);
        envoi.setOnClickListener(this);
    }

    private void afficherListeFichier() {
        File[] fichiers = Environment.getExternalStorageDirectory().listFiles();

        LinearLayout.LayoutParams marge = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        marge.setMargins(1, 1, 1, 1);

        tableau.removeAllViews();
        try {
            for (int i=fichiers.length-1; i>=0; i=i-1) {
                if ((!fichiers[i].isDirectory()) && (fichiers[i].getName().contains("Gestion"))) {
                    tableau.addView(new LigneSauvegarde(contexte, this, fichiers[i]), marge);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        ligne = null;
    }

    protected void actif(LigneSauvegarde ligne) {
        if (this.ligne != null) {
            this.ligne.enleverCouleur();
        }
        this.ligne = ligne;
    }

    private void sauvegarder() {
        try {
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            int seconde = calendrier.get(Calendar.MILLISECOND);
            int minute = calendrier.get(Calendar.MINUTE);
            int heure = calendrier.get(Calendar.HOUR_OF_DAY);
            int jour = calendrier.get(Calendar.DAY_OF_MONTH);
            int mois = calendrier.get(Calendar.MONTH) + 1;
            int annee = calendrier.get(Calendar.YEAR);
            File fichier = new File(Environment.getExternalStorageDirectory(), "Gestion_" + annee + "a_" + mois + "m_" + jour + "j_" + heure + "h_" + minute + "m_" + seconde + "ms.bak");
            fichier.createNewFile();
            FileWriter filewriter = new FileWriter(fichier, false);
                filewriter.write(TableEmplacement.instance(contexte).sauvegarde());
                filewriter.write(TableTypeBiere.instance(contexte).sauvegarde());
                filewriter.write(TableRecette.instance(contexte).sauvegarde());
                filewriter.write(TableBrassin.instance(contexte).sauvegarde());
                filewriter.write(TableEtatFermenteur.instance(contexte).sauvegarde());
                filewriter.write(TableFermenteur.instance(contexte).sauvegarde());
                filewriter.write(TableEtatCuve.instance(contexte).sauvegarde());
                filewriter.write(TableCuve.instance(contexte).sauvegarde());
                filewriter.write(TableEtatFut.instance(contexte).sauvegarde());
                filewriter.write(TableFut.instance(contexte).sauvegarde());
                filewriter.write(TableHistorique.instance(contexte).sauvegarde());
                filewriter.write(TableListeHistorique.instance(contexte).sauvegarde());
                filewriter.write(TableGestion.instance(contexte).sauvegarde());
                filewriter.write(TableCheminBrassinFermenteur.instance(contexte).sauvegarde());
                filewriter.write(TableCheminBrassinCuve.instance(contexte).sauvegarde());
                filewriter.write(TableCheminBrassinFut.instance(contexte).sauvegarde());
                filewriter.write(TableCalendrier.instance(contexte).sauvegarde());
                filewriter.close();
            Toast.makeText(contexte, "Sauvegarde réussite dans le fichier : Gestion_" + annee + "a_" + mois + "m_" + jour + "j_" + heure + "h_" + minute + "m_" + seconde + "ms.bak", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(contexte, "Erreur lors de la création du fichier de sauvegarde.", Toast.LENGTH_LONG).show();
        }
    }

    private void supprimer() {
        TableEmplacement.instance(contexte).supprimerToutesLaBdd();
        TableRecette.instance(contexte).supprimerToutesLaBdd();
        TableBrassin.instance(contexte).supprimerToutesLaBdd();
        TableEtatFermenteur.instance(contexte).supprimerToutesLaBdd();
        TableFermenteur.instance(contexte).supprimerToutesLaBdd();
        TableEtatCuve.instance(contexte).supprimerToutesLaBdd();
        TableCuve.instance(contexte).supprimerToutesLaBdd();
        TableEtatFut.instance(contexte).supprimerToutesLaBdd();
        TableFut.instance(contexte).supprimerToutesLaBdd();
        TableHistorique.instance(contexte).supprimerToutesLaBdd();
        TableListeHistorique.instance(contexte).supprimerToutesLaBdd();
    }

    private void charger() {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser analyseur = factory.newPullParser();

            analyseur.setInput(new FileReader(ligne.getFichier()));
            int eventType = analyseur.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                //Si c'est le debut d'une balise
                String nomBalise = analyseur.getName();
                if (nomBalise == null) {
                    nomBalise = "";
                }
                if ((eventType == XmlPullParser.START_TAG) && (nomBalise.charAt(0) == 'O')) {
                    //On switch selon le nom de la balise
                    switch (analyseur.getName()) {
                        case ("O:Emplacement") :
                            eventType = analyseur.next();
                            boolean corrompuEmplacement = false;
                            ArrayList<String> textesEmplacement = new ArrayList<>();
                            String nomBaliseEmplacement = analyseur.getName();
                            if (nomBaliseEmplacement == null) {
                                nomBaliseEmplacement = "";
                            }
                            String texteEmplacement = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Emplacement" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseEmplacement.equals("O:Emplacement"))) && !corrompuEmplacement) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseEmplacement.charAt(0) == 'E')) {
                                    if (texteEmplacement == null) {
                                        texteEmplacement = "";
                                    }
                                    textesEmplacement.add(texteEmplacement);
                                }
                                texteEmplacement = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuEmplacement = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseEmplacement = analyseur.getName();
                                    if (nomBaliseEmplacement == null) {
                                        nomBaliseEmplacement = "";
                                    }
                                }
                            }
                            //Si il n'y a que 2 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesEmplacement.size() == 2) && !corrompuEmplacement) {
                                try {
                                    TableEmplacement.instance(contexte).ajouter(
                                            textesEmplacement.get(0),
                                            Boolean.parseBoolean(textesEmplacement.get(1)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:TypeBiere") :
                            eventType = analyseur.next();
                            boolean corrompuTypeBiere = false;
                            ArrayList<String> textesTypeBiere = new ArrayList<>();
                            String nomBaliseTypeBiere = analyseur.getName();
                            if (nomBaliseTypeBiere == null) {
                                nomBaliseTypeBiere = "";
                            }
                            String texteTypeBiere = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "TypeBiere" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseTypeBiere.equals("O:TypeBiere"))) && !corrompuTypeBiere) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseTypeBiere.charAt(0) == 'E')) {
                                    if (texteTypeBiere == null) {
                                        texteTypeBiere = "";
                                    }
                                    textesTypeBiere.add(texteTypeBiere);
                                }
                                texteTypeBiere = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuTypeBiere = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseTypeBiere = analyseur.getName();
                                    if (nomBaliseTypeBiere == null) {
                                        nomBaliseTypeBiere = "";
                                    }
                                }
                            }
                            //Si il n'y a que 3 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesTypeBiere.size() == 3) && !corrompuTypeBiere) {
                                try {
                                    TableTypeBiere.instance(contexte).ajouter(
                                            textesTypeBiere.get(0),
                                            textesTypeBiere.get(1),
                                            Boolean.parseBoolean(textesTypeBiere.get(2)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:Recette") :
                            eventType = analyseur.next();
                            boolean corrompuRecette = false;
                            ArrayList<String> textesRecette = new ArrayList<>();
                            String nomBaliseRecette = analyseur.getName();
                            if (nomBaliseRecette == null) {
                                nomBaliseRecette = "";
                            }
                            String texteRecette = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Recette" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseRecette.equals("O:Recette"))) && !corrompuRecette) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseRecette.charAt(0) == 'E')) {
                                    if (texteRecette == null) {
                                        texteRecette = "";
                                    }
                                    textesRecette.add(texteRecette);
                                }
                                texteRecette = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuRecette = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseRecette = analyseur.getName();
                                    if (nomBaliseRecette == null) {
                                        nomBaliseRecette = "";
                                    }
                                }
                            }
                            //Si il n'y a que 6 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesRecette.size() == 6) && !corrompuRecette) {
                                try {
                                    TableRecette.instance(contexte).ajouter(
                                            textesRecette.get(0),
                                            textesRecette.get(1),
                                            Integer.parseInt(textesRecette.get(2)),
                                            Integer.parseInt(textesRecette.get(3)),
                                            Integer.parseInt(textesRecette.get(4)),
                                            Boolean.parseBoolean(textesRecette.get(5)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:Brassin") :
                            eventType = analyseur.next();
                            boolean corrompuBrassin = false;
                            ArrayList<String> textesBrassin = new ArrayList<>();
                            String nomBaliseBrassin = analyseur.getName();
                            if (nomBaliseBrassin == null) {
                                nomBaliseBrassin = "";
                            }
                            String texteBrassin = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Brassin" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseBrassin.equals("O:Brassin"))) && !corrompuBrassin) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseBrassin.charAt(0) == 'E')) {
                                    if (texteBrassin == null) {
                                        texteBrassin = "";
                                    }
                                    textesBrassin.add(texteBrassin);
                                }
                                texteBrassin = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuBrassin = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseBrassin = analyseur.getName();
                                    if (nomBaliseBrassin == null) {
                                        nomBaliseBrassin = "";
                                    }
                                }
                            }
                            //Si il n'y a que 7 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesBrassin.size() == 7) && !corrompuBrassin) {
                                try {
                                    TableBrassin.instance(contexte).ajouter(
                                            Integer.parseInt(textesBrassin.get(0)),
                                            textesBrassin.get(1),
                                            Long.parseLong(textesBrassin.get(2)),
                                            Integer.parseInt(textesBrassin.get(3)),
                                            Long.parseLong(textesBrassin.get(4)),
                                            Float.parseFloat(textesBrassin.get(5)),
                                            Float.parseFloat(textesBrassin.get(6)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:EtatFermenteur") :
                            eventType = analyseur.next();
                            boolean corrompuEtatFermenteur = false;
                            ArrayList<String> textesEtatFermenteur = new ArrayList<>();
                            String nomBaliseEtatFermenteur = analyseur.getName();
                            if (nomBaliseEtatFermenteur == null) {
                                nomBaliseEtatFermenteur = "";
                            }
                            String texteEtatFermenteur = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "EtatFermenteur" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseEtatFermenteur.equals("O:EtatFermenteur"))) && !corrompuEtatFermenteur) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseEtatFermenteur.charAt(0) == 'E')) {
                                    if (texteEtatFermenteur == null) {
                                        texteEtatFermenteur = "";
                                    }
                                    textesEtatFermenteur.add(texteEtatFermenteur);
                                }
                                texteEtatFermenteur = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuEtatFermenteur = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseEtatFermenteur = analyseur.getName();
                                    if (nomBaliseEtatFermenteur == null) {
                                        nomBaliseEtatFermenteur = "";
                                    }
                                }
                            }
                            //Si il n'y a que 6 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesEtatFermenteur.size() == 6) && !corrompuEtatFermenteur) {
                                try {
                                    TableEtatFermenteur.instance(contexte).ajouter(
                                            textesEtatFermenteur.get(0),
                                            textesEtatFermenteur.get(1),
                                            Integer.parseInt(textesEtatFermenteur.get(2)),
                                            Integer.parseInt(textesEtatFermenteur.get(3)),
                                            Boolean.parseBoolean(textesEtatFermenteur.get(4)),
                                            Boolean.parseBoolean(textesEtatFermenteur.get(5)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:Fermenteur") :
                            eventType = analyseur.next();
                            boolean corrompuFermenteur = false;
                            ArrayList<String> textesFermenteur = new ArrayList<>();
                            String nomBaliseFermenteur = analyseur.getName();
                            if (nomBaliseFermenteur == null) {
                                nomBaliseFermenteur = "";
                            }
                            String texteFermenteur = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fermenteur" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseFermenteur.equals("O:Fermenteur"))) && !corrompuFermenteur) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseFermenteur.charAt(0) == 'E')) {
                                    if (texteFermenteur == null) {
                                        texteFermenteur = "";
                                    }
                                    textesFermenteur.add(texteFermenteur);
                                }
                                texteFermenteur = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuFermenteur = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseFermenteur = analyseur.getName();
                                    if (nomBaliseFermenteur == null) {
                                        nomBaliseFermenteur = "";
                                    }
                                }
                            }
                            //Si il n'y a que 8 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesFermenteur.size() == 8) && !corrompuFermenteur) {
                                try {
                                    TableFermenteur.instance(contexte).ajouter(
                                            Integer.parseInt(textesFermenteur.get(0)),
                                            Integer.parseInt(textesFermenteur.get(1)),
                                            Long.parseLong(textesFermenteur.get(2)),
                                            Long.parseLong(textesFermenteur.get(3)),
                                            Long.parseLong(textesFermenteur.get(4)),
                                            Long.parseLong(textesFermenteur.get(5)),
                                            Long.parseLong(textesFermenteur.get(6)),
                                            Boolean.parseBoolean(textesFermenteur.get(7)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:EtatCuve") :
                            eventType = analyseur.next();
                            boolean corrompuEtatCuve = false;
                            ArrayList<String> textesEtatCuve = new ArrayList<>();
                            String nomBaliseEtatCuve = analyseur.getName();
                            if (nomBaliseEtatCuve == null) {
                                nomBaliseEtatCuve = "";
                            }
                            String texteEtatCuve = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "EtatCuve" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseEtatCuve.equals("O:EtatCuve"))) && !corrompuEtatCuve) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseEtatCuve.charAt(0) == 'E')) {
                                    if (texteEtatCuve == null) {
                                        texteEtatCuve = "";
                                    }
                                    textesEtatCuve.add(texteEtatCuve);
                                }
                                texteEtatCuve = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuEtatCuve = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseEtatCuve = analyseur.getName();
                                    if (nomBaliseEtatCuve == null) {
                                        nomBaliseEtatCuve = "";
                                    }
                                }
                            }
                            //Si il n'y a que 6 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesEtatCuve.size() == 6) && !corrompuEtatCuve) {
                                try {
                                    TableEtatCuve.instance(contexte).ajouter(
                                            textesEtatCuve.get(0),
                                            textesEtatCuve.get(1),
                                            Integer.parseInt(textesEtatCuve.get(2)),
                                            Integer.parseInt(textesEtatCuve.get(3)),
                                            Boolean.parseBoolean(textesEtatCuve.get(4)),
                                            Boolean.parseBoolean(textesEtatCuve.get(5)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:Cuve") :
                            eventType = analyseur.next();
                            boolean corrompuCuve = false;
                            ArrayList<String> textesCuve = new ArrayList<>();
                            String nomBaliseCuve = analyseur.getName();
                            if (nomBaliseCuve == null) {
                                nomBaliseCuve = "";
                            }
                            String texteCuve = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Cuve" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseCuve.equals("O:Cuve"))) && !corrompuCuve) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseCuve.charAt(0) == 'E')) {
                                    if (texteCuve == null) {
                                        texteCuve = "";
                                    }
                                    textesCuve.add(texteCuve);
                                }
                                texteCuve = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuCuve = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseCuve = analyseur.getName();
                                    if (nomBaliseCuve == null) {
                                        nomBaliseCuve = "";
                                    }
                                }
                            }
                            //Si il n'y a que 9 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesCuve.size() == 9) && !corrompuCuve) {
                                try {
                                    TableCuve.instance(contexte).ajouter(
                                            Integer.parseInt(textesCuve.get(0)),
                                            Integer.parseInt(textesCuve.get(1)),
                                            Long.parseLong(textesCuve.get(2)),
                                            Long.parseLong(textesCuve.get(3)),
                                            Long.parseLong(textesCuve.get(4)),
                                            Long.parseLong(textesCuve.get(5)),
                                            textesCuve.get(6),
                                            Long.parseLong(textesCuve.get(7)),
                                            Boolean.parseBoolean(textesCuve.get(8)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:EtatFut") :
                            eventType = analyseur.next();
                            boolean corrompuEtatFut = false;
                            ArrayList<String> textesEtatFut = new ArrayList<>();
                            String nomBaliseEtatFut = analyseur.getName();
                            if (nomBaliseEtatFut == null) {
                                nomBaliseEtatFut = "";
                            }
                            String texteEtatFut = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "EtatFut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseEtatFut.equals("O:EtatFut"))) && !corrompuEtatFut) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseEtatFut.charAt(0) == 'E')) {
                                    if (texteEtatFut == null) {
                                        texteEtatFut = "";
                                    }
                                    textesEtatFut.add(texteEtatFut);
                                }
                                texteEtatFut = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuEtatFut = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseEtatFut = analyseur.getName();
                                    if (nomBaliseEtatFut == null) {
                                        nomBaliseEtatFut = "";
                                    }
                                }
                            }
                            //Si il n'y a que 6 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesEtatFut.size() == 6) && !corrompuEtatFut) {
                                try {
                                    TableEtatFut.instance(contexte).ajouter(
                                            textesEtatFut.get(0),
                                            textesEtatFut.get(1),
                                            Integer.parseInt(textesEtatFut.get(2)),
                                            Integer.parseInt(textesEtatFut.get(3)),
                                            Boolean.parseBoolean(textesEtatFut.get(4)),
                                            Boolean.parseBoolean(textesEtatFut.get(5)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:Fut") :
                            eventType = analyseur.next();
                            boolean corrompuFut = false;
                            ArrayList<String> textesFut = new ArrayList<>();
                            String nomBaliseFut = analyseur.getName();
                            if (nomBaliseFut == null) {
                                nomBaliseFut = "";
                            }
                            String texteFut = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseFut.equals("O:Fut"))) && !corrompuFut) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseFut.charAt(0) == 'E')) {
                                    if (texteFut == null) {
                                        texteFut = "";
                                    }
                                    textesFut.add(texteFut);
                                }
                                texteFut = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuFut = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseFut = analyseur.getName();
                                    if (nomBaliseFut == null) {
                                        nomBaliseFut = "";
                                    }
                                }
                            }
                            //Si il n'y a que 7 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesFut.size() == 7) && !corrompuFut) {
                                try {
                                    TableFut.instance(contexte).ajouter(
                                            Integer.parseInt(textesFut.get(0)),
                                            Integer.parseInt(textesFut.get(1)),
                                            Long.parseLong(textesFut.get(2)),
                                            Long.parseLong(textesFut.get(3)),
                                            Long.parseLong(textesFut.get(4)),
                                            Long.parseLong(textesFut.get(5)),
                                            Boolean.parseBoolean(textesFut.get(6)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:Historique") :
                            eventType = analyseur.next();
                            boolean corrompuHistorique = false;
                            ArrayList<String> textesHistorique = new ArrayList<>();
                            String nomBaliseHistorique = analyseur.getName();
                            if (nomBaliseHistorique == null) {
                                nomBaliseHistorique = "";
                            }
                            String texteHistorique = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseHistorique.equals("O:Historique"))) && !corrompuHistorique) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseHistorique.charAt(0) == 'E')) {
                                    if (texteHistorique == null) {
                                        texteHistorique = "";
                                    }
                                    textesHistorique.add(texteHistorique);
                                }
                                texteHistorique = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuHistorique = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseHistorique = analyseur.getName();
                                    if (nomBaliseHistorique == null) {
                                        nomBaliseHistorique = "";
                                    }
                                }
                            }
                            //Si il n'y a que 6 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesHistorique.size() == 6) && !corrompuHistorique) {
                                try {
                                    TableHistorique.instance(contexte).ajouter(
                                            textesHistorique.get(0),
                                            Long.parseLong(textesHistorique.get(1)),
                                            Long.parseLong(textesHistorique.get(2)),
                                            Long.parseLong(textesHistorique.get(3)),
                                            Long.parseLong(textesHistorique.get(4)),
                                            Long.parseLong(textesHistorique.get(5)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:ListeHistorique") :
                            eventType = analyseur.next();
                            boolean corrompuListeHistorique = false;
                            ArrayList<String> textesListeHistorique = new ArrayList<>();
                            String nomBaliseListeHistorique = analyseur.getName();
                            if (nomBaliseListeHistorique == null) {
                                nomBaliseListeHistorique = "";
                            }
                            String texteListeHistorique = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseListeHistorique.equals("O:ListeHistorique"))) && !corrompuListeHistorique) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseListeHistorique.charAt(0) == 'E')) {
                                    if (texteListeHistorique == null) {
                                        texteListeHistorique = "";
                                    }
                                    textesListeHistorique.add(texteListeHistorique);
                                }
                                texteListeHistorique = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuListeHistorique = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseListeHistorique = analyseur.getName();
                                    if (nomBaliseListeHistorique == null) {
                                        nomBaliseListeHistorique = "";
                                    }
                                }
                            }
                            //Si il n'y a que 2 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesListeHistorique.size() == 2) && !corrompuListeHistorique) {
                                try {
                                    TableListeHistorique.instance(contexte).ajouter(
                                            Integer.parseInt(textesListeHistorique.get(0)),
                                            textesListeHistorique.get(1));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:Gestion") :
                            eventType = analyseur.next();
                            boolean corrompuGestion = false;
                            ArrayList<String> textesGestion = new ArrayList<>();
                            String nomBaliseGestion = analyseur.getName();
                            if (nomBaliseGestion == null) {
                                nomBaliseGestion = "";
                            }
                            String texteGestion = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseGestion.equals("O:Gestion"))) && !corrompuGestion) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseGestion.charAt(0) == 'E')) {
                                    if (texteGestion == null) {
                                        texteGestion = "";
                                    }
                                    textesGestion.add(texteGestion);
                                }
                                texteGestion = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuGestion = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseGestion = analyseur.getName();
                                    if (nomBaliseGestion == null) {
                                        nomBaliseGestion = "";
                                    }
                                }
                            }
                            //Si il n'y a que 4 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesGestion.size() == 4) && !corrompuGestion) {
                                TableGestion.instance(contexte).modifier(
                                        Integer.parseInt(textesGestion.get(0)),
                                        Integer.parseInt(textesGestion.get(1)),
                                        Integer.parseInt(textesGestion.get(2)),
                                        Integer.parseInt(textesGestion.get(3)));
                            }
                            break;

                        case ("O:Calendrier") :
                            eventType = analyseur.next();
                            boolean corrompuCalendrier = false;
                            ArrayList<String> textesCalendrier = new ArrayList<>();
                            String nomBaliseCalendrier = analyseur.getName();
                            if (nomBaliseCalendrier == null) {
                                nomBaliseCalendrier = "";
                            }
                            String texteCalendrier = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseCalendrier.equals("O:Calendrier"))) && !corrompuCalendrier) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseCalendrier.charAt(0) == 'E')) {
                                    if (texteCalendrier == null) {
                                        texteCalendrier = "";
                                    }
                                    textesCalendrier.add(texteCalendrier);
                                }
                                texteCalendrier = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuCalendrier = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseCalendrier = analyseur.getName();
                                    if (nomBaliseCalendrier == null) {
                                        nomBaliseCalendrier = "";
                                    }
                                }
                            }
                            //Si il n'y a que 4 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesCalendrier.size() == 4) && !corrompuCalendrier) {
                                try {
                                    /*TableCalendrier.instance(contexte).ajouter(
                                            Integer.parseInt(textesCalendrier.get(0)),
                                            Integer.parseInt(textesCalendrier.get(1)),
                                            Integer.parseInt(textesCalendrier.get(2)),
                                            Integer.parseInt(textesCalendrier.get(3)));*/
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:NoeudFermenteur") :
                            eventType = analyseur.next();
                            boolean corrompuNoeudFermenteur = false;
                            ArrayList<String> textesNoeudFermenteur = new ArrayList<>();
                            String nomBaliseNoeudFermenteur = analyseur.getName();
                            if (nomBaliseNoeudFermenteur == null) {
                                nomBaliseNoeudFermenteur = "";
                            }
                            String texteNoeudFermenteur = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "NoeudFermenteur" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseNoeudFermenteur.equals("O:NoeudFermenteur"))) && !corrompuNoeudFermenteur) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseNoeudFermenteur.charAt(0) == 'E')) {
                                    if (texteNoeudFermenteur == null) {
                                        texteNoeudFermenteur = "";
                                    }
                                    textesNoeudFermenteur.add(texteNoeudFermenteur);
                                }
                                texteNoeudFermenteur = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuNoeudFermenteur = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseNoeudFermenteur = analyseur.getName();
                                    if (nomBaliseNoeudFermenteur == null) {
                                        nomBaliseNoeudFermenteur = "";
                                    }
                                }
                            }
                            //Si il n'y a que 4 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesNoeudFermenteur.size() == 4) && !corrompuNoeudFermenteur) {
                                try {
                                    TableCheminBrassinFermenteur.instance(contexte).ajouter(
                                            Long.parseLong(textesNoeudFermenteur.get(0)),
                                            Long.parseLong(textesNoeudFermenteur.get(1)),
                                            Long.parseLong(textesNoeudFermenteur.get(2)),
                                            Long.parseLong(textesNoeudFermenteur.get(3)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:NoeudCuve") :
                            eventType = analyseur.next();
                            boolean corrompuNoeudCuve = false;
                            ArrayList<String> textesNoeudCuve = new ArrayList<>();
                            String nomBaliseNoeudCuve = analyseur.getName();
                            if (nomBaliseNoeudCuve == null) {
                                nomBaliseNoeudCuve = "";
                            }
                            String texteNoeudCuve = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseNoeudCuve.equals("O:NoeudCuve"))) && !corrompuNoeudCuve) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseNoeudCuve.charAt(0) == 'E')) {
                                    if (texteNoeudCuve == null) {
                                        texteNoeudCuve = "";
                                    }
                                    textesNoeudCuve.add(texteNoeudCuve);
                                }
                                texteNoeudCuve = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuNoeudCuve = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseNoeudCuve = analyseur.getName();
                                    if (nomBaliseNoeudCuve == null) {
                                        nomBaliseNoeudCuve = "";
                                    }
                                }
                            }
                            //Si il n'y a que 4 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesNoeudCuve.size() == 4) && !corrompuNoeudCuve) {
                                try {
                                    TableCheminBrassinCuve.instance(contexte).ajouter(
                                            Long.parseLong(textesNoeudCuve.get(0)),
                                            Long.parseLong(textesNoeudCuve.get(1)),
                                            Long.parseLong(textesNoeudCuve.get(2)),
                                            Long.parseLong(textesNoeudCuve.get(3)));
                                } catch (Exception e) {}
                            }
                            break;

                        case ("O:NoeudFut") :
                            eventType = analyseur.next();
                            boolean corrompuNoeudFut = false;
                            ArrayList<String> textesNoeudFut = new ArrayList<>();
                            String nomBaliseNoeudFut = analyseur.getName();
                            if (nomBaliseNoeudFut == null) {
                                nomBaliseNoeudFut = "";
                            }
                            String texteNoeudFut = analyseur.getText();
                            //Tant qu'il ne trouve pas la balise de fin de "Fut" ou tant qu'il ne trouve pas une balise de debut contenant 'O' (donc debut d'un objet)
                            while (!((eventType == XmlPullParser.END_TAG) && (nomBaliseNoeudFut.equals("O:NoeudFut"))) && !corrompuNoeudFut) {
                                if ((eventType == XmlPullParser.END_TAG) && (nomBaliseNoeudFut.charAt(0) == 'E')) {
                                    if (texteNoeudFut == null) {
                                        texteNoeudFut = "";
                                    }
                                    textesNoeudFut.add(texteNoeudFut);
                                }
                                texteNoeudFut = analyseur.getText();
                                if ((eventType == XmlPullParser.START_TAG) && (analyseur.getName().charAt(0) == 'O')) {
                                    corrompuNoeudFut = true;
                                } else {
                                    eventType = analyseur.next();
                                    nomBaliseNoeudFut = analyseur.getName();
                                    if (nomBaliseNoeudFut == null) {
                                        nomBaliseNoeudFut = "";
                                    }
                                }
                            }
                            //Si il n'y a que 4 elements et qu'il n 'y a pas de corruption detecte
                            if ((textesNoeudFut.size() == 4) && !corrompuNoeudFut) {
                                try {
                                    TableCheminBrassinFut.instance(contexte).ajouter(
                                            Long.parseLong(textesNoeudFut.get(0)),
                                            Long.parseLong(textesNoeudFut.get(1)),
                                            Long.parseLong(textesNoeudFut.get(2)),
                                            Long.parseLong(textesNoeudFut.get(3)));
                                } catch (Exception e) {}
                            }
                            break;

                        default : eventType = analyseur.next();
                            break;
                    }
                } else {
                    eventType = analyseur.next();
                }
            }
            Toast.makeText(contexte, "Chargement du fichier : " + ligne.getFichier().getName() + " terminé.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(contexte, "Problème avec le chargement du fichier. Le document ne peut pas être lu", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void envoyer() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Sauvegarde de l'application \"Gestion de Brasserie\"");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
        intent.putExtra(Intent.EXTRA_TEXT, "En pièce jointe, le fichier de sauvegarde de l'application \"Gestion de Brasserie\"");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(ligne.getFichier()));
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Choisir l'application d'envoi du courrier électronique"));
    }

    @Override
    public void invalidate() {}

    @Override
    public void onClick(View view) {
        if (view.equals(sauvegarde)) {
            sauvegarder();
            afficherListeFichier();
        } else if (view.equals(charger)) {
            if (ligne != null) {
                TextView texteView = new TextView(contexte);
                texteView.setText("    Voulez-vous sauvegarder l'état actuel de l'application ?");
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(contexte);
                dialogBuilder.setTitle("Lecture");
                dialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sauvegarder();
                        supprimer();
                        charger();
                        afficherListeFichier();
                    }
                });
                dialogBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        supprimer();
                        charger();
                    }
                });
                dialogBuilder.setView(texteView);
                dialogBuilder.show();
            }
        } else if (view.equals(envoi)) {
            if (ligne != null) {
                envoyer();
            }
        }
    }

    @Override
    public void onBackPressed() {}
}
