package fabrique.gestion.Widget;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Button;

import fabrique.gestion.Objets.EtatFermenteur;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.R;

public class BoutonFermenteur extends Button {

    public BoutonFermenteur(Context contexte, Fermenteur fermenteur) {
        super(new ContextThemeWrapper(contexte, R.style.bouton));
        setGravity(Gravity.CENTER);

        EtatFermenteur etat = fermenteur.getEtat(contexte);
        String texteEtat = "";
        int couleurTexte = Color.BLACK;
        int couleurFond = Color.WHITE;
        if (etat != null) {
            texteEtat = etat.getTexte();
            couleurTexte = etat.getCouleurTexte();
            couleurFond = etat.getCouleurFond();
        }
        Log.i("BoutonFermenteur", "" + (etat == null));
        String texte = "F" + fermenteur.getNumero() + "\n";
        texte = texte + fermenteur.getCapacite() + "L";
        texte = texte + "\n" + fermenteur.getEmplacement(contexte).getTexte() + "\n";
        texte = texte + texteEtat + "\n";
        if (fermenteur.getBrassin(contexte) != null) {
            texte = texte + fermenteur.getBrassin(contexte).getNumero();
        }
        texte = texte + "\n" + fermenteur.getDateEtat();
        setText(texte);

        setTextColor(couleurTexte);
        setBackgroundColor(couleurFond);
    }
}
