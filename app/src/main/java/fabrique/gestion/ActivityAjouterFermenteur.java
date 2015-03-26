package fabrique.gestion;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import fabrique.gestion.Objets.EtatFermenteur;

public class ActivityAjouterFermenteur extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_ajouter_fermenteur);

        Spinner spinner = (Spinner)this.findViewById(R.id.editEmplacement);

        String[] etats = EtatFermenteur.etats();

        ArrayAdapter<String> adapteur = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, etats);
        adapteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapteur);
    }

}
