package fabrique.gestion.FragmentListe;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableBrassinPere;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.Objets.BrassinPere;
import fabrique.gestion.R;
import fabrique.gestion.Vue.VueBrassin;

public class FragmentVueBrassin extends FragmentAmeliore implements View.OnClickListener{

    private Context contexte;
    private int index;

    private Button btnPrecedent, btnSuivant, btnRecherche;
    private EditText editRechercheBrassin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil)getActivity()).setVue(this);

        contexte = container.getContext();

        BrassinPere brassinPere = TableBrassinPere.instance(contexte).recupererId(getArguments().getLong("id"));

        LinearLayout layout = new LinearLayout(contexte);
        layout.setOrientation(LinearLayout.VERTICAL);

            RelativeLayout ligneEnTete = new RelativeLayout(contexte);
                LinearLayout ligneNavigation = new LinearLayout(contexte);
                ligneNavigation.setOrientation(LinearLayout.HORIZONTAL);
                    RelativeLayout.LayoutParams parametreLigneNavigation = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    parametreLigneNavigation.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                    parametreLigneNavigation.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                ligneNavigation.setLayoutParams(parametreLigneNavigation);
                    btnPrecedent = new Button(contexte);
                    btnPrecedent.setText("<");
                    btnPrecedent.setEnabled(false);
                    btnPrecedent.setOnClickListener(this);
                ligneNavigation.addView(btnPrecedent);
                    TextView txtActuel = new TextView(contexte);
                ligneNavigation.addView(txtActuel);
                    btnSuivant = new Button(contexte);
                    btnSuivant.setText(">");
                    btnSuivant.setEnabled(false);
                    btnSuivant.setOnClickListener(this);
                ligneNavigation.addView(btnSuivant);
            ligneEnTete.addView(ligneNavigation);
                LinearLayout ligneRecherche = new LinearLayout(contexte);
                ligneRecherche.setOrientation(LinearLayout.HORIZONTAL);
                    RelativeLayout.LayoutParams parametreLigneSpinner = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    parametreLigneSpinner.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                    parametreLigneSpinner.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                ligneRecherche.setLayoutParams(parametreLigneSpinner);
                    editRechercheBrassin = new EditText(contexte);
                    editRechercheBrassin.setInputType(InputType.TYPE_CLASS_NUMBER);
                ligneRecherche.addView(editRechercheBrassin);
                    btnRecherche = new Button(contexte);
                    btnRecherche.setText("rechercher ...");
                    btnRecherche.setOnClickListener(this);
                ligneRecherche.addView(btnRecherche);
            ligneEnTete.addView(ligneRecherche);
        layout.addView(ligneEnTete, parametreLigneSpinner);

        if (brassinPere != null) {
            index = TableBrassin.instance(contexte).recupererIndexSelonId(brassinPere.getId());
            if (index > 0) {
                btnPrecedent.setEnabled(true);
            }
            txtActuel.setText("Brassin " + brassinPere.getNumero());
            if (index < TableBrassin.instance(contexte).tailleListe()-1) {
                btnSuivant.setEnabled(true);
            }
            layout.addView(new VueBrassin(contexte, brassinPere));
        } else {
            index = -1;
            TextView txtErreur = new TextView(contexte);
            txtErreur.setText("Aucun brassin sélectionné");
        }

        ScrollView layoutVerticalScroll = new ScrollView(contexte);
        layoutVerticalScroll.addView(layout);

        return layoutVerticalScroll;
    }

    public void navigation(long id) {
        FragmentVueBrassin fragmentVueBrassin = new FragmentVueBrassin();
        Bundle args = new Bundle();
        args.putLong("id", id);
        fragmentVueBrassin.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueBrassin);
        transaction.setTransition((FragmentTransaction.TRANSIT_NONE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void invalidate() {}

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentListeBrassin());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnPrecedent)) {
            navigation(TableBrassin.instance(contexte).recupererIndex(index-1).getId());
        } else if (v.equals(btnSuivant)) {
            navigation(TableBrassin.instance(contexte).recupererIndex(index+1).getId());
        } else if (v.equals(btnRecherche)) {
            Brassin brassin = TableBrassin.instance(contexte).recupererNumero(Integer.parseInt(editRechercheBrassin.getText().toString()));
            if (brassin != null) {
                navigation(brassin.getId());
            } else {
                Toast.makeText(contexte, "Le brassin n'a pas été trouvé.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
