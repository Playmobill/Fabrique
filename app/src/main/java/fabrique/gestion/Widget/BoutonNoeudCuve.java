package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import fabrique.gestion.FragmentGestion.FragmentChemin;
import fabrique.gestion.Objets.NoeudCuve;

public class BoutonNoeudCuve extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;

    private NoeudCuve noeudPrecedent, noeudActuel;

    public BoutonNoeudCuve(Context contexte) {
        super(contexte);
    }

    public BoutonNoeudCuve(Context contexte, FragmentChemin fragmentChemin, NoeudCuve noeudPrecedent, NoeudCuve noeudActuel) {
        super(contexte);
        this.fragmentChemin = fragmentChemin;
        this.noeudPrecedent = noeudPrecedent;
        this.noeudActuel = noeudActuel;

        TableRow.LayoutParams parametre = new TableRow.LayoutParams();
        parametre.setMargins(5, 5, 5, 5);
        setLayoutParams(parametre);
        setBackgroundColor(Color.LTGRAY);
        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        if (noeudActuel != null) {
            setText(noeudActuel.getEtat(contexte).getTexte());
        } else {
            setText("Ajouter");
        }
    }

    public NoeudCuve getNoeudActif() {
        return noeudActuel;
    }

    @Override
    public void onClick(View v) {
        if (noeudActuel != null) {
            fragmentChemin.setBtnEtatCuveAvecBrassinSelectionne(this);
        }
        else {
            if (noeudPrecedent == null) {
                fragmentChemin.ajouterCuve(-1);
            } else {
                fragmentChemin.ajouterCuve(noeudPrecedent.getId());
            }
        }
    }
}
