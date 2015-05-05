package fabrique.gestion.Objets;

import android.content.Context;
import android.support.annotation.NonNull;

import fabrique.gestion.BDD.TableTypeBiere;

public class Recette extends Objet implements Comparable<Recette> {

    private String nom;
    private String acronyme;
    private long id_biere;
    private int couleurTexte;
    private int couleurFond;
    private boolean actif;

    public Recette(long id, String nom, String acronyme, long id_biere, int couleurTexte, int couleurFond, boolean actif){
        super(id);
        this.nom = nom;
        this.acronyme = acronyme;
        this.id_biere = id_biere;
        this.couleurTexte = couleurTexte;
        this.couleurFond = couleurFond;
        this.actif = actif;
    }

    public String getNom() { return nom; }
    public String getAcronyme() { return acronyme; }
    public long getId_biere() {
        return id_biere;
    }
    public TypeBiere getTypeBiere(Context contexte) {
        return TableTypeBiere.instance(contexte).recupererId(id_biere);
    }
    public int getCouleurTexte() {
        return couleurTexte;
    }
    public int getCouleurFond() {
        return couleurFond;
    }
    public boolean getActif() {
        return actif;
    }

    public void setNom(String nom) { this.nom = nom; }
    public void setAcronyme(String acronyme) { this.acronyme = acronyme; }
    public void setId_biere(long id_biere) {
        this.id_biere = id_biere;
    }
    public void setCouleurTexte(int couleurTexte) {
        this.couleurTexte = couleurTexte;
    }
    public void setCouleurFond(int couleurFond) {
        this.couleurFond = couleurFond;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public int compareTo(@NonNull Recette type) {
        if (getId() == type.getId()) {
            return 0;
        } else if (getId() > type.getId()) {
            return 1;
        }
        return -1;
    }

    @Override
    public String sauvegarde() {
        return ("<O:Recette>" +
                    "<E:nom>" + nom + "</E:nom>" +
                    "<E:acronyme>" + acronyme + "</E:acronyme>" +
                    "<E:couleurTexte>" + couleurTexte + "</E:couleurTexte>" +
                    "<E:couleurFond>" + couleurFond + "</E:couleurFond>" +
                    "<E:actif>" + actif + "</E:actif>" +
                "</O:Recette>");
    }
}
