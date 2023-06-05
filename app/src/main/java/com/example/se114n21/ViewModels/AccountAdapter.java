package com.example.se114n21.ViewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private Context context;
    private List<Account> accountList;

    public AccountAdapter(Context context, List<Account> accountList) {
        this.context = context;
        this.accountList = accountList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.re)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView recLinkAvata;
    TextView recHoTen, recEmail;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        initUI();
    }

    private void initUI() {
    }
}