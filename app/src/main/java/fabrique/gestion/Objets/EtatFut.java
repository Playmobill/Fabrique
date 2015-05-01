package fabrique.gestion.Objets;

import android.support.annotation.NonNull;

public class EtatFut extends Objet implements Comparable<EtatFut> {

    private String texte;
    private String historique;
    private int couleurTexte;
    private int couleurFond;
    private boolean actif;

    public String getTexte() { return texte; }
    public String getHistorique() {
        return historique;
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

    public void setTexte(String texte) { this.texte = texte; }
    public void setHistorique(String historique) {
        this.historique = historique;
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

    public EtatFut(long id, String texte, String historique, int couleurTexte, int couleurFond, boolean actif){
        super(id);
        this.texte = texte;
        this.historique = historique;
        this.couleurTexte = couleurTexte;
        this.couleurFond = couleurFond;
        this.actif = actif;
    }

    @Override
    public int compareTo(@NonNull EtatFut etat) {
        if (actif == etat.getActif()) {
            for (int i=0; i<Math.min(texte.length(), etat.getTexte().length()) ; i++) {
                if (texte.charAt(i) < etat.getTexte().charAt(i)) {
                    return -1;
                } else if (texte.charAt(i) > etat.getTexte().charAt(i)) {
                    return 1;
                }
            }
            if (texte.length() == etat.getTexte().length()) {
                return 0;
            } else if (texte.length() < etat.getTexte().length()) {
                return -1;
            }
        } else if (actif && !etat.actif) {
            return -1;
        }
        return 1;
    }

    public String sauvegarde() {
        return ("<O:EtatFut>" +
                    "<E:texte>" + texte + "</E:texte>" +
                    "<E:historique>" + historique + "</E:historique>" +
                    "<E:couleurTexte>" + couleurTexte + "</E:couleurTexte>" +
                    "<E:couleurFond>" + couleurFond + "</E:couleurFond>" +
                    "<E:actif>" + actif + "</E:actif>" +
                "</O:EtatFut>");
    }
}