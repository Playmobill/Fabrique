package fabrique.gestion.FragmentGestion;

import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import fabrique.gestion.R;

/**
 * Created by thibaut on 30/04/15.
 */
public class BoutonCalendrier extends RelativeLayout implements View.OnClickListener {

    private FragmentCalendrier parent;
    private Button bouton;
    private long date;

    public BoutonCalendrier(Context contexte) {
        super(contexte);
    }

    public BoutonCalendrier(Context contexte, FragmentCalendrier parent, long date, int longueur, int hauteur, String evenement_, int nbEvent_) {
        super(contexte);

        this.parent = parent;
        this.date = date;

        bouton = new Button(contexte);
        bouton.setLayoutParams(new ViewGroup.LayoutParams(longueur, hauteur));
        bouton.setOnClickListener(this);
        this.addView(bouton);

        LayoutParams parametres = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parametres.addRule(ALIGN_PARENT_LEFT);
        parametres.addRule(ALIGN_PARENT_TOP);

            TextView textJour = new TextView(contexte);
            textJour.setLayoutParams(parametres);
            textJour.setPadding(10, 10, 0, 0);
                Calendar calendrier = Calendar.getInstance();
                calendrier.setTimeInMillis(date);
            textJour.setText(calendrier.get(Calendar.DAY_OF_MONTH) + "");
        this.addView(textJour);

        TextView evenement = new TextView(contexte);
        evenement.setText(evenement_);
        parametres = new LayoutParams(longueur,(int)evenement.getTextSize()+2 );
        parametres.addRule(ALIGN_PARENT_LEFT);
        parametres.addRule(CENTER_VERTICAL);
        evenement.setLayoutParams(parametres);
        evenement.setPadding(10, 0, 10, 0);

        this.addView(evenement);

        if(nbEvent_ > 0) {
            TextView nbEvent = new TextView(contexte);
            nbEvent.setText("+" + nbEvent_);
            parametres = new LayoutParams(longueur, (int) nbEvent.getTextSize() + 2);
            parametres.addRule(ALIGN_PARENT_LEFT);
            parametres.addRule(CENTER_VERTICAL);
            nbEvent.setLayoutParams(parametres);
            nbEvent.setY(nbEvent.getY()+evenement.getTextSize()+5);
            nbEvent.setPadding(10, 0, 10, 0);

            this.addView(nbEvent);
        }
    }

    public long getDate() {
        return date;
    }

    @Override
    public void onClick(View v) {
        if (v.equals(bouton)) {
            FragmentJour fragmentJour = new FragmentJour();
            Bundle args = new Bundle();
            args.putLong("date", date);
            fragmentJour.setArguments(args);

            FragmentTransaction transaction = parent.getFragmentManager().beginTransaction();
            transaction.replace(R.id.onglet, fragmentJour);
            transaction.setTransition((FragmentTransaction.TRANSIT_FRAGMENT_OPEN));
            transaction.addToBackStack(null).commit();
        }
    }
}
