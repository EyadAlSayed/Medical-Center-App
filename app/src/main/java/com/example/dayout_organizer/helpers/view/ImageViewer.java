package com.example.dayout_organizer.helpers.view;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ImageViewer {

    public static void downloadImageAndCached(Context context, ImageView imv, int placeHolder, String url) {
        Glide.with(context)
                .load(url)
                .placeholder(placeHolder)
                .into(imv);
    }

    public static void downloadImage(Context context, ImageView imv, int placeHolder, String url) {
        Glide.with(context)
                .load(url)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(placeHolder)
                .into(imv);
    }
}
