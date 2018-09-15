package co.idwall.iddog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import co.idwall.iddog.adaptadores.TabsPagerAdapter;
import co.idwall.iddog.uteis.Contantes;

import static co.idwall.iddog.uteis.Contantes.DOGUINHOS;
import static co.idwall.iddog.uteis.Contantes.TOKEN;

public class GaleriaActivity extends AppCompatActivity {

    SharedPreferences prefs;
    String token;

    TabsPagerAdapter adapter;
    TabLayout tabLayout;
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        prefs = getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        token = prefs.getString(TOKEN, null);
        if(token == null) {
            showErroAcesso();
        }
        createToolBar();
        createTabLayout();
    }

    private void showErroAcesso() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(GaleriaActivity.this);
        alertbox.setIcon(android.R.drawable.ic_dialog_alert);
        alertbox.setTitle(R.string.sessao);
        alertbox.setMessage(R.string.sessao_message);
        alertbox.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        alertbox.show();
    }

    private void createToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.tb_titulo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.branca));
        toolbar.setBackgroundColor(getResources().getColor(R.color.laranja));
        setSupportActionBar(toolbar);
    }
    private void createTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Caso queiram adicionar um icon //.setIcon(R.drawable.checklist_icone)
        for(String i : Contantes.DOGUINHOS) {
            tabLayout.addTab(tabLayout.newTab().setText(i));
        }

        pager = (ViewPager) findViewById(R.id.pager);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                alteraViewPager();
                super.onTabSelected(tab);
            }
        });
        alteraViewPager();
    }

    private void alteraViewPager() {
        adapter = new TabsPagerAdapter(
                getSupportFragmentManager(),
                DOGUINHOS.length
        );
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
