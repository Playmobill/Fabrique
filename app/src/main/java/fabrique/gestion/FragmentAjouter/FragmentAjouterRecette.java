package fabrique.gestion.FragmentAjouter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.BDD.TableTypeBiere;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentAjouterRecette extends FragmentAmeliore implements View.OnClickListener{

    private Context contexte;

    private Button btnAjouter;

    private EditText editNom, editAcronyme;
    private Spinner editTypeBiere;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        View view = inflater.inflate(R.layout.activity_ajouter_recette, container, false);

        editNom = (EditText)view.findViewById(R.id.editNom);
        editAcronyme = (EditText)view.findViewById(R.id.editAcronyme);

        editTypeBiere = (Spinner)view.findViewById(R.id.spinnerTypeBiere);
            ArrayAdapter<String> adapteurRecette = new ArrayAdapter<>(contexte, R.layout.spinner_style, TableTypeBiere.instance(contexte).recupererNomTypeBieresActifs());
            adapteurRecette.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTypeBiere.setAdapter(adapteurRecette);

        btnAjouter = (Button)view.findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnAjouter)) {
            long typeBiere = TableTypeBiere.instance(contexte).recupererIndex(editTypeBiere.getSelectedItemPosition()).getId();
            TableRecette.instance(contexte).ajouter(editNom.getText().toString(), editAcronyme.getText().toString(), typeBiere, Color.BLACK, Color.WHITE, true);
            Toast.makeText(contexte, "Recette ajouté !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentAjouter());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }
}
