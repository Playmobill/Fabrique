package fabrique.gestion.Widget;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentTableauDeBord.FragmentTableauDeBord;
import fabrique.gestion.FragmentTableauDeBord.FragmentVueCuve;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.R;

public class BoutonCuve extends Button implements View.OnClickListener {

    private FragmentTableauDeBord fragmentTableauDeBord;

    private Cuve cuve;

    public BoutonCuve(Context contexte) {
        super(contexte);
    }

    public BoutonCuve(Context contexte, FragmentTableauDeBord fragmentTableauDeBord, Cuve cuve) {
        super(new ContextThemeWrapper(contexte, R.style.bouton));

        this.fragmentTableauDeBord = fragmentTableauDeBord;
        this.cuve = cuve;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        StringBuilder texte = new StringBuilder();
        texte.append("C").append(cuve.getNumero()).append("\n");
        texte.append(cuve.getCapacite()).append("L").append("\n");
        texte.append(cuve.getEmplacement(contexte).getTexte()).append("\n");
        texte.append(cuve.getEtat(contexte).getTexte()).append("\n");
        if (cuve.getBrassin(contexte) != null) {
            texte.append(cuve.getBrassin(contexte).getRecette(contexte).getAcronyme()).append(" / ").append(cuve.getBrassin(contexte).getNumero());
            setTextColor(cuve.getBrassin(contexte).getRecette(contexte).getCouleurTexte());
            setBackgroundColor(cuve.getBrassin(contexte).getRecette(contexte).getCouleurFond());
        } else {
            setTextColor(cuve.getEtat(contexte).getCouleurTexte());
            setBackgroundColor(cuve.getEtat(contexte).getCouleurFond());
        }
        texte.append("\n").append(cuve.getDateEtat());

        setText(texte.toString());
    }

    @Override
    public void onClick(View v) {
        FragmentVueCuve fragmentVueCuve = new FragmentVueCuve();
        Bundle args = new Bundle();
        args.putLong("id", cuve.getId());
        fragmentVueCuve.setArguments(args);

        FragmentTransaction transaction = fragmentTableauDeBord.getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueCuve);
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
        transaction.addToBackStack(null).commit();
    }
}
