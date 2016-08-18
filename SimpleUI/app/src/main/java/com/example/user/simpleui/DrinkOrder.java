package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by user on 2016/8/15.
 */
@ParseClassName("DrinkOrder")
public class DrinkOrder extends ParseObject implements Parcelable {
//    Drink drink;
//    int INumber = 0;
//    int mNumber = 0;
//    String ice = "REGULAR";
//    String suger = "REGULAR";
//    String note = "";
    static final String DRINK_COL = "drink";
    static final String LNUMBER_COL = "INumber";
    static final String MNUMBER_COL = "mNumber";
    static final String ICE_COL = "ice";
    static final String SUGAR_COL = "sugar";
    static final String NOTE_COL = "note";


    public DrinkOrder(Drink drink)
    {
        super();
        this.setDrink(drink);
    }

    public DrinkOrder()
    {
        super();
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
            dest.writeParcelable(this.getDrink(), flags);
            dest.writeInt(this.getINumber());
            dest.writeInt(this.getmNumber());
            dest.writeString(this.getIce());
            dest.writeString(this.getSuger());
            dest.writeString(this.getNote());
        }
        else
        {
            dest.writeInt(1);
            dest.writeString(getObjectId());
        }
    }

    protected DrinkOrder(Parcel in) {
        super();
        this.setDrink((Drink)in.readParcelable(Drink.class.getClassLoader()));
        this.setINumber(in.readInt());
        this.setmNumber(in.readInt());
        this.setIce(in.readString());
        this.setSuger(in.readString());
        this.setNote(in.readString());
    }

    public static final Creator<DrinkOrder> CREATOR = new Creator<DrinkOrder>() {
        @Override
        public DrinkOrder createFromParcel(Parcel source) {
//            return new DrinkOrder(source);
            int isFromRemote = source.readInt();
            if(isFromRemote == 0)
            {
                return new DrinkOrder(source);
            }
            else
            {
                String objectId = source.readString();
                return getDrinkOrderFromCache(objectId);
            }
        }

        @Override
        public DrinkOrder[] newArray(int size) {
            return new DrinkOrder[size];
        }
    };

    public Drink getDrink() {
        return (Drink)getParseObject(DRINK_COL);
    }

    public void setDrink(Drink drink) {
        this.put(DRINK_COL,drink);
    }

    public int getINumber() {
        return getInt(LNUMBER_COL);
    }

    public void setINumber(int INumber) {
        this.put(LNUMBER_COL,INumber);
    }

    public int getmNumber() {
        return getInt(MNUMBER_COL);
    }

    public void setmNumber(int mNumber) {
        this.put(MNUMBER_COL,mNumber);
    }

    public String getIce() {
        return getString(ICE_COL);
    }

    public void setIce(String ice) {
        this.put(ICE_COL,ice);
    }

    public String getSuger() {
        return getString(SUGAR_COL);
    }

    public void setSuger(String suger) {
        this.put(SUGAR_COL,suger);
    }

    public String getNote() {
        return getString(NOTE_COL);
    }

    public void setNote(String note) {
        this.put(NOTE_COL,note);
    }

    public static ParseQuery<DrinkOrder> getQuery()
    {
        return ParseQuery.getQuery(DrinkOrder.class);
    }

    public  static DrinkOrder getDrinkOrderFromCache(String objectId)
    {
        try {
            return getQuery().fromLocalDatastore().get(objectId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return DrinkOrder.createWithoutData(DrinkOrder.class,objectId);
    }
}
