package com.example.se114n21.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.se114n21.R;

import java.io.IOException;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    private List<Uri> mListImage;

    public ImageAdapter(List<Uri> mListImage) {
        this.mListImage = mListImage;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri uri = mListImage.get(position);
        if (uri == null) {
            return;
        }

        holder.Img.setImageURI(uri);
    }


    @Override
    public int getItemCount() {
        if (mListImage != null) {
            return mListImage.size();
        }
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView Img;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            Img = itemView.findViewById(R.id.img);
        }
    }

}
