package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.R;

import java.util.List;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.HoaDonViewHolder>{
    List<HoaDon> listhoadon;
    AdapterHoaDon.IclickListener iclickListener;

    public void setFilteredList(List<HoaDon> filteredList)
    {
        this.listhoadon = filteredList;
        notifyDataSetChanged();
    }
    public interface IclickListener{
        void OnClickDeleteitem(HoaDon hd);
    }
    public AdapterHoaDon (List<HoaDon> listhoadon, IclickListener iclickListener) {
        this.listhoadon = listhoadon;
        this.iclickListener = iclickListener;
    }
    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hoa_don, parent,false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hd = listhoadon.get(position);
        if (hd == null)
        {
            return;
        }
        holder.tvid.setText(hd.getMaHD());
        holder.tvkhname.setText(hd.getMaKH());
        holder.tvngayhd.setText(hd.getNgayHD());
        holder.tvtinhtrang.setText("Đã hoàn thành!");
        holder.tvthanhtien.setText(hd.getTongTienPhaiTra().toString());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iclickListener.OnClickDeleteitem(hd);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listhoadon != null)
        {
            return listhoadon.size();
        }
        return 0;
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout itemhoadon;
        private TextView tvid;
        private TextView tvkhname;
        private TextView tvthanhtien;
        private TextView tvtinhtrang;
        private TextView tvngayhd;
        private Button delete;
        public HoaDonViewHolder(@NonNull View item)
        {
            super(item);
            InitUI();
        }

        private void InitUI() {
            tvid = itemView.findViewById(R.id.tv_mahoadon);
            tvkhname = itemView.findViewById(R.id.tv_makhachhang);
            tvngayhd = itemView.findViewById(R.id.tv_ngayhd);
            tvtinhtrang = itemView.findViewById(R.id.tv_tinhtrang);
            tvthanhtien = itemView.findViewById(R.id.tv_tongtien);
            delete = itemView.findViewById(R.id.button_del_hd);
            itemhoadon = itemView.findViewById(R.id.item_hoadon);
        }
    }
}
