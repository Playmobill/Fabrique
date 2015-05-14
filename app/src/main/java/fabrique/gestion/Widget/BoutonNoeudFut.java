package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableRow;

import fabrique.gestion.FragmentGestion.FragmentChemin;
import fabrique.gestion.Objets.NoeudFut;

public class BoutonNoeudFut extends Button implements View.OnClickListener {

    private FragmentChemin fragmentChemin;

    private NoeudFut noeudPrecedent, noeudActuel;

    private boolean avecBrassin;

    public BoutonNoeudFut(Context contexte) {
        super(contexte);
    }

    public BoutonNoeudFut(Context contexte, FragmentChemin fragmentChemin, NoeudFut noeudPrecedent, NoeudFut noeudActuel, boolean avecBrassin) {
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

    public NoeudFut getNoeudActif() {
        return noeudActuel;
    }

    @Override
    public void onClick(View v) {
        if (noeudActuel != null) {
            fragmentChemin.setBtnNoeudFutSelectionne(this);
        }
        else {
            if (noeudPrecedent == null) {
                fragmentChemin.ajouterCheminDansFut(-1, avecBrassin);
            } else {
                fragmentChemin.ajouterCheminDansFut(noeudPrecedent.getId(), avecBrassin);
            }
        }
    }
}
