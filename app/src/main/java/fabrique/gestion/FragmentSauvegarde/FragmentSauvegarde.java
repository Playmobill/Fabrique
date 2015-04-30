package fabrique.gestion.FragmentSauvegarde;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentSauvegarde extends FragmentAmeliore implements View.OnClickListener {

    private View view;

    private Context contexte;

    private Button sauvegarde, lecture;

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

        initialiserBouton();

        return view;
    }

    private void initialiserBouton() {
        sauvegarde = (Button)view.findViewById(R.id.sauvegarde);
        sauvegarde.setOnClickListener(this);

        lecture = (Button)view.findViewById(R.id.lecture);
        lecture.setOnClickListener(this);
    }

    private void sauvegarder() {
        try {
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(System.currentTimeMillis());
            int seconde = calendrier.get(Calendar.SECOND);
            int minute = calendrier.get(Calendar.MINUTE);
            int heure = calendrier.get(Calendar.HOUR_OF_DAY);
            int jour = calendrier.get(Calendar.DAY_OF_MONTH);
            int mois = calendrier.get(Calendar.MONTH);
            int annee = calendrier.get(Calendar.YEAR);
            File fichier = new File(Environment.getExternalStorageDirectory(), "Gestion_" + heure + "h_" + minute + "m_" + seconde + "s_" + jour + "j_" + mois + "m_" + annee + "a.txt");
            fichier.createNewFile();
            FileWriter filewriter = new FileWriter(fichier, false);
            filewriter.write(TableEmplacement.instance(contexte).sauvegarde());
            filewriter.write(TableRecette.instance(contexte).sauvegarde());
            filewriter.write(TableBrassin.instance(contexte).sauvegarde());
            filewriter.write(TableFermenteur.instance(contexte).sauvegarde());
            filewriter.write(TableEtatFermenteur.instance(contexte).sauvegarde());
            filewriter.write(TableCuve.instance(contexte).sauvegarde());
            filewriter.write(TableEtatCuve.instance(contexte).sauvegarde());
            filewriter.write(TableFut.instance(contexte).sauvegarde());
            filewriter.write(TableHistorique.instance(contexte).sauvegarde());
            filewriter.write(TableListeHistorique.instance(contexte).sauvegarde());
            filewriter.write(TableGestion.instance(contexte).sauvegarde());
            filewriter.close();
        } catch (IOException e) {
            Toast.makeText(contexte, "Erreur lors de la création du fichier de sauvegarde.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.equals(sauvegarde)) {
            sauvegarder();
        } else if (view.equals(lecture)) {

        }
    }

    @Override
    public void onBackPressed() {}
}
