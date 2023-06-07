package com.example.se114n21.ViewModels;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.se114n21.Models.KhachHang;
import com.example.se114n21.R;

import java.util.List;

public class AdapterCustomer extends RecyclerView.Adapter<AdapterCustomer.CustomerViewHolder> {
    List<KhachHang> listcustomer;
    IclickListener iclickListener;
    public interface IclickListener{
        void OnClickUpdateitem(KhachHang kh);
        void OnClickDeleteitem(KhachHang kh);
    }
    public AdapterCustomer(List<KhachHang> listcustomer, IclickListener iclickListener) {
        this.listcustomer = listcustomer;
        this.iclickListener = iclickListener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout, parent,false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        KhachHang kh = listcustomer.get(position);
        if (kh == null)
        {
            return;
        }
        holder.tvid.setText(kh.getMaKH());
        holder.tvname.setText(kh.getTen());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iclickListener.OnClickUpdateitem(kh);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iclickListener.OnClickDeleteitem(kh);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (listcustomer != null)
        {
            return listcustomer.size();
        }
        return 0;
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder{
        private TextView tvid;
        private TextView tvname;

        private Button edit;
        private Button delete;
        public CustomerViewHolder(@NonNull View item)
        {
            super(item);
            InitUI();
        }

        private void InitUI() {
            tvid = itemView.findViewById(R.id.tv_makh);
            tvname = itemView.findViewById(R.id.tv_name);
            edit = itemView.findViewById(R.id.update_customer_button);
            delete = itemView.findViewById(R.id.delete_customer_button);
        }
    }
}
