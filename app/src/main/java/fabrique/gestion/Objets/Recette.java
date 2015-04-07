package fabrique.gestion.Objets;

public class Recette extends Objet implements Comparable<Recette> {

    private String nom;
    private String couleur;
    private String acronyme;

    public Recette(int id, String nom, String couleur, String acronyme){
        super(id);
        this.nom = nom;
        this.couleur = couleur;
        this.acronyme = acronyme;
    }

    public String getNom() { return nom; }
    public String getCouleur() { return couleur; }
    public String getAcronyme() { return acronyme; }

    public void setNom(String nom) { this.nom = nom; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setAcronyme(String acronyme) { this.acronyme = acronyme; }

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
