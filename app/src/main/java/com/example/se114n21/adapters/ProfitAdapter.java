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
import com.example.se114n21.models.Profit;

import java.util.List;

public class ProfitAdapter extends RecyclerView.Adapter<ProfitAdapter.ProfitViewHolder> {

    private Context context;
    private List<Profit> listProfit;

    public ProfitAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Profit> list)
    {
        listProfit = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sale_profit, parent, false);
        return new ProfitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfitViewHolder holder, int position) {
        Profit Profit = listProfit.get(position);
        if (Profit == null)
            return;

        holder.tvDate.setText(Profit.getDate());
        holder.tvTotal.setText(Profit.getTotal() + " vnd");

    }

    @Override
    public int getItemCount() {
        if (listProfit != null)
            return listProfit.size();
        return 0;
    }

    public class ProfitViewHolder extends ViewHolder {

        private TextView tvDate, tvTotal, tvOrder;

        public ProfitViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDateProfit);
            tvTotal = itemView.findViewById(R.id.tvTotalProfit);
        }
    }
}
