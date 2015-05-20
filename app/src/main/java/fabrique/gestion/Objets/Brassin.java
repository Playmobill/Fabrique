package fabrique.gestion.Objets;

import android.content.Context;

import fabrique.gestion.BDD.TableBrassinPere;

public class Brassin extends BrassinPere {

    private long id_brassinPere;

    private final static float constanteConversionPourcentageAlcool = 1.047f;
    //Pour un gramme de CO2 produit, il y a environ (à 3 décimales sures) 1.047 grammes d'ethanol produit.

    public Brassin(long id, long id_brassinPere, int numero, String commentaire, long dateCreation, int quantite, long id_recette, float densiteOriginale, float densiteFinale) {
        super(id, numero, commentaire, dateCreation, quantite, id_recette, densiteOriginale, densiteFinale);
        this.id_brassinPere = id_brassinPere;
    }
    
    public Brassin(Context contexte, long id, BrassinPere brassinPere) {
        this(id, brassinPere.getId(), brassinPere.getNumero(), brassinPere.getCommentaire(), brassinPere.getDateLong(), brassinPere.getQuantite(), brassinPere.getId_recette(), brassinPere.getDensiteOriginale(), brassinPere.getDensiteFinale());
    }
    
    public long getId_brassinPere() {
        return id_brassinPere;
    }
    public BrassinPere getBrassinPere(Context contexte) {
        return TableBrassinPere.instance(contexte).recupererId(id_brassinPere);
    }

    public String sauvegarde() {
        return ("<O:Brassin>" +
                    "<E:id_brassinPere>" + id_brassinPere + "</E:id_brassinPere>" +
                    "<E:numero>" + getNumero() + "</E:numero>" +
                    "<E:commentaire>" + getCommentaire() + "</E:commentaire>" +
                    "<E:dateCreation>" + getDateCreation() + "</E:dateCreation>" +
                    "<E:quantite>" + getQuantite() + "</E:quantite>" +
                    "<E:id_recette>" + getId_recette() + "</E:id_recette>" +
                    "<E:densiteOriginale>" + getDensiteOriginale() + "</E:densiteOriginale>" +
                    "<E:densiteFinale>" + getDensiteFinale() + "</E:densiteFinale>" +
                "</O:Brassin>");
    }
}
