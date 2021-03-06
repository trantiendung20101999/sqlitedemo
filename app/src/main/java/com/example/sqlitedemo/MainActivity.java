package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.sqlitedemo.model.Order;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edName,edPrice,edQuantity;
    TextView txtDate;
    Button btnAdd,btnDate;

    RecyclerView rev;
    OrderRevAdapter adapter;

    SqliteOrderHelper sqliteOrderHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init()
    {
        edName = findViewById( R.id.edItemName);
        edPrice = findViewById(R.id.edItemPrice);
        edQuantity = findViewById(R.id.edItemQuantity);

        txtDate = findViewById( R.id.txtOrderDate);

        btnAdd = findViewById(R.id.btnAdd);
        btnDate = findViewById(R.id.btnDate);
        btnAdd.setOnClickListener(this);
        btnDate.setOnClickListener(this);

        sqliteOrderHelper = new SqliteOrderHelper(this);

        rev = findViewById(R.id.rev);
        List<Order> listOrders = sqliteOrderHelper.getAll();
        adapter = new OrderRevAdapter(listOrders);
        rev.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rev.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        MenuItem item = menu.findItem(R.id.mSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<Order> listOrders = sqliteOrderHelper.searchByName(newText);
                adapter.setListOrders(listOrders);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDate)
        {
            Calendar calendar = Calendar.getInstance();
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            int mMonth = calendar.get(Calendar.MONTH);
            int mYear = calendar.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    txtDate.setText(dayOfMonth+"/"+(month-1)+"/"+year);
                }
            },mYear,mMonth,mDay);
            datePickerDialog.show();
        }
        if(v == btnAdd)
        {
            String name = edName.getText().toString();
            Double price = Double.parseDouble(edPrice.getText().toString());
            int quantity = Integer . parseInt(edQuantity.getText().toString());
            String orderDate = txtDate.getText().toString();
            Order  order = new Order(name,price,quantity,orderDate);
            sqliteOrderHelper.addOrder(order);
            List<Order> listOrders = sqliteOrderHelper.getAll();
            adapter.setListOrders(listOrders);
        }
    }
}