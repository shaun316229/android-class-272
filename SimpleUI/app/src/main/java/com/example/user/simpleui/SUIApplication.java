package com.example.user.simpleui;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by user on 2016/8/16.
 */
public class SUIApplication extends Application {
    public void onCreate()
    {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                        .applicationId("d3EhgjkTXd6moT0iLrjGD9Zgp9Wdk0SO6pwO3T22")
                        .server("https://parseapi.back4app.com/")
                        .clientKey("fjq21kEOPiLBV7TuiA8Ea456FYvfhI53wMsmI0s7")
                        .build()
        );

    }
}
