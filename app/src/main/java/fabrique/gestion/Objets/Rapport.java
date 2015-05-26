package fabrique.gestion.Objets;


import android.content.Context;

import fabrique.gestion.BDD.TableBrassinPere;

public class Rapport extends Objet implements Comparable<Rapport> {

    private long id_brassinPere;
    private int mois;
    private int annee;
    private int quantiteFermente;
    private int quantiteTransfere;
    private int quantiteUtilise;

    public Rapport(long id, long id_brassinPere, int mois, int annee, int quantiteFermente, int quantiteTransfere, int quantiteUtilise) {
        super(id);
        this.id_brassinPere = id_brassinPere;
        this.mois = mois;
        this.annee = annee;
        this.quantiteFermente = quantiteFermente;
        this.quantiteTransfere = quantiteTransfere;
        this.quantiteUtilise = quantiteUtilise;
    }

    public long getId_brassinPere() {
        return id_brassinPere;
    }
    public BrassinPere getBrassinPere(Context contexte) {
        return TableBrassinPere.instance(contexte).recupererId(id_brassinPere);
    }
    public int getMois() {
        return mois;
    }
    public int getAnnee() {
        return annee;
    }
    public int getQuantiteFermente() {
        return quantiteFermente;
    }
    public int getQuantiteTransfere() {
        return quantiteTransfere;
    }
    public int getQuantiteUtilise() {
        return quantiteUtilise;
    }

    public void setQuantiteFermente(int quantiteFermente) {
        this.quantiteFermente = quantiteFermente;
    }
    public void setQuantiteTransfere(int quantiteTransfere) {
        this.quantiteTransfere = quantiteTransfere;
    }
    public void setQuantiteUtilise(int quantiteUtilise) {
        this.quantiteUtilise = quantiteUtilise;
    }

    @Override
    public int compareTo(Rapport rapport) {
        return 0;
    }

    @Override
    public String sauvegarde() {
        return ("<O:Rapport>" +
                    "<E:mois>" + mois + "</E:mois>" +
                    "<E:annee>" + annee + "</E:annee>" +
                    "<E:quantiteFermente>" + quantiteFermente + "</E:quantiteFermente>" +
                    "<E:quantiteTransfere>" + quantiteTransfere + "</E:quantiteTransfere>" +
                    "<E:quantiteUtilise>" + quantiteUtilise + "</E:quantiteUtilise>" +
                "</O:Rapport>");
    }
}
