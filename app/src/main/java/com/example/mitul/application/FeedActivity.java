package com.example.mitul.application;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class FeedActivity extends AppCompatActivity {

    public DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final TextView textView = findViewById(R.id.textViewFeedback);
        final EditText feedback = findViewById(R.id.editTextFeedback);
        final Button btnSubmit = (Button) findViewById(R.id.btnFeedSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingBar.setRating(0.0f);
                feedback.setText(null);
                Snackbar.make(v, "Feedback Submit Successfully ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
