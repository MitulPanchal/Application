package com.example.mitul.application;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class StationActivity extends AppCompatActivity {

    List<StationInfo> stationData = new ArrayList<StationInfo>();
    DataHelper dataHelper;
    LinearLayout stationContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);

        Button btnGetList = findViewById(R.id.btnGetData);
        stationContainer = findViewById(R.id.station_container);
        dataHelper = new DataHelper(getApplicationContext());
        try {
            dataHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnGetList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stationData = dataHelper.getAllStation();
                for (StationInfo stationInfo : stationData) {
                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.station_list, null);
                    TextView station = view.findViewById(R.id.textView2);

                    String[] allStation = new String[]{stationInfo.getStationName()};
                    station.setText(stationInfo.getStationName());
                    stationContainer.addView(view);
                }
            }
        });
    }
}
