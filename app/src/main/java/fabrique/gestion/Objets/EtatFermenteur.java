package fabrique.gestion.Objets;

public class EtatFermenteur extends Objet {

    private String texte;

    public EtatFermenteur(int id, String texte){
        super(id);
        this.texte = texte;
    }

    public String getTexte() { return texte; }

    public void setTexte(String texte) { this.texte = texte; }
}
