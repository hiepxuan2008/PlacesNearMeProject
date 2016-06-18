package com.itshareplus.placesnearme.Adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.itshareplus.placesnearme.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mai Thanh Hiep on 4/25/2016.
 */
public class ImageGalleryAdapter {
    Context context;
    List<String> images;
    LinearLayout linearLayoutImageGallery;
    int thumbnailSize = 200;
    public ImageGalleryAdapter(Context context, LinearLayout linearLayoutImageGallery, List<String> images) {
        this.context = context;
        this.images = images;
        this.linearLayoutImageGallery = linearLayoutImageGallery;
    }

    public void execute() {
        loadImages();
    }

    private void loadImages() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
        params.setMargins(15, 15, 15, 15);
        for (int i = 0; i < images.size(); ++i) {
            ImageView thumbImageView = new ImageView(context);
            thumbImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbImageView.setLayoutParams(params);
            Picasso.with(context).load(images.get(i)).error(R.drawable.photo_placeholder).into(thumbImageView);
            linearLayoutImageGallery.addView(thumbImageView);
        }
    }

    public void setData(List<String> images) {
        this.images = images;
    }

    public void notifyDataSetChanged() {
        linearLayoutImageGallery.removeAllViews();
        loadImages();
    }
}
