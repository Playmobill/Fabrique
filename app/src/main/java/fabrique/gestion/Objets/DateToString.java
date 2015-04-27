package fabrique.gestion.Objets;

import java.util.Calendar;

public class DateToString {

    public static String dateToString(long date) {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(date);
        String mois = "";
        switch (calendrier.get(Calendar.MONTH)) {
            case(0) : mois = "Jan";
                break;
            case(1) : mois = "Fev";
                break;
            case(2) : mois = "Mar";
                break;
            case(3) : mois = "Avr";
                break;
            case(4) : mois = "Mai";
                break;
            case(5) : mois = "Jun";
                break;
            case(6) : mois = "Jul";
                break;
            case(7) : mois = "Aou";
                break;
            case(8) : mois = "Sep";
                break;
            case(9) : mois = "Oct";
                break;
            case(10) : mois = "Nov";
                break;
            case(11) : mois = "Dec";
                break;
            default : calendrier.get(Calendar.MONTH);
        }
        return calendrier.get(Calendar.DAY_OF_MONTH) + " " + mois + " " + calendrier.get(Calendar.YEAR);
    }
}
