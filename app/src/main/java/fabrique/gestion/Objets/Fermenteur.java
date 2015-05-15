package fabrique.gestion.Objets;

import android.content.Context;
import android.support.annotation.NonNull;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCheminBrassinFermenteur;
import fabrique.gestion.BDD.TableEmplacement;

public class Fermenteur extends Objet implements Comparable<Fermenteur> {

    private int numero;
    private int capacite;
    private long id_emplacement;
    private long dateLavageAcide;
    private long id_noeud;
    private long dateEtat;
    private long id_brassin;
    private boolean actif;

    public Fermenteur(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_noeud, long dateEtat, long id_brassin, boolean actif){
        super(id);
        this.numero = numero;
        this.capacite = capacite;
        this.id_emplacement = id_emplacement;
        this.dateLavageAcide = dateLavageAcide;
        this.id_noeud = id_noeud;
        this.dateEtat = dateEtat;
        this.id_brassin = id_brassin;
        this.actif = actif;
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
    public String getDateLavageAcide() {
        return DateToString.dateToString(dateLavageAcide);
    }
    public long getDateLavageAcideToLong() {
        return dateLavageAcide;
    }
    public long getIdNoeud() {
        return id_noeud;
    }
    public NoeudFermenteur getNoeud(Context contexte) {
        return TableCheminBrassinFermenteur.instance(contexte).recupererId(id_noeud);
    }
    public long getDateEtatToLong() {
        return dateEtat;
    }
    public String getDateEtat() {
        return DateToString.dateToString(dateEtat);
    }
    public long getIdBrassin() {
        return id_brassin;
    }
    public Brassin getBrassin(Context contexte) {
        return TableBrassin.instance(contexte).recupererId(id_brassin);
    }
    public Boolean getActif() {
        return actif;
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
    public void setNoeud(long id_noeud) {
        this.id_noeud = id_noeud;
    }
    public void setDateEtat(long dateEtat) {
        this.dateEtat = dateEtat;
    }
    public void setBrassin(long id_brassin) {
        this.id_brassin = id_brassin;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public int compareTo(@NonNull Fermenteur fermenteur) {
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

    public String sauvegarde() {
        return ("<O:Fermenteur>" +
                    "<E:numero>" + numero + "</E:numero>" +
                    "<E:capacite>" + capacite + "</E:capacite>" +
                    "<E:id_emplacement>" + id_emplacement + "</E:id_emplacement>" +
                    "<E:dateLavageAcide>" + dateLavageAcide + "</E:dateLavageAcide>" +
                    "<E:id_noeud>" + id_noeud + "</E:id_noeud>" +
                    "<E:dateEtat>" + dateEtat + "</E:dateEtat>" +
                    "<E:id_brassin>" + id_brassin + "</E:id_brassin>" +
                    "<E:actif>" + actif + "</E:actif>" +
                "</O:Fermenteur>");
    }
}
