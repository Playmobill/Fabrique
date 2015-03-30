package fabrique.gestion.BDD;

import java.util.ArrayList;

import fabrique.gestion.Objets.Fermenteur;

public class TableFermenteur {

    private ArrayList<Fermenteur> fermenteurs;

    private static TableFermenteur instance;

    private TableFermenteur() {
        fermenteurs = new ArrayList<>();

        //Fermenteur 1 contenant brassin 1
        Fermenteur fermenteur = new Fermenteur();
        fermenteur.setId(0);
        fermenteur.setNumero(1);
        fermenteur.setCapacite(100);
        fermenteur.setEtat(1);
        fermenteur.setBrassin(TableBrassin.instance(null).brassin(0));
        fermenteurs.add(fermenteur);

        //Fermenteur 2
        fermenteur = new Fermenteur();
        fermenteur.setId(1);
        fermenteur.setNumero(2);
        fermenteur.setDateEtat(System.currentTimeMillis()-1000*60*60*24);
        fermenteur.setEtat(0);
        fermenteurs.add(fermenteur);
    }

    public static TableFermenteur instance() {
        if(instance == null) {
            instance = new TableFermenteur();
        }
        return instance;
    }

    public void ajouter(Fermenteur fermenteur) {
        fermenteurs.add(fermenteur);
    }

    public Fermenteur fermenteur(int index ) {
        return fermenteurs.get(index);
    }

    public ArrayList<Fermenteur> fermenteurs() {
        return fermenteurs;
    }
}
