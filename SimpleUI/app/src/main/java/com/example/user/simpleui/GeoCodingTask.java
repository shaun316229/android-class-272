package com.example.user.simpleui;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2016/8/18.
 */
public class GeoCodingTask extends AsyncTask<String,Void, double[]> {
    WeakReference<GeocodingCallback> geocodingCallbackWeakReference;

    @Override
    protected double[] doInBackground(String... params) {
//        return new double[0];
        return Utils.getLatLngFromAddress(params[0]);
   }

    @Override
    protected void onPostExecute(double[] doubles) {
        super.onPostExecute(doubles);
        if(geocodingCallbackWeakReference.get()!=null)
        {
            GeocodingCallback geocodingCallback = geocodingCallbackWeakReference.get();
            geocodingCallback.done(doubles);
        }
    }
    public GeoCodingTask(GeocodingCallback geocodingCallback)
    {
        geocodingCallbackWeakReference = new WeakReference<GeocodingCallback>(geocodingCallback);//可能指向activity
    }

    interface GeocodingCallback
    {
        void done(double[] latlng);
    }
}
