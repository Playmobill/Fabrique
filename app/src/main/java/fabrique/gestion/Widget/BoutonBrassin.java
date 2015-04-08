package fabrique.gestion.Widget;

import android.content.Context;
import android.widget.Button;

import fabrique.gestion.Objets.Brassin;

/**
 * Created by thibaut on 08/04/15.
 */
public class BoutonBrassin extends Button {

    private Brassin brassin;

    public BoutonBrassin(Context context, Brassin brassin) {
        super(context);
        this.brassin=brassin;
    }

    public Brassin getBrassin() {
        return brassin;
    }
}
