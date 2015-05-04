package fabrique.gestion.Widget;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.FragmentListe.FragmentListeBrassin;
import fabrique.gestion.FragmentListe.FragmentVueBrassin;
import fabrique.gestion.Objets.Brassin;
import fabrique.gestion.R;

public class BoutonBrassin extends RelativeLayout implements View.OnClickListener {

    private FragmentListeBrassin fragmentListeBrassin;
    private Brassin brassin;

    public BoutonBrassin(Context contexte) {
        super(contexte);
    }

    public BoutonBrassin(Context contexte, FragmentListeBrassin fragmentListeBrassin, Brassin brassin) {
        this(contexte);
        this.fragmentListeBrassin = fragmentListeBrassin;
        this.brassin=brassin;

        RelativeLayout.LayoutParams paramsLigne = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(paramsLigne);

        RelativeLayout.LayoutParams[] paramsTexte = new RelativeLayout.LayoutParams[3];
        paramsTexte[0] = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[0].setMargins(10, 10, 10, 10);
        paramsTexte[0].addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        paramsTexte[0].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);

        paramsTexte[1] = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[1].setMargins(10, 10, 10, 10);
        paramsTexte[1].addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        paramsTexte[2] = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTexte[2].setMargins(10, 10, 10, 10);
        paramsTexte[2].addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        paramsTexte[2].addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        setLayoutParams(paramsLigne);

        TextView numero = new TextView(contexte);
        numero.setText("#"+brassin.getNumero());
        numero.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        numero.setLayoutParams(paramsTexte[0]);

        TextView typeBiere = new TextView(contexte);
        typeBiere.setText("" + TableRecette.instance(contexte).recupererId(brassin.getId_recette()).getNom());
        typeBiere.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        typeBiere.setLayoutParams(paramsTexte[1]);

        TextView dateCreation = new TextView(contexte);
        dateCreation.setText(brassin.getDateCreation());
        dateCreation.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        dateCreation.setLayoutParams(paramsTexte[2]);

        RelativeLayout.LayoutParams paramsBouton = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(paramsLigne);

        Button bouton = new Button(contexte);
        bouton.setOnClickListener(this);
        bouton.setLayoutParams(paramsBouton);

        addView(numero);
        addView(typeBiere);
        addView(dateCreation);
        addView(bouton);
    }

    public Brassin getBrassin() {
        return brassin;
    }

    @Override
    public void onClick(View v) {
        FragmentVueBrassin fragmentVueBrassin = new FragmentVueBrassin();
        Bundle args = new Bundle();
        args.putLong("id", brassin.getId());
        fragmentVueBrassin.setArguments(args);

        FragmentTransaction transaction = fragmentListeBrassin.getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueBrassin);
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
        transaction.addToBackStack(null).commit();
    }
}
