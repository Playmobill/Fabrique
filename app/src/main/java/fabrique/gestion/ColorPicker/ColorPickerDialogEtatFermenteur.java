package fabrique.gestion.ColorPicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

public class ColorPickerDialogEtatFermenteur extends Dialog {

    private String elementModifier;
    private EditText txtEtat;

    public ColorPickerDialogEtatFermenteur(Context context, String elementModifier, EditText txtEtat) {
        super(context);
        this.elementModifier = elementModifier;
        this.txtEtat = txtEtat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(new ColorPickerViewEtatFermenteur(getContext(), elementModifier, txtEtat));
        setTitle("Choisissez la couleur");
    }
}