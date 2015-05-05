package fabrique.gestion.Objets;

import android.support.annotation.NonNull;

public class TypeBiere extends Objet implements Comparable<TypeBiere> {

    private String nom;
    private String couleur;
    private boolean actif;

    public TypeBiere(long id, String nom, String couleur, boolean actif) {
        super(id);
        this.nom = nom;
        this.couleur = couleur;
        this.actif = actif;
    }

    public String getNom() {
        return nom;
    }
    public String getCouleur() {
        return couleur;
    }
    public boolean getActif() {
        return actif;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public int compareTo(@NonNull TypeBiere type) {
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
                    "<E:couleur>" + couleur + "</E:couleur>" +
                "</O:Recette>");
    }
}
