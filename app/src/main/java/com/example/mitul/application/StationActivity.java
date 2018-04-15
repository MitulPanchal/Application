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
    }
}
