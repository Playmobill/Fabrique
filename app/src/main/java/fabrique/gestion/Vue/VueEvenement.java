package fabrique.gestion.Vue;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import fabrique.gestion.BDD.TableCalendrier;
import fabrique.gestion.FragmentGestion.FragmentJour;
import fabrique.gestion.Objets.Calendrier;

/**
 * Created by thibaut on 26/05/15.
 */
public class VueEvenement extends LinearLayout implements View.OnClickListener{

    private Button supprimer;
    private TextView texteEvenement;
    private Calendrier evenement;
    private FragmentJour source;

    public VueEvenement(Context context, Calendrier evenement, FragmentJour source) {
        super(context);
        this.setOrientation(LinearLayout.HORIZONTAL);

        this.source = source;

        supprimer = new Button(context);
        supprimer.setText("x");
        supprimer.setOnClickListener(this);

        this.evenement = evenement;

        texteEvenement = new TextView(context);
        texteEvenement.setText(this.evenement.getNomEvenement());

        this.addView(supprimer);
        this.addView(texteEvenement);
    }

    public Button getSupprimer(){
        return supprimer;
    }

    @Override
    public void onClick(View view) {
        if(view.equals(supprimer)){
            TableCalendrier.instance(getContext()).supprimer(evenement.getId());
            source.initialiserTexte();
        }
    }
}
