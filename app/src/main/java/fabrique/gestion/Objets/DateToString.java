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

    public static String moisToString(int mois) {
        switch (mois) {
            case(-1) : return "Dec";
            case(0) : return "Jan";
            case(1) : return "Fev";
            case(2) : return "Mar";
            case(3) : return "Avr";
            case(4) : return "Mai";
            case(5) : return "Jun";
            case(6) : return "Jul";
            case(7) : return "Aou";
            case(8) : return "Sep";
            case(9) : return "Oct";
            case(10) : return "Nov";
            case(11) : return "Dec";
            case(12) : return "Jan";
            default : return null;
        }
    }
}
