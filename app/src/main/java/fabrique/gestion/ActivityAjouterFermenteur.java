package fabrique.gestion;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.BDD.TableFermenteur;

public class ActivityAjouterFermenteur extends Activity implements View.OnClickListener {

    private Button btnAjouter;

    private EditText editNumero, editQuantite;

    private Spinner editEmplacement, editEtat;

    private ImageButton dateLavageAcide;
    protected int jour;
    protected int mois;
    protected int annee;
    private EditText editDateLavageAcide;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_fermenteur);

        editNumero = (EditText)findViewById(R.id.editNumero);
        TableFermenteur tableFermenteur = TableFermenteur.instance(this);

        int i;
        for (i=0; ((i<tableFermenteur.tailleListe()) && (tableFermenteur.recuperer(i).getNumero() == i+1)); i=i+1) {
        }
        editNumero.setText("" + (i+1));

        editQuantite = (EditText)findViewById(R.id.editQuantite);

        editEmplacement = (Spinner)this.findViewById(R.id.editEmplacement);
        TableEmplacement tableEmplacement = TableEmplacement.instance(this);
        ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(this, R.layout.spinner_style, tableEmplacement.emplacements());
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEmplacement.setAdapter(adapteurEmplacement);

        editEtat = (Spinner)this.findViewById(R.id.editEtat);
        TableEtatFermenteur tableEtatFermenteur = TableEtatFermenteur.instance(this);
        ArrayAdapter<String> adapteurEtat = new ArrayAdapter<>(this, R.layout.spinner_style, tableEtatFermenteur.etats());
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEtat.setAdapter(adapteurEtat);

        dateLavageAcide = (ImageButton) findViewById(R.id.imageButton1);
        dateLavageAcide.setOnClickListener(this);

        Calendar calendrier = Calendar.getInstance();
        jour = calendrier.get(Calendar.DAY_OF_MONTH);
        mois = calendrier.get(Calendar.MONTH);
        annee = calendrier.get(Calendar.YEAR);
        editDateLavageAcide = (EditText) findViewById(R.id.editText);
        editDateLavageAcide.setText(jour + " / " + (mois + 1) + " / " + annee);

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

            Calendar calendar = new GregorianCalendar(annee, mois, jour);
            long dateLavageAcide = calendar.getTimeInMillis();

            TableFermenteur.instance(this).ajouter(null, numero, capacite, emplacement, dateLavageAcide, etat, System.currentTimeMillis(), -1);

            Intent intent = new Intent(this, ActivityTableauDeBord.class);
            startActivity(intent);
        } else if (v.equals(dateLavageAcide)){
            showDialog(0);
        }
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, annee, mois, jour);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int annee2, int mois2, int jour2) {
            jour = jour2;
            mois = mois2;
            annee = annee2;
            editDateLavageAcide.setText(jour + " / " + (mois + 1) + " / " + annee);
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }
}