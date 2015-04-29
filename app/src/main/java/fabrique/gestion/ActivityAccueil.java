package fabrique.gestion;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import fabrique.gestion.FragmentAjouter.FragmentAjouter;
import fabrique.gestion.FragmentGestion.FragmentGestion;
import fabrique.gestion.FragmentListe.FragmentListe;
import fabrique.gestion.FragmentTableauDeBord.FragmentTableauDeBord;

public class ActivityAccueil extends FragmentActivity {

    private FragmentAmeliore vue;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.onglet);

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
    }

    public void setVue(FragmentAmeliore vue) {
        this.vue = vue;
    }

    @Override
    public void onBackPressed() {
        vue.onBackPressed();
    }
}
