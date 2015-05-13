package fabrique.gestion.Widget;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentListe.FragmentListeFut;
import fabrique.gestion.FragmentListe.FragmentVueFut;
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

        StringBuilder texte = new StringBuilder();
        texte.append(fut.getNumero()).append("\n");
        texte.append(fut.getCapacite()).append("L").append("\n");
        texte.append(fut.getEtat(contexte).getTexte()).append("\n");
        if (fut.getBrassin(contexte) != null) {
            texte.append(fut.getBrassin(contexte).getRecette(contexte).getAcronyme()).append(" #").append(fut.getBrassin(contexte).getNumero());
            setTextColor(fut.getBrassin(contexte).getRecette(contexte).getCouleurTexte());
            setBackgroundColor(fut.getBrassin(contexte).getRecette(contexte).getCouleurFond());
        } else {
            setTextColor(fut.getEtat(contexte).getCouleurTexte());
            setBackgroundColor(fut.getEtat(contexte).getCouleurFond());
        }
        texte.append("\n").append(fut.getDateEtat());

        setText(texte.toString());
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
