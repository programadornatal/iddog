package co.idwall.iddog.fragmentos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import co.idwall.iddog.FullImageActivity;
import co.idwall.iddog.R;
import co.idwall.iddog.adaptadores.ImageAdapeter;
import co.idwall.iddog.uteis.Contantes;

public class ItemGaleria extends Fragment implements AdapterView.OnItemClickListener {
    PackageManager packageManager;
    View v;
    GridView gv_item_galeria;

    public static ItemGaleria newInstance() {
        ItemGaleria itemGaleria = new ItemGaleria();
        Bundle args = new Bundle();
        itemGaleria.setArguments(args);
        return itemGaleria;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        packageManager = getActivity().getPackageManager();
        v = inflater.inflate(R.layout.item_galeria, container, false);
        gv_item_galeria = (GridView) v.findViewById(R.id.gv_item_galeria);
        gv_item_galeria.setAdapter(new ImageAdapeter(getContext()));
        gv_item_galeria.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Log.i("CLIQUE", "Selecionou " + position);
        Intent intent = new Intent(getActivity(), FullImageActivity.class);
        view.buildDrawingCache();
        Bitmap image= view.getDrawingCache();
        Bundle extras = new Bundle();
        extras.putParcelable(Contantes.IMAGE_FULL, image);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
