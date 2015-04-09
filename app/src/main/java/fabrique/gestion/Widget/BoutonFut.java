package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;

import fabrique.gestion.Objets.EtatFut;
import fabrique.gestion.Objets.Fut;

public class BoutonFut extends Button {

    public BoutonFut(Context contexte, Fut fut) {
        super(contexte);

        setGravity(Gravity.CENTER);

        EtatFut etat = fut.getEtat(contexte);

        String texteEtat = "";
        int couleurTexte = Color.BLACK;
        int couleurFond = Color.WHITE;
        if (etat != null) {
            texteEtat = etat.getTexte();
            couleurTexte = etat.getCouleurTexte();
            couleurFond = etat.getCouleurFond();
        }
        String texte = "F" + fut.getNumero() + "\n";
        texte = texte + fut.getCapacite() + "L \n";
        texte = texte + texteEtat + "\n";
        if (fut.getBrassin(contexte) != null) {
            texte = texte + fut.getBrassin(contexte).getNumero();
        }
        texte = texte + "\n" + fut.getDateEtat() + "\n";

        setText(texte);
        setTextColor(couleurTexte);
        setBackgroundColor(couleurFond);
    }
}
