package co.idwall.iddog;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import co.idwall.iddog.uteis.Contantes;

public class FullImageActivity extends AppCompatActivity {

    ImageView img_full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable(Contantes.IMAGE_FULL);

        img_full = (ImageView) findViewById(R.id.img_full);
        img_full.setImageBitmap(bmp);
        img_full.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FullImageActivity.this.finish();
            }
        });
        createToolBar();
    }

    private void createToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.voltar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.branca));
        toolbar.setBackgroundColor(getResources().getColor(R.color.laranja));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.arrow_left));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
