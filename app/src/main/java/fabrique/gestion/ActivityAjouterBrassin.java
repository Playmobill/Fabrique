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

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableTypeBiere;

public class ActivityAjouterBrassin extends Activity implements View.OnClickListener {

    private Button btnAjouter;

    private EditText editNumero, editCommentaire, editAcronyme, editQuantite,
            editCouleur, editDensiteOriginale, editDensiteFinale, editPourcentageAlcool;

    private Spinner editTypeBiere;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_ajouter_brassin);

        editNumero = (EditText)findViewById(R.id.editNumero);
        TableBrassin tableBrassin = TableBrassin.instance(this);
        int i;
        for (i=0; ((i<tableBrassin.tailleListe()) && (tableBrassin.recuperer(i).getNumero() == i+1)); i=i+1) {}
        editNumero.setText("" + (i+1));

        editCommentaire = (EditText)findViewById(R.id.editCommentaire);
        editAcronyme = (EditText)findViewById(R.id.editNumero);
        editQuantite = (EditText)findViewById(R.id.editNumero);
        editCouleur = (EditText)findViewById(R.id.editCouleur);
        editDensiteOriginale = (EditText)findViewById(R.id.editDensiteOriginale);
        editDensiteFinale = (EditText)findViewById(R.id.editDensiteFinale);
        editPourcentageAlcool = (EditText)findViewById(R.id.editPourcentageAlcool);

        editTypeBiere = (Spinner)this.findViewById(R.id.editEmplacement);
        TableTypeBiere tableTypeBiere = TableTypeBiere.instance(this);
        ArrayAdapter<String> adapteurTypeBiere = new ArrayAdapter<>(this, R.layout.spinner_style, tableTypeBiere.types());
        adapteurTypeBiere.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTypeBiere.setAdapter(adapteurTypeBiere);

        btnAjouter = (Button)findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(btnAjouter)){

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityGestion.class);
        startActivity(intent);
    }

}
