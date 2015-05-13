package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import fabrique.gestion.FragmentGestion.FragmentChemin;
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

        TableRow.LayoutParams parametre = new TableRow.LayoutParams();
        parametre.setMargins(5, 5, 5, 5);
        setLayoutParams(parametre);
        setBackgroundColor(Color.LTGRAY);
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
