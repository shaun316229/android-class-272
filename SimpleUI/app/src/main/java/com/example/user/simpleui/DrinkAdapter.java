package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by user on 2016/8/11.
 */
public class DrinkAdapter extends BaseAdapter {

    List<Drink> drinkList;
    LayoutInflater inflater;

    public DrinkAdapter(Context context,List<Drink>drinks){
        this.drinkList = drinks;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return drinkList.size();
    }

    @Override
    public Object getItem(int position) {
        return drinkList.get(position);
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
            convertView = inflater.inflate(R.layout.listview_drink_item,null);
            holder = new Holder();
            holder.drinkNametextView = (TextView)convertView.findViewById(R.id.drinkNametextView);
            holder.mPricetextView = (TextView)convertView.findViewById(R.id.mPricetextView);
            holder.lPricetextView = (TextView)convertView.findViewById(R.id.lPricetextView);
            holder.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder)convertView.getTag();//拿到order
        }

        Drink drink = drinkList.get(position);//拿出資料
        holder.drinkNametextView.setText(drink.getName());//
        holder.mPricetextView.setText(String.valueOf(drink.getmPrice()));
        holder.lPricetextView.setText(String.valueOf(drink.getIPrice()));
        String imageURL = drink.getImage().getUrl();
        Picasso.with(inflater.getContext()).load(imageURL).into(holder.imageView);//載下圖片

        return convertView;
    }

    class Holder{
        TextView drinkNametextView;
        TextView mPricetextView;
        TextView lPricetextView;
        ImageView imageView;
    }
}
