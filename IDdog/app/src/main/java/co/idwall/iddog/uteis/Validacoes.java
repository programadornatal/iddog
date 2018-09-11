package co.idwall.iddog.uteis;

import android.text.TextUtils;

public class Validacoes {
    public final static boolean ValidaEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
