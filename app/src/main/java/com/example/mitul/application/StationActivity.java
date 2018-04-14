package com.example.mitul.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.example.mitul.application.Adapter.StationAdapter;

import java.util.ArrayList;
import java.util.List;

public class StationActivity extends AppCompatActivity {

    DataHelper dataHelper;
    List<StationInfo> stationData = new ArrayList<>();
    RecyclerView recyclerView;
    ListView listView;
    String[] station_name = {"Bus Station","Railway Station","Bhestan","Udhna darwaja", "V.R.Mall", "Piplod", "Adajan", "Katargam", "Varacha", "Athwa Gate", "Majura Gate", "Navsari Bazar", "Chowk Bazar", "Parle Point", "Ghoddoad Road", "Sachin", "Udhna"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataHelper = new DataHelper(this);

        recyclerView = findViewById(R.id.recyclerViewStation);
        StationAdapter adapter = new StationAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        stationData.addAll(dataHelper.getAllStation());

        adapter.changeData(stationData);

/*
        stationData = dataHelper.getAllStation();
        int i=0;
        for(StationInfo stationInfo: stationData){
            station[i] = stationInfo.getStationName();
            i++;
            Log.d(station[i],station[i]);
        }
*/
//        listView = findViewById(R.id.listViewStation);
//        CustomListView customListView = new CustomListView(this,station_name);
//        listView.setAdapter(customListView);
    }
}
