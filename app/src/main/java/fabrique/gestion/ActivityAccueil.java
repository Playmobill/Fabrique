package fabrique.gestion;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import fabrique.gestion.BDD.TableBrassin;
import fabrique.gestion.BDD.TableCuve;
import fabrique.gestion.BDD.TableEmplacement;
import fabrique.gestion.BDD.TableEtatCuve;
import fabrique.gestion.BDD.TableEtatFermenteur;
import fabrique.gestion.BDD.TableEtatFut;
import fabrique.gestion.BDD.TableFermenteur;
import fabrique.gestion.BDD.TableFut;
import fabrique.gestion.BDD.TableGestion;
import fabrique.gestion.BDD.TableHistorique;
import fabrique.gestion.BDD.TableListeHistorique;
import fabrique.gestion.BDD.TableRecette;
import fabrique.gestion.FragmentAjouter.FragmentAjouter;
import fabrique.gestion.FragmentGestion.FragmentGestion;
import fabrique.gestion.FragmentListe.FragmentListe;
import fabrique.gestion.FragmentSauvegarde.FragmentSauvegarde;
import fabrique.gestion.FragmentTableauDeBord.FragmentTableauDeBord;

public class ActivityAccueil extends FragmentActivity {

    private FragmentAmeliore vue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.onglet);

        configurerActionBar();
        chargerBDD();
    }

    @SuppressWarnings("deprecation")
    private void configurerActionBar() {
        ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tabTableauDeBord = actionBar.newTab().setText("Tableau de bord");
        FragmentTableauDeBord fragmentTableauDeBord = new FragmentTableauDeBord();
        vue = fragmentTableauDeBord;
        tabTableauDeBord.setTabListener(new TabListener(fragmentTableauDeBord));
        actionBar.addTab(tabTableauDeBord);

        ActionBar.Tab tabTransfert = actionBar.newTab().setText("Transfert");
        tabTransfert.setTabListener(new TabListener(new FragmentTransfert()));
        actionBar.addTab(tabTransfert);

        ActionBar.Tab tabListe = actionBar.newTab().setText("Liste");
        tabListe.setTabListener(new TabListener(new FragmentListe()));
        actionBar.addTab(tabListe);

        ActionBar.Tab tabGestion = actionBar.newTab().setText("Gestion");
        tabGestion.setTabListener(new TabListener(new FragmentGestion()));
        actionBar.addTab(tabGestion);

        ActionBar.Tab tabAjouter = actionBar.newTab().setText("Ajouter");
        tabAjouter.setTabListener(new TabListener(new FragmentAjouter()));
        actionBar.addTab(tabAjouter);

        ActionBar.Tab tabSauvegardeLecture = actionBar.newTab().setText("Sauvegarde");
        tabSauvegardeLecture.setTabListener(new TabListener(new FragmentSauvegarde()));
        actionBar.addTab(tabSauvegardeLecture);
    }

    private void chargerBDD() {
        ProgressDialog progressDialog = ProgressDialog.show(this, "Chargement de la base de données", "", true);
        progressDialog.show();
        progressDialog.setMessage("Emplacements");
        TableEmplacement.instance(this);

        progressDialog.setMessage("Recettes");
        TableRecette.instance(this);

        progressDialog.setMessage("Brassins");
        TableBrassin.instance(this);

        progressDialog.setMessage("États fermenteur");
        TableEtatFermenteur.instance(this);

        progressDialog.setMessage("Fermenteurs");
        TableFermenteur.instance(this);

        progressDialog.setMessage("États cuve");
        TableEtatCuve.instance(this);

        progressDialog.setMessage("Cuves");
        TableCuve.instance(this);

        progressDialog.setMessage("États fut");
        TableEtatFut.instance(this);

        progressDialog.setMessage("Fûts");
        TableFut.instance(this);

        progressDialog.setMessage("Historiques");
        TableHistorique.instance(this);

        progressDialog.setMessage("Liste d'historiques");
        TableListeHistorique.instance(this);

        progressDialog.setMessage("Gestion");
        TableGestion.instance(this);

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public void setVue(FragmentAmeliore vue) {
        this.vue = vue;
    }

    @Override
    public void onBackPressed() {
        vue.onBackPressed();
    }
}
