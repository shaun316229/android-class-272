package com.example.user.simpleui;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import android.os.Handler;

public class OrderDetalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detal);

        TextView noteTextView = (TextView)findViewById(R.id.noteTextView);
        TextView storeInfoTextView = (TextView)findViewById(R.id.storeInfoTextView);
        TextView drinkOrderResultsTextView = (TextView)findViewById(R.id.drinkOrderResultsTextView);
        final TextView latlngTextView = (TextView)findViewById(R.id.latLngTextView);

        Intent intent = getIntent();
        Order order = intent.getParcelableExtra("order");
        noteTextView.setText(order.getNote());
        storeInfoTextView.setText(order.getStoreInfo());
        String resultText = "";
        for (DrinkOrder drinkOrder: order.getDrinkOrderList())
        {
            String INumber = String.valueOf(drinkOrder.getINumber());
            String mNumber = String.valueOf(drinkOrder.getmNumber());
            String drinkName = drinkOrder.getDrink().getName();
            resultText+=drinkName + "  M:" + mNumber + "  L:" + INumber + "\n";
        }
        drinkOrderResultsTextView.setText(resultText);
        (new GeoCodingTask()).execute("");

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
    }

}
