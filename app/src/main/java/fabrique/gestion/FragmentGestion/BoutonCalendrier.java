package fabrique.gestion.FragmentGestion;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by thibaut on 30/04/15.
 */
public class BoutonCalendrier extends RelativeLayout {

    protected Button bouton;
    protected int jour, mois, annee;
    protected TextView textJour;
    protected TextView evenement;
    protected LayoutParams parametres;

    public BoutonCalendrier(Context contexte, int jour_, int mois_, int annee_, int longueur, int hauteur, String evenement_) {
        super(contexte);

        bouton = new Button(contexte);
        bouton.setLayoutParams(new ViewGroup.LayoutParams(longueur, hauteur));
        this.addView(bouton);

        parametres = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parametres.addRule(ALIGN_PARENT_LEFT);
        parametres.addRule(ALIGN_PARENT_TOP);

        jour = jour_;
        mois = mois_;
        annee = annee_;

        textJour = new TextView(contexte);
        textJour.setText("" + jour_);
        this.addView(textJour);
        textJour.setLayoutParams(parametres);
        textJour.setPadding(10, 10, 0, 0);

        evenement = new TextView(contexte);
        evenement.setText(evenement_);
        this.addView(evenement);
        parametres = new LayoutParams(longueur,(int)evenement.getTextSize()+2 );
        parametres.addRule(ALIGN_PARENT_LEFT);
        parametres.addRule(CENTER_VERTICAL);
        evenement.setLayoutParams(parametres);
        evenement.setPadding(10,0,10,0);
    }
}
