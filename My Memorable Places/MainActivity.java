package com.example.mymemorableplaces2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<String>();
    static ArrayList<LatLng> locations = new ArrayList<LatLng>();
    ArrayList<String> latitudes;
    ArrayList<String> longtiudes;
    static ArrayAdapter arrayAdapter;
    SharedPreferences sharedPreferences;
    Button btnClr;

    //Clear all the arrays
    public void initArrays() {
        places.clear();
        latitudes.clear();
        longtiudes.clear();
        locations.clear();
    }

    //Clear the sharedPrefences and the listView
    public void clear(View view) {
        sharedPreferences.edit().clear().apply();
        initArrays();
        arrayAdapter.notifyDataSetChanged();
        places.add("Add a new place...");

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnClr = (Button) findViewById(R.id.btnclear);

        //Restore the locations from sharedPreferences
        sharedPreferences = this.getSharedPreferences("com.example.mymemorableplaces2", MODE_PRIVATE);

        latitudes = new ArrayList<String>();
        longtiudes = new ArrayList<String>();

        //Gets the places that were saved in the SharedPrefences
        try {
            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));
            latitudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lats", ObjectSerializer.serialize(new ArrayList<String>())));
            longtiudes = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("lons", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (Exception e) {
            e.printStackTrace();
        }

        //Copy the location that saved to the listView
        if (places.size() > 0 && latitudes.size() > 0 && longtiudes.size() > 0) {
            if (places.size() == latitudes.size() && places.size() == longtiudes.size()) {
                for (int i = 0; i < latitudes.size(); i++) {
                    locations.add(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longtiudes.get(i))));
                }
            }
            // If there is nothing to copy it starts fresh listView
        } else {
            places.add("Add a new place...");
            locations.add(new LatLng(0, 0));
        }

        ListView listView = findViewById(R.id.listView);


        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, places);
        listView.setAdapter(arrayAdapter);

        //Click on item from the listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                intent.putExtra("placeNumber", position);
                startActivity(intent);
            }
        });
    }
}
