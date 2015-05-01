package fabrique.gestion.Objets;

import android.support.annotation.NonNull;

public class Emplacement extends Objet implements Comparable<Emplacement> {

    private String texte;
    private boolean actif;

    public Emplacement(long id, String texte, boolean actif){
        super(id);
        this.texte = texte;
        this.actif = actif;
    }

    public String getTexte() {
        return texte;
    }
    public boolean getActif() {
        return actif;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public int compareTo(@NonNull Emplacement emplacement) {
        if (actif == emplacement.getActif()) {
            for (int i=0; i<Math.min(texte.length(), emplacement.getTexte().length()) ; i++) {
                if (texte.charAt(i) < emplacement.getTexte().charAt(i)) {
                    return -1;
                } else if (texte.charAt(i) > emplacement.getTexte().charAt(i)) {
                    return 1;
                }
            }
            if (texte.length() == emplacement.getTexte().length()) {
                return 0;
            } else if (texte.length() < emplacement.getTexte().length()) {
                return -1;
            }
        } else if (actif && !emplacement.actif) {
            return -1;
        }
        return 1;
    }

    public String sauvegarde() {
        return ("<O:Emplacement>" +
                    "<E:texte>" + texte + "</E:texte>" +
                    "<E:actif>" + actif + "</E:actif>" +
                "</O:Emplacement>");
    }
}
