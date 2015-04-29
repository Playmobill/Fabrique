package fabrique.gestion.Widget;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentListe.FragmentListeFut;
import fabrique.gestion.FragmentListe.FragmentVueFut;
import fabrique.gestion.Objets.EtatFut;
import fabrique.gestion.Objets.Fut;
import fabrique.gestion.R;

public class BoutonFut extends Button implements View.OnClickListener {

    private FragmentListeFut fragmentListeFut;

    private Fut fut;

    public BoutonFut(Context contexte) {
        super(contexte);
    }

    public BoutonFut(Context contexte, FragmentListeFut fragmentListeFut, Fut fut) {
        this(contexte);

        this.fragmentListeFut = fragmentListeFut;

        this.fut = fut;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        EtatFut etat = fut.getEtat(contexte);

        StringBuilder texteEtat = new StringBuilder();
        int couleurTexte = Color.BLACK;
        int couleurFond = Color.WHITE;
        if (etat != null) {
            texteEtat.append(etat.getTexte());
            couleurTexte = etat.getCouleurTexte();
            couleurFond = etat.getCouleurFond();
        }
        StringBuilder texte = new StringBuilder();
        texte.append(fut.getNumero()).append("\n");
        texte.append(fut.getCapacite()).append("L").append("\n");
        texte.append(texteEtat.toString()).append("\n");
        if (fut.getBrassin(contexte) != null) {
            texte.append(fut.getBrassin(contexte).getNumero());
        }
        texte.append("\n").append(fut.getDateEtat());

        setText(texte.toString());
        setTextColor(couleurTexte);
        setBackgroundColor(couleurFond);
    }

    @Override
    public void onClick(View v) {
        FragmentVueFut fragmentVueFut = new FragmentVueFut();
        Bundle args = new Bundle();
        args.putLong("id", fut.getId());
        fragmentVueFut.setArguments(args);

        FragmentTransaction transaction = fragmentListeFut.getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueFut);
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
        transaction.addToBackStack(null).commit();
    }
}
