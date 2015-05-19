package fabrique.gestion.Objets;

import android.support.annotation.NonNull;

public class ListeHistorique extends Objet implements Comparable<ListeHistorique> {

    private int elementConcerne;
    private String texte;
    private int supprimable;

    public ListeHistorique(long id, int elementConcerne, String texte, int supprimable) {
        super(id);
        this.elementConcerne = elementConcerne;
        this.texte = texte;
        this.supprimable = supprimable;
    }

    public int getElementConcerne() {
        return elementConcerne;
    }
    public String getTexte() {
        return texte;
    }
    public int getSupprimable() {
        return supprimable;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }

    @Override
    public int compareTo(@NonNull ListeHistorique listeHistorique) {
        if (getId() == listeHistorique.getId()) {
            return 0;
        } else if (getId() > listeHistorique.getId()) {
            return 1;
        }
        return -1;
    }

    public String sauvegarde() {
        return ("<O:ListeHistorique>" +
                    "<E:elementConcerne>" + elementConcerne + "</E:elementConcerne>" +
                    "<E:texte>" + texte + "</E:texte>" +
                    "<E:supprimable>" + supprimable + "</E:supprimable>" +
                "</O:ListeHistorique>");
    }
}
