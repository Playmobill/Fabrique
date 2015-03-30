package fabrique.gestion.Objets;

import android.content.Context;

import java.util.Calendar;

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatFermenteur;

public class Fermenteur implements Comparable<Fermenteur> {

    private int id;

    private int numero;

    private int capacite;

    private int emplacement;

    private long dateLavageAcide;

    private int etat;

    private long dateEtat;

    private Brassin brassin;

    public int getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getCapacite() {
        return capacite;
    }

    public String getEmplacement(Context contexte) {
        return TableEmplacement.instance(contexte).emplacement(emplacement);
    }

    public long getDateLavageAcide() {
        return dateLavageAcide;
    }

    public String getEtat(Context contexte) {
        return TableEtatFermenteur.instance(contexte).etat(etat);
    }

    public String getDateEtat() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateEtat);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH)+1) + "/" + calendrier.get(Calendar.YEAR);
    }

    public Brassin getBrassin() {
        return brassin;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public void setEmplacement(int emplacement) {
        this.emplacement = emplacement;
    }

    public void setDateLavageAcide(long dateLavageAcide) {
        this.dateLavageAcide = dateLavageAcide;
    }

    public void setEtat(int etat) {
        this.etat = etat;
        dateEtat = System.currentTimeMillis();
    }

    public void setDateEtat(long dateEtat) {
        this.dateEtat = dateEtat;
    }

    public void setBrassin(Brassin brassin) {
        this.brassin = brassin;
    }

    public Fermenteur(int id, int numero, int capacite, int emplacement, long dateLavageAcide, int etat, long dateEtat, Brassin brassin){
        this.id = id;
        this.numero = numero;
        this.capacite = capacite;
        this.emplacement = emplacement;
        this.dateLavageAcide = dateLavageAcide;
        this.etat = etat;
        this.dateEtat = dateEtat;
        this.brassin = brassin;
    }

    @Override
    public int compareTo(Fermenteur fermenteur) {
        if (numero == fermenteur.numero) {
            if (id == fermenteur.id) {
                return 0;
            } else if (id > fermenteur.id) {
                return 1;
            } else {
                return -1;
            }
        } else if (numero > fermenteur.numero) {
            return 1;
        }
        return -1;
    }
}
