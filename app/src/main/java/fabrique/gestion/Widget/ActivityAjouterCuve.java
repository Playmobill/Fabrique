package fabrique.gestion.Widget;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import fabrique.gestion.ActivityGestion;
import fabrique.gestion.ActivityTableauDeBord;
import fabrique.gestion.R;

public class ActivityAjouterCuve extends Activity implements View.OnClickListener {

    Button btnAjouter;

    EditText editNumero, editQuantite;

    Spinner editEmplacement, editEtat;

    DatePicker dateLavageAcide;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_cuve);

        editNumero = (EditText)findViewById(R.id.editNumero);
        /*TableCuve tableCuve = TableCuve.instance();
        int i;
        for (i=0; ((i<tableCuve.cuves().size()) && (tableCuve.cuve(i).getNumero() == i+1)); i=i+1) {
        }
        editNumero.setText("" + (i+1));*/

        editQuantite = (EditText)findViewById(R.id.editQuantite);

        editEmplacement = (Spinner)this.findViewById(R.id.editEmplacement);
        /*ArrayList<String> emplacements = TableEmplacement.instance().emplacements();
        ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, emplacements);
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEmplacement.setAdapter(adapteurEmplacement);*/

        editEtat = (Spinner)this.findViewById(R.id.editEtat);
        /*ArrayList<String> etats = TableEtatCuve.instance().etats();
        ArrayAdapter<String> adapteurEtat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, etats);
        adapteurEtat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEtat.setAdapter(adapteurEtat);*/

        dateLavageAcide = (DatePicker)findViewById(R.id.dateLavageAcide);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ((v.equals(btnAjouter)) && (editNumero.getText() != null) && (Integer.parseInt(editNumero.getText().toString())>0)) {
            /*Cuve cuve = new Cuve();
            cuve.setNumero(Integer.parseInt(editNumero.getText().toString()));

            if ((editQuantite.getText().toString() == null) || (editQuantite.getText().toString().equals(""))) {
                cuve.setCapacite(0);
            } else {
                cuve.setCapacite(Integer.parseInt(editQuantite.getText().toString()));
            }

            cuve.setEmplacement(editEmplacement.getSelectedItemPosition());

            cuve.setEtat(editEtat.getSelectedItemPosition());

            Calendar calendar = new GregorianCalendar(dateLavageAcide.getYear(), dateLavageAcide.getMonth(), dateLavageAcide.getDayOfMonth());
            long date = calendar.getTimeInMillis();
            cuve.setDateLavageAcide(date);

            TableCuve.instance().ajouter(cuve);*/

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