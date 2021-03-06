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

        String texteEtat = "État non défini";
        int couleurTexteEtat = Color.BLACK;
        int couleurFondEtat = Color.WHITE;
        if ((fermenteur.getNoeud(contexte) != null) && (fermenteur.getNoeud(contexte).getEtat(contexte) != null)) {
            texteEtat = fermenteur.getNoeud(contexte).getEtat(contexte).getTexte();
            couleurTexteEtat = fermenteur.getNoeud(contexte).getEtat(contexte).getCouleurTexte();
            couleurFondEtat = fermenteur.getNoeud(contexte).getEtat(contexte).getCouleurFond();
        }

        StringBuilder texte = new StringBuilder();
        texte.append("F").append(fermenteur.getNumero()).append("\n");
        texte.append(fermenteur.getCapacite()).append("L").append("\n");
        texte.append(fermenteur.getEmplacement(contexte).getTexte()).append("\n");
        texte.append(texteEtat).append("\n");
        if (fermenteur.getBrassin(contexte) != null) {
            texte.append(fermenteur.getBrassin(contexte).getRecette(contexte).getAcronyme()).append(" #").append(fermenteur.getBrassin(contexte).getNumero());
            setTextColor(fermenteur.getBrassin(contexte).getRecette(contexte).getCouleurTexte());
            setBackgroundColor(fermenteur.getBrassin(contexte).getRecette(contexte).getCouleurFond());
        } else {
            setTextColor(couleurTexteEtat);
            setBackgroundColor(couleurFondEtat);
        }
        texte.append("\n").append(fermenteur.getDateEtat());

        setText(texte.toString());
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
