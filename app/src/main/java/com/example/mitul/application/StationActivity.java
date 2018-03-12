package com.example.mitul.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class StationActivity extends AppCompatActivity {

    ListView listView;
    String[] station_name = {"Bus Station","Railway Station","Bhestan","Udhna darwaja", "V.R.Mall", "Piplod", "Adajan", "Katargam", "Varacha", "Athwa Gate", "Majura Gate", "Navsari Bazar", "Chowk Bazar", "Parle Point", "Ghoddoad Road", "Sachin", "Udhna"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        listView = findViewById(R.id.listViewStation);
        CustomListView customListView = new CustomListView(this,station_name);
        listView.setAdapter(customListView);
    }
}
