package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fabrique.gestion.BDD.TableFut;

public class ActivityAjouterFut extends Activity implements View.OnClickListener {

    private Button btnAjouter;

    private EditText editNumero, editQuantite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_fut);

        editNumero = (EditText)findViewById(R.id.editNumero);
        TableFut tableFut = TableFut.instance(this);
        int i;
        for (i=0; ((i<tableFut.tailleListe()) && (tableFut.recupererIndex(i).getNumero() == i+1)); i=i+1) {
        }
        editNumero.setText("" + (i+1));

        editQuantite = (EditText)findViewById(R.id.editQuantite);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAjouter)) {
            String erreur = "";

            int numero = 0;
            if (editNumero.getText().toString().equals("")) {
                erreur = erreur + "Le fût doit avoir un numéro.";
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

            if(!erreur.equals("")) {
                TableFut.instance(this).ajouter(numero, capacite, 1, System.currentTimeMillis(), -1, System.currentTimeMillis());

                Intent intent = new Intent(this, ActivityListeFut.class);
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
