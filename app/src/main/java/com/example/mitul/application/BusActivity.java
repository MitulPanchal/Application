package com.example.mitul.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mitul.application.Adapter.BusAdapter;
import com.example.mitul.application.Adapter.StationAdapter;

import java.util.ArrayList;
import java.util.List;

public class BusActivity extends AppCompatActivity {

    DataHelper dataHelper;
    List<BusInfo> busData = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataHelper = new DataHelper(this);

        recyclerView = findViewById(R.id.recyclerViewBus);
        BusAdapter adapter = new BusAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        busData.addAll(dataHelper.getAllBus());

        adapter.changeData(busData);

    }
}
