package fabrique.gestion.Objets;

import java.util.Calendar;

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatFermenteur;

public class Fermenteur {

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

    public String getEmplacement() {
        return TableEmplacement.instance().emplacement(emplacement);
    }

    public long getDateLavageAcide() {
        return dateLavageAcide;
    }

    public String getEtat() {
        return TableEtatFermenteur.instance().etat(etat);
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
}
