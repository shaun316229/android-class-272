package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by user on 2016/8/10.
 */
@ParseClassName("Order")
public class Order extends ParseObject implements Parcelable{
    final static  String Note_COL = "note";
    final static  String STOREINFO_COL = "storeInfo";
    final  static  String DRINKORDERS_COL = "drinkOrderList";

    public Order()
    {
        super();
    }

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
                .include(DRINKORDERS_COL + '.' + DrinkOrder.DRINK_COL);
    }

    public static void getOrdersFromLocalThenRemote(final FindCallback<Order> callback)
    {
        getQuery().fromLocalDatastore().findInBackground(callback);
        getQuery().findInBackground(new FindCallback<Order>() {
            @Override
            public void done(List<Order> list, ParseException e) {
                if (e == null)
                    pinAllInBackground("Order", list);
                callback.done(list, e);
            }
        });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if(getObjectId() == null)
        {
            dest.writeInt(0);
            dest.writeString(getNote());
            dest.writeString(getStoreInfo());
            dest.writeParcelableArray((Parcelable[]) getDrinkOrderList().toArray(), flags);
        }
        else
        {
            dest.writeInt(1);
            dest.writeString(getObjectId());
        }
    }

    protected Order(Parcel in) {
        super();
        this.setNote(in.readString());
        this.setStoreInfo(in.readString());
        this.setDrinkOrderList(Arrays.asList((DrinkOrder[]) in.readArray(DrinkOrder.class.getClassLoader())));
    }

    public static final Parcelable.Creator<Order> CREATOR = new Parcelable.Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            int isFromRemote = source.readInt();
            if(isFromRemote == 0)
            {
                return new Order(source);
            }
            else
            {
                String objectId = source.readString();
                return getOrderFromCache(objectId);
            }
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public static Order getOrderFromCache(String objectId)
        {
            try {
                return getQuery().fromLocalDatastore().get(objectId);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return Order.createWithoutData(Order.class,objectId);
            }
}
