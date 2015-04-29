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
import fabrique.gestion.FragmentTableauDeBord.FragmentVueCuve;
import fabrique.gestion.Objets.Cuve;
import fabrique.gestion.Objets.EtatCuve;
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

        EtatCuve etat = cuve.getEtat(contexte);

        StringBuilder texteEtat = new StringBuilder();
        int couleurTexte = Color.BLACK;
        int couleurFond = Color.WHITE;
        if (etat != null) {
            texteEtat.append(etat.getTexte());
            couleurTexte = etat.getCouleurTexte();
            couleurFond = etat.getCouleurFond();
        }
        StringBuilder texte = new StringBuilder();
        texte.append("C").append(cuve.getNumero()).append("\n");
        texte.append(cuve.getCapacite()).append("L").append("\n");
        texte.append(cuve.getEmplacement(contexte).getTexte()).append("\n");
        texte.append(texteEtat).append("\n");
        if (cuve.getBrassin(contexte) != null) {
            texte.append(cuve.getBrassin(contexte).getNumero());
        }
        texte.append("\n");
        /*texte.append(cuve.getCommentaireEtat()).append("\n");
        texte.append("depuis ").append(cuve.getDureeEtat()).append("\n");*/
        texte.append(cuve.getDateEtat());

        setText(texte.toString());
        setTextColor(couleurTexte);
        setBackgroundColor(couleurFond);
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