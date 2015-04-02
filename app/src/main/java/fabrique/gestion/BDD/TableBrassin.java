package fabrique.gestion.BDD;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

import fabrique.gestion.Objets.Brassin;

public class TableBrassin extends Controle {

    private ArrayList<Brassin> brassins;

    private static TableBrassin INSTANCE;

    public static TableBrassin instance(Context contexte){
        if(INSTANCE == null){
            INSTANCE = new TableBrassin(contexte);
        }
        return INSTANCE;
    }

    private TableBrassin(Context contexte){
        super(contexte, "Brassin");

        brassins = new ArrayList<>();

        Cursor tmp = super.select();
        for (tmp.moveToFirst(); !(tmp.isAfterLast()); tmp.moveToNext()) {
            brassins.add(new Brassin(tmp.getInt(0), tmp.getInt(1), tmp.getString(2), tmp.getString(3), tmp.getInt(4), tmp.getInt(5), tmp.getInt(6), tmp.getString(7), tmp.getFloat(8), tmp.getFloat(9), tmp.getFloat(10)));
        }
        Collections.sort(brassins);
    }

    public void ajouter(int numero, String commentaire, String acronyme, int dateCreation, int quantite, int id_typeBiere, String couleur, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        brassins.add(new Brassin(brassins.size(), numero, commentaire, acronyme, dateCreation, quantite, id_typeBiere, couleur, densiteOriginale, densiteFinale, pourcentageAlcool));
        accesBDD.execSQL("INSERT INTO Brassin (numero, commentaire, acronyme, dateCreation, quantite, id_typeBiere, couleur, densiteOriginale, densiteFinale, pourcentageAlcool) VALUES (" + numero + ", '" + commentaire + "', '" + acronyme + "', " + dateCreation + ", " + quantite + ", " + id_typeBiere + ", '" + couleur + "', " + densiteOriginale + ", " + densiteFinale + ", " + pourcentageAlcool + ")");
        Collections.sort(brassins);
    }

    public Brassin recuperer(int index){
        return brassins.get(index);
    }

    public void modifier(int index, int numero, String commentaire, String acronyme, int dateCreation, int quantite, int id_typeBiere, String couleur, float densiteOriginale, float densiteFinale, float pourcentageAlcool){
        brassins.get(index).setNumero(numero);
        brassins.get(index).setCommentaire(commentaire);
        brassins.get(index).setAcronyme(acronyme);
        brassins.get(index).setDateCreation(dateCreation);
        brassins.get(index).setQuantite(quantite);
        brassins.get(index).setId_typeBiere(id_typeBiere);
        brassins.get(index).setCouleur(couleur);
        brassins.get(index).setDensiteOriginale(densiteOriginale);
        brassins.get(index).setDensiteFinale(densiteFinale);
        brassins.get(index).setPourcentageAlcool(pourcentageAlcool);
    }

    public void supprimer(int index){
        brassins.remove(index);
    }

    public int tailleListe(){
        return brassins.size();
    }
}
