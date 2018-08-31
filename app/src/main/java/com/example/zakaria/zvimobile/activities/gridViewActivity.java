package com.example.zakaria.zvimobile.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.zakaria.zvimobile.R;
import com.example.zakaria.zvimobile.beans.ImageAdapter;

public class gridViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(gridViewActivity.this, "Checking...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),listViewActivity2.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });

    }
}
