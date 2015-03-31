package fabrique.gestion.Objets;

public class EtatCuve extends Objet {

    private String texte;

    public EtatCuve(int id, String texte){
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
