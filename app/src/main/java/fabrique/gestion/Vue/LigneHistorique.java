package fabrique.gestion.Vue;

import android.app.DatePickerDialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.Objets.DateToString;
import fabrique.gestion.Objets.Historique;

public class LigneHistorique extends TableRow implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private View parent;
    private Historique historique;

    private TableRow.LayoutParams marge;
    private LinearLayout sous_ligne;
    private TextView date, entre, texte, btnModifier, btnSupprimer, btnValider, btnAnnuler;
    private EditText editTexte;

    //DatePicker
    private long longDate;
    private EditText editDate;

    protected LigneHistorique(Context context) {
        super(context);
    }

    protected LigneHistorique(Context context, View parent, Historique historique) {
        this(context);
        this.parent = parent;
        this.historique = historique;
        longDate = historique.getDate();
        initialiser();
        afficher();
    }

    private void initialiser() {
        marge = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        marge.setMargins(10, 2, 10, 2);

        sous_ligne = new LinearLayout(getContext());

        date = new TextView(getContext());

        editDate = new EditText(getContext());
        editDate.setTextSize(14);
        editDate.setOnClickListener(this);
        editDate.setFocusable(false);

        entre = new TextView(getContext());
        entre.setText(" : ");

        texte = new TextView(getContext());
        editTexte = new EditText(getContext());
        editTexte.setTextSize(14);

        btnModifier = new TextView(getContext());
        SpannableString modifier = new SpannableString("Modifier");
        modifier.setSpan(new UnderlineSpan(), 0, modifier.length(), 0);
        btnModifier.setText(modifier);
        btnModifier.setOnClickListener(this);

        btnSupprimer = new TextView(getContext());
        SpannableString supprimer = new SpannableString("Supprimer");
        supprimer.setSpan(new UnderlineSpan(), 0, supprimer.length(), 0);
        btnSupprimer.setText(supprimer);
        btnSupprimer.setOnClickListener(this);

        btnValider = new TextView(getContext());
        SpannableString valider = new SpannableString("Valider");
        valider.setSpan(new UnderlineSpan(), 0, valider.length(), 0);
        btnValider.setText(valider);
        btnValider.setOnClickListener(this);

        btnAnnuler = new TextView(getContext());
        SpannableString annuler = new SpannableString("Annuler");
        annuler.setSpan(new UnderlineSpan(), 0, annuler.length(), 0);
        btnAnnuler.setText(annuler);
        btnAnnuler.setOnClickListener(this);
    }

    private void afficher() {
        removeAllViews();
        sous_ligne.removeAllViews();
        date.setText(historique.getDateToString());
        sous_ligne.addView(date);
        sous_ligne.addView(entre);
        texte.setText(historique.getTexte());
        sous_ligne.addView(texte);
        addView(sous_ligne);
        addView(btnModifier, marge);
        addView(btnSupprimer, marge);
        invalidate();
    }

    private void modifier() {
        removeAllViews();
                longDate = historique.getDate();
            sous_ligne.removeAllViews();
                editDate.setText(DateToString.dateToString(longDate));
            sous_ligne.addView(editDate);
            sous_ligne.addView(entre);
                editTexte.setText(historique.getTexte());
            sous_ligne.addView(editTexte);
        addView(sous_ligne);
        addView(btnValider, marge);
        addView(btnAnnuler, marge);
        invalidate();
    }

    private void valider() {
        TableHistorique.instance(getContext()).modifier(
                historique.getId(),
                editTexte.getText().toString(),
                longDate,
                historique.getId_fermenteur(),
                historique.getId_cuve(),
                historique.getId_fut(),
                historique.getId_brassin());
        parent.invalidate();
    }

    private void supprimer() {
        TableHistorique.instance(getContext()).supprimer(historique.getId());
        parent.invalidate();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModifier)) {
            modifier();
        }
        else if (v.equals(btnSupprimer)) {
            supprimer();
        }
        else if (v.equals(btnValider)) {
            valider();
            afficher();
        }
        else if (v.equals(btnAnnuler)) {
            afficher();
        }
        else if (v.equals(editDate)) {
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTimeInMillis(longDate);
            new DatePickerDialog(getContext(), this, calendrier.get(Calendar.YEAR), calendrier.get(Calendar.MONTH), calendrier.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    @Override
    public void onDateSet(DatePicker view, int annee, int mois, int jour) {
        GregorianCalendar calendrier = new GregorianCalendar(annee, mois, jour);
        longDate = calendrier.getTimeInMillis();
        editDate.setText(DateToString.dateToString(longDate));
    }
}
