package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import fabrique.gestion.FragmentGestion.FragmentChemin;
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

        TableRow.LayoutParams parametre = new TableRow.LayoutParams();
        parametre.setMargins(5, 5, 5, 5);
        setLayoutParams(parametre);
        setBackgroundColor(Color.LTGRAY);
        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        setText(etat.getTexte());
    }

    public EtatFermenteur getEtat() {
        return etat;
    }

    @Override
    public void onClick(View v) {
        if (etat.getAvecBrassin()) {
            fragmentChemin.setBtnEtatFermenteurAvecBrassin(this);
        } else {
            fragmentChemin.setBtnEtatFermenteurSansBrassin(this);
        }
    }
}
