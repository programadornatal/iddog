package co.idwall.iddog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Animação da logo com Alpha
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animaalpha);

        // Casting logo
        ImageView imgLogo = (ImageView) findViewById(R.id.imgLogo);
        imgLogo.startAnimation(animation);

        final Intent mainintent = new Intent(this, LoginActivity.class);
        Thread timer = new Thread() {
            public void run () {
                try {
                    sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    startActivity(mainintent);
                    finish();
                }
            }
        };
        timer.start();
    }
}
