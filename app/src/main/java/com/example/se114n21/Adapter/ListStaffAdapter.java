package com.example.se114n21.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.se114n21.Interface.StaffInterface;
import com.example.se114n21.Models.NhanVien;
import com.example.se114n21.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListStaffAdapter extends RecyclerView.Adapter<ListStaffAdapter.ListStaffViewHolder> implements Filterable {
    private List<NhanVien> mListNhanVien;
    private List<NhanVien> mListNhanVienOLD;
    private StaffInterface staffInterface;


    public ListStaffAdapter(List<NhanVien> mListNhanVien, StaffInterface staffInterface) {
        this.mListNhanVien = mListNhanVien;
        this.mListNhanVienOLD = mListNhanVien;
        this.staffInterface = staffInterface;
    }

    @NonNull
    @Override
    public ListStaffViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_staff, parent, false);
        return new ListStaffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListStaffViewHolder holder, int position) {
        NhanVien nhanVien = mListNhanVien.get(position);
        if (nhanVien == null) {
            return;
        }

        if (nhanVien.getMaNV() != null) {
            holder.id.setText(nhanVien.getMaNV());
        } else {
            holder.id.setText("--");
        }

        holder.name.setText(nhanVien.getHoTen());

        if (nhanVien.getSDT() != null) {
            holder.phone.setText(nhanVien.getSDT());
        } else {
            holder.phone.setText("--");
        }

        holder.email.setText(nhanVien.getEmail());

        if (nhanVien.getLoaiNhanVien().equals("Admin")) {
            holder.role.setText("Admin");
        } else {
            holder.role.setText("Nhân viên");
        }

        if (nhanVien.isTrangThai() == false) {
            holder.state.setText("Đã nghỉ việc");
            holder.state.setTextColor(Color.parseColor("#EE786C"));
        } else {
            holder.state.setText("Đang làm việc");
            holder.state.setTextColor(Color.parseColor("#39AAFC"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                staffInterface.onClick(nhanVien);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mListNhanVien != null) {
            return mListNhanVien.size();
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
                    mListNhanVien = mListNhanVienOLD;
                }
                else {
                    List<NhanVien> list = new ArrayList<>();
                    for (NhanVien nhanVien : mListNhanVienOLD) {

                        if (nhanVien.getMaNV() != null) {

                            if (nhanVien.getSDT() != null) {
                                if (nhanVien.getMaNV().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getHoTen().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getSDT().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getEmail().toLowerCase().contains(strSearch.toLowerCase())) {
                                    list.add(nhanVien);
                                }
                            } else {
                                if (nhanVien.getMaNV().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getHoTen().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getEmail().toLowerCase().contains(strSearch.toLowerCase())) {
                                    list.add(nhanVien);
                                }
                            }

                        } else {

                            if (nhanVien.getSDT() != null) {
                                if (nhanVien.getHoTen().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getSDT().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getEmail().toLowerCase().contains(strSearch.toLowerCase())) {
                                    list.add(nhanVien);
                                }
                            } else {
                                if (nhanVien.getHoTen().toLowerCase().contains(strSearch.toLowerCase())
                                        || nhanVien.getEmail().toLowerCase().contains(strSearch.toLowerCase())) {
                                    list.add(nhanVien);
                                }
                            }

                        }
                    }

                    mListNhanVien = list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListNhanVien;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListNhanVien =  (List<NhanVien>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ListStaffViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, phone, email, role, state;
        public ListStaffViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_id_staff);
            name = itemView.findViewById(R.id.tv_name_staff);
            phone = itemView.findViewById(R.id.tv_phone_staff);
            email = itemView.findViewById(R.id.tv_email_staff);
            role = itemView.findViewById(R.id.tv_role_staff);
            state = itemView.findViewById(R.id.tv_state_staff);
        }
    }
}
