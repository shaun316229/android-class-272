package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 2016/8/11.
 */
public class DrinkAdapter extends BaseAdapter {

    List<Drink> drinks;
    LayoutInflater layoutInflater;

    public DrinkAdapter(Context context,List<Drink>drinkList){
        this.drinks = drinkList;
        this.layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return drinks.size();
    }

    @Override
    public Object getItem(int position) {
        return drinks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.listview_order_item,null);

            TextView drinkNametextView = (TextView)convertView.findViewById(R.id.drinkNametextView);
            TextView mPricetextView = (TextView)convertView.findViewById(R.id.mPricetextView);
            TextView lPricetextView = (TextView)convertView.findViewById(R.id.lPricetextView);
            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);

            holder = new Holder();

            holder. drinkNametextView =  drinkNametextView;
            holder.mPricetextView= mPricetextView;
            holder.lPricetextView = lPricetextView;
            holder.imageView = imageView;

            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();//拿到order
        }

        Drink drink = drinks.get(position);//拿出資料
        holder.drinkNametextView.setText(drink.name);//
        holder.mPricetextView.setText(String.valueOf(drink.mPrice));
        holder.lPricetextView.setText(String.valueOf(drink.IPrice));
        holder.imageView.setImageResource(drink.imageId);

        return convertView;
    }

    class Holder{
        TextView drinkNametextView;
        TextView mPricetextView;
        TextView lPricetextView;
        ImageView imageView;
    }
}
