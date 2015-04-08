package fabrique.gestion.Objets;

import android.content.Context;

import java.util.Calendar;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatCuve;

public class Cuve extends Objet implements Comparable<Cuve> {

    private int numero;
    private int capacite;
    private long id_emplacement;
    private long dateLavageAcide;
    private long id_etat;
    private long dateEtat;
    private String commentaireEtat;
    private long id_brassin;

    public Cuve(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_etat, long dateEtat, String commentaireEtat, long id_brassin){
        super(id);
        this.numero = numero;
        this.capacite = capacite;
        this.id_emplacement = id_emplacement;
        this.dateLavageAcide = dateLavageAcide;
        this.id_etat = id_etat;
        this.dateEtat = dateEtat;
        this.commentaireEtat = commentaireEtat;
        this.id_brassin = id_brassin;
    }

    public int getNumero() {
        return numero;
    }
    public int getCapacite() {
        return capacite;
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
    public EtatCuve getEtat(Context contexte) {
        return TableEtatCuve.instance(contexte).recupererId(id_etat);
    }
    public String getDureeEtat() {
        long temps = System.currentTimeMillis() - dateEtat;
        int jour = (int)(temps / 1000 / 60 / 60 / 24);
        int heure = (((int)(temps / 1000 / 60 / 60 / 24))-jour) * 24;
        return jour + "j" + heure + "h";
    }
    public String getDateEtat() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateEtat);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH)+1) + "/" + calendrier.get(Calendar.YEAR);
    }
    public String getCommentaireEtat() {
        String texte = "";
        if (commentaireEtat != null) {
            texte = texte + commentaireEtat;
        }
        return texte;
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
        dateEtat = System.currentTimeMillis();
    }
    public void setCommentaireEtat(String commentaireEtat) {
        this.commentaireEtat = commentaireEtat;
    }
    public void setBrassin(long id_brassin) {
        this.id_brassin = id_brassin;
    }

    @Override
    public int compareTo(Cuve cuve) {
        if (numero == cuve.numero) {
            if (getId() == cuve.getId()) {
                return 0;
            } else if (getId() > cuve.getId()) {
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
