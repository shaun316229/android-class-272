package com.example.user.simpleui;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by user on 2016/8/11.
 */
@ParseClassName("Drink")
public class Drink extends ParseObject implements Parcelable {
//    String name;
//    int mPrice;
//    int IPrice;
    static final String Name_COL = "name";
    static final String MPRICE_COL = "mPrice";
    static final  String LPRICE_COL ="IPrice";
    static final  String IMAGE_COL = "image";
//    int imageId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {//物件要怎麼打包
        if(getObjectId() == null)
        {
            dest.writeInt(0);
            dest.writeString(this.getName());
            dest.writeInt(this.getmPrice());
            dest.writeInt(this.getIPrice());
//            dest.writeInt(this.imageId);
        }
        else
        {
            dest.writeInt(1);
            dest.writeString(getObjectId());
        }
    }

    public Drink() {
        super();
    }

    protected Drink(Parcel in) {//包裹讀出來 要塞回物件
        super();
        this.setName(in.readString());
        this.setmPrice(in.readInt());
        this.setIPrice(in.readInt());
//        this.imageId = in.readInt();
    }

    public static final Parcelable.Creator<Drink> CREATOR = new Parcelable.Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel source) {
            int isFromRemote = source.readInt();
            if(isFromRemote == 0)
            {
                return new Drink(source);
            }
            else
            {
                String objectId = source.readString();
                return getDrinkFromCache(objectId);
            }
//            return new Drink(source);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    public String getName() {
        return getString(Name_COL);
    }

    public void setName(String name) {
        this.put(Name_COL,name);
    }

    public int getmPrice() {
        return getInt(MPRICE_COL);
    }

    public void setmPrice(int mPrice) {
        this.put(MPRICE_COL,mPrice);
    }

    public int getIPrice() {
        return getInt(LPRICE_COL);
    }

    public void setIPrice(int IPrice) {
        this.put(LPRICE_COL,IPrice);
    }

    public ParseFile getImage()
    {
        return getParseFile(IMAGE_COL);
    }

    public static ParseQuery<Drink> getQuery()
    {
        return  ParseQuery.getQuery(Drink.class);
    }
    public static Drink getDrinkFromCache(String objectId)
    {
        try {
            Drink drink = getQuery().setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK).get(objectId);
            return drink;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Drink.createWithoutData(Drink.class,objectId);//最重要objectId
    }
}
