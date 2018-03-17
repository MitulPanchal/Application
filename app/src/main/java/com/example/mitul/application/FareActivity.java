package com.example.mitul.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FareActivity extends AppCompatActivity {

    DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fare);


        final EditText ss = findViewById(R.id.editTextS);
        final EditText ds = findViewById(R.id.editTextD);
        Button button = findViewById(R.id.buttonCalculate);
        final TextView textView1 = (TextView) findViewById(R.id.textViewFare);
        TextView textView2 = (TextView) findViewById(R.id.textViewFareCalc);
        final String temp = "Calculated Fare is: ";

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

                String _abc_ = dataHelper.sourceStation(source);
                String _def_ = dataHelper.destinationStation(destination);

                if(_abc_.equals(source) && _def_.equals(destination)) {
                    Toast.makeText(FareActivity.this, "Fare Calculated", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(FareActivity.this, "Route not Found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
