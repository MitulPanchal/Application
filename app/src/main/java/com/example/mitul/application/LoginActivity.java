package com.example.mitul.application;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public DatabaseHelper databaseHelper;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.editTextEmail);
        final EditText password = findViewById(R.id.editTextPassword);
        final Button buttonLogin = findViewById(R.id.buttonSignIn);
        final TextView textViewSignUp = findViewById(R.id.textViewSignUp);

        sharedPreferences = getSharedPreferences("loginPref", Context.MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("username", _email);
                    editor.apply();

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
    }
}
