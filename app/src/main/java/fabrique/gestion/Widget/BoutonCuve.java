package fabrique.gestion.Widget;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;

import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.R;

public class BoutonCuve extends Button {

    private Cuve cuve;

    public BoutonCuve(Context contexte, Cuve cuve) {
        super(new ContextThemeWrapper(contexte, R.style.bouton));
        this.cuve = cuve;
        setGravity(Gravity.CENTER);

        String texte = "C" + cuve.getNumero() + "\n";
        texte = texte + cuve.getCapacite() + "L \n";
        texte = texte + cuve.getEmplacement(getContext()) + "\n";
        texte = texte + cuve.getEtat(getContext()) + "\n";
        if (cuve.getBrassin() != null) {
            texte = texte + cuve.getBrassin().getNumero();
        }
        texte = texte + "\n" + cuve.getCommentaireEtat() + "\n";
        texte = texte + "depuis " + cuve.getDureeEtat() + "\n";
        texte = texte + cuve.getDateEtat() + "\n";
        setText(texte);

        setTextColor(cuve.getCouleurTexte(contexte));
        setBackgroundColor(cuve.getCouleurFond(contexte));
    }
}