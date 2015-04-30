package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import fabrique.gestion.ActivityAccueil;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.FragmentAmeliore;
import fabrique.gestion.R;

public class FragmentTempsInspectionDateLavageAcide extends FragmentAmeliore implements View.OnClickListener{

    Context contexte;
    EditText lavageAcideAvert, lavageAcideUrgence, inspectAvert, inspectUrgence;
    LinearLayout layoutLavageAcide, layoutInspectionBaril;
    Button btnModifierLA, btnValiderLA, btnAnnulerLA,
            btnModifierIB, btnValiderIB, btnAnnulerIB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (container == null) {
            return null;
        }

        ((ActivityAccueil) getActivity()).setVue(this);

        contexte = container.getContext();

        View view = inflater.inflate(R.layout.activity_inspection_lavageacide, container, false);
        lavageAcideAvert = (EditText)view.findViewById(R.id.editDelaiLavageAcideAvertissement);
        lavageAcideUrgence = (EditText)view.findViewById(R.id.editDelaiLavageAcideUrgence);
        inspectAvert = (EditText)view.findViewById(R.id.editDelaiInspectionAvertissement);
        inspectUrgence = (EditText)view.findViewById(R.id.editDelaiInspectionUrgence);

        initialiserLavageAcide();
        initialiserInspectionBaril();

        layoutLavageAcide = (LinearLayout)view.findViewById(R.id.layoutBtnLavageAcide);
        layoutInspectionBaril = (LinearLayout)view.findViewById(R.id.layoutBtnInspectionBaril);


        btnModifierLA = new Button(contexte);
        btnModifierLA.setText("Modifier");
        btnModifierLA.setOnClickListener(this);

        btnValiderLA = new Button(contexte);
        btnValiderLA.setText("Valider");
        btnValiderLA.setOnClickListener(this);

        btnAnnulerLA = new Button(contexte);
        btnAnnulerLA.setText("Annuler");
        btnAnnulerLA.setOnClickListener(this);

        btnModifierIB = new Button(contexte);
        btnModifierIB.setText("Modifier");
        btnModifierIB.setOnClickListener(this);

        btnValiderIB = new Button(contexte);
        btnValiderIB.setText("Valider");
        btnValiderIB.setOnClickListener(this);

        btnAnnulerIB = new Button(contexte);
        btnAnnulerIB.setText("Annuler");
        btnAnnulerIB.setOnClickListener(this);

        layoutLavageAcide.addView(btnModifierLA);
        layoutInspectionBaril.addView(btnModifierIB);

        return view;
    }

    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, new FragmentGestion());
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_CLOSE));
        transaction.addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {

        if(v.equals(btnModifierLA)){
            layoutLavageAcide.removeAllViews();
            layoutLavageAcide.addView(btnValiderLA);
            layoutLavageAcide.addView(btnAnnulerLA);

            lavageAcideAvert.setEnabled(true);
            lavageAcideUrgence.setEnabled(true);
        }
        if(v.equals(btnAnnulerLA)){
            layoutLavageAcide.removeAllViews();
            layoutLavageAcide.addView(btnModifierLA);

            lavageAcideAvert.setEnabled(false);
            lavageAcideUrgence.setEnabled(false);
            initialiserLavageAcide();
        }
        if(v.equals(btnValiderLA)){
            initialiserInspectionBaril();
            TableGestion.instance(contexte).modifier(Long.parseLong(lavageAcideUrgence.getText().toString())*(1000*60*60*24),
                    Long.parseLong(lavageAcideAvert.getText().toString())*(1000*60*60*24),
                    Long.parseLong(inspectUrgence.getText().toString())*(1000*60*60*24),
                    Long.parseLong(inspectAvert.getText().toString())*(1000*60*60*24));

            layoutLavageAcide.removeAllViews();
            layoutLavageAcide.addView(btnModifierLA);

            lavageAcideAvert.setEnabled(false);
            lavageAcideUrgence.setEnabled(false);
        }

        if(v.equals(btnModifierIB)){
            layoutInspectionBaril.removeAllViews();
            layoutInspectionBaril.addView(btnValiderIB);
            layoutInspectionBaril.addView(btnAnnulerIB);

            inspectAvert.setEnabled(true);
            inspectUrgence.setEnabled(true);
        }
        if(v.equals(btnAnnulerIB)){
            layoutInspectionBaril.removeAllViews();
            layoutInspectionBaril.addView(btnModifierIB);

            inspectAvert.setEnabled(false);
            inspectUrgence.setEnabled(false);
            initialiserInspectionBaril();
        }
        if(v.equals(btnValiderIB)){
            initialiserLavageAcide();
            TableGestion.instance(contexte).modifier(Long.parseLong(lavageAcideUrgence.getText().toString())*(1000*60*60*24),
                    Long.parseLong(lavageAcideAvert.getText().toString())*(1000*60*60*24),
                    Long.parseLong(inspectUrgence.getText().toString())*(1000*60*60*24),
                    Long.parseLong(inspectAvert.getText().toString())*(1000*60*60*24));

            layoutInspectionBaril.removeAllViews();
            layoutInspectionBaril.addView(btnModifierIB);

            inspectAvert.setEnabled(false);
            inspectUrgence.setEnabled(false);
        }
    }

    public void initialiserLavageAcide(){
        TableGestion tableGestion = TableGestion.instance(contexte);
        lavageAcideUrgence.setText(""+tableGestion.delaiLavageAcide()/(1000*60*60*24));
        lavageAcideAvert.setText(""+tableGestion.avertissementLavageAcide()/(1000*60*60*24));
    }

    public void initialiserInspectionBaril(){
        TableGestion tableGestion = TableGestion.instance(contexte);
        inspectUrgence.setText(""+tableGestion.delaiInspectionBaril()/(1000*60*60*24));
        inspectAvert.setText(""+tableGestion.avertissementInspectionBaril()/(1000*60*60*24));
    }
}
