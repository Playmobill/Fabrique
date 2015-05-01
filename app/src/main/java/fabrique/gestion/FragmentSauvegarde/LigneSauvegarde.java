package fabrique.gestion.FragmentSauvegarde;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import java.io.File;

public class LigneSauvegarde extends TextView implements View.OnClickListener {

    private FragmentSauvegarde parent;

    private File fichier;

    public LigneSauvegarde(Context context) {
        super(context);
    }

    public LigneSauvegarde(Context context, FragmentSauvegarde parent, File fichier) {
        this(context);
        this.parent = parent;
        this.fichier = fichier;
        setText(fichier.getName());
        setPadding(5, 5, 5, 5);
        this.setOnClickListener(this);
    }

    protected void enleverCouleur() {
        setBackgroundColor(Color.WHITE);
    }

    protected File getFichier() {
        return fichier;
    }

    @Override
    public void onClick(View v) {
        setBackgroundColor(Color.RED);
        parent.actif(this);
    }
}
