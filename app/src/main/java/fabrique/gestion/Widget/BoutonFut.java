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

        String texteEtat = "Non utilisé";
        int couleurTexteEtat = Color.BLACK;
        int couleurFondEtat = Color.WHITE;
        if ((fut.getNoeud(contexte) != null) && (fut.getNoeud(contexte).getEtat(contexte) != null)) {
            texteEtat = fut.getNoeud(contexte).getEtat(contexte).getTexte();
            couleurTexteEtat = fut.getNoeud(contexte).getEtat(contexte).getCouleurTexte();
            couleurFondEtat = fut.getNoeud(contexte).getEtat(contexte).getCouleurFond();
        }
        
        StringBuilder texte = new StringBuilder();
        texte.append(fut.getNumero()).append("\n");
        texte.append(fut.getCapacite()).append("L").append("\n");
        texte.append(texteEtat).append("\n");
        if (fut.getBrassin(contexte) != null) {
            texte.append(fut.getBrassin(contexte).getRecette(contexte).getAcronyme()).append(" #").append(fut.getBrassin(contexte).getNumero());
            setTextColor(fut.getBrassin(contexte).getRecette(contexte).getCouleurTexte());
            setBackgroundColor(fut.getBrassin(contexte).getRecette(contexte).getCouleurFond());
        } else {
            setTextColor(couleurTexteEtat);
            setBackgroundColor(couleurFondEtat);
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
