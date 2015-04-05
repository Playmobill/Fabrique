package fabrique.gestion.Objets;

public class EtatCuve extends Objet {

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

    public EtatCuve(int id, String texte, int couleurTexte, int couleurFond, boolean actif){
        super(id);
        this.texte = texte;
        this.couleurTexte = couleurTexte;
        this.couleurFond = couleurFond;
        this.actif = actif;
    }
}