package fabrique.gestion.Objets;

/**
 * Created by thibaut on 30/03/15.
 */
public class EtatCuve {

    private int id;
    private String texte;

    public String getTexte() { return texte; }
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    public void setTexte(String texte) { this.texte = texte; }

    public EtatCuve(int id, String texte){
        this.id = id ;
        this.texte = texte;
    }
}
