package fabrique.gestion.Objets;

import java.util.Calendar;

public class Brassin extends Objet implements Comparable<Brassin> {

    private int numero;
    private String commentaire;
    private long dateCreation;
    private int quantite;
    private int id_recette;
    private float densiteOriginale;
    private float densiteFinale;
    private float pourcentageAlcool;

    public Brassin(int id, int numero, String commentaire, long dateCreation, int quantite, int id_recette, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        super(id);
        this.numero = numero;
        this.commentaire = commentaire;
        this.dateCreation = dateCreation;
        this.quantite = quantite;
        this.id_recette = id_recette;
        this.densiteOriginale = densiteOriginale;
        this.densiteFinale = densiteFinale;
        this.pourcentageAlcool = pourcentageAlcool;
    }

    public int getNumero() { return numero; }
    public String getCommentaire() { return commentaire; }
    public String getDateCreation() {
        Calendar calendrier = Calendar.getInstance();
        calendrier.setTimeInMillis(dateCreation);
        return calendrier.get(Calendar.DAY_OF_MONTH) + "/" + (calendrier.get(Calendar.MONTH)+1) + "/" + calendrier.get(Calendar.YEAR);
    }
    public int getQuantite() { return quantite; }
    public int getId_recette() { return id_recette; }
    public float getDensiteOriginale() { return densiteOriginale; }
    public float getDensiteFinale() { return densiteFinale; }
    public float getPourcentageAlcool() { return pourcentageAlcool; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public void setDateCreation(long dateCreation) { this.dateCreation = dateCreation; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setId_recette(int id_recette) { this.id_recette = id_recette; }
    public void setDensiteOriginale(float densiteOriginale) { this.densiteOriginale = densiteOriginale; }
    public void setDensiteFinale(float densiteFinale) { this.densiteFinale = densiteFinale; }
    public void setPourcentageAlcool(float pourcentageAlcool) { this.pourcentageAlcool = pourcentageAlcool; }

    @Override
    public int compareTo(Brassin brassin) {
        if (numero == brassin.numero) {
            if (getId() == brassin.getId()) {
                return 0;
            } else if (getId() > brassin.getId()) {
                return 1;
            } else {
                return -1;
            }
        } else if (numero > brassin.numero) {
            return 1;
        }
        return -1;
    }
}
