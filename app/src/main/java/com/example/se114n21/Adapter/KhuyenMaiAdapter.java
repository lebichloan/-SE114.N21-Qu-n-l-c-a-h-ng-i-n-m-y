package com.example.se114n21.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import com.example.se114n21.ViewModels.QLKhuyenMai;

import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.ViewHolder> {
    private Context context;
    private List<KhuyenMai> khuyenMaiList;
    private ItemClickListener itemClickListener;
    private LayoutInflater inflater;

    public KhuyenMaiAdapter(Context context, List<KhuyenMai> khuyenMaiList){
        this.inflater = LayoutInflater.from(context);
        this.khuyenMaiList = khuyenMaiList;
    }

    public KhuyenMaiAdapter(List<KhuyenMai> khuyenMaiList, ItemClickListener itemClickListener) {
//        this.context = context;
        this.khuyenMaiList = khuyenMaiList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sale, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhuyenMaiAdapter.ViewHolder holder, int position) {
        KhuyenMai khuyenMai = khuyenMaiList.get(position);

        if (khuyenMai == null){
            return;
        }

        holder.txtTenCT.setText(khuyenMai.getTenKM());
        holder.txtKhuyenMai.setText(String.valueOf(khuyenMai.getKhuyenMai()));
        holder.txtNgayBD.setText(khuyenMai.getNgayBD());
        holder.txtNgayKT.setText(khuyenMai.getNgayKT());

        holder.ic_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onEditButtonClick(khuyenMai);
            }
        });
        holder.ic_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int adapterPosition = holder.getAdapterPosition();
//                if (itemClickListener != null && adapterPosition != RecyclerView.NO_POSITION) {
//                    itemClickListener.onDeleteButtonClick(adapterPosition);
//                }
                itemClickListener.onDeleteButtonClick(khuyenMai);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (khuyenMaiList != null) {
            return khuyenMaiList.size();
        }
        return 0;
    }

    public interface ItemClickListener{
//        void onDeleteButtonClick(int position);
        void onDeleteButtonClick(KhuyenMai khuyenMai);
        void onEditButtonClick(KhuyenMai khuyenMai);
    }

    public void setItemClickListener(QLKhuyenMai itemClickListener) {
        this.itemClickListener = (ItemClickListener) itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTenCT;
        public TextView txtKhuyenMai;
        public TextView txtNgayBD;
        public TextView txtNgayKT;
        public ImageView ic_edit;
        public ImageView ic_delete;

        public ViewHolder(View itemView){
            super(itemView);
            txtTenCT = itemView.findViewById(R.id.txtTenCT);
            txtKhuyenMai = itemView.findViewById(R.id.txtKhuyenMai);
            txtNgayBD = itemView.findViewById(R.id.txtNgayBD);
            txtNgayKT = itemView.findViewById(R.id.txtNgayKT);
            ic_edit = itemView.findViewById(R.id.ic_edit);
            ic_delete = itemView.findViewById(R.id.ic_delete);
        }
    }

}
