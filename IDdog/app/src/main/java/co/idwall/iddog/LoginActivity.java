package co.idwall.iddog;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import co.idwall.iddog.uteis.ConexaoInternet;
import co.idwall.iddog.uteis.Validacoes;
import co.idwall.iddog.uteis.VolleySingleton;

import static co.idwall.iddog.uteis.Contantes.*;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    String email;

    ProgressDialog progressDialog = null;

    EditText edt_main_email;
    Button btn_main_acessar;

    SharedPreferences.Editor editor; // Para salvar o token localmente
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Casting
        edt_main_email = (EditText) findViewById(R.id.edt_main_email);
        btn_main_acessar = (Button) findViewById(R.id.btn_main_acessar);
        btn_main_acessar.setOnClickListener(this);

        // Dev
        // edt_main_email.setText("email@valido.com");
        prefs = getSharedPreferences(TOKEN, Context.MODE_PRIVATE);
        String token = prefs.getString(TOKEN, null);
        editor = getSharedPreferences(TOKEN, Context.MODE_PRIVATE).edit();
        if(token != null) {
            editor.clear();
        }
    }

    // Controle geral de cliques de botões da aplicação
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_main_acessar:
            verificaInternet();
            break;
        }
    }

    // Será que tem internet?
    void verificaInternet() {
        if (!ConexaoInternet.temInternet(LoginActivity.this)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(LoginActivity.this);
            alertbox.setIcon(android.R.drawable.ic_dialog_alert);
            alertbox.setTitle(R.string.sem_internet);
            alertbox.setMessage(R.string.conectar_wifi);
            alertbox.setPositiveButton(R.string.sim, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });
            alertbox.setNegativeButton(R.string.nao, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertbox.show();
        } else {
            validaLogin();
        }
    }

    // Validação do login
    void validaLogin() {
        email = edt_main_email.getText().toString();
        if(Validacoes.ValidaEmail(email)) {
            getTokenLogin(email); // Se e-mail válido faz acesso a servidor
        } else {
           edt_main_email.setError(getString(R.string.email_obrigatorio));
        }
    }

    public void getTokenLogin(String email) {

        // Loader básico
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getResources().getString(R.string.carregando));
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Criação do objeto email para envio ao servidor
        JSONObject acesso = new JSONObject();
        try {
            acesso.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, // Método
                LOGIN, // url
                acesso, // dados necessários no body da requisição
                new Response.Listener<JSONObject>() { // Resposta com um Objeto JSON
                    @Override
                    public void onResponse(JSONObject response) {
                       JSONObject jsonObject = response;
                        try {
                            JSONObject user = jsonObject.getJSONObject("user");
                            // Log.i("RESPOSTA", response.getString("user"));
                            // Log.i("RESPOSTA", user.getString("token"));
                            salvarToken(user.getString(TOKEN));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(getString(R.string.erro),
                                error.toString() + ":" + error.getMessage() + "" + error.getCause());
                        error.printStackTrace();
                        Toast.makeText(LoginActivity.this, R.string.suporte, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
                VolleySingleton.getInstance(LoginActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    void salvarToken(String token) {
        editor.putString(TOKEN, token);
        editor.apply();
        final Intent galeriaintent = new Intent(this, GaleriaActivity.class);
        startActivity(galeriaintent);
    }
}
