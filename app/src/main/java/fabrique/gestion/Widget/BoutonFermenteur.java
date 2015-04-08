package fabrique.gestion.Widget;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;

import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.R;

public class BoutonFermenteur extends Button {

    public BoutonFermenteur(Context contexte, Fermenteur fermenteur) {
        super(new ContextThemeWrapper(contexte, R.style.bouton));
        setGravity(Gravity.CENTER);

        String texte = "F" + fermenteur.getNumero() + "\n";
        texte = texte + fermenteur.getCapacite() + "L";
        texte = texte + "\n" + fermenteur.getEmplacement(contexte) + "\n";
        texte = texte + fermenteur.getEtat(contexte) + "\n";
        if (fermenteur.getBrassin() != null) {
            texte = texte + fermenteur.getBrassin().getNumero();
        }
        texte = texte + "\n" + fermenteur.getDateEtat();
        setText(texte);

        setTextColor(fermenteur.getCouleurTexte(contexte));
        setBackgroundColor(fermenteur.getCouleurFond(contexte));
    }
}
