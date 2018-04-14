package com.example.mitul.application;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class FeedActivity extends AppCompatActivity {

    public DataHelper dataHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final RatingBar ratingBar = findViewById(R.id.ratingBar);
        final TextView textView = findViewById(R.id.textViewFeedback);
        final EditText feedback = findViewById(R.id.editTextFeedback);
        final Button btnSubmit = (Button) findViewById(R.id.btnFeedSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedText = feedback.getText().toString();
                if(TextUtils.isEmpty(feedText)){
                    Toast.makeText(FeedActivity.this, "Feedback is Empty", Toast.LENGTH_LONG).show();
                    return;
                }

                float rating = ratingBar.getRating();
                if(rating == 0.0){
                    Toast.makeText(FeedActivity.this, "Rating Field is Empty.", Toast.LENGTH_LONG).show();
                    return;
                }

                ratingBar.setRating(0.0f);
                feedback.setText(null);
                Snackbar.make(v, "Feedback Submit Successfully. Thank You. ", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }
}
