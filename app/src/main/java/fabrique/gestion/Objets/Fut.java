package fabrique.gestion.Objets;

import android.content.Context;
import android.support.annotation.NonNull;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCheminBrassinFut;

public class Fut extends Objet implements Comparable<Fut> {

    private int numero;
    private int capacite;
    private long id_noeud;
    private long dateEtat;
    private long id_brassin;
    private long dateInspection;
    private boolean actif;

    public Fut(long id, int numero, int capacite, long id_noeud, long dateEtat, long id_brassin, long dateInspection, boolean actif) {
        super(id);
        this.numero = numero;
        this.capacite = capacite;
        this.id_noeud = id_noeud;
        this.dateEtat = dateEtat;
        this.id_brassin = id_brassin;
        this.dateInspection = dateInspection;
        this.actif = actif;
    }

    public int getNumero() {
        return numero;
    }
    public int getCapacite() {
        return capacite;
    }
    public long getId_noeud() {
        return id_noeud;
    }
    public NoeudFut getNoeud(Context contexte) {
        return TableCheminBrassinFut.instance(contexte).recupererId(id_noeud);
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
    public boolean getActif() {
        return actif;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public void setNoeud(long id_noeud) {
        this.id_noeud = id_noeud;
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
    public void setActif(boolean actif) {
        this.actif = actif;
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
        return ("<O:Fut>" +
                    "<E:numero>" + numero + "</E:numero>" +
                    "<E:capacite>" + capacite + "</E:capacite>" +
                    "<E:id_noeud>" + id_noeud + "</E:id_noeud>" +
                    "<E:dateEtat>" + dateEtat + "</E:dateEtat>" +
                    "<E:id_brassin>" + id_brassin + "</E:id_brassin>" +
                    "<E:dateInspection>" + dateInspection + "</E:dateInspection>" +
                    "<E:actif>" + actif + "</E:actif>" +
                "</O:Fut>");
    }
}
