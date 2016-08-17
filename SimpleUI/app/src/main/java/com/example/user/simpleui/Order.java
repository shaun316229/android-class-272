package com.example.user.simpleui;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
@ParseClassName("Order")
public class Order extends ParseObject{
    final static  String Note_COL = "note";
    final static  String STOREINFO_COL = "storeInfo";
    final  static  String DRINKORDERS_COL = "drinkOrderList";

//    String note;
//    String storeInfo;
//    List<DrinkOrder> drinkOrderList;

    public int getTotal()
    {
        int total = 0;
        for(DrinkOrder drinkOrder : getDrinkOrderList())
        {
            total += drinkOrder.getINumber()*drinkOrder.getDrink().getIPrice() + drinkOrder.getmNumber()*drinkOrder.getDrink().getmPrice();
        }
        return total;
    }

    public String getNote() {
        return getString(Note_COL);
    }

    public void setNote(String note) {
        this.put(Note_COL,note);
    }

    public String getStoreInfo() {
        return getString(STOREINFO_COL);
    }

    public void setStoreInfo(String storeInfo) {
        this.put(STOREINFO_COL,storeInfo);
    }

    public List<DrinkOrder> getDrinkOrderList() {
        return getList(DRINKORDERS_COL);
    }

    public void setDrinkOrderList(List<DrinkOrder> drinkOrderList) {
        this.put(DRINKORDERS_COL,drinkOrderList);
    }

    public static ParseQuery<Order> getQuery()
    {
        return  ParseQuery.getQuery(Order.class)
                .include(DRINKORDERS_COL)
                .include(DRINKORDERS_COL+'.'+DrinkOrder.DRINK_COL);
    }
}
