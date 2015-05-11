package fabrique.gestion.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentGestion.chemin.FragmentChemin;
import fabrique.gestion.Objets.EtatFermenteur;

public class BoutonEtatFermenteur extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;
    private EtatFermenteur etat;

    public BoutonEtatFermenteur(Context context) {
        super(context);
    }

    public BoutonEtatFermenteur(Context context, FragmentChemin fragmentChemin, EtatFermenteur etat) {
        super(context);

        this.fragmentChemin = fragmentChemin;
        this.etat = etat;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        setText(etat.getTexte());
    }

    @Override
    public void onClick(View v) {

    }
}
