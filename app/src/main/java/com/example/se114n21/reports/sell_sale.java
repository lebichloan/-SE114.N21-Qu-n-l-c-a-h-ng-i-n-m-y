package com.example.se114n21.reports;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.example.se114n21.models.Sale;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;


public class sell_sale extends Fragment {

    TextView tvDate, tvDate1;
    Button btnDate, btnDate1;
    RecyclerView rvSale;
    List<Sale> saleList;
    SaleAdapter adapter;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    private DatePickerDialog createDatePicker(TextView tv)
    {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int Selectedyear, int Selectedmonth, int SelecteddayOfMonth) {
                // Xử lý sự kiện khi người dùng chọn ngày
                calendar.set(Calendar.YEAR, Selectedyear);
                calendar.set(Calendar.MONTH, Selectedmonth);
                calendar.set(Calendar.DAY_OF_MONTH, SelecteddayOfMonth);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDate = sdf.format(calendar.getTime());

                tv.setText(selectedDate);

            }
        }, year, month, dayOfMonth);
        return datePickerDialog;
    }

    private void getDataRealTime()
    {
        List<Sale> list = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("sale");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Sale sale = dataSnapshot.getValue(Sale.class);
                    saleList.add(sale);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_sale, container, false);

        rvSale = view.findViewById(R.id.rvSale);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvSale.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvSale.addItemDecoration(divider);

        saleList = new ArrayList<>();
        getDataRealTime();

        adapter = new SaleAdapter(getContext());
        adapter.setData(saleList);

        rvSale.setAdapter(adapter);


        tvDate = view.findViewById(R.id.tvDate);
        tvDate1 = view.findViewById(R.id.tvDate1);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today = sdf.format(calendar.getTime());

        tvDate.setText(today);
        tvDate1.setText(today);

        btnDate = view.findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = createDatePicker(tvDate);
                datePicker.show();
            }
        });

        btnDate1 = view.findViewById(R.id.btnDate1);
        btnDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = createDatePicker(tvDate1);
                datePicker.show();
            }
        });

        return view;
    }
}