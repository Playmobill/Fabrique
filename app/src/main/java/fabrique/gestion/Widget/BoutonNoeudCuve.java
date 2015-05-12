package fabrique.gestion.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentGestion.chemin.FragmentChemin;
import fabrique.gestion.Objets.NoeudCuve;

public class BoutonNoeudCuve extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;

    private NoeudCuve noeudPrecedent;

    public BoutonNoeudCuve(Context contexte) {
        super(contexte);
    }

    public BoutonNoeudCuve(Context contexte, FragmentChemin fragmentChemin, NoeudCuve noeudPrecedent, NoeudCuve noeudActuel) {
        super(contexte);
        this.fragmentChemin = fragmentChemin;
        this.noeudPrecedent = noeudPrecedent;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        if (noeudActuel != null) {
            setText(noeudActuel.getEtat(contexte).getTexte());
        } else {
            setText("Ajouter");
        }
    }

    @Override
    public void onClick(View v) {
        if (noeudPrecedent == null) {
            fragmentChemin.ajouterCuve(-1);
        } else {
            fragmentChemin.ajouterCuve(noeudPrecedent.getId());
        }
    }
}
