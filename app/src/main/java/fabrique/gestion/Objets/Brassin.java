package fabrique.gestion.Objets;

public class Brassin extends Objet implements Comparable<Brassin> {

    private int numero;
    private String commentaire;
    private String acronyme;
    private int dateCreation;
    private int quantite;
    private int id_typeBiere;
    private String couleur;
    private float densiteOriginale;
    private float densiteFinale;
    private float pourcentageAlcool;

    public Brassin(int id, int numero, String commentaire, String acronyme, int dateCreation, int quantite, int id_typeBiere, String couleur, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        super(id);
        this.numero = numero;
        this.commentaire = commentaire;
        this.acronyme = acronyme;
        this.dateCreation = dateCreation;
        this.quantite = quantite;
        this.id_typeBiere = id_typeBiere;
        this.couleur = couleur;
        this.densiteOriginale = densiteOriginale;
        this.densiteFinale = densiteFinale;
        this.pourcentageAlcool = pourcentageAlcool;
    }

    public int getNumero() { return numero; }
    public String getAcronyme() { return acronyme; }
    public String getCommentaire() { return commentaire; }
    public int getDateCreation() { return dateCreation; }
    public int getQuantite() { return quantite; }
    public int getId_typeBiere() { return id_typeBiere; }
    public String getCouleur() { return couleur; }
    public float getDensiteOriginale() { return densiteOriginale; }
    public float getDensiteFinale() { return densiteFinale; }
    public float getPourcentageAlcool() { return pourcentageAlcool; }

    public void setNumero(int numero) { this.numero = numero; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public void setDateCreation(int dateCreation) { this.dateCreation = dateCreation; }
    public void setAcronyme(String acronyme) { this.acronyme = acronyme; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    public void setId_typeBiere(int id_typeBiere) { this.id_typeBiere = id_typeBiere; }
    public void setCouleur(String couleur) { this.couleur = couleur; }
    public void setDensiteOriginale(float densiteOriginale) { this.densiteOriginale = densiteOriginale; }
    public void setDensiteFinale(float densiteFinale) { this.densiteFinale = densiteFinale; }
    public void setPourcentageAlcool(float pourcentageAlcool) { this.pourcentageAlcool = pourcentageAlcool; }

    @Override
    public int compareTo(Brassin brassin) {
        if (numero == brassin.numero) {
            if (getId() == brassin.getId()) {
                return 0;
            } else if (getId() > brassin.getId()) {
                return 1;
            } else {
                return -1;
            }
        } else if (numero > brassin.numero) {
            return 1;
        }
        return -1;
    }
}
