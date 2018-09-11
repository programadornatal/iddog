package co.idwall.iddog.fragmentos;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import co.idwall.iddog.R;

public class ItemGaleria extends Fragment {
    PackageManager packageManager;
    View v;

    public static ItemGaleria newInstance() {
        ItemGaleria motorFragment = new ItemGaleria();
        Bundle args = new Bundle();
        motorFragment.setArguments(args);
        return motorFragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        packageManager = getActivity().getPackageManager();
        v = inflater.inflate(R.layout.item_galeria, container, false);
        atualizarInformacoes();
        return v;
    }

    public void atualizarInformacoes() {

    }
}
