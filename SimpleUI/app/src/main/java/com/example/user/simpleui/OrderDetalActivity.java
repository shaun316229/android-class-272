package com.example.user.simpleui;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Handler;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrderDetalActivity extends AppCompatActivity implements GeoCodingTask.GeocodingCallback {

    TextView latlngTextView;
    GoogleMap map;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detal);

        TextView noteTextView = (TextView) findViewById(R.id.noteTextView);
        TextView storeInfoTextView = (TextView) findViewById(R.id.storeInfoTextView);
        TextView drinkOrderResultsTextView = (TextView) findViewById(R.id.drinkOrderResultsTextView);
        latlngTextView = (TextView) findViewById(R.id.latLngTextView);//指向外面的變數

        Intent intent = getIntent();
        Order order = intent.getParcelableExtra("order");
        noteTextView.setText(order.getNote());
        storeInfoTextView.setText(order.getStoreInfo());
        String address = order.getStoreInfo().split(",")[1];
        String resultText = "";
        for (DrinkOrder drinkOrder : order.getDrinkOrderList()) {
            String INumber = String.valueOf(drinkOrder.getINumber());
            String mNumber = String.valueOf(drinkOrder.getmNumber());
            String drinkName = drinkOrder.getDrink().getName();
            resultText += drinkName + "  M:" + mNumber + "  L:" + INumber + "\n";
        }
        drinkOrderResultsTextView.setText(resultText);

        MapFragment fragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                (new GeoCodingTask(OrderDetalActivity.this)).execute("台北市大安區羅斯福路四段一號");
            }
        });


//        final Handler handler = new Handler(new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                latlngTextView.setText("123,456");
//                return false;
//            }
//        });
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                        try{
//                            Thread.sleep(1000);
////                            latlngTextView.setText("123,456");
//                            handler.sendMessage(new Message());
//                        }catch(InterruptedException e) {
//                            e.printStackTrace();
//                        }
//            }
//        });
//
//        thread.start();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void done(double[] latlng) {
        if (latlng != null) {
            String latlngString = String.valueOf(latlng[0] + "," + latlng[1]);
            latlngTextView.setText(latlngString);

            LatLng latLng = new LatLng(latlng[0], latlng[1]);
            CameraUpdate  cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
            MarkerOptions makerOptions = new MarkerOptions().position(latLng).title("NTU");
            map.moveCamera(cameraUpdate);
            map.addMarker(makerOptions);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "OrderDetal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.user.simpleui/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "OrderDetal Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.user.simpleui/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
