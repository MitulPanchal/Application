package com.example.mitul.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class TicketActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private ImageView qrView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Button button = findViewById(R.id.booking);
        qrView = findViewById(R.id.ticket_qrView);

        SharedPreferences sharedPreferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "-1");

        bitmap = encodeAsBitmap(username + "%"+System.currentTimeMillis()+"%"+"TICKET_NO=5");

        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrView.setVisibility(View.VISIBLE);
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();

        qrView.setImageBitmap(bitmap);
    }

    private Bitmap encodeAsBitmap(String str) {
        BitMatrix result;

        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300, null);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        int width = result.getWidth();
        Log.e("Width", String .valueOf(width));
        int height = result.getHeight();

        int pixels [] = new int[width*height];

        for (int y = 0; y < height; y++){
            int offset = y*height;
            for (int x = 0; x<width; x++){
                pixels [offset+x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
