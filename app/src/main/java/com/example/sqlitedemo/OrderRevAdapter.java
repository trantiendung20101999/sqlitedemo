package com.example.sqlitedemo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitedemo.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRevAdapter extends RecyclerView.Adapter<OrderRevAdapter.OrderViewHolder> {

    List<Order> listOrders;

    public OrderRevAdapter() {
    }

    public OrderRevAdapter(List<Order> listOrders) {
        this.listOrders = listOrders;
    }

    public List<Order> getListOrders() {
        return listOrders;
    }

    public void setListOrders(List<Order> listOrders) {
        this.listOrders = listOrders;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rev_main,parent,false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {

        Order order = listOrders.get(position);
        if(order!=null)
        {
            holder.txtItemNameID.setText(order.getId()+"-"+order.getItemName());
            holder.txtItemOrderDate.setText(order.getDateOrder());
            holder.txtItemPrice.setText(order.getPrice()+"");
            holder.txtItemQuantity.setText(order.getQuantity()+"");
            holder.itemClickListener = new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {

                    Intent intent = new Intent(view.getContext(),UpdateActivity.class);
                    intent.putExtra("order",order);
                    view.getContext().startActivity(intent);
                }
            };
        }

    }

    @Override
    public int getItemCount() {
        if (listOrders!=null)
        {
            return listOrders.size();
        }
        return 0;
    }

    public interface  ItemClickListener
    {
        public void onClick(View view , int position);
    }
    class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView txtItemNameID,txtItemQuantity,txtItemPrice,txtItemOrderDate;

        ItemClickListener itemClickListener;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItemNameID = itemView.findViewById(R.id.txtNameId);
            txtItemQuantity = itemView.findViewById(R.id.txtQuantity);
            txtItemPrice = itemView.findViewById(R.id.txtPrice);
            txtItemOrderDate = itemView.findViewById(R.id.txtOrderDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(v,getAdapterPosition());
                }
            });
        }
    }
}
