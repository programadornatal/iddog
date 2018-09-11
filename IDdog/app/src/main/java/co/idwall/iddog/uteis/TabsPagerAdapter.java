package co.idwall.iddog.uteis;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import co.idwall.iddog.fragmentos.ItemGaleria;

/**
 * Created by fabio on 10/09/18.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private int tabs;
    public TabsPagerAdapter(FragmentManager fm, int tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = ItemGaleria.newInstance();
        return fragment;
    }

    @Override
    public int getCount() {
        return tabs;
    }
}
