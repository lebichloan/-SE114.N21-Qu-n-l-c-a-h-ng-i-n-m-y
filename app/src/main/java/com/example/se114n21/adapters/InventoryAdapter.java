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
import com.example.se114n21.models.Inventory;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private Context context;
    private List<Inventory> listInventory;

    public InventoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Inventory> list)
    {
        listInventory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repo_inventory, parent, false);
        return new InventoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        Inventory inventory = listInventory.get(position);
        if (inventory == null)
            return;

        holder.tvName.setText(inventory.getName());
        holder.tvIdInventory.setText(inventory.getId());
        holder.tvImport.setText("SL nhập: " + inventory.getImp());
        holder.tvExport.setText("SL xuất: " + inventory.getExp());
    }

    @Override
    public int getItemCount() {
        if (listInventory != null)
            return listInventory.size();
        return 0;
    }

    public class InventoryViewHolder extends ViewHolder {

        private TextView tvName, tvIdInventory, tvImport, tvExport;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNameInventory);
            tvIdInventory = itemView.findViewById(R.id.tvIdInventory);
            tvImport = itemView.findViewById(R.id.tvImport);
            tvExport = itemView.findViewById(R.id.tvExport);

        }
    }
}
