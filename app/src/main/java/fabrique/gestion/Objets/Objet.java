package fabrique.gestion.Objets;

public abstract class Objet {

    private long id;

    public Objet(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public abstract String sauvegarde();

    public int getCapacite(){
        return -1;
    }
}
