package com.example.se114n21.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.se114n21.Interface.ImageInterface;
import com.example.se114n21.R;
import com.example.se114n21.ViewModels.DetailProduct;
import com.example.se114n21.ViewModels.EditProduct;
import com.example.se114n21.ViewModels.ListProduct;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class ImageForEditAdapter extends RecyclerView.Adapter<ImageForEditAdapter.ImageForEditViewHolder>{
    private List<String> mListURL;
    private Context mContext;
    private ImageInterface mImageInterface;

    public ImageForEditAdapter(List<String> mListURL, Context mContext, ImageInterface imageInterface) {
        this.mListURL = mListURL;
        this.mContext =  mContext;
        this.mImageInterface = imageInterface;
    }

    public List<String> getListURL() {
        return mListURL;
    }

    @NonNull
    @Override
    public ImageForEditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageForEditViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageForEditViewHolder holder, int position) {
        String URL = mListURL.get(position);
        if (URL == null) {
            return;
        }

        Glide.with(mContext)
                .load(URL)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.mipmap.ic_launcher)
                .into(holder.Img);

        holder.deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListURL.remove(holder.getAbsoluteAdapterPosition());
                notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                notifyItemRangeChanged(holder.getAbsoluteAdapterPosition(), mListURL.size());

                mImageInterface.deleteImage(true, URL);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListURL != null) {
            return mListURL.size();
        }
        return 0;
    }

    public class ImageForEditViewHolder extends RecyclerView.ViewHolder {
        private ImageView Img;
        private Button deleteImg;
        public ImageForEditViewHolder(@NonNull View itemView) {
            super(itemView);

            Img = itemView.findViewById(R.id.img);
            deleteImg = itemView.findViewById(R.id.btn_delete_img);
        }
    }
}
