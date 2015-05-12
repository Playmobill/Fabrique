package fabrique.gestion.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentGestion.chemin.FragmentChemin;
import fabrique.gestion.Objets.NoeudFermenteur;

public class BoutonNoeudFermenteur extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;

    private NoeudFermenteur noeudPrecedent;

    public BoutonNoeudFermenteur(Context contexte) {
        super(contexte);
    }

    public BoutonNoeudFermenteur(Context contexte, FragmentChemin fragmentChemin, NoeudFermenteur noeudPrecedent, NoeudFermenteur noeudActuel) {
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
            fragmentChemin.ajouterFermenteur(-1);
        } else {
            fragmentChemin.ajouterFermenteur(noeudPrecedent.getId());
        }
    }
}
