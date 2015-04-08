package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;

public class ActivityAjouterCuve extends Activity implements View.OnClickListener {

    private Button btnAjouter;

    private EditText editNumero, editQuantite;

    private Spinner editEmplacement;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_cuve);

        editNumero = (EditText)findViewById(R.id.editNumero);
        TableCuve tableCuve = TableCuve.instance(this);
        int i;
        for (i=0; ((i<tableCuve.tailleListe()) && (tableCuve.recupererIndex(i).getNumero() == i+1)); i=i+1) {
        }
        editNumero.setText("" + (i+1));

        editQuantite = (EditText)findViewById(R.id.editQuantite);

        editEmplacement = (Spinner)findViewById(R.id.editEmplacement);
        TableEmplacement tableEmplacement = TableEmplacement.instance(this);
        ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(this, R.layout.spinner_style, tableEmplacement.emplacements());
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEmplacement.setAdapter(adapteurEmplacement);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ((v.equals(btnAjouter)) && (editNumero.getText() != null) && (Integer.parseInt(editNumero.getText().toString())>0)) {
            int numero = Integer.parseInt(editNumero.getText().toString());

            int capacite = 0;
            if ((editQuantite.getText() != null) && (!editQuantite.getText().toString().equals(""))) {
                capacite = Integer.parseInt(editQuantite.getText().toString());
            }

            long emplacement = editEmplacement.getSelectedItemPosition();

            TableCuve.instance(this).ajouter(numero, capacite, emplacement, System.currentTimeMillis(), 1, System.currentTimeMillis(), "", -1);

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