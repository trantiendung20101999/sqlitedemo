package com.example.sqlitedemo.model;

import java.io.Serializable;

public class Order implements Serializable {

    public int id;
    public String itemName;
    public double price;
    public int quantity;
    public String dateOrder;

    public Order() {
    }

    public Order(String itemName, double price, int quantity, String dateOrder) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.dateOrder = dateOrder;
    }

    public Order(int id, String itemName, double price, int quantity, String dateOrder) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.dateOrder = dateOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }
}
