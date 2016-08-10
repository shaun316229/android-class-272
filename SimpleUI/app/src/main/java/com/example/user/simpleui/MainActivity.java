package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    EditText editText;
    RadioGroup radioGroup;
    ListView listView;

    Spinner spinner;
    String drink="Black Tea";

    List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview=(TextView)findViewById(R.id.textview);
        editText=(EditText)findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        listView=(ListView)findViewById(R.id.listView);
        spinner=(Spinner)findViewById(R.id.spinner);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.blacktearadiobutton) {
                    drink = "Black Tea";
                } else if (checkedId == R.id.greentearadioButton2)
                {
                    drink = "Green Tea";
                }
            }
        });
        setupListView();
        setupSpinner();

    }

    private void setupListView()
    {
//        String[] data=new String[]{"1","2","3","4","5","6","7","8"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
    }

    private void setupSpinner()
    {
        String[] storeInfo = getResources().getStringArray(R.array.storeInfo);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,storeInfo);
        spinner.setAdapter(adapter);
    }

    public void click(View view)
    {
        String text=editText.getText().toString();
        text=text+" Order "+drink;
        textview.setText(text);
        data.add(text);
        setupListView();

        editText.setText("");
    }
}
