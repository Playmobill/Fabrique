package fabrique.gestion.Objets;

import android.content.Context;
import android.support.annotation.NonNull;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCheminBrassinCuve;
import fabrique.gestion.BDD.TableEmplacement;

public class Cuve extends Objet implements Comparable<Cuve> {

    private int numero;
    private int capacite;
    private long id_emplacement;
    private long dateLavageAcide;
    private long id_noeud;
    private long dateEtat;
    private String commentaireEtat;
    private long id_brassin;
    private boolean actif;

    public Cuve(long id, int numero, int capacite, long id_emplacement, long dateLavageAcide, long id_noeud, long dateEtat, String commentaireEtat, long id_brassin, boolean actif){
        super(id);
        this.numero = numero;
        this.capacite = capacite;
        this.id_emplacement = id_emplacement;
        this.dateLavageAcide = dateLavageAcide;
        this.id_noeud = id_noeud;
        this.dateEtat = dateEtat;
        this.commentaireEtat = commentaireEtat;
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
    public long getDateLavageAcide() {
        return dateLavageAcide;
    }
    public String getDateLavageAcideToString() {
        return DateToString.dateToString(dateLavageAcide);
    }
    public long getIdNoeud() {
        return id_noeud;
    }
    public NoeudCuve getNoeud(Context contexte) {
        return TableCheminBrassinCuve.instance(contexte).recupererId(id_noeud);
    }
    public long getLongDateEtat() {
        return dateEtat;
    }
    public String getDureeEtat() {
        long temps = System.currentTimeMillis() - dateEtat;
        int jour = (int)(temps / 1000 / 60 / 60 / 24);
        int heure = (((int)(temps / 1000 / 60 / 60 / 24))-jour) * 24;
        return jour + "j" + heure + "h";
    }
    public String getDateEtat() {
        return DateToString.dateToString(dateEtat);
    }
    public String getCommentaireEtat() {
        String texte = "";
        if (commentaireEtat != null) {
            texte = texte + commentaireEtat;
        }
        return texte;
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
    public void setCommentaireEtat(String commentaireEtat) {
        this.commentaireEtat = commentaireEtat;
    }
    public void setBrassin(long id_brassin) {
        this.id_brassin = id_brassin;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public int compareTo(@NonNull Cuve cuve) {
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

    public String sauvegarde() {
        return ("<O:Cuve>" +
                    "<E:numero>" + numero + "</E:numero>" +
                    "<E:capacite>" + capacite + "</E:capacite>" +
                    "<E:id_emplacement>" + id_emplacement + "</E:id_emplacement>" +
                    "<E:dateLavageAcide>" + dateLavageAcide + "</E:dateLavageAcide>" +
                    "<E:id_noeud>" + id_noeud + "</E:id_noeud>" +
                    "<E:dateEtat>" + dateEtat + "</E:dateEtat>" +
                    "<E:commentaireEtat>" + commentaireEtat + "</E:commentaireEtat>" +
                    "<E:id_brassin>" + id_brassin + "</E:id_brassin>" +
                    "<E:actif>" + actif + "</E:actif>" +
                "</O:Cuve>");
    }
}
