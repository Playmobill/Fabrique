package fabrique.gestion;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * Created by thibaut on 07/04/15.
 */
public class ActivityListeBrassin extends Activity implements AdapterView.OnItemSelectedListener{

    private Spinner tri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_liste_brassin);

        tri = (Spinner)findViewById(R.id.editTri);
        ArrayList<String> options = new ArrayList<>();
        options.add("Numero");
        options.add("Biere");
        options.add("Date de création");
        ArrayAdapter<String> triAdapter = new ArrayAdapter<>(this, R.layout.spinner_style, options);
        triAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tri.setAdapter(triAdapter);


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ActivityListe.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
