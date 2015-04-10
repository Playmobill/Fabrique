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
import android.widget.Toast;

import java.util.ArrayList;

import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.Objets.Emplacement;

public class ActivityAjouterCuve extends Activity implements View.OnClickListener {

    private ArrayList<Emplacement> emplacements;

    private Button btnAjouter;

    private EditText editNumero, editQuantite;

    private Spinner editEmplacement;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        emplacements = TableEmplacement.instance(this).recupererActifs();
        if (emplacements.size() == 0) {
            Toast.makeText(this, "Il faut avoir au moins UN emplacement ACTIF pour pouvoir ajouter une cuve.", Toast.LENGTH_LONG).show();
            finish();
        }
        ArrayList<String> texteEmplacements = new ArrayList<>();
        for (int i=0; i<emplacements.size() ; i++) {
            texteEmplacements.add(emplacements.get(i).getTexte());
        }

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

        editEmplacement = (Spinner)this.findViewById(R.id.editEmplacement);
        ArrayAdapter<String> adapteurEmplacement = new ArrayAdapter<>(this, R.layout.spinner_style, texteEmplacements);
        adapteurEmplacement.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editEmplacement.setAdapter(adapteurEmplacement);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAjouter)) {
            if (!(editNumero.getText().toString().equals(""))) {
                try {
                    int numero = Integer.parseInt(editNumero.getText().toString());

                    try {
                        int capacite = 0;
                        if (!editQuantite.getText().toString().equals("")) {
                            capacite = Integer.parseInt(editQuantite.getText().toString());
                        }
                        long emplacement = emplacements.get(editEmplacement.getSelectedItemPosition()).getId();

                        TableCuve.instance(this).ajouter(numero, capacite, emplacement, System.currentTimeMillis(), 1, System.currentTimeMillis(), "", -1);

                        Intent intent = new Intent(this, ActivityTableauDeBord.class);
                        startActivity(intent);
                    } catch(NumberFormatException e) {
                        Toast.makeText(this, "La quantité est trop grande.", Toast.LENGTH_LONG).show();
                    }
                } catch(NumberFormatException ex) {
                    Toast.makeText(this, "Le numéro est trop grand.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "La cuve doit avoir un numéro.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }
}