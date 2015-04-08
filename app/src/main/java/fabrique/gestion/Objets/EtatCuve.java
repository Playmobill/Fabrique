package fabrique.gestion.Objets;

public class EtatCuve extends Objet implements Comparable<EtatCuve> {

    private String texte;
    private int couleurTexte;
    private int couleurFond;
    private boolean actif;

    public String getTexte() { return texte; }
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
    public void setCouleurTexte(int couleurTexte) {
        this.couleurTexte = couleurTexte;
    }
    public void setCouleurFond(int couleurFond) {
        this.couleurFond = couleurFond;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public EtatCuve(long id, String texte, int couleurTexte, int couleurFond, boolean actif){
        super(id);
        this.texte = texte;
        this.couleurTexte = couleurTexte;
        this.couleurFond = couleurFond;
        this.actif = actif;
    }

    @Override
    public int compareTo(EtatCuve etat) {
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
        } else if (actif && !etat.getActif()) {
            return -1;
        }
        return 1;
    }
}