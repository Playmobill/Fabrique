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

import fabrique.gestion.BDD.TableRecette;

/**
 * Created by thibaut on 15/04/15.
 */
public class ActivityAjouterRecette extends Activity implements View.OnClickListener{

    private Button btnAjouter;

    private EditText editNom, editAcronyme, editCouleur;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_ajouter_recette);

        editNom = (EditText)findViewById(R.id.editNom);
        editAcronyme = (EditText)findViewById(R.id.editAcronyme);
        editCouleur = (EditText)findViewById(R.id.editCouleur);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAjouter)) {

            String erreur = "";

            if (editNom.getText().toString().equals("")) {
                erreur = erreur + "La recette doit avoir un nom. ";
            }
            if (editAcronyme.getText().toString().equals("")) {
                erreur = erreur + "La recette doit avoir un nom. ";
            }
            if (editCouleur.getText().toString().equals("")) {
                erreur = erreur + "La recette doit avoir un nom. ";
            }

            if (erreur.equals("")) {

                TableRecette.instance(this).ajouter(editNom.getText().toString(), editCouleur.getText().toString(), editAcronyme.getText().toString(), true);

                Intent intent = new Intent(this, ActivityGestion.class);
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
