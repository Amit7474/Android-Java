package com.example.myapplication;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view){
        TextView output = (TextView) findViewById(R.id.textView);
        TextView input = (TextView) findViewById(R.id.input);
        double res = 0;
        int number = Integer.parseInt(input.getText().toString());
        RadioGroup group = (RadioGroup) findViewById(R.id.group);
        int option = group.getCheckedRadioButtonId();
        switch (option){
            case R.id.dollar:
                res = dollar(number);
                break;
            case R.id.euro:
                res = euro(number);
                break;
            case R.id.nzdollar:
                res = nzDollar(number);
                break;
            default:
                res = 0;
                break;
        }
        output.setText(Double.toString(res));

    }

    public double dollar(int num){
        double res = num*3.5;
        return res;
    }
    public double euro(int num){
        double res = num*4;
        return res;
    }
    public double nzDollar(int num){
        double res = num*2.5;
        return res;
    }
}
