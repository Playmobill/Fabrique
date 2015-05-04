package fabrique.gestion.Widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import fabrique.gestion.FragmentListe.FragmentListeRecette;
import fabrique.gestion.Objets.Recette;

public class BoutonRecette extends Button implements View.OnClickListener {

    private FragmentListeRecette fragmentListeRecette;

    private Recette recette;

    public BoutonRecette(Context contexte) {
        super(contexte);
    }

    public BoutonRecette(Context contexte, FragmentListeRecette fragmentListeRecette, Recette recette) {
        this(contexte);

        this.fragmentListeRecette = fragmentListeRecette;

        this.recette = recette;

        setOnClickListener(this);

        setGravity(Gravity.CENTER);

        StringBuilder texte = new StringBuilder();
        texte.append(recette.getNom()).append("\n");
        texte.append(recette.getAcronyme()).append("L").append("\n");
        texte.append(recette.getCouleur());

        //setTextColor(recette.getCouleurTexte());
        //setBackgroundColor(recette.getCouleurFond());
    }

    @Override
    public void onClick(View v) {
        /*VueRecette fragmentVueRecette = new VueRecette(contexte);
        Bundle args = new Bundle();
        args.putLong("id", fut.getId());
        fragmentVueRecette.setArguments(args);

        FragmentTransaction transaction = fragmentListeRecette.getFragmentManager().beginTransaction();
        transaction.replace(R.id.onglet, fragmentVueRecette);
        transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
        transaction.addToBackStack(null).commit();*/
    }
}
