package com.example.se114n21.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.se114n21.Interface.HoaDonInterface;
import com.example.se114n21.Models.HoaDon;
import com.example.se114n21.Models.KhuyenMai;
import com.example.se114n21.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> implements Filterable {
    private List<HoaDon> mListHoaDon;
    private List<HoaDon> mListHoaDonOLD;
    private HoaDonInterface hoaDonInterface;
    private String code;

    public HoaDonAdapter(List<HoaDon> mListHoaDon, HoaDonInterface hoaDonInterface, String code) {
        this.mListHoaDon = mListHoaDon;
        this.mListHoaDonOLD = mListHoaDon;
        this.hoaDonInterface = hoaDonInterface;
        this.code = code;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hd, parent, false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        HoaDon hoaDon = mListHoaDon.get(position);
        if (hoaDon == null) {
            return;
        }

        holder.id.setText(hoaDon.getMaHD());
        holder.name.setText(hoaDon.getTenKH());
        if (code.equals("loinhuan")) {
            holder.tongthanhtoan.setText(String.valueOf(hoaDon.getTongTienPhaiTra() - hoaDon.getTienVon()));
        } else {
            holder.tongthanhtoan.setText(String.valueOf(hoaDon.getTongTienPhaiTra()));
        }
        holder.thoigian.setText(hoaDon.getNgayHD());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hoaDonInterface.onClick(hoaDon);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListHoaDon != null) {
            return mListHoaDon.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();

                if (strSearch.isEmpty()) {
                    mListHoaDon = mListHoaDonOLD;
                }
                else {
                    List<HoaDon> list = new ArrayList<>();
                    for (HoaDon hoaDon : mListHoaDonOLD) {
                        if (hoaDon.getMaHD().toLowerCase().contains(strSearch.toLowerCase())
                                || hoaDon.getTenKH().toLowerCase().contains(strSearch.toLowerCase())
                                || hoaDon.getSoDienThoaiKH().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(hoaDon);
                        }


                    }

                    mListHoaDon = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListHoaDon;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListHoaDon =  (List<HoaDon>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, tongthanhtoan, thoigian;
        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_hd);
            name = itemView.findViewById(R.id.ten_kh);
            tongthanhtoan = itemView.findViewById(R.id.tongthanhtoan_hd);
            thoigian = itemView.findViewById(R.id.thoigian_hd);
        }
    }
}
