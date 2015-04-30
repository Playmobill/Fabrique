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
    protected TextView jour;
    protected TextView evenement;
    protected LayoutParams parametres;

    public BoutonCalendrier(Context contexte, int jour_, int longueur, int hauteur) {
        super(contexte);

        bouton = new Button(contexte);
        bouton.setLayoutParams(new ViewGroup.LayoutParams(longueur, hauteur));

        parametres = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parametres.addRule(ALIGN_PARENT_LEFT);
        parametres.addRule(ALIGN_PARENT_TOP);

        jour = new TextView(contexte);
        jour.setText(""+jour_);
        jour.setLayoutParams(parametres);
        jour.setPadding(10, 10, 0, 0);

        this.addView(bouton);
        this.addView(jour);
    }
}
