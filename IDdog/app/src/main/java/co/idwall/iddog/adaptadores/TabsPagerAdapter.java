package co.idwall.iddog.adaptadores;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import co.idwall.iddog.fragmentos.ItemGaleria;

/**
 * Created by fabio on 10/09/18.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private int tabs;
    private String token;
    private int doguinho;

    public TabsPagerAdapter(FragmentManager fm, String token, int doguinho, int tabs) {
        super(fm);
        this.tabs = tabs;
        this.token = token;
        this.doguinho = doguinho;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ItemGaleria.newInstance(this.token, this.doguinho);
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
