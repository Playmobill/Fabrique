package fabrique.gestion.Objets;

public class ListeHistorique extends Objet implements Comparable<ListeHistorique> {

    private int elementConcerne;
    private String texte;

    public ListeHistorique(long id, int elementConcerne, String texte) {
        super(id);
        this.elementConcerne = elementConcerne;
        this.texte = texte;
    }

    public int getElementConcerne() {
        return elementConcerne;
    }
    public String getTexte() {
        return texte;
    }

    public void setElementConcerne(int elementConcerne) {
        this.elementConcerne = elementConcerne;
    }
    public void setTexte(String texte) {
        this.texte = texte;
    }

    @Override
    public int compareTo(ListeHistorique listeHistorique) {
        if (getId() == listeHistorique.getId()) {
            return 0;
        } else if (getId() > listeHistorique.getId()) {
            return 1;
        }
        return -1;
    }
}
