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
    private NoeudCuve noeudActuel;

    public BoutonNoeudCuve(Context contexte) {
        super(contexte);
    }

    public BoutonNoeudCuve(Context contexte, FragmentChemin fragmentChemin, NoeudCuve noeudPrecedent, NoeudCuve noeudActuel) {
        super(contexte);

        this.fragmentChemin = fragmentChemin;
        this.noeudPrecedent = noeudPrecedent;
        this.noeudActuel = noeudActuel;

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

    }
}
