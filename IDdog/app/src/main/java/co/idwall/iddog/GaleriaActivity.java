package co.idwall.iddog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import co.idwall.iddog.adaptadores.TabsPagerAdapter;

import static co.idwall.iddog.uteis.Contantes.TOKEN;

public class GaleriaActivity extends AppCompatActivity {

    SharedPreferences prefs;
    String token;
    String[] doguinhos = {
            "Husky",
            "Labrador",
            "Hound",
            "Pug",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        prefs = getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        token = prefs.getString(TOKEN, null);
        if(token != null) {
            Log.i("TOKKKEN", token);
        } else {
            showErroAcesso();
        }

        createToolBar();
        createTabLayout();
    }

    private void showErroAcesso() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(GaleriaActivity.this);
        alertbox.setIcon(android.R.drawable.ic_dialog_alert);
        alertbox.setTitle("Sessão finalizada");
        alertbox.setMessage("Sua sessão foi finalizada e por isso é necessário fazer um novo ");
        alertbox.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        alertbox.show();
    }

    private void createToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("IDdog - Selecione o seu favorito abaixo");
        toolbar.setTitleTextColor(getResources().getColor(R.color.branca));
        toolbar.setBackgroundColor(getResources().getColor(R.color.laranja));
        setSupportActionBar(toolbar);
    }
    private void createTabLayout() {
        //Toast.makeText(this, "TipoVA " + tipo_veiculo, Toast.LENGTH_SHORT).show();
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        // Caso queiram adicionar um icon //.setIcon(R.drawable.checklist_icone)
        for(String i : doguinhos) {
            tabLayout.addTab(tabLayout.newTab().setText(i));
        }

        TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));
    }
}
