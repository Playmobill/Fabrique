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
        setText("C" + cuve.getNumero() + "\n" + "\n" + cuve.getEtat());
    }

    public void max() {
        super.max();
        String texte = "";
        if (cuve.getNumero() != 0) {
            texte = texte + "C" + cuve.getNumero();
        }
        texte = texte + "\n";

        if (cuve.getCapacite() != 0) {
            texte = texte + cuve.getCapacite() + "L";
        }

        texte = texte + "\n" + cuve.getEtat() + "\n";

        texte = texte + cuve.getCommentaireEtat() + "\n";

        texte = texte + cuve.getDateEtat();
        setText(texte);
    }
}
