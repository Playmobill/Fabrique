package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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
        if ((v.equals(btnAjouter)) && (editNumero.getText() != null) && (Integer.parseInt(editNumero.getText().toString())>0)) {
            int numero = Integer.parseInt(editNumero.getText().toString());

            int capacite = 0;
            if ((editQuantite.getText() != null) && (!editQuantite.getText().toString().equals(""))) {
                capacite = Integer.parseInt(editQuantite.getText().toString());
            }

            TableFut.instance(this).ajouter(numero, capacite, 1, System.currentTimeMillis(), -1, System.currentTimeMillis());

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
