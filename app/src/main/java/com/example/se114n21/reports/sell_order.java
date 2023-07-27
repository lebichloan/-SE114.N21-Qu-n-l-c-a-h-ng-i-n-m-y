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

import com.example.se114n21.R;

import com.example.se114n21.adapters.OrderAdapter;
import com.example.se114n21.models.Order;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class sell_order extends Fragment {

    TextView tvDate, tvDate1, tvTotal;
    Button btnDate, btnDate1;
    RecyclerView rvOrder;
    List<Order> orderList;
    OrderAdapter adapter;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell_order, container, false);

        initUI(view);
        initEvent();

        return view;
    }

    private void initUI(View view)
    {
        rvOrder = view.findViewById(R.id.rvOrder);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String today = sdf.format(calendar.getTime());

        tvDate = view.findViewById(R.id.tvDate);
        tvDate1 = view.findViewById(R.id.tvDate1);
        tvDate.setText(today);
        tvDate1.setText(today);

        tvTotal = view.findViewById(R.id.tvTotal);

        btnDate = view.findViewById(R.id.btnDate);
        btnDate1 = view.findViewById(R.id.btnDate1);
    }

    private void initEvent()
    {
        //set up data for recycle view
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rvOrder.setLayoutManager(manager);

        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvOrder.addItemDecoration(divider);

        orderList = new ArrayList<>();
        getDataRealTime((String) tvDate.getText(), (String) tvDate1.getText());

        adapter = new OrderAdapter(getContext());
        adapter.setData(orderList);

        rvOrder.setAdapter(adapter);

        //set datePicker for button
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = createDatePicker(tvDate);
                datePicker.show();
            }
        });

        btnDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = createDatePicker(tvDate1);
                datePicker.show();
            }
        });
    }

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
                getDataRealTime((String) tvDate.getText(), (String) tvDate1.getText());
            }
        }, year, month, dayOfMonth);
        return datePickerDialog;
    }

    private void getDataRealTime(String dateStart, String dateEnd)
    {
        List<Order> list = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("order");

        Query query = ref.orderByChild("date");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (orderList != null)
                    orderList.clear();

                int total = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Order order = dataSnapshot.getValue(Order.class);
                    if (order.getDate().compareTo(dateStart) >= 0 && order.getDate().compareTo(dateEnd) <= 0)
                    {
                        orderList.add(order);
                        total += order.getTotal();
                    }
                }
                String t = "Tổng giá trị đơn hàng: " + total;
                tvTotal.setText(t);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}

