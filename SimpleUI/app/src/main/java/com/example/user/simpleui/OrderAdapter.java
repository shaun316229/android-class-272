package com.example.user.simpleui;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
public class OrderAdapter extends BaseAdapter {

    List<Order> orders;
    LayoutInflater layoutInflater;

    public OrderAdapter(Context context,List<Order>orderList){
        this.orders = orderList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {//總共要顯示幾筆資料
        return orders.size();
    }

    @Override
    public Object getItem(int position) {//把第幾筆資料(position)丟給我
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {//資料要和網路作連結 需要拿到id
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {//去拿到每一筆position的樣子

        Holder holder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_order_item,null);

            TextView noteTextView = (TextView)convertView.findViewById(R.id.noteTextView);
            TextView storeInfoTextView = (TextView)convertView.findViewById(R.id.storeTextView);
            TextView drinkTextView = (TextView)convertView.findViewById(R.id.drinkTextView);

            holder = new Holder();

            holder.noteTextView = noteTextView;
            holder.storeInfoTextView = storeInfoTextView;
            holder.drinkTextView = drinkTextView;

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();
        }

        Order order = orders.get(position);//拿出資料
        holder.noteTextView.setText(order.note);
        holder.storeInfoTextView.setText(order.storeInfo);
        holder.drinkTextView.setText(order.drink);

        return convertView;
    }

    class Holder{
        TextView noteTextView;
        TextView storeInfoTextView;
        TextView drinkTextView;
    }
}
