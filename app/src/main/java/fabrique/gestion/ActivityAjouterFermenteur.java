package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.BDD.TableFermenteur;

public class ActivityAjouterFermenteur extends Activity implements View.OnClickListener {

    Button btnAjouter;

    EditText editNumero, editQuantite;

    Spinner editEmplacement, editEtat;

    DatePicker dateLavageAcide;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_fermenteur);

        editNumero = (EditText)findViewById(R.id.editNumero);
        TableFermenteur tableFermenteur = TableFermenteur.instance(this);
        int i = 0;
        for (i=0; ((i<tableFermenteur.tailleResult()) && (tableFermenteur.recuperer(i).getNumero() == i+1)); i=i+1) {
        }
        editNumero.setText("" + (i+1));

        editQuantite = (EditText)findViewById(R.id.editQuantite);

        editEmplacement = (Spinner)this.findViewById(R.id.editEmplacement);
        TableEmplacement tableEmplacement = TableEmplacement.instance(this);
        ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tableEmplacement.emplacements());
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEmplacement.setAdapter(adapteurEmplacement);

        editEtat = (Spinner)this.findViewById(R.id.editEtat);
        TableEtatFermenteur tableEtatFermenteur = TableEtatFermenteur.instance(this);
        ArrayAdapter<String> adapteurEtat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tableEtatFermenteur.etats());
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEtat.setAdapter(adapteurEtat);

        dateLavageAcide = (DatePicker)findViewById(R.id.dateLavageAcide);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ((v.equals(btnAjouter)) && (editNumero.getText() != null) && (Integer.parseInt(editNumero.getText().toString())>0)) {
            int numero = Integer.parseInt(editNumero.getText().toString());

            int capacite = 0;
            if ((editQuantite.getText()!= null) && (!editQuantite.getText().toString().equals(""))) {
                capacite = Integer.parseInt(editQuantite.getText().toString());
            }

            int emplacement = editEmplacement.getSelectedItemPosition();

            int etat = editEtat.getSelectedItemPosition();

            Calendar calendar = new GregorianCalendar(dateLavageAcide.getYear(), dateLavageAcide.getMonth(), dateLavageAcide.getDayOfMonth());
            long dateLavageAcide = calendar.getTimeInMillis();

            TableFermenteur.instance(null).ajout(null, numero, capacite, emplacement, dateLavageAcide, etat, System.currentTimeMillis());

            Intent intent = new Intent(this, ActivityTableauDeBord.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }
}