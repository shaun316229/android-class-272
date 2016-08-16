package com.example.user.simpleui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static final  int REQUEST_CODE_DRINK_MENU_ACTIVITY = 0;
    TextView textview;
    EditText editText;
    RadioGroup radioGroup;
    ListView listView;

    Spinner spinner;
    String drink="Black Tea";

    ArrayList<DrinkOrder> drinkOrderList = new ArrayList<>();
    List<Order> orderList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview=(TextView)findViewById(R.id.textview);
        editText=(EditText)findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        listView=(ListView)findViewById(R.id.listView);
        spinner=(Spinner)findViewById(R.id.spinner);

        sharedPreferences = getSharedPreferences("UIState", MODE_PRIVATE);
        editor = sharedPreferences.edit();


        editText.setText(sharedPreferences.getString("editText", ""));
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString("editText", editText.getText().toString());
                editor.apply();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blacktearadiobutton) {
                    drink = "Black Tea";
                } else if (checkedId == R.id.greentearadioButton2) {
                    drink = "Green Tea";
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Order order = (Order) parent.getAdapter().getItem(position);
                Toast.makeText(MainActivity.this, order.note, Toast.LENGTH_LONG).show();//這裡的this是指向MainActivity
            }
        });//item事件處發

        setupOrderHistory();
        setupListView();
        setupSpinner();
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(MainActivity.this, "Sucess", Toast.LENGTH_LONG).show();
                }
            }
        });//上傳到所連結的server

//        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("TestObject");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e == null)
//                {
//                    Toast.makeText(MainActivity.this,objects.get(0).getString("foo"),Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        Log.d("DEBUG","MainActivity OnCreate");
    }

    private void setupOrderHistory()
    {
        String orderDatas = Utils.readFile(this,"history");
        String[] orderDataArray = orderDatas.split("\n");
        Gson gson = new Gson();
        for(String orderData : orderDataArray)
        {
            try
            {
                Order order = gson.fromJson(orderData,Order.class);
                if(order != null)
                {
                    orderList.add(order);
                }
            }
            catch (JsonSyntaxException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void setupListView()
    {
//        String[] orderList=new String[]{"1","2","3","4","5","6","7","8"};

//      List<Map<String,String>> mapList = new ArrayList<>();
//
//        for (Order order : orderList)
//        {
//            Map<String,String> item = new HashMap<>();
//
//            item.put("note",order.note);
//            item.put("storeInfo",order.storeInfo);
//            item.put("drink",order.drink);
//
//            mapList.add(item);
//        }
//
//        String[] from = {"note","storeInfo","drink"};
//        int[] to = {R.id.noteTextView,R.id.storeTextView,R.id.drinkTextView};
//
//        SimpleAdapter adapter = new SimpleAdapter(this,mapList,R.layout.listview_order_item,from,to);

        OrderAdapter adapter = new OrderAdapter(this, orderList);
        listView.setAdapter(adapter);
    }

    private void setupSpinner()
    {
        String[] storeInfo = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,storeInfo);
        spinner.setAdapter(adapter);
        spinner.setSelection(sharedPreferences.getInt("spinner", 0));//要先設定才拿得到東西
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                editor.putInt("spinner", spinner.getSelectedItemPosition());
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void click(View view)
    {
        String text=editText.getText().toString();
        String result = text+" Order "+drink;
        textview.setText(text);

        editText.setText("");

        Order order = new Order();

        order.note = text;
        order.drinkOrderList = drinkOrderList;
        order.storeInfo = (String)spinner.getSelectedItem();

        orderList.add(order);

        Gson gson = new Gson();
        String orderData = gson.toJson(order);
        Utils.writeFile(this,"history",orderData+'\n');

        drinkOrderList = new ArrayList<>();
        setupListView();


    }

    public void goToMenu(View view)
    {
        Intent intent = new Intent();//兩個Activity要互相溝通
        intent.putExtra("result",drinkOrderList);
        intent.setClass(this,DrinkMenuActivity.class);//設定哪個Activity要到哪個Activity
        startActivityForResult(intent,REQUEST_CODE_DRINK_MENU_ACTIVITY);//drinkmenuActivity會被疊在MainActivity上
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_DRINK_MENU_ACTIVITY)
        {
            if(resultCode == RESULT_OK)
            {
                drinkOrderList = data.getParcelableArrayListExtra("result");
//                Toast.makeText(this,result,Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,"Cancel Menu",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("DEBUG", "MainActivity OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("DEBUG", "MainActivity OnResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("DEBUG", "MainActivity OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("DEBUG", "MainActivity OnStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "MainActivity OnDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("DEBUG", "MainActivity OnRestart");
    }
}
