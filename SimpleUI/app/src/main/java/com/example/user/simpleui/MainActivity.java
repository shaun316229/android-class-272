package com.example.user.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textview;
    EditText editText;
    RadioGroup radioGroup;
    String drink="Black Tea";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textview=(TextView)findViewById(R.id.textview);
        editText=(EditText)findViewById(R.id.editText);
        radioGroup=(RadioGroup)findViewById(R.id.radiogroup);

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

    }

    public void click(View view)
    {
        String text=editText.getText().toString();
        text=text+"Order"+drink;
        textview.setText(text);
    }
}
