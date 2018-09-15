package co.idwall.iddog.adaptadores;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import co.idwall.iddog.R;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    public String[] images;

    public ImageAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(context);
        Glide.with(context)
                .load(images[position])
                .thumbnail(Glide.with(context).load(R.drawable.loading))
                .into(imageView);
        //imageView.setImageResource(images[position]);//Caso fosse imagem do Resource
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(240, 240));
        return imageView;
    }
}
