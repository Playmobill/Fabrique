package fabrique.gestion.ColorPicker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

public class ColorPickerDialog extends Dialog {

    private OnColorChangedListener mListener;

    public ColorPickerDialog(Context context, OnColorChangedListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnColorChangedListener l = new OnColorChangedListener() {
            public void colorChanged(int color) {
                mListener.colorChanged(color);
                dismiss();
            }
        };

        setContentView(new ColorPickerView(getContext(), l));
        setTitle("Choisissez la couleur");
    }
}