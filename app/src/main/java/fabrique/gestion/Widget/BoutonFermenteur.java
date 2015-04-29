package fabrique.gestion.Widget;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentTableauDeBord.FragmentTableauDeBord;
import fabrique.gestion.FragmentTableauDeBord.FragmentVueFermenteur;
import fabrique.gestion.Objets.EtatFermenteur;
import fabrique.gestion.Objets.Fermenteur;
import fabrique.gestion.R;

public class BoutonFermenteur extends Button implements View.OnClickListener {

    private FragmentTableauDeBord fragmentTableauDeBord;

    private Fermenteur fermenteur;

    public BoutonFermenteur(Context contexte) {
        super(contexte);
    }

    public BoutonFermenteur(Context contexte, FragmentTableauDeBord fragmentTableauDeBord, Fermenteur fermenteur) {
        super(new ContextThemeWrapper(contexte, R.style.bouton));

        this.fragmentTableauDeBord = fragmentTableauDeBord;
        this.fermenteur = fermenteur;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        EtatFermenteur etat = fermenteur.getEtat(contexte);

        StringBuilder texteEtat = new StringBuilder();
        int couleurTexte = Color.BLACK;
        int couleurFond = Color.WHITE;
        if (etat != null) {
            texteEtat.append(etat.getTexte());
            couleurTexte = etat.getCouleurTexte();
            couleurFond = etat.getCouleurFond();
        }
        StringBuilder texte = new StringBuilder();
        texte.append("F").append(fermenteur.getNumero()).append("\n");
        texte.append(fermenteur.getCapacite()).append("L").append("\n");
        texte.append(fermenteur.getEmplacement(contexte).getTexte()).append("\n");
        texte.append(texteEtat.toString()).append("\n");
        if (fermenteur.getBrassin(contexte) != null) {
            texte.append(fermenteur.getBrassin(contexte).getNumero());
        }
        texte.append("\n").append(fermenteur.getDateEtat());

        setText(texte.toString());
        setTextColor(couleurTexte);
        setBackgroundColor(couleurFond);
    }

    @Override
    public void onClick(View v) {
        FragmentVueFermenteur fragmentVueFermenteur = new FragmentVueFermenteur();
        Bundle args = new Bundle();
        args.putLong("id", fermenteur.getId());
        fragmentVueFermenteur.setArguments(args);

        FragmentTransaction transaction = fragmentTableauDeBord.getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueFermenteur);
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
        transaction.addToBackStack(null).commit();
    }
}
