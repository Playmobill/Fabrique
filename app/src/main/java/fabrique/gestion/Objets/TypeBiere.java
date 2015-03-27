package fabrique.gestion.Objets;

/**
 * Created by thibaut on 27/03/15.
 */
public class TypeBiere {

    private int id;
    private String texte;

    public int getId() { return id; }
    public String getTexte() { return texte; }

    public void setTexte(String texte) { this.texte = texte; }
    public void setId(int id) { this.id = id; }

    public TypeBiere(int id, String texte){
        this.id = id;
        this.texte = texte;
    }
}
