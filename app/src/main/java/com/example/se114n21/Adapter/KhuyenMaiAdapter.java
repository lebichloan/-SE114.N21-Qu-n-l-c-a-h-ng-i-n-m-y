package com.example.se114n21.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;

import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.ViewHolder> {
    private Context context;
    private List<KhuyenMai> khuyenMaiList;

    public KhuyenMaiAdapter(Context context, List<KhuyenMai> khuyenMaiList) {
        this.context = context;
        this.khuyenMaiList = khuyenMaiList;
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

        holder.txtTenCT.setText(khuyenMai.getTenKM());
        holder.txtKhuyenMai.setText(String.valueOf(khuyenMai.getKhuyenMai()));
        holder.txtNgayBD.setText(khuyenMai.getNgayBD());
        holder.txtNgayKT.setText(khuyenMai.getNgayKT());
    }

    @Override
    public int getItemCount() {
        return khuyenMaiList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTenCT;
        public TextView txtKhuyenMai;
        public TextView txtNgayBD;
        public TextView txtNgayKT;

        public ViewHolder(View itemView){
            super(itemView);
            txtTenCT = itemView.findViewById(R.id.txtTenCT);
            txtKhuyenMai = itemView.findViewById(R.id.txtKhuyenMai);
            txtNgayBD = itemView.findViewById(R.id.txtNgayBD);
            txtNgayKT = itemView.findViewById(R.id.txtNgayKT);
        }
    }

}
