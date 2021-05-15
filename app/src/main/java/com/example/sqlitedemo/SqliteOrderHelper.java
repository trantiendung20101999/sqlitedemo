package com.example.sqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sqlitedemo.model.Order;

import java.util.ArrayList;
import java.util.List;

public class SqliteOrderHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME ="OrderDB.db";
    static final int DATABASE_VERSION = 1;

    public SqliteOrderHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createSQL=" CREATE TABLE orders(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT ," +
                "itemname TEXT," +
                "price REAL," +
                "quantity INTEGER," +
                "orderDate TEXT)";
        db.execSQL(createSQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addOrder(Order order)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("itemname",order.getItemName());
        contentValues.put("price",order.getPrice());
        contentValues.put("quantity",order.getQuantity());
        contentValues.put("orderDate",order.getDateOrder());
        SQLiteDatabase statement = getWritableDatabase();

        return statement.insert("orders",null,contentValues);
    }

    public List<Order> getAll()
    {
        List<Order> listOrders = new ArrayList<>();

        SQLiteDatabase statement = getReadableDatabase();

        Cursor cursor = statement.query("orders",null,null,null,null,null,null);
        while (cursor!=null && cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            String itemname = cursor.getString(1);
            Double price = cursor.getDouble(2);
            int quantity = cursor.getInt(3);
            String orderDate = cursor.getString(4);
            Order order = new Order(id,itemname,price,quantity,orderDate);
            listOrders.add(order);
        }

        return listOrders;
    }

    public List<Order> searchByName(String key)
    {
    List<Order> listOrders = new ArrayList<>();

    String whereClause = "itemname LIKE ?";
    String[] whereArgs= {"%"+key+"%"};

    SQLiteDatabase statement = getReadableDatabase();

    Cursor cursor = statement.query("orders",null,whereClause,whereArgs,null,null,null);
    while (cursor!=null && cursor.moveToNext())
    {
        int id = cursor.getInt(0);
        String itemname = cursor.getString(1);
        Double price = cursor.getDouble(2);
        int quantity = cursor.getInt(3);
        String orderDate = cursor.getString(4);
        Order order = new Order(id,itemname,price,quantity,orderDate);
        listOrders.add(order);
    }

    return listOrders;
    }

    public int update(Order order){
        ContentValues contentValues = new ContentValues();
        contentValues.put("id",order.getId());
        contentValues.put("itemname",order.getItemName());
        contentValues.put("price",order.getPrice());
        contentValues.put("quantity",order.getQuantity());
        contentValues.put("orderDate",order.getDateOrder());
        SQLiteDatabase statement = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs={Integer.toString(order.getId())};
        return  statement.update("orders",contentValues,whereClause,whereArgs);
    }
    public int delete(int id)
    {
        SQLiteDatabase statement = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        return statement.delete("orders",whereClause,whereArgs);
    }

}
