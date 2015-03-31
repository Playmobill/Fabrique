package fabrique.gestion.Objets;

public class TypeBiere extends Objet implements Comparable<TypeBiere> {

    private String texte;

    public TypeBiere(int id, String texte){
        super(id);
        this.texte = texte;
    }

    public String getTexte() { return texte; }

    public void setTexte(String texte) { this.texte = texte; }

    @Override
    public int compareTo(TypeBiere type) {
        if (getId() == type.getId()) {
            return 0;
        } else if (getId() > type.getId()) {
            return 1;
        }
        return -1;
    }
}
