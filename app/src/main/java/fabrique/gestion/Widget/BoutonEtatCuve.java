package fabrique.gestion.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentGestion.chemin.FragmentChemin;
import fabrique.gestion.Objets.EtatCuve;

public class BoutonEtatCuve extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;
    private EtatCuve etat;

    public BoutonEtatCuve(Context context) {
        super(context);
    }

    public BoutonEtatCuve(Context context, FragmentChemin fragmentChemin, EtatCuve etat) {
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
