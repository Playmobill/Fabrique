package fabrique.gestion.Objets;

public class Recette extends Objet implements Comparable<Recette> {

    private String nom;
    private String couleur;
    private String acronyme;
    private boolean actif;

    public Recette(long id, String nom, String couleur, String acronyme, boolean actif){
        super(id);
        this.nom = nom;
        this.couleur = couleur;
        this.acronyme = acronyme;
        this.actif = actif;
    }

    public String getNom() { return nom; }
    public String getCouleur() { return couleur; }
    public String getAcronyme() { return acronyme; }
    public boolean getActif() {
        return actif;
    }

    public void setNom(String nom) { this.nom = nom; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setAcronyme(String acronyme) { this.acronyme = acronyme; }
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @Override
    public int compareTo(Recette type) {
        if (getId() == type.getId()) {
            return 0;
        } else if (getId() > type.getId()) {
            return 1;
        }
        return -1;
    }
}
