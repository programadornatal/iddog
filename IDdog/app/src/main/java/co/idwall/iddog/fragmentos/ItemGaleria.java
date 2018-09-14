package co.idwall.iddog.fragmentos;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import co.idwall.iddog.FullImageActivity;
import co.idwall.iddog.R;
import co.idwall.iddog.adaptadores.ImageAdapter;
import co.idwall.iddog.uteis.Contantes;
import co.idwall.iddog.uteis.VolleySingleton;

import static co.idwall.iddog.uteis.Contantes.DOGUINHO;
import static co.idwall.iddog.uteis.Contantes.DOGUINHOS;
import static co.idwall.iddog.uteis.Contantes.FEED;
import static co.idwall.iddog.uteis.Contantes.TOKEN;

public class ItemGaleria extends Fragment implements AdapterView.OnItemClickListener {
    PackageManager packageManager;
    View v;
    GridView gv_item_galeria;
    LinearLayout ll_item_galeria;
    String token;
    int doguinho;
    String[] images;

    public static ItemGaleria newInstance(String token, int doguinho) {
        ItemGaleria itemGaleria = new ItemGaleria();
        Log.i("ITEM", doguinho + " ");
        // Bundle para passagem dos objetos até o Fragment
        Bundle args = new Bundle();
        args.putString(TOKEN, token);
        args.putInt(DOGUINHO, doguinho);
        itemGaleria.setArguments(args);
        return itemGaleria;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        packageManager = getActivity().getPackageManager();

        // Casting dos objetos
        v = inflater.inflate(R.layout.item_galeria, container, false);
        ll_item_galeria = (LinearLayout) v.findViewById(R.id.ll_item_galeria);
        gv_item_galeria = (GridView) v.findViewById(R.id.gv_item_galeria);

        // Carrega doguinho correspondente
        carregaDoguinho();
        return v;
    }

    private void exibeGaleria() {
        // Controla quem vai aparecer
        ll_item_galeria.setVisibility(View.GONE);
        gv_item_galeria.setVisibility(View.VISIBLE);

        gv_item_galeria.setAdapter(new ImageAdapter(getContext(), images));
        gv_item_galeria.setOnItemClickListener(this);
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

    private void carregaDoguinho() {
        // Recuperando os dados enviados
        token = getArguments().getString(TOKEN);
        doguinho = getArguments().getInt(DOGUINHO);
        Log.i("TD", " - " + doguinho);

        // Criação do objeto email para envio ao servidor

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, // Método
                FEED + DOGUINHOS[doguinho], // url
                null,
                new Response.Listener<JSONObject>() { // Resposta com um Objeto JSON
                    @Override
                    public void onResponse(JSONObject response) {
                        // JSONObject jsonObject = response;
                        try {
                            JSONArray jsonArray = response.getJSONArray("list");
                            // Log.i("RESPOSTA", "" + jsonArray.length()); // Vem tudo
                            images = new String[jsonArray.length()];
                            for(int i = 0; i < jsonArray.length(); i++) {
                                images[i] = jsonArray.getString(i);
                            }
                            exibeGaleria();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), R.string.suporte, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(getString(R.string.erro),
                                error.toString() + ":" + error.getMessage() + "" + error.getCause());
                        error.printStackTrace();
                        Toast.makeText(getContext(), R.string.suporte, Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }
}
