package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableRecette;

public class ActivityAjouterBrassin extends Activity implements View.OnClickListener {

    private Button btnAjouter;

    private EditText editNumero, editCommentaire, editQuantite,
            editDensiteOriginale, editDensiteFinale, editPourcentageAlcool;

    private Spinner editRecette;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_ajouter_brassin);

        editNumero = (EditText)findViewById(R.id.editNumero);
        TableBrassin tableBrassin = TableBrassin.instance(this);
        int i;
        for (i=0; ((i<tableBrassin.tailleListe()) && (tableBrassin.recupererIndex(i).getNumero() == i+1)); i=i+1) {}
        editNumero.setText("" + (i+1));

        editCommentaire = (EditText)findViewById(R.id.editCommentaire);
        editQuantite = (EditText)findViewById(R.id.editQuantite);
        editDensiteOriginale = (EditText)findViewById(R.id.editDensiteOriginale);
        editDensiteFinale = (EditText)findViewById(R.id.editDensiteFinale);
        editPourcentageAlcool = (EditText)findViewById(R.id.editPourcentageAlcool);

        editRecette = (Spinner)this.findViewById(R.id.editRecette);
        TableRecette tableRecette = TableRecette.instance(this);
        ArrayAdapter<String> adapteurRecette = new ArrayAdapter<>(this, R.layout.spinner_style, tableRecette.recupererRecettesActifs());
        adapteurRecette.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editRecette.setAdapter(adapteurRecette);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAjouter)) {
            if (!(editNumero.getText().toString().equals(""))) {
                String erreur = "";
                int numero = 0;
                try {
                    numero = Integer.parseInt(editNumero.getText().toString());
                } catch (NumberFormatException e) {
                    erreur = erreur + "Le numéro est trop grand.\n";
                }

                int capacite = 0;
                try {
                    if ((!editQuantite.getText().toString().equals(""))) {
                        capacite = Integer.parseInt(editQuantite.getText().toString());
                    }
                } catch (NumberFormatException e) {
                    erreur = erreur + "La quantité est trop grande.\n";
                }

                float densiteOriginale = 0;
                try {
                    if ((!editDensiteOriginale.getText().toString().equals(""))) {
                        densiteOriginale = Float.parseFloat(editDensiteOriginale.getText().toString());
                    }
                } catch (NumberFormatException e) {
                    erreur = erreur + "La densité originale est trop grande.\n";
                }

                float densiteFinale = 0;
                try {
                    if ((editDensiteFinale.getText() != null) && (!editDensiteFinale.getText().toString().equals(""))) {
                        densiteFinale = Float.parseFloat(editDensiteFinale.getText().toString());
                    }
                } catch (NumberFormatException e) {
                    Log.i("AjouterBrassin", "erreur densite final");
                    erreur = erreur + "La densité final est trop grande.\n";
                }

                float pourcentageAlcool = 0;
                try {
                    if ((editPourcentageAlcool.getText() != null) && (!editPourcentageAlcool.getText().toString().equals(""))) {
                        pourcentageAlcool = Float.parseFloat(editPourcentageAlcool.getText().toString());
                    }
                } catch (NumberFormatException e) {
                    erreur = erreur + "Le pourcentage d'alcool est trop grand.\n";
                }

                if (erreur.equals("")) {
                    long recette = TableRecette.instance(this).recupererIndex(editRecette.getSelectedItemPosition()).getId();

                    TableBrassin.instance(this).ajouter(numero, editCommentaire.getText().toString(), System.currentTimeMillis(), capacite, recette, densiteOriginale, densiteFinale, pourcentageAlcool);

                    Intent intent = new Intent(this, ActivityListeBrassin.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, erreur, Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Le brassin doit avoir un numéro.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }

}
