package com.example.se114n21.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.se114n21.R;

public class GlideUtils {

    public static void loadUrlBanner(String url, ImageView imageView) {
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_person);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.ic_person)
                .dontAnimate()
                .into(imageView);
    }

    public static void loadUrl(String url, ImageView imageView) {
        if (StringUtil.isEmpty(url)) {
            imageView.setImageResource(R.drawable.ic_person);
            return;
        }
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.ic_person)
                .dontAnimate()
                .into(imageView);
    }
}