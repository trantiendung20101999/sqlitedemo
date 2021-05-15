package com.example.sqlitedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.sqlitedemo.model.Order;

import java.util.Calendar;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edId,edName,edPrice,edQuantity;
    TextView txtDate;
    Button btnDel,btnDate,btnUpdate;

    SqliteOrderHelper sqliteOrderHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        init();

        Intent intent = getIntent();
        Order order = (Order) intent.getSerializableExtra("order");
        edId.setText(order.getId()+"");
        edName.setText(order.getItemName());
        edPrice.setText(order.getPrice()+"");
        edQuantity.setText(order.getQuantity()+"");
        txtDate.setText(order.getDateOrder());
    }

    public void init()
    {
        edName = findViewById( R.id.edItemName);
        edPrice = findViewById(R.id.edItemPrice);
        edQuantity = findViewById(R.id.edItemQuantity);
        edId = findViewById(R.id.edItemID);


        txtDate = findViewById( R.id.txtOrderDate);

        btnDate = findViewById(R.id.btnDate);
        btnDate.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(this);

        sqliteOrderHelper = new SqliteOrderHelper(this);

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
        if(v== btnDel)
        {
            int id = Integer.parseInt(edId.getText().toString());
            sqliteOrderHelper.delete(id);
            Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
            startActivity(intent);
        }
        if(v== btnUpdate)
        {
            int id = Integer.parseInt(edId.getText().toString());
            String name = edName.getText().toString();
            double price = Double.parseDouble(edPrice.getText().toString());
            int quantity = Integer.parseInt(edQuantity.getText().toString());
            String orderDate = txtDate.getText().toString();
            Order order = new Order(id,name,price,quantity,orderDate);
            sqliteOrderHelper.update(order);
            Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
            startActivity(intent);
        }

    }
}