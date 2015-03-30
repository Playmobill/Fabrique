package fabrique.gestion.Objets;

import java.util.Calendar;

import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatCuve;

public class Cuve implements Comparable<Cuve> {

    private int id;
    private int numero;
    private int capacite;
    private int emplacement;
    private long dateLavageAcide;
    private int etat;
    private long dateEtat;
    private String commentaireEtat;
    private Brassin brassin;

    public Cuve() {
        commentaireEtat = "";
        dateLavageAcide = 0;
    }
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
        return TableEmplacement.instance(null).emplacement(emplacement);
    }
    public long getDateLavageAcide() {
        return dateLavageAcide;
    }
    public String getEtat() {
        return TableEtatCuve.instance().etat(etat) + "\n";
    }
    public String getDateEtat() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateEtat);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + calendrier.get(Calendar.MONTH) + "/" + calendrier.get(Calendar.YEAR);
    }
    public String getDureeEtat() {
        long temps = System.currentTimeMillis() - dateEtat;
        int jour = (int)(temps / 1000 / 60 / 60 / 24);
        int heure = (((int)(temps / 1000 / 60 / 60 / 24))-jour) * 24;
        return jour + "j" + heure + "h";
    }
    public String getCommentaireEtat() {
        String texte = "";
        if (commentaireEtat != null) {
            texte = texte + commentaireEtat;
        }
        texte = texte + " depuis " + getDureeEtat();
        return texte;
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
    public void setCommentaireEtat(String commentaireEtat) {
        this.commentaireEtat = commentaireEtat;
    }
    public void setBrassin(Brassin brassin) {
        this.brassin = brassin;
    }

    @Override
    public int compareTo(Cuve cuve) {
        if (numero == cuve.numero) {
            if (id == cuve.id) {
                return 0;
            } else if (id > cuve.id) {
                return 1;
            } else {
                return -1;
            }
        } else if (numero > cuve.numero) {
            return 1;
        }
        return -1;
    }
}
