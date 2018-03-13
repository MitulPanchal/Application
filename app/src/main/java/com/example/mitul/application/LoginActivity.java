package com.example.mitul.application;

import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mitul.application.api.client.MyClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    public DatabaseHelper databaseHelper;
    private EditText email;
    private ConstraintLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rootLayout = findViewById(R.id.rootLayoutLogin);

        email = findViewById(R.id.editTextEmail);
        final EditText password = findViewById(R.id.editTextPassword);
        final Button buttonLogin = findViewById(R.id.buttonSignIn);
        final TextView textViewSignUp = findViewById(R.id.textViewSignUp);

        databaseHelper  = new DatabaseHelper(LoginActivity.this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email = email.getText().toString();
                String _password = password.getText().toString();

                if (TextUtils.isEmpty(_email)){
                    Toast.makeText(LoginActivity.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(_password)){
                    Toast.makeText(LoginActivity.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }

                String _password_ = databaseHelper.searchPassword(_email);
                if(_password.equals(_password_)){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Email or Password Incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
        
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //TODO: Call this method from login button onclick
        verifyUser();
    }

    public void verifyUser(){
        String url = "https://localhost:56846/api/";    //Actual Url
        String testUrl = "https://randomuser.me/api/";  //Test Url

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(testUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyClient client = retrofit.create(MyClient.class);

        //TODO: Uncomment below sentence(Actual Call) and Comment/Remove Test Call

        //Call call = client.getPassword(email.getText().toString());  //Actual Call

        Call call = client.getTestData(); //Test Call

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("Response :", "yes");

                String dataString = response.body().toString();

                //TODO: Compare Password with dataString and return Result Success or failure below
                Log.e("Data From Url", dataString);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Snackbar.make(rootLayout, "Network Error", Snackbar.LENGTH_INDEFINITE)
                        .setAction("Try Again", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                verifyUser();
                            }
                        })
                        .setActionTextColor(Color.RED)
                        .show();
            }
        });
    }
}

