package fabrique.gestion.Widget;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentGestion.chemin.FragmentChemin;
import fabrique.gestion.Objets.EtatCuve;

public class BoutonEtatCuve extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;
    private EtatCuve etat;

    public BoutonEtatCuve(Context contexte) {
        super(contexte);
    }

    public BoutonEtatCuve(Context contexte, FragmentChemin fragmentChemin, EtatCuve etat) {
        super(contexte);

        this.fragmentChemin = fragmentChemin;
        this.etat = etat;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        setText(etat.getTexte());
    }

    public EtatCuve getEtat() {
        return etat;
    }

    @Override
    public void onClick(View v) {
        Log.i("BoutonEtatCuve", etat.getTexte());
        if (etat.getAvecBrassin()) {
            fragmentChemin.setBtnEtatCuveAvecBrassin(this);
        } else {
            fragmentChemin.setBtnEtatCuveSansBrassin(this);
        }
    }
}
