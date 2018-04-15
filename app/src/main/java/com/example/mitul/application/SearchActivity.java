package com.example.mitul.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    List<SearchInfo> stationData = new ArrayList<>();
    public DataHelper dataHelper;
    String[] station ={"Bus Station","Udhna Darwaja","Majura Gate","Bhestan","Athwa Gate","Sachin","Katargam",
            "Ghajera Circle","Prime Arcade","Vesu","Bhagad","Chawk Bazar","Navsari Bazar","Nanpura","Bhatar","Vesu","V.R.Mall",
            "Piplod","AmbikaniKetan","Univercity Road","Sahara Darwaja","SVNIT","Railway Station"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final AutoCompleteTextView sourceStation = findViewById(R.id.editTextSource);
        final AutoCompleteTextView destinationStation= findViewById(R.id.editTextDestination);
        final Button search = findViewById(R.id.buttonSearch);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, station);
        sourceStation.setAdapter(adapter);
        destinationStation.setAdapter(adapter);

        dataHelper = new DataHelper(getApplicationContext());

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String source = sourceStation.getText().toString();
                String destination = destinationStation.getText().toString();

                if(TextUtils.isEmpty(source)){
                    Toast.makeText(SearchActivity.this,"Source Station Field empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(destination)){
                    Toast.makeText(SearchActivity.this, "Destination Field empty ", Toast.LENGTH_LONG).show();
                    return;
                }

                String _source_ = dataHelper.sourceStation(source);
                String _destination_ = dataHelper.destinationStation(destination);

                if(_source_.equals(source) && _destination_.equals(destination)) {
                    Toast.makeText(SearchActivity.this, "Route Found", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SearchActivity.this, RouteActivity.class);
                    intent.putExtra("source", source);
                    intent.putExtra("destination", destination);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SearchActivity.this, "Route not Found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
