package com.example.mitul.application;

import android.app.AlertDialog;
import android.app.Dialog;
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

import java.util.Random;

public class FareActivity extends AppCompatActivity {


    String[] station ={"Bus Station","Udhna Darwaja","Majura Gate","Bhestan","Athwa Gate","Sachin","Katargam",
            "Ghajera Circle","Prime Arcade","Vesu","Bhagad","Chawk Bazar","Navsari Bazar","Nanpura","Bhatar","Vesu","V.R.Mall",
            "Piplod","AmbikaniKetan","Univercity Road","Sahara Darwaja","SVNIT","Railway Station"};
    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare);

        final AutoCompleteTextView ss = findViewById(R.id.editTextS);
        final AutoCompleteTextView ds = findViewById(R.id.editTextD);
        Button button = findViewById(R.id.buttonCalculate);
        final TextView textView = (TextView) findViewById(R.id.textViewFare);
        final String temp = "Calculated Fare is: ";

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, station);
        ss.setAdapter(adapter);
        ds.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String source = ss.getText().toString();
                String destination = ds.getText().toString();

                if(TextUtils.isEmpty(source)){
                    Toast.makeText(FareActivity.this,"Source Station Field empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(destination)){
                    Toast.makeText(FareActivity.this, "Destination Field empty ", Toast.LENGTH_LONG).show();
                    return;
                }
                dataHelper = new DataHelper(getApplicationContext());
                String _source_ = null;
                _source_ = dataHelper.sourceStation(source);
                String _destination_ = null;
                _destination_ = dataHelper.destinationStation(destination);

                if(_source_.equals(source) && _destination_.equals(destination)) {
                    Toast.makeText(FareActivity.this, "Route Found", Toast.LENGTH_LONG).show();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(FareActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_fare, null);
                    TextView textView10 = (TextView) mView.findViewById(R.id.textView10);
                    TextView textView12 = (TextView) mView.findViewById(R.id.textView12);
                    TextView textView13 = (TextView) mView.findViewById(R.id.textView13);
                    textView10.setText(source);
                    textView12.setText(destination);
                    String fare = null;

                    Random rand = new Random();

                    int  n = rand.nextInt(25) + 10;

                    if(source.equals(destination)){
                        fare = "Total Amount is: 0";

                    }
                    else{
                         String amount =
                        fare = "Total Amount is: " +n;
                    }
                    textView13.setText(fare);
                    mBuilder.setView(mView);
                    AlertDialog dialog = mBuilder.create();
                    dialog.show();

                }
                else {
                    Toast.makeText(FareActivity.this, "Route not Found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
