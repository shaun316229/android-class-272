package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    EditText editText;
    RadioGroup radioGroup;
    ListView listView;
    String drink="Black Tea";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview=(TextView)findViewById(R.id.textview);
        editText=(EditText)findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        listView=(ListView)findViewById(R.id.listView);

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

    }

    private void setupListView()
    {
        String[] data=new String[]{"1","2","3","4","5","6","7","8"};

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);
        listView.setAdapter(adapter);
    }

    public void click(View view)
    {
        String text=editText.getText().toString();
        text=text+" Order "+drink;
        textview.setText(text);

        editText.setText("");
    }
}
