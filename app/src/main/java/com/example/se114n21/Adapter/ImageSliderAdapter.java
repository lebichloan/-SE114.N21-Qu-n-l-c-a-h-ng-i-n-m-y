package com.example.se114n21.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.se114n21.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class ImageSliderAdapter extends SliderViewAdapter<ImageSliderAdapter.ImageSliderViewHolder>{

    private Context mContext;
    private List<String> mListImageSlider;

    public ImageSliderAdapter(Context mContext, List<String> mListImageSlider) {
        this.mContext = mContext;
        this.mListImageSlider = mListImageSlider;
    }

    @Override
    public ImageSliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image_slider, parent, false);
        return new ImageSliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageSliderViewHolder holder, int position) {
        String str = mListImageSlider.get(position);
        if (str == null) {
            return;
        }

//        Toast.makeText(mContext, "co ne", Toast.LENGTH_SHORT).show();

        Glide.with(mContext)
                .load(str)
                .into(holder.imgSlider);
    }

    @Override
    public int getCount() {
        if (mListImageSlider != null) {
            return mListImageSlider.size();
        }
        return 0;
    }

    public class ImageSliderViewHolder extends SliderViewAdapter.ViewHolder {
       ImageView imgSlider;

       public ImageSliderViewHolder(View itemView) {
           super(itemView);
           imgSlider = itemView.findViewById(R.id.img_slider);
       }
   }
}
