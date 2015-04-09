package fabrique.gestion.Objets;

import android.content.Context;

import java.util.Calendar;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatFermenteur;

public class Fermenteur extends Objet implements Comparable<Fermenteur> {

    private int numero;
    private int capacite;
    private long id_emplacement;
    private long dateLavageAcide;
    private long id_etat;
    private long dateEtat;
    private long id_brassin;

    public Fermenteur(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, long id_brassin){
        super(id);
        this.numero = numero;
        this.capacite = capacite;
        this.id_emplacement = id_emplacement;
        this.dateLavageAcide = dateLavageAcide;
        this.id_etat = id_etat;
        this.dateEtat = dateEtat;
        this.id_brassin = id_brassin;
    }

    public int getNumero() {
        return numero;
    }
    public int getCapacite() {
        return capacite;
    }
    public long getIdEmplacement() {
        return id_emplacement;
    }
    public Emplacement getEmplacement(Context contexte) {
        return TableEmplacement.instance(contexte).recupererId(id_emplacement);
    }
    public long getDateLavageAcide() {
        return dateLavageAcide;
    }
    public String getDateLavageAcideToString() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateLavageAcide);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH)+1) + "/" + calendrier.get(Calendar.YEAR);
    }
    public long getIdEtat() {
        return id_etat;
    }
    public EtatFermenteur getEtat(Context contexte) {
        return TableEtatFermenteur.instance(contexte).recupererId(id_etat);
    }
    public long getLongDateEtat() {
        return dateEtat;
    }
    public String getDateEtat() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateEtat);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH)+1) + "/" + calendrier.get(Calendar.YEAR);
    }
    public long getIdBrassin() {
        return id_brassin;
    }
    public Brassin getBrassin(Context contexte) {
        return TableBrassin.instance(contexte).recupererId(id_brassin);
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
    public void setEmplacement(long id_emplacement) {
        this.id_emplacement = id_emplacement;
    }
    public void setDateLavageAcide(long dateLavageAcide) {
        this.dateLavageAcide = dateLavageAcide;
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

    @Override
    public int compareTo(Fermenteur fermenteur) {
        if (numero == fermenteur.numero) {
            if (getId() == fermenteur.getId()) {
                return 0;
            } else if (getId() > fermenteur.getId()) {
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
