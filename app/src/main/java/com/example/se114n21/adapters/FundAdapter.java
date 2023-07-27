package com.example.se114n21.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.se114n21.R;
import com.example.se114n21.models.Fund;

import java.util.List;

public class FundAdapter extends RecyclerView.Adapter<FundAdapter.FundViewHolder> {

    private Context context;
    private List<Fund> listFund;

    public FundAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Fund> list)
    {
        listFund = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FundViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profit_fund, parent, false);
        return new FundViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FundViewHolder holder, int position) {
        Fund fund = listFund.get(position);
        if (fund == null)
            return;

        holder.tvDate.setText(fund.getDate());
        holder.tvTotal.setText(fund.getTotal() + " vnd");
    }

    @Override
    public int getItemCount() {
        if (listFund != null)
            return listFund.size();
        return 0;
    }

    public class FundViewHolder extends ViewHolder {

        private TextView tvDate, tvTotal;

        public FundViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDateFund);
            tvTotal = itemView.findViewById(R.id.tvTotalFund);
        }
    }
}
