package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import fabrique.gestion.FragmentGestion.FragmentChemin;
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

        TableRow.LayoutParams parametre = new TableRow.LayoutParams();
        parametre.setMargins(5, 5, 5, 5);
        setLayoutParams(parametre);
        setBackgroundColor(Color.LTGRAY);
        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        setText(etat.getTexte());
    }

    public EtatCuve getEtat() {
        return etat;
    }

    @Override
    public void onClick(View v) {
        if (etat.getAvecBrassin()) {
            fragmentChemin.setBtnEtatCuveAvecBrassin(this);
        } else {
            fragmentChemin.setBtnEtatCuveSansBrassin(this);
        }
    }
}
