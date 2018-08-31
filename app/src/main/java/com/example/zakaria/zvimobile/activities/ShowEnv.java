package com.example.zakaria.zvimobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zakaria.zvimobile.R;

public class ShowEnv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_env);

        //return all host about properties
        Bundle extras = getIntent().getExtras();

       // Intent intent = getIntent();
        String value = "failed" ;
        value = extras.getString("jsonInfo");

        Log.i("Let's see : ",value);


        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText(value);
    }
}
