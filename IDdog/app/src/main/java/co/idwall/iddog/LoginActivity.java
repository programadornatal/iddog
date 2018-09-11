package co.idwall.iddog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import co.idwall.iddog.uteis.Validacoes;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edt_main_email;
    Button btn_main_acessar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Casting
        edt_main_email = (EditText) findViewById(R.id.edt_main_email);
        btn_main_acessar = (Button) findViewById(R.id.btn_main_acessar);
        btn_main_acessar.setOnClickListener(this);
    }

    // Controle geral de cliques de botões da aplicação
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_main_acessar:
            validaLogin();
            break;
        }
    }

    // Validação do login
    void validaLogin() {
        String email = edt_main_email.getText().toString();
        if(Validacoes.ValidaEmail(email)) {
            final Intent galeriaintent = new Intent(this, GaleriaActivity.class);
            startActivity(galeriaintent);
        } else {
           edt_main_email.setError("E-mail obrigatório inválido");
        }
    }
}
