package fabrique.gestion.Objets;

public class TypeBiere implements Comparable<TypeBiere> {

    private int id;
    private String texte;

    public int getId() { return id; }
    public String getTexte() { return texte; }

    public void setTexte(String texte) { this.texte = texte; }

    public TypeBiere(int id, String texte){
        this.id = id;
        this.texte = texte;
    }

    @Override
    public int compareTo(TypeBiere type) {
        if (id == type.getId()) {
            return 0;
        } else if (id > type.getId()) {
            return 1;
        }
        return -1;
    }
}
