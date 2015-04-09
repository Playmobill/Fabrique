package fabrique.gestion.Objets;

import android.content.Context;

import java.util.Calendar;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableEtatFut;

public class Fut extends Objet implements Comparable<Fut> {

    private int numero;
    private int capacite;
    private long id_etat;
    private long dateEtat;
    private long id_brassin;
    private long dateInspection;

    public Fut(long id, int numero, int capacite, long id_etat, long dateEtat, long id_brassin, long dateInspection) {
        super(id);

        this.numero = numero;
        this.capacite = capacite;
        this.id_etat = id_etat;
        this.dateEtat = dateEtat;
        this.id_brassin = id_brassin;
        this.dateInspection = dateInspection;
    }

    public int getNumero() {
        return numero;
    }
    public int getCapacite() {
        return capacite;
    }
    public long getId_etat() {
        return id_etat;
    }
    public EtatFut getEtat(Context contexte) {
        return TableEtatFut.instance(contexte).recupererId(id_etat);
    }
    public String getDateEtat() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateEtat);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH)+1) + "/" + calendrier.get(Calendar.YEAR);
    }
    public long getLongDateEtat() {
        return dateEtat;
    }
    public long getId_brassin() {
        return id_brassin;
    }
    public Brassin getBrassin(Context contexte) {
        return TableBrassin.instance(contexte).recupererId(id_brassin);
    }
    public long getDateInspection() {
        return dateInspection;
    }
    public String getDateInspectionToString() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateInspection);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH)+1) + "/" + calendrier.get(Calendar.YEAR);
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public void setEtat(long id_etat) {
        this.id_etat = id_etat;
    }
    public void setDateEtat(long dateEtat) {
        this.dateEtat = dateEtat;
    }
    public void setBrassin(long id_brassin) {
        this.id_brassin = id_brassin;
    }
    public void setDateInspection(long dateInspection) {
        this.dateInspection = dateInspection;
    }

    @Override
    public int compareTo(Fut fut) {
        if (numero == fut.numero) {
            if (getId() == fut.getId()) {
                return 0;
            } else if (getId() > fut.getId()) {
                return 1;
            } else {
                return -1;
            }
        } else if (numero > fut.numero) {
            return 1;
        }
        return -1;
    }
}
