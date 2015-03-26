package fabrique.gestion.Widget;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public abstract class Bouton extends Button implements View.OnClickListener {

    private boolean min;

    public Bouton(Context context) {
        super(context);
        setOnClickListener(this);
    }

    public void min() {
        min = true;
    }

    public void max() {
        min = false;
    }

    public void onClick(View v) {
        if (min) {
            max();
        } else {
            min();
        }
    }
}
