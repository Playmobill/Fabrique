package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import fabrique.gestion.FragmentGestion.FragmentChemin;
import fabrique.gestion.Objets.NoeudFermenteur;

public class BoutonNoeudFermenteur extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;

    private NoeudFermenteur noeudPrecedent, noeudActuel;

    private boolean avecBrassin;

    public BoutonNoeudFermenteur(Context contexte) {
        super(contexte);
    }

    public BoutonNoeudFermenteur(Context contexte, FragmentChemin fragmentChemin, NoeudFermenteur noeudPrecedent, NoeudFermenteur noeudActuel, boolean avecBrassin) {
        super(contexte);

        this.fragmentChemin = fragmentChemin;
        this.noeudPrecedent = noeudPrecedent;
        this.noeudActuel = noeudActuel;
        this.avecBrassin = avecBrassin;

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

    public NoeudFermenteur getNoeudActif() {
        return noeudActuel;
    }

    @Override
    public void onClick(View v) {
        if (noeudActuel != null) {
            fragmentChemin.setBtnNoeudFermenteurSelectionne(this);
        }
        else {
            if (noeudPrecedent == null) {
                fragmentChemin.ajouterCheminDansFermenteur(-1, avecBrassin);
            } else {
                fragmentChemin.ajouterCheminDansFermenteur(noeudPrecedent.getId(), avecBrassin);
            }
        }
    }
}
