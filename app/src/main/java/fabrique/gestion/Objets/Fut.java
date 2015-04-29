package fabrique.gestion.Objets;

import android.content.Context;
import android.support.annotation.NonNull;

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
        return DateToString.dateToString(dateEtat);
    }
    public long getDateEtatToLong() {
        return dateEtat;
    }
    public long getId_brassin() {
        return id_brassin;
    }
    public Brassin getBrassin(Context contexte) {
        return TableBrassin.instance(contexte).recupererId(id_brassin);
    }
    public String getDateInspection() {
        return DateToString.dateToString(dateInspection);
    }
    public long getDateInspectionToLong() {
        return dateInspection;
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
    public int compareTo(@NonNull Fut fut) {
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

    public String sauvegarde() {
        return ("<Fut>" +
                    "<numero>" + numero + "</numero>" +
                    "<capacite>" + capacite + "</capacite>" +
                    "<id_etat>" + id_etat + "</id_etat>" +
                    "<dateEtat>" + dateEtat + "</dateEtat>" +
                    "<id_brassin>" + id_brassin + "</id_brassin>" +
                    "<dateInspection>" + dateInspection + "</dateInspection>" +
                "</Fut>");
    }
}
