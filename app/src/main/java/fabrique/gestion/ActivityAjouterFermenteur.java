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

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.Objets.Emplacement;

public class ActivityAjouterFermenteur extends Activity implements View.OnClickListener {

    private ArrayList<Emplacement> emplacements;

    private Button btnAjouter;

    private EditText editNumero, editQuantite;

    private Spinner editEmplacement;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        emplacements = TableEmplacement.instance(this).recupererActifs();
        if (emplacements.size() == 0) {
            Toast.makeText(this, "Il faut avoir au moins UN emplacement ACTIF pour pouvoir ajouter un fermenteur.", Toast.LENGTH_LONG).show();
            finish();
        }
        ArrayList<String> texteEmplacements = new ArrayList<>();
        for (int i=0; i<emplacements.size() ; i++) {
            texteEmplacements.add(emplacements.get(i).getTexte());
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_fermenteur);

        editNumero = (EditText)findViewById(R.id.editNumero);
        TableFermenteur tableFermenteur = TableFermenteur.instance(this);

        int i;
        for (i=0; ((i<tableFermenteur.tailleListe()) && (tableFermenteur.recupererIndex(i).getNumero() == i+1)); i=i+1) {
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
            String erreur = "";

            int numero = 0;
            if (editNumero.getText().toString().equals("")) {
                erreur = erreur + "Le fermenteur doit avoir un numéro.";
            } else {
                try {
                    numero = Integer.parseInt(editNumero.getText().toString());
                } catch (NumberFormatException e) {
                    erreur = erreur + "Le numéro est trop grand.";
                }
            }

            int capacite = 0;
            try {
                if (!editQuantite.getText().toString().equals("")) {
                    capacite = Integer.parseInt(editQuantite.getText().toString());
                }
            } catch(NumberFormatException e) {
                if (!erreur.equals("")) {
                    erreur = erreur + "\n";
                }
                erreur = erreur + "La quantité est trop grande.";
            }

            if (erreur.equals("")) {
                long emplacement = emplacements.get(editEmplacement.getSelectedItemPosition()).getId();

                TableFermenteur.instance(this).ajouter(numero, capacite, emplacement, System.currentTimeMillis(), 1, System.currentTimeMillis(), -1);

                Intent intent = new Intent(this, ActivityTableauDeBord.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, erreur, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }
}