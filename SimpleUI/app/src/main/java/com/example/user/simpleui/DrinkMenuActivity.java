package com.example.user.simpleui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DrinkMenuActivity extends AppCompatActivity {

    ListView drinkMenuListView;
    TextView totalTextView;

    String[] names = {"冬瓜紅茶","玫瑰鹽奶蓋紅茶","珍珠紅茶拿鐵","紅茶拿鐵"};
    int[] IPrices = {35,45,55,45};
    int[] mPrices = {25,35,45,35};
    int[] imageIds = {R.drawable.drink1,R.drawable.drink2,R.drawable.drink3,R.drawable.drink4};
    int total =0;

    List<Drink> drinkList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);

        setdata();

        drinkMenuListView = (ListView)findViewById(R.id.drinkmenulistView);
        totalTextView = (TextView)findViewById(R.id.totaltextview);

        drinkMenuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Drink drink = (Drink)parent.getAdapter().getItem(position);
                total+=drink.mPrice;
                totalTextView.setText(String.valueOf(total));
            }
        });
        
        setupDrinkMenu();

        Log.d("DEBUG","DrinkMenuActivity");
    }

    private void setdata()
    {
        for(int i = 0;i <names.length; i++)
        {
            Drink drink = new Drink();
            drink.name = names[i];
            drink.IPrice = IPrices[i];
            drink.mPrice = mPrices[i];
            drink.imageId = imageIds[i];
            drinkList.add(drink);
        }
    }

    private void setupDrinkMenu()
    {
        DrinkAdapter adapter = new DrinkAdapter(this,drinkList);
        drinkMenuListView.setAdapter(adapter);
    }

    public  void done(View view)
    {
        Intent intent = new Intent();
        intent.putExtra("result",String.valueOf(total));

        setResult(RESULT_OK,intent);
        finish();

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG", "DrinkMenuActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG", "DrinkMenuActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "DrinkMenuActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "DrinkMenuActivity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "DrinkMenuActivity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DEBUG", "DrinkMenuActivity OnRestart");
    }
}
