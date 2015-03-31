package fabrique.gestion.Objets;

public class Emplacement extends Objet {

    private String texte;

    public Emplacement(int id, String texte){
        super(id);
        this.texte = texte;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
