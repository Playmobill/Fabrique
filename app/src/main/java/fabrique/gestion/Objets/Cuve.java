package fabrique.gestion.Objets;

import java.util.Calendar;

public class Cuve {

    private int id;

    private int numero;

    private int capacite;

    private String dateLavageAcide;

    private int etat = 0;

    private long dateEtat = System.currentTimeMillis();

    private String commentaireEtat;

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

    public String getDateLavageAcide() {
        return dateLavageAcide;
    }

    public String getEtat() {
        if ((etat == 0) || (etat == 3)) {
            return EtatCuve.etat(etat) + "\n";
        }
        return EtatCuve.etat(etat) + "\n" + brassin.getNumero();
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

        if (etat == 1) {
            texte = texte + " depuis " + getDureeEtat();
        }
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

    public void setDateLavageAcide(String dateLavageAcide) {
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
        etat = 1;
    }
}
