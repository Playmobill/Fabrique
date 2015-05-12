package fabrique.gestion.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentGestion.chemin.FragmentChemin;
import fabrique.gestion.Objets.EtatFut;

public class BoutonEtatFut extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;
    private EtatFut etat;

    public BoutonEtatFut(Context context) {
        super(context);
    }

    public BoutonEtatFut(Context context, FragmentChemin fragmentChemin, EtatFut etat) {
        super(context);

        this.fragmentChemin = fragmentChemin;
        this.etat = etat;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        setText(etat.getTexte());
    }

    public EtatFut getEtat() {
        return etat;
    }

    @Override
    public void onClick(View v) {
        if (etat.getAvecBrassin()) {
            fragmentChemin.setBtnEtatFutAvecBrassin(this);
        } else {
            fragmentChemin.setBtnEtatFutSansBrassin(this);
        }
    }
}
