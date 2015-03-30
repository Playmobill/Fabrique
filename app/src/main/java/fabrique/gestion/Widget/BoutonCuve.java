package fabrique.gestion.Widget;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;

import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.R;

public class BoutonCuve extends Bouton {

    private Cuve cuve;

    public BoutonCuve(Context context, Cuve cuve) {
        super(new ContextThemeWrapper(context, R.style.bouton));
        this.cuve = cuve;
        setGravity(Gravity.CENTER);
        min();
    }

    public void min() {
        super.min();
        String texte = "C" + cuve.getNumero() + "\n" + "\n" + cuve.getEtat(null) + "\n";
        if (cuve.getBrassin() != null) {
            texte = texte + cuve.getBrassin().getNumero();
        }
        setText(texte);
    }

    public void max(Context ctxt) {
        super.max();
        String texte = "C" + cuve.getNumero() + "\n";

        if (cuve.getCapacite() != 0) {
            texte = texte + cuve.getCapacite() + "L";
        }

        texte = texte + "\n" + cuve.getEmplacement(ctxt) + "\n";

        texte = texte + cuve.getEtat(null) + "\n";

        if (cuve.getBrassin() != null) {
            texte = texte + cuve.getBrassin().getNumero();
        }

        texte = texte + "\n" + cuve.getCommentaireEtat() + "\n";

        texte = texte + cuve.getDateEtat();
        setText(texte);
    }
}
