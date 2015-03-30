package fabrique.gestion.Widget;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;

import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.R;

public class BoutonFermenteur extends Bouton {

    Fermenteur fermenteur;

    public BoutonFermenteur(Context context, Fermenteur fermenteur) {
        super(new ContextThemeWrapper(context, R.style.bouton));
        this.fermenteur = fermenteur;
        setGravity(Gravity.CENTER);
        min();
    }

    public void min() {
        super.min();
        String texte = "F" + fermenteur.getNumero() + "\n" + "\n" + fermenteur.getEtat(null) + "\n";
        if (fermenteur.getBrassin() != null) {
            texte = texte + fermenteur.getBrassin().getNumero();
        }
        setText(texte);
    }

    public void max() {
        super.max();
        String texte = "F" + fermenteur.getNumero() + "\n";

        if (fermenteur.getCapacite() != 0) {
            texte = texte + fermenteur.getCapacite() + "L";
        }

        texte = texte + "\n" + fermenteur.getEmplacement(null) + "\n";

        texte = texte + fermenteur.getEtat(null) + "\n";

        if (fermenteur.getBrassin() != null) {
            texte = texte + fermenteur.getBrassin().getNumero();
        }

        texte = texte + "\n" + fermenteur.getDateEtat();
        setText(texte);
    }
}
