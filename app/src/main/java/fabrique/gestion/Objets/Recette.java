package fabrique.gestion.Objets;

import android.support.annotation.NonNull;

public class Recette extends Objet implements Comparable<Recette> {

    private String nom;
    private String acronyme;
    private String couleur;
    private int couleurTexte;
    private int couleurFond;
    private boolean actif;

    public Recette(long id, String nom, String couleur, String acronyme, int couleurTexte, int couleurFond, boolean actif){
        super(id);
        this.nom = nom;
        this.couleur = couleur;
        this.acronyme = acronyme;
        this.couleurTexte = couleurTexte;
        this.couleurFond = couleurFond;
        this.actif = actif;
    }

    public String getNom() { return nom; }
    public String getCouleur() { return couleur; }
    public String getAcronyme() { return acronyme; }
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
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setAcronyme(String acronyme) { this.acronyme = acronyme; }
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

    public String sauvegarde() {
        return ("<O:Recette>" +
                    "<E:nom>" + nom + "</E:nom>" +
                    "<E:couleur>" + couleur + "</E:couleur>" +
                    "<E:acronyme>" + acronyme + "</E:acronyme>" +
                    "<E:couleurTexte>" + couleurTexte + "</E:couleurTexte>" +
                    "<E:couleurFond>" + couleurFond + "</E:couleurFond>" +
                    "<E:actif>" + actif + "</E:actif>" +
                "</O:Recette>");
    }
}
