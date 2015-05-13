package fabrique.gestion.Objets;

import android.content.Context;
import android.support.annotation.NonNull;

import fabrique.gestion.BDD.TableCheminBrassinFermenteur;
import fabrique.gestion.BDD.TableEtatFermenteur;

public class NoeudFermenteur extends Objet implements Comparable<NoeudFermenteur> {

    private long id_noeudPrecedent;
    private long id_etat;
    private long id_noeudAvecBrassin;
    private long id_noeudSansBrassin;

    public NoeudFermenteur(long id, long id_noeudPrecedent, long id_etat, long id_noeudAvecBrassin, long id_noeudSansBrassin) {
        super(id);
        this.id_noeudPrecedent = id_noeudPrecedent;
        this.id_etat = id_etat;
        this.id_noeudAvecBrassin = id_noeudAvecBrassin;
        this.id_noeudSansBrassin = id_noeudSansBrassin;
    }

    public long getId_noeudPrecedent() {
        return id_noeudPrecedent;
    }
    public long getId_etat() {
        return id_etat;
    }
    public EtatFermenteur getEtat(Context contexte) {
        return TableEtatFermenteur.instance(contexte).recupererId(id_etat);
    }
    public long getId_noeudAvecBrassin() {
        return id_noeudAvecBrassin;
    }
    public NoeudFermenteur getNoeudAvecBrassin(Context contexte) {
        return TableCheminBrassinFermenteur.instance(contexte).recupererId(id_noeudAvecBrassin);
    }
    public long getId_noeudSansBrassin() {
        return id_noeudSansBrassin;
    }
    public NoeudFermenteur getNoeudSansBrassin(Context contexte) {
        return TableCheminBrassinFermenteur.instance(contexte).recupererId(id_noeudSansBrassin);
    }

    public void setId_etat(long id_etat) {
        this.id_etat = id_etat;
    }
    public void setId_noeudAvecBrassin(long id_noeudAvecBrassin) {
        this.id_noeudAvecBrassin = id_noeudAvecBrassin;
    }
    public void setId_noeudSansBrassin(long id_noeudSansBrassin) {
        this.id_noeudSansBrassin = id_noeudSansBrassin;
    }

    @Override
    public int compareTo(@NonNull NoeudFermenteur type) {
        if (getId() == type.getId()) {
            return 0;
        } else if (getId() < type.getId()) {
            return 1;
        }
        return -1;
    }

    @Override
    public String sauvegarde() {
        return ("<O:NoeudFermenteur>" +
                    "<E:id_noeudPrecedent>" + id_noeudPrecedent + "</E:id_noeudPrecedent>" +
                    "<E:id_etat>" + id_etat + "</E:id_etat>" +
                    "<E:id_noeudAvecBrassin>" + id_noeudAvecBrassin + "</E:id_noeudAvecBrassin>" +
                    "<E:id_noeudSansBrassin>" + id_noeudSansBrassin + "</E:id_noeudSansBrassin>" +
                "</O:NoeudFermenteur>");
    }
}
