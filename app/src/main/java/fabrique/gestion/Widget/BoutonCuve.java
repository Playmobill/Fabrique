package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;

import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.EtatCuve;
import fabrique.gestion.R;

public class BoutonCuve extends Button {

    public BoutonCuve(Context contexte, Cuve cuve) {
        super(new ContextThemeWrapper(contexte, R.style.bouton));
        setGravity(Gravity.CENTER);

        EtatCuve etat = cuve.getEtat(contexte);
        String texteEtat = "";
        int couleurTexte = Color.BLACK;
        int couleurFond = Color.WHITE;
        if (etat != null) {
            texteEtat = etat.getTexte();
            couleurTexte = etat.getCouleurTexte();
            couleurFond = etat.getCouleurFond();
        }
        String texte = "C" + cuve.getNumero() + "\n";
        texte = texte + cuve.getCapacite() + "L \n";
        texte = texte + cuve.getEmplacement(contexte) + "\n";
        texte = texte + texteEtat + "\n";
        if (cuve.getBrassin(contexte) != null) {
            texte = texte + cuve.getBrassin(contexte).getNumero();
        }
        texte = texte + "\n" + cuve.getCommentaireEtat() + "\n";
        texte = texte + "depuis " + cuve.getDureeEtat() + "\n";
        texte = texte + cuve.getDateEtat() + "\n";
        setText(texte);

        setTextColor(couleurTexte);
        setBackgroundColor(couleurFond);
    }
}