package com.example.se114n21.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;

import java.util.List;

public class KhuyenMaiAdapter extends RecyclerView.Adapter<KhuyenMaiAdapter.ViewHolder> {

    //    private Context context;
    private List<KhuyenMai> khuyenMaiList;

    IclickListener iclickListener;
  
    public interface IclickListener{
        void OnClickUpdateitem(KhuyenMai khuyenMai);
        void OnClickDeleteitem(KhuyenMai khuyenMai);
    }

    public KhuyenMaiAdapter(List<KhuyenMai> khuyenMaiList, KhuyenMaiAdapter.IclickListener iclickListener) {
        this.khuyenMaiList = khuyenMaiList;
        this.iclickListener = iclickListener;
    }

//    public KhuyenMaiAdapter(Context context, List<KhuyenMai> khuyenMaiList) {
//        this.context = context;
//        this.khuyenMaiList = khuyenMaiList;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale, parent, false);
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

        holder.ic_edit_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iclickListener.OnClickUpdateitem(khuyenMai);
            }
        });

        holder.ic_delete_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iclickListener.OnClickDeleteitem(khuyenMai);
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

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTenCT;
        public TextView txtKhuyenMai;
        public TextView txtNgayBD;
        public TextView txtNgayKT;
        public Button ic_edit_sale;
        public Button ic_delete_sale;


        public ViewHolder(View itemView){
            super(itemView);
            txtTenCT = itemView.findViewById(R.id.txtTenCT);
            txtKhuyenMai = itemView.findViewById(R.id.txtKhuyenMai);
            txtNgayBD = itemView.findViewById(R.id.txtNgayBD);
            txtNgayKT = itemView.findViewById(R.id.txtNgayKT);
            ic_edit_sale = itemView.findViewById(R.id.ic_edit_sale);
            ic_delete_sale = itemView.findViewById(R.id.ic_delete_sale);
        }
    }

}
