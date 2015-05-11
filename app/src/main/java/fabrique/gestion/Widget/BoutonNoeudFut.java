package fabrique.gestion.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentGestion.chemin.FragmentChemin;
import fabrique.gestion.Objets.NoeudFut;

public class BoutonNoeudFut extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;

    private NoeudFut noeudPrecedent;
    private NoeudFut noeudActuel;

    public BoutonNoeudFut(Context contexte) {
        super(contexte);
    }

    public BoutonNoeudFut(Context contexte, FragmentChemin fragmentChemin, NoeudFut noeudPrecedent, NoeudFut noeudActuel) {
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
