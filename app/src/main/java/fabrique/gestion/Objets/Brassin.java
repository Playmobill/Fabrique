package fabrique.gestion.Objets;

import android.content.Context;

import fabrique.gestion.BDD.TableBrassinPere;

public class Brassin extends BrassinPere {

    private long id_brassinPere;
    
    public Brassin(long id, BrassinPere brassinPere, int quantite) {
        super(id, brassinPere.getNumero(), brassinPere.getCommentaire(), brassinPere.getDateLong(), quantite, brassinPere.getId_recette(), brassinPere.getDensiteOriginale(), brassinPere.getDensiteFinale());
        this.id_brassinPere = brassinPere.getId();
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
                    "<E:quantite>" + getQuantite() + "</E:quantite>" +
                "</O:Brassin>");
    }
}
