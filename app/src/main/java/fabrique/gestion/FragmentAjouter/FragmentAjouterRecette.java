package fabrique.gestion.FragmentAjouter;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.R;
import fabrique.gestion.FragmentAmeliore;

public class FragmentAjouterRecette extends FragmentAmeliore implements View.OnClickListener{

    private Context contexte;

    private Button btnAjouter;

    private EditText editNom, editAcronyme, editCouleur;

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
        editCouleur = (EditText)view.findViewById(R.id.editCouleur);

        btnAjouter = (Button)view.findViewById(R.id.btnAjouter);
        btnAjouter.setOnClickListener(this);

        return view;
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
                TableRecette.instance(contexte).ajouter(editNom.getText().toString(), editCouleur.getText().toString(), editAcronyme.getText().toString(), true);
                Toast.makeText(contexte, "Recette ajouté !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(contexte, erreur, Toast.LENGTH_LONG).show();
            }
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
