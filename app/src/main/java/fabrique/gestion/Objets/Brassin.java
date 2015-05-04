package fabrique.gestion.Objets;

import android.content.Context;
import android.support.annotation.NonNull;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import fabrique.gestion.BDD.TableRecette;

public class Brassin extends Objet implements Comparable<Brassin> {

    private int numero;
    private String commentaire;
    private long dateCreation;
    private int quantite;
    private long id_recette;
    private float densiteOriginale;
    private float densiteFinale;
    private float pourcentageAlcool;

    private final static float constanteConversionPourcentageAlcool = 1.047f;
    //Pour un gramme de CO2 produit, il y a environ (à 3 décimales sures) 1.047 grammes d'ethanol produit.

    public Brassin(long id, int numero, String commentaire, long dateCreation, int quantite, long id_recette, float densiteOriginale, float densiteFinale, float pourcentageAlcool) {
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
        return DateToString.dateToString(dateCreation);
    }
    public int getQuantite() { return quantite; }
    public long getId_recette() { return id_recette; }
    public Recette getRecette(Context contexte) {
        return TableRecette.instance(contexte).recupererId(id_recette);
    }
    public float getDensiteOriginale() { return densiteOriginale; }
    public float getDensiteFinale() { return densiteFinale; }
    public float getPourcentageAlcool() { return pourcentageAlcool; }
    public long getDateLong() { return dateCreation; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public void setDateCreation(long dateCreation) { this.dateCreation = dateCreation; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setId_recette(long id_recette) { this.id_recette = id_recette; }
    public void setDensiteOriginale(float densiteOriginale) { this.densiteOriginale = densiteOriginale; }
    public void setDensiteFinale(float densiteFinale) { this.densiteFinale = densiteFinale; }
    public void setPourcentageAlcool() { pourcentageAlcool = convertDensiteVersPourcentageAlcool(densiteOriginale, densiteFinale); }

    @Override
    public int compareTo(@NonNull Brassin brassin) {
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

    public static float convertDensiteVersPourcentageAlcool(float densiteO, float densiteF){
        float differenceDensite = densiteO - densiteF;
        float pourcentageAlcool = ((((differenceDensite * constanteConversionPourcentageAlcool)/densiteF)/0.789f)*100f)*1000f;
        DecimalFormat formatResultat = new DecimalFormat("####");
        formatResultat.setRoundingMode(RoundingMode.HALF_UP);
        try {
            pourcentageAlcool = Float.parseFloat(formatResultat.format(pourcentageAlcool))/1000f;
        }
        catch(NumberFormatException e){
            pourcentageAlcool /= 1000f;
        }
        return pourcentageAlcool;
    }

    public String sauvegarde() {
        return ("<O:Brassin>" +
                    "<E:numero>" + numero + "</E:numero>" +
                    "<E:commentaire>" + commentaire + "</E:commentaire>" +
                    "<E:dateCreation>" + dateCreation + "</E:dateCreation>" +
                    "<E:quantite>" + quantite + "</E:quantite>" +
                    "<E:id_recette>" + id_recette + "</E:id_recette>" +
                    "<E:densiteOriginale>" + densiteOriginale + "</E:densiteOriginale>" +
                    "<E:densiteFinale>" + densiteFinale + "</E:densiteFinale>" +
                    "<E:pourcentageAlcool>" + pourcentageAlcool + "</E:pourcentageAlcool>" +
                "</O:Brassin>");
    }
}
